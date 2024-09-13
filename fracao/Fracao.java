public class Fracao{
    int numerador;
    int denominador;

    public Fracao(int numerador, int denominador) {
        // Verifica se o denominador é zero e lança uma exceção se for o caso
        if (denominador == 0) {
            throw new IllegalArgumentException("O denominador não pode ser zero.");
        }
        this.numerador = numerador;
        this.denominador = denominador;

    }

    public static int calcularMDC(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    public Fracao simplificar(){
        int mdc = calcularMDC(numerador,denominador);
        int n1 = numerador / mdc;
        int d1 = denominador / mdc;
        return new Fracao(n1,d1);
    }
    public int getNumerador() {
        return numerador;
    }

    public int getDenominador() {
        return denominador;
    }

    @Override
    public String toString() {
        return numerador + "/" + denominador;
    }
}