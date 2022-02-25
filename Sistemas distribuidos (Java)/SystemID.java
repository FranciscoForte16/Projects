import java.util.*;

public class SystemID {
    
    private static Hashtable<String, IP> sistemaIdentificao = new Hashtable<String, IP>();
    private static int contador = 0;
    String identificadorUnico;

    public Vector<String> getIP(String IdentificadorUnico){
        contador = contador + 1;
        long currentTime = new Date().getTime();
        this.identificadorUnico = IdentificadorUnico;

    //vamos procurar se existe o IP pretendido
       synchronized(this){
       if (sistemaIdentificao.containsKey(IdentificadorUnico)){
                IP newIP = sistemaIdentificao.get(IdentificadorUnico);
                System.out.println("Client já registado com o IP: "+ newIP);
                newIP.setLastSeen(currentTime);
            } else {
                IP newIP = new IP(IdentificadorUnico, currentTime);
                sistemaIdentificao.put(IdentificadorUnico, newIP);
                System.out.println("O Client " + IdentificadorUnico + " foi registado com IP: " + newIP);
            }
        }
        return getIPList();
    }

    private Vector<String> getIPList(){
		Vector<String> result = new Vector<String>();
		for (Enumeration<IP> e = sistemaIdentificao.elements(); e.hasMoreElements(); ) {
			IP element = e.nextElement();
			if (!element.timeOutPassed(180*1000)) {
				result.add(element.getIP());
			}
		}
		return result;
	}
}

class IP {
	private String ip;
	private long lastSeen;

	public IP(String ip, long lastSeen) {
		this.ip = ip;
		this.lastSeen = lastSeen;
	}

	public String getIP () {
		return this.ip;
	}

	public void setLastSeen(long time){
		this.lastSeen = time;
	}

	public boolean timeOutPassed(int timeout){
		boolean result = false;
		long timePassedSinceLastSeen = new Date().getTime() - this.lastSeen;
		if (timePassedSinceLastSeen >= timeout)
			result = true;
		return result;
	}
}


