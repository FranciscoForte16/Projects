
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import java.io.*;

public class HandlerST extends Thread {
    BufferedReader in;
    PrintWriter out;
    Socket conexao ;

    String clientID;
 
    ArrayList<ServiceRegister> availableServices = new ArrayList<ServiceRegister>();
    
    SystemTicketing st = new SystemTicketing();


    public HandlerST(Socket conexao, ArrayList<ServiceRegister> availableServices) throws IOException{
        this.conexao = conexao;
        this.availableServices = availableServices;
      
        this.in = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
        this.out = new PrintWriter(conexao.getOutputStream(),true);
                
    }
    
    public void run(){
        try {    
            String request = in.readLine();
            String[] answer = request.split("[,]", 0);

        //registar servico
        if (answer[0].equals("registerService")){
            String answer2 = registarServico(answer);
            out.println(answer2);
            
        }

        //verificar hash
        if(answer.length == 3 && answer[2].equals("verificarHash")){
            String answer2 = verifyHash(answer);
            out.println(answer2);
            
        }
        
        
        out.flush();
		in.close();
		out.close();
	    conexao.close();

    } catch (IOException e) {
        System.out.println("Erro na execucao do servidor: " + e);
        System.exit(1);
    }
}




//---------------------------------------------------------------------------------------------------------------------------------//
        public String registarServico(String[] answer) {
            ServiceRegister service = null;
    
            if (answer[3].equals("Java RMI")) {
                service = new ServiceRegister(answer[1], answer[2], answer[3], answer[4], answer[5], answer[6]);
                return " Servico Registado com Sucesso ! ";

            } else if (answer[3].equals("Java Sockets")) {
                service = new ServiceRegister(answer[1], answer[2], answer[3], answer[4], answer[5],null);
                return " Servico Registado com Sucesso ! ";
            }else{
                return "tecnologia inexistente";
            }

            
        }
    
        // metodo para verificar se o Identificador Unico coincide com a Hash
        public String verifyHash(String[] args) {
            String identificadorUnico = args[0];
            String hash = args[2];
            if (identificadorUnico.equals(hash)) {
                return "true";
            } else
                return "false";
        }
    
        // Modificadores
        public void setservicesList(ArrayList<ServiceRegister> availableServices) {
            this.availableServices = availableServices;
        }
    
        // Seletores
        public ArrayList<ServiceRegister> getserviceList() {
            return new ArrayList<>(availableServices);
        }
    
        // verificar se existe um Servico
        public boolean serviceCheck (String iu) {
            if (iu.isEmpty()) {
                throw new NullPointerException("No iu detected");
            } else {
                for (ServiceRegister a : availableServices) {
                    if (a.getIdentificadorUnico().equals(iu)) { //--------------------> verificar
                        return true;
                    }
                }
                return false;
            }
        }
    
        // Retorna um servico pretendido dando a sua chave de registo
        public ServiceRegister getService(String iu) {
            for (ServiceRegister s : availableServices) {
                if (s.getIdentificadorUnico().equals(iu)) {
                    return s;
                }
            }
            return null;
        }
    
        // Método para receber o timestamp no momento em que o pedido é feito
        public String getTimeStamp() {
            TimeZone tz = TimeZone.getTimeZone("UTC");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            df.setTimeZone(tz);
            String nowAsISO = df.format(new Date());
    
            return nowAsISO;
        }


    public void sendMessage(String string){

        out.println(string);
        out.flush();

    }

    public void listenForMessage() throws IOException{
        String messagesReceived;

            messagesReceived = in.readLine();
            System.out.println(messagesReceived);  
    }
}


