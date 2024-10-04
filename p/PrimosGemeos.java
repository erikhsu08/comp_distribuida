import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PrimosGemeos extends Remote {
    boolean saoPrimosGemeos(int n1, int n2) throws RemoteException;
}
