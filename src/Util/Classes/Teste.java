package Util.Classes;

import Controller.AtendimentoDAO;

public class Teste {
//        QRCode.geraQRCode("www.olivetsistemas.com.br");

    public static void main(String[] args) {
        String cpf="101.874.696-01";
//        String cpf="111.111.111-11";
        System.out.println(ValidarCGCCPF.validaCPF(cpf));
    }
}
