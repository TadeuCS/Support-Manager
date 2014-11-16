package Util.Classes;

import java.util.Locale;

public class Teste {
//        QRCode.geraQRCode("www.olivetsistemas.com.br");

    public static void main(String[] args) {
//---------------- classe para chamar Moeda -------------------- 
        double VALOR = 10000.5; // estar assim no MySql 
        Moeda tp = new Moeda();
        String Formato2 = tp.mascaraDinheiro(VALOR);
        System.out.println(Formato2);
    }
}
