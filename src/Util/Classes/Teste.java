package Util.Classes;

import Controller.AtendimentoDAO;

public class Teste {
//        QRCode.geraQRCode("www.olivetsistemas.com.br");

    public static void main(String[] args) {
        AtendimentoDAO atendimentoDAO;
        atendimentoDAO = new AtendimentoDAO();
        Object[] lista=(Object[]) atendimentoDAO.getCountAtendimentos().get(0);
        System.out.println(lista[0]);
    }
}
