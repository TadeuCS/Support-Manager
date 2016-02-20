package Util.Classes;

public class Teste {

    private char numero;

    public static void imprime(int numero) {
        String retorno="";
        for (int i = 0; i < numero; i++) {
            retorno=retorno+"*";
            System.out.println(retorno+"\n");
        }
    }
    public static void main(String[] args) {
        imprime(4);
    }
}