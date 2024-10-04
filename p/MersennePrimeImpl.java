import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.math.BigInteger;

public class MersennePrimeImpl extends UnicastRemoteObject implements MersennePrime {

    protected MersennePrimeImpl() throws RemoteException {
        super();
    }

    @Override
    public boolean ehPrimoM(int n) throws RemoteException {
        if (n < 1) return false; // Mersenne numbers começam a partir de M1
        
        // Loop para encontrar se n é um número de Mersenne
        for (int x = 1; ; x++) {
            BigInteger mersenneNumber = BigInteger.valueOf(2).pow(x).subtract(BigInteger.ONE);
            if (mersenneNumber.intValue() == n) {
                return isPrime(x); // Verifica se x é primo
            }
            if (mersenneNumber.intValue() > n) {
                break; // Se M_x ultrapassou n, não é um número de Mersenne
            }
        }
        return false; // Se não encontrou M_x igual a n
    }

    private boolean isPrime(int num) {
        if (num <= 1) return false;
        if (num <= 3) return true;
        if (num % 2 == 0 || num % 3 == 0) return false;
        for (int i = 5; i * i <= num; i += 6) {
            if (num % i == 0 || num % (i + 2) == 0) return false;
        }
        return true;
    }
}
