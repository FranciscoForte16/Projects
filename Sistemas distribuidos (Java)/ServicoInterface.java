
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.Instant;

public interface ServicoInterface extends Remote {
    public Float getTemp(Instant temperatura) throws RemoteException;
}
