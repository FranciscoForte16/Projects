
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.Instant;

//RMI
public interface ServicesInterface extends Remote {
	 public Float getTemp(Instant tsp) throws RemoteException;

}