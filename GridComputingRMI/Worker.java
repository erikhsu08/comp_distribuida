// Implementação do worker (nível 3)
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class Worker extends UnicastRemoteObject implements PriceProcessor {
    private static final double MAX_PRICE_THRESHOLD = 75000.00;
    private String workerId;

    public Worker(String workerId) throws RemoteException {
        super();
        this.workerId = workerId;
    }

    @Override
    public List<Double> processPrices(List<Double> prices) throws RemoteException {
        List<Double> result = new ArrayList<>();
        for (Double price : prices) {
            if (price < MAX_PRICE_THRESHOLD) {
                result.add(price);
            }
        }
        System.out.println("Worker " + workerId + " processou " + prices.size() + " preços");
        return result;
    }
}
