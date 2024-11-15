// Inicialização do servidor RMI
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MainServer {
    public static void main(String[] args) {
        try {
            // Inicia o registro RMI
            Registry registry = LocateRegistry.createRegistry(1099);
            System.out.println("Registro RMI iniciado na porta 1099");

            // Inicia o Data Center Central
            CentralDataCenterImpl centralDC = new CentralDataCenterImpl();
            registry.rebind("CentralDC", centralDC);
            System.out.println("Servidor Grid Computing iniciado");
        } catch (Exception e) {
            System.err.println("Erro ao iniciar servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
