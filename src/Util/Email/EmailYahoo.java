/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util.Email;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

/**
 *
 * @author Tadeu
 */
public class EmailYahoo implements InterfaceEmail {

    @Override
    public void enviaEmailSimpes(String smtp, int porta, String emailRemetente, String senhaRemetente, String emailDestinatario, String nomeDestinatario, String assunto, String mensagem) {
        SimpleEmail email;
        email = new SimpleEmail();
        email.setDebug(true);
        email.setHostName(smtp);
        email.setSmtpPort(porta);
        email.setAuthentication(emailRemetente, senhaRemetente);
        email.setSSL(true);
        try {
            email.addTo(emailDestinatario); //pode ser qualquer um email  
            email.setFrom(emailRemetente); //aqui necessita ser o email que voce fara a autenticacao  
            email.setSubject(assunto);
            email.setMsg(mensagem);
            email.send();
        } catch (EmailException ex) {
            Logger.getLogger(EmailYahoo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void enviaEmailAnexo(String smtp, int porta, String emailRemetente, String senhaRemetente, String emailDestinatario, String nomeDestinatario, String assunto, String mensagem) throws EmailException {
        // cria o anexo.
        EmailAttachment attachment = new EmailAttachment();
        attachment.setPath("C:/der.png"); //caminho da imagem
        attachment.setDisposition(EmailAttachment.ATTACHMENT);
        attachment.setDescription("diagrama");
        attachment.setName("DER");

// Cria a mensagem de e-mail.
        MultiPartEmail email = new MultiPartEmail();
        email.setHostName(smtp); // o servidor SMTP para envio do e-mail
        email.addTo(emailDestinatario, nomeDestinatario); //destinatario
        email.setFrom(emailRemetente, senhaRemetente); //remetente
        email.setSubject("Mensagem de teste com anexo"); //Assunto
        email.setMsg("Aqui está a Imagem anexada ao e-mail"); //conteudo do e-mail
        email.attach(attachment); // adiciona o anexo à mensagem
        email.send();// envia o e-mail
    }

    public void enviaEmailHTML(String smtp, int porta, String emailRemetente, String senhaRemetente, String emailDestinatario, String nomeDestinatario, String assunto, String mensagem) throws EmailException, MalformedURLException {
        // Cria o e-mail
        HtmlEmail email = new HtmlEmail();
        email.setHostName("mail.myserver.com");
        email.addTo("jdoe@somewhere.org", "John Doe");
        email.setFrom("me@apache.org", "Me");
        email.setSubject("Teste de e-mail em formato HTML");

// adiciona uma imagem ao corpo da mensagem e retorna seu id
        URL url = new URL("http://www.apache.org/images/asf_logo_wide.gif");
        String cid = email.embed(url, "Apache logo");

// configura a mensagem para o formato HTML
        email.setHtmlMsg("<html>The apache logo - <img src=\"cid:" + cid + "\"></html>");

// configure uma mensagem alternativa caso o servidor não suporte HTML
        email.setTextMsg("Seu servidor de e-mail não suporta mensagem HTML");

// envia o e-mail
        email.send();
    }

}
