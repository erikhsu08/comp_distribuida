import java.io.*;
import java.net.*;
import java.util.*;

class Node {
    private String nodeName;
    private Set<String> files;
    private List<Node> neighbors;

    public Node(String name) {
        this.nodeName = name;
        this.files = new HashSet<>();
        this.neighbors = new ArrayList<>();
    }

    public void addFile(String fileName) {
        files.add(fileName);
    }

    public void addNeighbor(Node neighbor) {
        neighbors.add(neighbor);
    }

    public void queryFile(String fileName) {
        System.out.println(nodeName + " está procurando por '" + fileName + "'...");

        if (files.contains(fileName)) {
            System.out.println(nodeName + " encontrou o arquivo '" + fileName + "'!");
            return;
        }

        // Envia a consulta para vizinhos
        for (Node neighbor : neighbors) {
            neighbor.sendQuery(fileName);
        }
    }

    private void sendQuery(String fileName) {
        // Implementar a comunicação via Socket aqui
        try (Socket socket = new Socket("localhost", 12345); // Endereço do nó vizinho
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
             
            out.println(fileName);
            String response = in.readLine();
            if (response != null) {
                System.out.println(nodeName + " recebeu: " + response);
            }
        } catch (IOException e) {
            System.out.println("Erro ao enviar consulta: " + e.getMessage());
        }
    }

    public void listenForQueries() {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
                     
                    String fileName = in.readLine();
                    if (files.contains(fileName)) {
                        out.println(nodeName + " encontrou o arquivo '" + fileName + "'!");
                    } else {
                        out.println(nodeName + " não encontrou o arquivo '" + fileName + "'.");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro no servidor: " + e.getMessage());
        }
    }
}

public class P2PNetwork {
    public static void main(String[] args) {
        Node node1 = new Node("Nó 1");
        Node node2 = new Node("Nó 2");

        node1.addFile("arquivo1.txt");
        node2.addFile("arquivo2.txt");

        node1.addNeighbor(node2);
        node2.addNeighbor(node1);

        new Thread(node1::listenForQueries).start();
        new Thread(node2::listenForQueries).start();

        // Iniciando busca
        node1.queryFile("arquivo2.txt");
    }
}
