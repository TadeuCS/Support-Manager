/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util.Classes;

public class Teste {

    public static void main(String[] args) {
//        QRCode.geraQRCode("www.google.com.br");
        
        GeraRelatorios g=new GeraRelatorios();
        System.out.println(g.getDiretorio("Recibo.jasper"));
    }
}
