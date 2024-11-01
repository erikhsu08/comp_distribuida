import java.io.*;
import java.net.*;
import java.util.*;

class Node {
    private String nodeName;
    private Set<String> files;
    private List<Node> neighbors;
    private List<String> neighborIPs; // Lista de IPs dos vizinhos

    public Node(String name) {
        this.nodeName = name;
        this.files = new HashSet<>();
        this.neighbors = new ArrayList<>();
        this.neighborIPs = new ArrayList<>(); // Inicializa a lista de IPs
    }

    public void addFile(String fileName) {
        files.add(fileName);
    }

    public void addNeighbor(Node neighbor, String ip) {
        neighbors.add(neighbor);
        neighborIPs.add(ip); // Armazena o IP do vizinho
    }

    public void queryFile(String fileName) {
        Set<String> visitedQueries = new HashSet<>();
        System.out.println(nodeName + " está procurando por '" + fileName + "'...");
        sendQuery(fileName, visitedQueries, 3); // TTL inicial de 3
    }

    private void sendQuery(String fileName, Set<String> visitedQueries, int ttl) {
        // Verifica se o TTL expirou
        if (ttl <= 0) {
            System.out.println(nodeName + " expirou TTL para '" + fileName + "'.");
            return;
        }

        // Verifica se já consultou este arquivo
        if (visitedQueries.contains(fileName + "_" + nodeName)) {
            return; // Evita duplicação da consulta
        }

        visitedQueries.add(fileName + "_" + nodeName); // Marca a consulta como visitada

        // Envia a consulta para todos os vizinhos
        for (int i = 0; i < neighbors.size(); i++) {
            Node neighbor = neighbors.get(i);
            String neighborIP = neighborIPs.get(i); // Obtém o IP do vizinho
            neighbor.receiveQuery(fileName, visitedQueries, ttl, neighborIP);
        }
    }

    public void receiveQuery(String fileName, Set<String> visitedQueries, int ttl, String neighborIP) {
        System.out.println(nodeName + " recebeu a consulta por '" + fileName + "'...");

        // Verifica se o arquivo está disponível
        if (files.contains(fileName)) {
            System.out.println(nodeName + " encontrou o arquivo '" + fileName + "'!");
            return;
        }

        // Envia a consulta para os vizinhos
        sendQuery(fileName, visitedQueries, ttl);
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

        // Adiciona vizinhos com seus respectivos IPs
        node1.addNeighbor(node2, "192.168.1.2"); // Substitua pelo IP real do nó 2
        node2.addNeighbor(node1, "192.168.1.1"); // Substitua pelo IP real do nó 1

        new Thread(node1::listenForQueries).start();
        new Thread(node2::listenForQueries).start();

        // Iniciando busca
        node1.queryFile("arquivo2.txt");
    }
}
