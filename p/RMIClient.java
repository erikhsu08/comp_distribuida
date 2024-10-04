import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1098);
            MersennePrime service = (MersennePrime) registry.lookup("MersennePrime");

            // Testando o método
            for (int i = 0; i <= 31; i++) { // Testando números de Mersenne para n=0 até n=31
                boolean isPrime = service.ehPrimoM(i);
                System.out.println("M" + i + " eh primo: " + isPrime);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
