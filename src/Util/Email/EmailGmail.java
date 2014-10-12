package Util.Email;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailGmail implements InterfaceEmail {

    @Override
    public void enviaEmailSimpes(String smtp, int porta, String emailRemetente, String senhaRemetente, String emailDestinatario, String nomeDestinatario, String assunto, String mensagem) {
        Properties props = new Properties();
        props.put("mail.smtp.host", smtp);
        props.put("mail.smtp.socketFactory.port", porta);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", porta);

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(emailRemetente, senhaRemetente);
                    }
                });

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(emailRemetente));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailDestinatario));
            msg.setSubject(assunto);
            msg.setText(mensagem);
            Transport.send(msg);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
