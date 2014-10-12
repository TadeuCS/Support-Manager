/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util.Email;


/**
 *
 * @author Tadeu
 */
public interface InterfaceEmail {
    public void enviaEmailSimpes(String smtp, int porta, String emailRemetente, String senhaRemetente, String emailDestinatario, String nomeDestinatario, String assunto, String mensagem);
}
