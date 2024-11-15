// Interface remota
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface PriceProcessor extends Remote {
    List<Double> processPrices(List<Double> prices) throws RemoteException;
}