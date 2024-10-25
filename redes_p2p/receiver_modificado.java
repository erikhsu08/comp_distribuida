import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Receiver {
    private static final String[] HOSTS = {"IP_SENDER_A", "IP_SENDER_B", "IP_SENDER_C"}; // Substitua pelos IPs reais
    private static final int PORT = 5001;
    private static final int BUFFER_SIZE = 4096;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o nome do arquivo a ser recebido: ");
        String fileName = scanner.nextLine();
        boolean fileFound = false;

        for (String host : HOSTS) {
            try (Socket socket = new Socket(host, PORT)) {
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                DataInputStream dis = new DataInputStream(socket.getInputStream());

                // Envia o nome do arquivo solicitado
                dos.writeUTF(fileName);

                // Recebe a resposta do Sender
                String response = dis.readUTF();
                if ("FOUND".equals(response)) {
                    // Cria o arquivo para salvar o conteúdo recebido
                    String prefixedFileName = "recebido_" + fileName;
                    try (FileOutputStream fos = new FileOutputStream(prefixedFileName)) {
                        // Recebe o conteúdo do arquivo
                        byte[] buffer = new byte[BUFFER_SIZE];
                        int bytesRead;
                        while ((bytesRead = dis.read(buffer)) != -1) {
                            fos.write(buffer, 0, bytesRead);
                        }
                    }

                    System.out.println("Arquivo recebido de " + host + ".");
                    fileFound = true; // Marcamos que encontramos o arquivo
                    break; // Sai do loop após encontrar o arquivo
                } else {
                    System.out.println("Arquivo não encontrado em " + host + ".");
                }

                dos.close();
                dis.close();
            } catch (IOException e) {
                System.out.println("Conexão falhou com: " + host);
            }
        }

        if (!fileFound) {
            System.out.println("Arquivo não encontrado em nenhum dos Senders.");
        }

        scanner.close();
    }
}
