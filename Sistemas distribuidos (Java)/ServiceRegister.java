

public class ServiceRegister {
    
    String description;
    String typeTech;
    String IP;
    String port;
    String name;
    String identificadorUnico;
    String timeStamp;
    String decision;
    int contadorTable = 1;

    public ServiceRegister(String iu, String description, String typeTech, String IP, String port, String name){
        this.description = description;
        this.typeTech = typeTech ;
        this.IP = IP;
        this.port = port;
        this.name = name;
        this.identificadorUnico = iu;
    }


//agora é definir os seletores para podermos obter dados especificos de cada serviço
public String getDescription(){
    return description;
}

public String getTech (){
return typeTech;
}

public String getIp (){
return IP;
}

public String getPort (){
return port;
}

public String getName (){
return name;
}

public String getIdentificadorUnico (){
return identificadorUnico;
}



//agora é definir os modificadores para podermos obter alterar especificos de cada serviço
public void setDescription(String description){
this.description = description;
}
 
public void setTech (String tech){
this.typeTech = tech;
}

public void setIp (String ip){
this.IP = ip;
}

public void setPort (String port){
this.port = port;
}

public void setName (String name){
this.name = name;
}

public void setIdentificadorUnico (String iu){
this.identificadorUnico = iu;
}

public void setIUSocket(String ip, String port) {
    this.identificadorUnico = ip+port;
}

public void setIURMI(String ip, String port, String name) {
    this.identificadorUnico = ip+port+name;
}
}