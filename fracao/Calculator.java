import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Calculator extends Remote {
    // Métodos para operações com frações
    public Fracao add(Fracao a, Fracao b) throws RemoteException;
    public Fracao subtract(Fracao a, Fracao b) throws RemoteException;
    public Fracao multiply(Fracao a, Fracao b) throws RemoteException;
    public Fracao divide(Fracao a, Fracao b) throws RemoteException;
}
