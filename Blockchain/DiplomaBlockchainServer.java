import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DiplomaBlockchainServer {
    private static final int PORT = 8080;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Servidor de blockchain de diplomas iniciado. Aguardando conexões...");

            ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

            while (true) {
                Socket socket = serverSocket.accept();
                executorService.submit(new DiplomaBlockchainHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}