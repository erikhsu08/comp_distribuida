import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PrimosGemeosImpl extends UnicastRemoteObject implements PrimosGemeos {
    protected PrimosGemeosImpl() throws RemoteException {
        super();
    }

    @Override
    public boolean saoPrimosGemeos(int n1, int n2) throws RemoteException {
        return (Math.abs(n1 - n2) == 2) && (isPrimo(n1) && isPrimo(n2));
    }

    private boolean isPrimo(int n) {
        if (n <= 1) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }
}
