import java.io.*;
import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Zmain {

    public static void main(String[] args) throws IOException, InterruptedException, NotBoundException {
        menu();
    }

    static Scanner scan = new Scanner(System.in);
    static ArrayList<String[]> servicesListSocket = new ArrayList<String[]>() ;
    static ArrayList<String[]> servicesListRMI = new ArrayList<String[]>() ;

    public static void menu() throws IOException, InterruptedException, NotBoundException {

        Client cliente = new Client();
        System.out.println("+ -  -  -  -  -  -  -  -  -  -  -  - +");
        System.out.println("|                                    |");
        System.out.println("|      Servico de Identificacao      |");
		System.out.println("|                                    |");
        System.out.println("+ -  -  -  -  -  -  -  -  -  -  -  - +\n"); 
        System.out.print("\tPor favor, insira o seu numero de identificacao: ");
        String[] id = new String[2];
        id[0] = scan.nextLine();
        id[1] = "1";
        System.out.println(cliente.criarConexao(id));
        menuTicketing();
    }

    static String IPAdressST_Scan;
    static String Port_Scan;
    static String Hash_Scan;
    static String ID_Scan;

    public static void menuTicketing() throws IOException, InterruptedException, NotBoundException {
        System.out.println("\nInsira a Porta do Servico de Ticketing: ");
        Port_Scan = scan.nextLine();
        System.out.println("Insira o IP do Servico de Ticketing: ");
        IPAdressST_Scan = scan.nextLine();

        if (verificarHash()) {
            menuSTconnected();
        } else {
            hashInvalida();
        }
    }
	

    public static void menuSTconnected() throws IOException, InterruptedException, NotBoundException {
        System.out.println("\n+ -  -  -  -  -  -  -  -  -  -  -  - +");
        System.out.println("|                                    |");
        System.out.println("|        Servico de Ticketing        |");
		System.out.println("|                                    |");
        System.out.println("+ -  -  -  -  -  -  -  -  -  -  -  - +\n"); 


        System.out.println("\t+ -  -  -  +");
        System.out.println("\t|   MENU   |");
        System.out.println("\t+ -  -  -  +");
        System.out.println("\t\t(1) Registar Servico");
        System.out.println("\t\t(2) Aceder Servico Externo");
        System.out.println("\t\t(3) Visualizar lista de Servicos");
        System.out.println("\t\t(4) Sair \n");
        System.out.print("Insira uma opcao: ");
        String decision = scan.nextLine();

        switch (decision) {
            case "1":
                registarClient();
                break;
            case "2":
                connectService();
                break;
            case "3":
                printServices();
                break;
            case "4":
                System.exit(0);
                break;
            default:
                System.out.println("\nOpcao Inválida!\n");
                menuSTconnected();
        }
    }


    public static void registarClient() throws IOException, InterruptedException, NotBoundException {
        String chave = "";
        String descricao;
        String tecnologia = "";
        String ip;
        String nomeRMI = "";
        String porta;

        System.out.println("\n\t+ -  -  -  -  -  -  - +");
        System.out.println("\t|  Registar Servico   |");
        System.out.println("\t+ -  -  -  -  -  -  - +");

        //DESCRIÇÃO
        System.out.print("\tInsira a Descricao do Servico: ");
        descricao = scan.nextLine();

        //TECNOLOGIA
        System.out.println("\tIntroduza a Tecnologia que deseja utilizar:");
        System.out.println("\t\t(1) Java Sockets");
        System.out.println("\t\t(2) Java RMI\n");
        System.out.print("Insira uma opcao: ");
        String number = scan.nextLine();
        if (number.equals("1")) {
            tecnologia = "Java Sockets";
        } else if (number.equals("2")) {
            tecnologia = "Java RMI";
        } else {
            System.out.println("Número Invalido");
            registarClient();
        }
        //IP
        System.out.print("\tInsira o IP: ");
        ip = scan.nextLine();

        //PORTA
        System.out.print("\tInsira a Porta: ");
        porta = scan.nextLine();

        //NO CASO DE SER RMI
        if (number.equals("2")) {
            System.out.print("\tInsira o nome: ");
            nomeRMI = scan.nextLine();
        }

        // MAKE KEY AND CREATE OBJECT SERVICE

        if(tecnologia.equals("Java Sockets")){
        chave = ip + porta;
        }else if(tecnologia.equals("Java RMI")){
        chave = ip + porta + nomeRMI;
        }

        //ENVIAR PARA O CLIENTE
        String[] id = new String[6];
        id[0] = "registerService" + "," + chave + "," + descricao + "," + tecnologia + "," + ip + "," + porta + ","+ nomeRMI;
        id[1] = "2";
        id[2] = "";
        id[3] = tecnologia;
        id[4] = IPAdressST_Scan;
        id[5] = Port_Scan;

        String[] id2 = new String[6];
        id2[0] = chave;
        id2[1] = descricao;
        id2[2] = tecnologia;
        id2[3] = ip;
        id2[4] = porta;
        id2[5] = nomeRMI;

        //CRIAR CONECÇÃO CLIENTE
        //System.out.println(id[0]);
        Client cliente1 = new Client();

        //RESPOSTA RECEBIDA PELO SERVIDOR
        cliente1.registarServico(id); //-> erro esta aqui //já regista, só falta mostrar no consultar
        if(tecnologia.equals("Java Sockets")){
            servicesListSocket.add(id2);
            }else if(tecnologia.equals("Java RMI")){
                servicesListRMI.add(id2);
            }
        System.out.println("Servico registado: " + descricao + " , " + tecnologia + " , " + ip + " , " + porta + " , "+ nomeRMI);
		System.out.println("\t+----------------------------------+");
	    System.out.println("\t|  Servico registado com sucesso!  |");
	    System.out.println("\t+----------------------------------+"); 
        Thread.sleep(5000);
		menuSTconnected();
		
    }
        



    public static void listaSockets() throws IOException {
        if(servicesListSocket.size() != 0){
        System.out.println("\t SERVICOS SOCKET \n"); 
       for(int i = 0; i < servicesListSocket.size(); i++){
          String[] service = servicesListSocket.get(i);
        System.out.println("Servico no: " + (i+1));
          System.out.println("\t"+service[0]);
          System.out.println("\t"+service[1]);
          System.out.println("\t"+service[2]);
          System.out.println("\t"+service[3]);
          System.out.println("\t"+service[4]);
          System.out.println("\t"+service[5]);
          System.out.println("/------------/");
       }}else{
        System.out.println("NAO EXISTEM SERVICOS SOCKETS DE MOMENTO");
    }
    }
    

    public static void listaRMI() throws IOException {
        if(servicesListRMI.size() != 0){
            System.out.println("\t SERVICOS RMI \n"); 
        for(int i = 0; i < servicesListRMI.size(); i++){
            String[] service = servicesListRMI.get(i);
        System.out.println("Servico no: " + (i+1));
          System.out.println("\t"+service[0]);
          System.out.println("\t"+service[1]);
          System.out.println("\t"+service[2]);
          System.out.println("\t"+service[3]);
          System.out.println("\t"+service[4]);
          System.out.println("\t"+service[5]);
          System.out.println("/------------/");
       }}else{
           System.out.println("NAO EXISTEM SERVICOS RMI DE MOMENTO");
       }
        
    }
        
        
        

    public static void connectService() throws IOException, InterruptedException, NotBoundException {
        String metodo = "";
       System.out.println("\n\tTipo de comunicacao que pretende estabelecer: ");
       System.out.println("\t\t(1) Java Sockets");
       System.out.println("\t\t(2) Java RMI\n");
       String type = scan.nextLine();
       System.out.print("\tPor favor, insira o ip que pretende aceder: ");
       String ip = scan.nextLine();
       System.out.print("\tPor favor, insira o porto que pretende aceder: ");
       String port = scan.nextLine();
       if (type.equals("1")) {
           System.out.print("\tMetodo: ");
           metodo = scan.nextLine();
       }

        Client cliente1 = new Client();

        if (type.equals("1")) {
            cliente1.connectServico_Socket(ip, metodo, port);
        } else if (type.equals("2")) {
            System.out.println("Insira o nome do serviço a que pretende aceder");
            String nome = scan.nextLine();
            cliente1.connectServico_RMI(nome, ip, port);
        } else {
            System.out.println("Invalido");
        }

        System.out.println("(1) Voltar ");
        System.out.println("(2) Sair ");
        String option3 = scan.nextLine();

        if (option3.equals("1")) {
            menuSTconnected();
        } else if (option3.equals("2")) {
            System.exit(0);
        } else {
            System.out.println("\nINVALIDO! \n");
            menuTicketing();
        }
    }

    public static boolean verificarHash() throws IOException, InterruptedException, NotBoundException {
        Client clienteVerify = new Client();

        System.out.print("Insira o seu numero de Identificacao: ");
        ID_Scan = scan.nextLine();
        System.out.print("Insira a sua Hash de Identificacao: ");
        Hash_Scan = scan.nextLine();
		
        String[] informacao = new String[6];
        informacao[0] = ID_Scan;
        informacao[1] = "2";
        informacao[2] = Hash_Scan;
        informacao[3] = "verificarHash";
        informacao[4] = IPAdressST_Scan;
        informacao[5] = Port_Scan;

        String cc_hash = Hash.getHash(ID_Scan);
        Boolean True_False;

        if (cc_hash.equals(Hash_Scan)) {
            clienteVerify.criarConexao(informacao);
            True_False = true;
        } else {
            hashInvalida();
            True_False = false;
        }
        return True_False;
    }

    public static void hashInvalida() throws IOException, InterruptedException, NotBoundException {
		System.out.println("\t+---------------------------------------------------------+");
        System.out.println("\t| Dados Invalidos! Por favor introduza os dados novamente!  |");
		System.out.println("\t+---------------------------------------------------------+");
		menuTicketing(); 
    }

    public static void printServices() throws IOException, InterruptedException, NotBoundException{
        System.out.println("");
        listaSockets();
        System.out.println("");
        listaRMI();
        menuSTconnected();



    }

}