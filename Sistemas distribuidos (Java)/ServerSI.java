import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSI {
    static int DEFAULT_PORT=2000;
    static int contofclients = 0;
    ServerSocket server;

    public static void main (String[] args) throws Exception{
        ServerSI server = new ServerSI();
        //primeira coisa a fazer é criar um socket para se estabelecer ligações
        System.out.println("\n\nA iniciar o Servico de Identificacao");
        Thread.sleep(500);
        System.out.println("\t1...");
        Thread.sleep(500);
        System.out.println("\t2...");
        Thread.sleep(500);
        System.out.println("\t3...");
        Thread.sleep(1000);
        server.startServer();
    }

    public void startServer() throws IOException{
        try (ServerSocket server = new ServerSocket(DEFAULT_PORT)) {
            System.out.println("\n* * * Servico pronto a utilizar * * *");
            while(true){
                Socket socket = server.accept();
                System.out.println("\t\nConexao aceite no endereco " + socket.getInetAddress().getHostAddress() + " e na porta " + socket.getPort() + "!");
                HandlerSI handler = new HandlerSI(socket);      
                handler.start();
            }
        }
   }
    

}
