package Util.Email;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.commons.mail.EmailException;

public class EmailHotmail implements InterfaceEmail {

    @Override
    public void enviaEmailSimpes(String smtp, int porta, String emailRemetente, String senhaRemetente, String emailDestinatario, String nomeDestinatario, String assunto, String mensagem) {
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", smtp);
        props.put("mail.smtp.socketFactory.port", porta);
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", porta);

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(emailRemetente, senhaRemetente);
                    }
                });
        session.setDebug(true);

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(emailRemetente)); //Remetente
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailDestinatario)); //Destinatário(s)
            msg.setSubject(assunto);//Assunto
            msg.setText(mensagem);
            Transport.send(msg);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
