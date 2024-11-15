// Cliente que utiliza o grid computing
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class MainClient {
    public static void main(String[] args) {
        try {
            // Conecta ao registro RMI
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            
            // Obtém referência para o Data Center Central
            PriceProcessor centralDC = (PriceProcessor) registry.lookup("CentralDC");
            
            // Gera dados de teste
            List<Double> carPrices = new ArrayList<>();
            for (int i = 0; i < 1000; i++) {
                carPrices.add(Math.random() * 100000);
            }

            // Processa os dados usando o grid
            List<Double> results = centralDC.processPrices(carPrices);

            // Exibe os resultados
            System.out.println("\nResultados finais:");
            System.out.println("Total de preços abaixo de R$ 75.000: " + results.size());
            System.out.println("\nPrimeiros 10 preços encontrados:");
            results.stream()
                  .limit(10)
                  .forEach(price -> System.out.println("R$ " + String.format("%.2f", price)));

        } catch (Exception e) {
            System.err.println("Erro no cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }
}