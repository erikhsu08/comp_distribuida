//  Implementação do data center regional (nível 2)
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class RegionalDataCenter extends UnicastRemoteObject implements PriceProcessor {
    private static final int NUM_WORKERS = 2;
    private String centerId;
    private List<PriceProcessor> workers;

    public RegionalDataCenter(String centerId) throws RemoteException {
        super();
        this.centerId = centerId;
        this.workers = new ArrayList<>();
        initializeWorkers();
    }

    private void initializeWorkers() throws RemoteException {
        Registry registry = LocateRegistry.getRegistry("localhost");
        for (int i = 0; i < NUM_WORKERS; i++) {
            String workerId = centerId + "-W" + i;
            Worker worker = new Worker(workerId);
            registry.rebind(workerId, worker);
            workers.add(worker);
            System.out.println("Worker " + workerId + " registrado");
        }
    }

    @Override
    public List<Double> processPrices(List<Double> prices) throws RemoteException {
        System.out.println("Data Center Regional " + centerId + " iniciando processamento");
        
        int pricesPerWorker = prices.size() / NUM_WORKERS;
        List<List<Double>> results = new ArrayList<>();

        for (int i = 0; i < NUM_WORKERS; i++) {
            int startIndex = i * pricesPerWorker;
            int endIndex = (i == NUM_WORKERS - 1) ? prices.size() : (i + 1) * pricesPerWorker;
            List<Double> workerPrices = prices.subList(startIndex, endIndex);
            
            results.add(workers.get(i).processPrices(workerPrices));
        }

        List<Double> regionalResults = new ArrayList<>();
        for (List<Double> result : results) {
            regionalResults.addAll(result);
        }

        System.out.println("Data Center Regional " + centerId + " finalizou processamento");
        return regionalResults;
    }
}