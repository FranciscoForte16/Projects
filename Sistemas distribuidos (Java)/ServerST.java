import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class ServerST {

    static int DEFAULT_PORT = 2005;
    //String DEFAULT_IP = "172.24.16.1";
    String DEFAULT_IP = "127.0.0.1";
    
    static ArrayList<ServiceRegister> availableServices;

    public static void main (String[] args) throws Exception{
        
        //primeira coisa a fazer é criar um socket para se estabelecer ligações
        System.out.println("\n\nA iniciar o Servico de Ticketing");
        Thread.sleep(500);
        System.out.println("\t1...");
        Thread.sleep(500);
        System.out.println("\t2...");
        Thread.sleep(500);
        System.out.println("\t3...");
        Thread.sleep(1000);
        startServer();
    }

    public static void startServer() throws IOException{
            try (ServerSocket server = new ServerSocket(DEFAULT_PORT)) {
                System.out.println("\n* * * Servico pronto a utilizar * * *");

                while(true){
                    Socket socket = server.accept();
                    System.out.println("\t\nConexao aceite no endereco " + socket.getInetAddress().getHostAddress() + " e na porta " + socket.getPort() + "!");
                    HandlerST handler = new HandlerST(socket, availableServices);      
                    handler.start();
                }
            }
    }


    public int getPort(){
        return DEFAULT_PORT;
    }

    public String getIP(){
        return DEFAULT_IP;
    }
}



        
        
    

