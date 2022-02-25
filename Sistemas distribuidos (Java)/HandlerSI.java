import java.net.*;
import java.io.*;

public class HandlerSI extends Thread{
    BufferedReader in;
    PrintWriter out;
    Socket conexao;
    String clientID;
    String hash;
    SystemID si = new SystemID();
      
    ServerST servidorSt = new ServerST();

    public HandlerSI(Socket conexao) throws IOException{
            this.conexao = conexao;          
            this.in = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            this.out = new PrintWriter(conexao.getOutputStream());  
    }

    public void run(){
        try{
            String msg = in.readLine();
            this.clientID = msg;
            si.getIP(clientID);
            String hash = Hash.getHash(msg);
            System.out.println(clientID);
            sendMessage("\t| Porta do Servico de Ticketing: " + servidorSt.getPort() + "\t| IP do Servico de Ticketing: " + servidorSt.getIP() + "\t| Hash: " + hash) ;
            disconnectClientHandler();

        }catch (IOException e) {
			System.out.println("Erro na execucao do servidor: " + e);
			System.exit(1);
		}
    }

    public void sendMessage(String messageToServer){
        out.println(messageToServer);
    }

    public void listenForMessage() throws IOException{
        String messagesReceived;
        messagesReceived = in.readLine();
        System.out.println(messagesReceived);       
    }

    public void disconnectClientHandler() throws IOException{
        out.flush();
        in.close();
		out.close();
		conexao.close();     
    }
}
