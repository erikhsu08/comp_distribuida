import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class DiplomaBlockchainHandler implements Runnable {
    private final Socket socket;

    public DiplomaBlockchainHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            // Recebe a solicitação do cliente
            String request = (String) in.readObject();
            if (request.equals("GET_BLOCKCHAIN")) {
                // Envia os blocos da blockchain de diplomas
                enviarBlockchain(out);

                // Verifica se a blockchain é válida
                if (Blockchain.ehBlockchainValida()) {
                    System.out.println("Blockchain de diplomas válida!");
                } else {
                    System.out.println("Blockchain de diplomas inválida!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void enviarBlockchain(ObjectOutputStream out) throws IOException {
        for (Bloco bloco : Blockchain.blockchain) {
            out.writeObject(bloco);
        }
        out.writeObject(null); // Sinal de fim da transmissão
        out.flush();
    }
}