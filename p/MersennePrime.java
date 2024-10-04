import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MersennePrime extends Remote {
    boolean ehPrimoM(int n) throws RemoteException;
}
