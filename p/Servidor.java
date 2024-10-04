import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Servidor {
    public static void main(String[] args) {
        try {
            PrimosGemeos primos = new PrimosGemeosImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("PrimosGemeos", primos);
            System.out.println("Servidor RMI está pronto.");
        } catch (Exception e) {
            System.err.println("Erro no servidor: " + e.toString());
            e.printStackTrace();
        }
    }
}
