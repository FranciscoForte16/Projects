import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class SystemTicketing {
	
	ArrayList<ServiceRegister> availableServices;
    
    public String registarServico(String[] answer, ArrayList<ServiceRegister> availableServices) {
		this.availableServices = availableServices;
		ServiceRegister service = null;

		if (answer[2].equals("Java RMI")) {
			service = new ServiceRegister(answer[1], answer[2], answer[3], answer[4], answer[5], answer[6]);
		} else if (answer[3].equals("Java Sockets")) {
			service = new ServiceRegister(answer[1], answer[2], answer[3], answer[4], answer[5],null);
		}
		if (!serviceCheck(service.getIdentificadorUnico())) {
			availableServices.add(service);
			getserviceList();
			return " Servico Registado com Sucesso ! ";

		} else {
			getService(service.getIdentificadorUnico()).setDescription(service.getDescription());
			getserviceList();
			return " Servico Atualizado com Sucesso ! ";
		}
	}

	// metodo para verificar se o Identificador Unico coincide com a Hash
	public String verifyHash(String[] args) {
		String identificadorUnico = args[0];
		String hash = args[2];
		if (identificadorUnico.equals(hash)) {
			return "false";
		} else
			return "true";
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
	public boolean serviceCheck (String registerKey) {
		if (registerKey.isEmpty()) {
			throw new NullPointerException("No key detected");
		} else {
			for (ServiceRegister a : availableServices) {
				if (a.getIdentificadorUnico().equals(registerKey)) { //--------------------> verificar
					return true;
				}
			}
			return false;
		}
	}

	// Retorna um servico pretendido dando a sua chave de registo
	public ServiceRegister getService(String registerKey) {
		for (ServiceRegister s : availableServices) {
			if (s.getIdentificadorUnico().equals(registerKey)) {
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
}

    
        
    
    
    
    
    

    

