import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class DiplomaBlockchainClient {
    private static final String HOST = "localhost";
    private static final int PORT = 8080;

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            // Solicita a blockchain de diplomas para o servidor
            out.writeObject("GET_BLOCKCHAIN");
            out.flush();

            // Recebe e imprime a blockchain
            System.out.println("Blockchain de diplomas recebida:");
            while (true) {
                Bloco bloco = (Bloco) in.readObject();
                if (bloco == null) {
                    break;
                }
                System.out.println("Nome: " + bloco.nome);
                System.out.println("Curso: " + bloco.curso);
                System.out.println("Universidade: " + bloco.universidade);
                System.out.println("Hash do bloco: " + bloco.hash);
                System.out.println();
            }

            System.out.println("Blockchain de diplomas recebida com sucesso!");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
