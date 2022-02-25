
import java.net.*;
import java.io.*;
import java.util.*;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.time.Instant;

public class Client {
    static final int DEFAULT_PORT_SI = 2000;
    static final int DEFAULT_PORT_ST = 0;

    static String servidorAtual = "";
   // static String DEFAULT_HOST_SERVICO_SI = "172.24.16.1";
    static String DEFAULT_HOST_SERVICO_SI = "127.0.0.1";
    static String DEFAULT_HOST_SERVICO_ST;


    PrintWriter out;
    BufferedReader in;
    StringTokenizer st;

    public static void main(String[] args) throws IOException {

    }

    //CRIAR CONEXAO
    public String criarConexao(String[] args) throws IOException {
        int port = 0;
        String servidor = "";
        String msg = "";

        //TER ACESSO AO SERVICO DE IDENTIFICACAO
        if (args[1] == "1") {
            port = DEFAULT_PORT_SI;
            servidorAtual = "SERVICO IDENTIFICACAO";
            servidor = DEFAULT_HOST_SERVICO_SI;

        //TER ACESSO AO SERVICO DE TICKETING
        } else if (args[1] == "2") {
            DEFAULT_HOST_SERVICO_ST = args[4];
            port = Integer.parseInt(args[5]);
            servidorAtual = "SERVICO TICKETING";
            servidor = DEFAULT_HOST_SERVICO_ST;
        }
                        //verificação
                        if (args.length != 2 && args[1] == "1" || args.length != 6 && args[1] == "2") {
                            System.out.println("Erro: E possivel que se tenha enganado em algum campo");
                            System.exit(-1);
                        }


        //CRIAR CONEXAO COM SOCKET
        InetAddress serverAddress = InetAddress.getByName(servidor);
        Socket ligacao = null;
        ligacao = new Socket(serverAddress, port);

        try {
            in = new BufferedReader(new InputStreamReader(ligacao.getInputStream()));
            out = new PrintWriter(ligacao.getOutputStream(), true);

            //DIZER QUE PARAMETROS PASSAR
            if (args[1] == "2" && args[3].equals("verificarHash")) {
                out.println(args[0] + "," + args[2] + "," + args[3]);

            } else if (args[1] == "2" && args[3].equals("getServicesSocket") || args[1] == "2" && args[3].equals("getServicesRMI")) {
                sendMessage(args[3]);
                
            } else {
                sendMessage(args[0]);
            }



        //RETORNAR LISTA DE RESPOSTAS
        if (args[1] == "2" && args[3].equals("getServicesSocket") || args[1] == "2" && args[3].equals("getServicesRMI")) {
            
                msg =  in.readLine();
                
            } else{
                msg = in.readLine();
                System.out.println(msg);
            }
            ligacao.close();
                       
			} catch (IOException e) {
            	System.out.print("ERRO" + e);
            	System.exit(1);
				}
				return msg;
		}


    //VERIFICAR HASH
   	public boolean verificarHash(String[] args) throws IOException {
        String sucesso = criarConexao(args);
        if (sucesso.equals("true")) {
           	return true;
       	} else
           	return false;
	}
	
		
    //REGISTAR SERVICO
    public String registarServico(String[] args) throws IOException {
       	String sucesso;
       	sucesso = criarConexao(args);
       	return sucesso;
   	}
	
	
	//CONSULTAR RMI
	public void consultarRMI(String[] args) throws IOException {
		String answer = criarConexao(args);
            System.out.println(answer);
        
	}
		
	
	//SERVICOS DISPONIVEIS
    public void consultarSockets(String[] args) throws IOException {
        String answer = criarConexao(args);
            System.out.println(answer);
        

	}
	

    //CRIAR CONEXAO COM O SERVIDOR RMI
    public void criarConexao_RMI(String nome, String ip, String port) throws AccessException, RemoteException, NotBoundException {
        String NOME_SERVICO = "";
        NOME_SERVICO = nome;

 
            ServicoInterface services = (ServicoInterface) LocateRegistry.getRegistry(ip).lookup(NOME_SERVICO);
            Instant tsp = Instant.parse(port);
            Float message = services.getTemp(tsp);
            System.out.println("Resposta: " + message);
        
    }
	
    //CONECTAR AO SERVICO RMI
    public void connectServico_RMI(String nome, String ip, String port) throws AccessException, RemoteException, NotBoundException {
        System.getProperties().put("java.security.policy", "./client.policy");
        criarConexao_RMI(nome, ip, port);
    }
	


    //CONECTAR AO SERVICO SOCKET
    public void connectServico_Socket(String ip, String metodo, String port) throws IOException {
        criarConexao_Socket(ip, metodo, port);
    }

    //CRIAR CONEXAO COM O SERVIDOR SOCKET
    public void criarConexao_Socket(String ip, String metodo, String port) throws IOException {
        String servidor = "";
        int porto = Integer.parseInt(port);       
        servidor = ip;

        InetAddress serverAddress = InetAddress.getByName(servidor);
        Socket ligacao = null;
        ligacao = new Socket(serverAddress, porto);


        try {
            in = new BufferedReader(new InputStreamReader(ligacao.getInputStream()));
            out = new PrintWriter(ligacao.getOutputStream(), true);
            out.println("getHumidity");
            
            do {
                System.out.println(in.readLine());

            } while (hasNext());
            
            ligacao.close();

        } catch (IOException e) {
            System.out.print("ERRO" + e);
            System.exit(1);
        }
    }

    


    public void sendMessage(String messageToServer){
        out.println(messageToServer);
    }	

    public void printServices(String[] pedido) throws IOException{
        if(pedido[0].equals("getServicesSocket")){
            criarConexao(pedido);
        }else if(pedido[0].equals("getServicesRMI")){
            criarConexao(pedido);
        }else{
            System.out.println("metodo errado");
        }
    }


    
	//----------------------------------------------
    // Verificar se há algo mais no buffer
    public boolean hasNext() {
        if (st != null && st.hasMoreTokens()) {
            return true;
        }
        String tmp;
        try {
            in.mark(1000);
            tmp = in.readLine();
            if (tmp == null) {
                return false;
            }
            in.reset();
        } catch (IOException e) {
            return false;
        }
        return true;
    }
	//----------------------------------------------
	
}
