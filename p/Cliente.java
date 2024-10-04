import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Cliente {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            PrimosGemeos primos = (PrimosGemeos) registry.lookup("PrimosGemeos");

            int n1 = 11;
            int n2 = 13;

            boolean resultado = primos.saoPrimosGemeos(n1, n2);
            System.out.println("Os números " + n1 + " e " + n2 + " são primos gêmeos? " + resultado);
        } catch (Exception e) {
            System.err.println("Erro no cliente: " + e.toString());
            e.printStackTrace();
        }
    }
}
