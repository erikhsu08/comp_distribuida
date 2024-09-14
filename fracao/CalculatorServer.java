/* TURMA: 06G11
NOME: ERIK SAMUEL VIANA HSU 							RA:10403109
NOME: THIAGO SHIHAN CARDOSO TOMA						RA:10400764
*/



import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class CalculatorServer implements Calculator {

    // Implementar o método de adição de frações
    public Fracao add(Fracao a, Fracao b) throws RemoteException {
        int num = a.getNumerador() * b.getDenominador() + b.getNumerador() * a.getDenominador();
        int denom = a.getDenominador() * b.getDenominador();
        return new Fracao(num, denom);
    }

    // Implementar o método de subtração de frações
    public Fracao subtract(Fracao a, Fracao b) throws RemoteException {
        int num = a.getNumerador() * b.getDenominador() - b.getNumerador() * a.getDenominador();
        int denom = a.getDenominador() * b.getDenominador();
        return new Fracao(num, denom);
    }

    // Implementar o método de multiplicação de frações
    public Fracao multiply(Fracao a, Fracao b) throws RemoteException {
        int num = a.getNumerador() * b.getNumerador();
        int denom = a.getDenominador() * b.getDenominador();
        return new Fracao(num, denom);
    }

    // Implementar o método de divisão de frações
    public Fracao divide(Fracao a, Fracao b) throws RemoteException {
        if (b.getNumerador() == 0) throw new ArithmeticException("Divisão por zero");
        int num = a.getNumerador() * b.getDenominador();
        int denom = a.getDenominador() * b.getNumerador();
        return new Fracao(num, denom);
    }

    public static void main(String[] args) {
        try {
            // Criar e exportar o objeto CalculatorServer
            CalculatorServer server = new CalculatorServer();
            Calculator stub = (Calculator) UnicastRemoteObject.exportObject(server, 0);

            // Vincular o stub do objeto remoto no registro
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("Calculator", stub);

            System.out.println("CalculatorServer is running...");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
