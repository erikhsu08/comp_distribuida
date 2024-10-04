import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServer {
    public static void main(String[] args) {
        try {
            MersennePrime service = new MersennePrimeImpl();
            Registry registry = LocateRegistry.createRegistry(1098);
            registry.rebind("MersennePrime", service);
            System.out.println("Servidor RMI pronto e aguardando chamadas...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
