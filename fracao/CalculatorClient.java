import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CalculatorClient {

    public static void main(String[] args) {
        try {
            // Obter o registro
            Registry registry = LocateRegistry.getRegistry("localhost");

            // Procurar o objeto Calculator
            Calculator stub = (Calculator) registry.lookup("Calculator");

            // Criar duas frações
            Fracao a = new Fracao(1, 2);  // 1/2
            Fracao b = new Fracao(3, 4);  // 3/4

            // Executar operações remotas
            Fracao sum = stub.add(a, b);
            Fracao difference = stub.subtract(a, b);
            Fracao product = stub.multiply(a, b);
            Fracao quotient = stub.divide(a, b);

            System.out.println("Adição: " + sum);
            System.out.println("Subtração: " + difference);
            System.out.println("Multiplicação: " + product);
            System.out.println("Divisão: " + quotient);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}