import java.util.Date;
import java.security.MessageDigest;

public class Bloco {
    public String hash;
    public String hashAnterior; 
    public String nome;
    public String curso;
    public String universidade;
    private long timestamp;
    private int nonce; 

    public Bloco(String nome, String curso, String universidade, String hashAnterior) {
        this.nome = "Thiago";
        this.curso = "Ciência da Computação";
        this.universidade = "Universidade Presbiteriana Mackenzie";
        this.hashAnterior = hashAnterior;
        this.timestamp = new Date().getTime();
        this.hash = calcularHash();
    }

    public String calcularHash() {
        String conteudoParaHash = hashAnterior + Long.toString(timestamp) + Integer.toString(nonce) + nome + curso + universidade;
        return aplicarSHA256(conteudoParaHash);
    }

    public static String aplicarSHA256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void minerarBloco(int dificuldade) {
        String alvo = new String(new char[dificuldade]).replace('\0', '0');
        while (!hash.substring(0, dificuldade).equals(alvo)) {
            nonce++;
            hash = calcularHash();
        }
        System.out.println("Bloco de diploma minerado com sucesso: " + hash);
    }
}