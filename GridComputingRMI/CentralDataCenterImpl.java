// Implementação do data center central (nível 1)
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class CentralDataCenterImpl extends UnicastRemoteObject implements PriceProcessor {
    private static final int NUM_REGIONAL_CENTERS = 2;
    private List<PriceProcessor> regionalCenters;

    public CentralDataCenterImpl() throws RemoteException {
        super();
        this.regionalCenters = new ArrayList<>();
        initializeRegionalCenters();
    }

    private void initializeRegionalCenters() throws RemoteException {
        Registry registry = LocateRegistry.getRegistry("localhost");
        for (int i = 0; i < NUM_REGIONAL_CENTERS; i++) {
            String centerId = "R" + i;
            RegionalDataCenter regional = new RegionalDataCenter(centerId);
            registry.rebind(centerId, regional);
            regionalCenters.add(regional);
            System.out.println("Data Center Regional " + centerId + " registrado");
        }
    }

    @Override
    public List<Double> processPrices(List<Double> prices) throws RemoteException {
        System.out.println("Data Center Central iniciando distribuição dos dados");
        
        int pricesPerRegion = prices.size() / NUM_REGIONAL_CENTERS;
        List<List<Double>> results = new ArrayList<>();

        for (int i = 0; i < NUM_REGIONAL_CENTERS; i++) {
            int startIndex = i * pricesPerRegion;
            int endIndex = (i == NUM_REGIONAL_CENTERS - 1) ? prices.size() : (i + 1) * pricesPerRegion;
            List<Double> regionalPrices = prices.subList(startIndex, endIndex);
            
            results.add(regionalCenters.get(i).processPrices(regionalPrices));
        }

        List<Double> finalResults = new ArrayList<>();
        for (List<Double> result : results) {
            finalResults.addAll(result);
        }

        System.out.println("Data Center Central finalizou processamento");
        return finalResults;
    }
}