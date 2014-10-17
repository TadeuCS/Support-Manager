/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util.Classes;

import javax.swing.JOptionPane;

public class Teste {

    

    public static void main(String[] args) {
//        QRCode.geraQRCode("www.google.com.br");
        if(JOptionPane.showConfirmDialog(null, "Deseja relamente excluir o atendimento: "+1,"",0,JOptionPane.YES_NO_OPTION)==0){
            System.out.println("excluiu!");
        }else{
            System.out.println("ainda não excluiu");
        }
    }
}
