import java.net.*;
import ServiceHumidityPublic.GetServicesRequestHandler;
import ServiceHumidityPublic.Sources;

import java.io.*;

public class ServiceHumidityServer {
	static int DEFAULT_PORT=2009;
	
	public static void main(String[] args) {
		int port=DEFAULT_PORT;
		Sources source = new Sources();
		source.loadData();
		ServerSocket servidor = null; 
	
		try	{ 
			servidor = new ServerSocket(DEFAULT_PORT);
	        System.out.println("\n\nA iniciar o Servico de Humidade");
	        Thread.sleep(500);
	        System.out.println("\t1...");
	        Thread.sleep(500);
	        System.out.println("\t2...");
	        Thread.sleep(500);
	        System.out.println("\t3...");
	        Thread.sleep(1000);
			System.out.println("\n* * * Servico pronto a utilizar * * *");
			
		} catch (Exception e) { 
			System.err.println("Erro ao criar socket servidor!");
			e.printStackTrace();
			System.exit(-1);
		}
				
		while(true) {
			try {
				Socket ligacao = servidor.accept();
				GetServicesRequestHandler t = new GetServicesRequestHandler(ligacao, source);
				t.start();
			} catch (IOException e) {
				System.out.println("Erro na execucao do servidor: "+e);
				System.exit(1);
			}
		}
	}
}
