package Util.Email;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.swing.JOptionPane;

public class EnviaEmail {

    //cria as propriedades necessárias para o envio de email
    public void enviaEmail(String smtp, int porta, String usuario, String senha, List<String> destinatarios, String assunto, String mensagem, List<String> anexos) {
        ConfigEmail mj = new ConfigEmail();
        //configuracoes de envio
        mj.setSmtpHostMail(smtp);
        mj.setSmtpPortMail(porta + "");
        mj.setSmtpAuth("true");
        mj.setSmtpStarttls("true");
        mj.setUserMail(usuario);
        mj.setFromNameMail("Olivet Sistemas");
        mj.setPassMail(senha);
        mj.setCharsetMail("ISO-8859-1");
//        mj.setCharsetMail("UTF-8");
        mj.setSubjectMail(assunto);
        mj.setBodyMail(mensagem);//aqui vc passa o texto do email, ou o texto em html usando o metodo "htmlMessage()".
        mj.setTypeTextMail("text/html; charset=iso-8859-1");

        //sete quantos destinatarios desejar
        Map<String, String> map = new HashMap<String, String>();
        for (int i = 0; i < destinatarios.size(); i++) {
            map.put(destinatarios.get(i), "");
        }
        mj.setToMailsUsers(map);

        //seta quantos anexos for nescessário
        if (anexos != null) {
            mj.setFileMails(anexos);
        }
        EnviaEmail sender = new EnviaEmail();
        try {
            sender.senderMail(mj);
        } catch (UnsupportedEncodingException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } catch (MessagingException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void senderMail(ConfigEmail mail) throws
            UnsupportedEncodingException, MessagingException {
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.host", mail.getSmtpHostMail());
        properties.setProperty("mail.smtp.auth", mail.getSmtpAuth());
        properties.setProperty("mail.smtp.starttls.enable", mail.getSmtpStarttls());
        properties.setProperty("mail.smtp.port", mail.getSmtpPortMail());
        properties.setProperty("mail.mime.charset", mail.getCharsetMail());

        properties.getProperty("mail.host");
        properties.getProperty("mail.smtp.port");
        //classe anonima que realiza a autenticação
        //do usuario no servidor de email.
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        mail.getUserMail(), mail.getPassMail()
                );
            }
        };

        // Cria a sessao passando as propriedades e a autenticação
        Session session = Session.getInstance(properties, auth);
        // Gera um log no console referente ao processo de envio
        session.setDebug(true);

        //cria a mensagem setando o remetente e seus destinatários
        Message msg = new MimeMessage(session);
        //aqui seta o remetente
        msg.setFrom(new InternetAddress(
                mail.getUserMail(), mail.getFromNameMail())
        );
        boolean first = true;
        for (Map.Entry<String, String> map : mail.getToMailsUsers().entrySet()) {
            if (first) {
                //setamos o 1° destinatario
                msg.addRecipient(Message.RecipientType.TO,
                        new InternetAddress(map.getKey(), map.getValue())
                );
                first = false;
            } else {
                //setamos os demais destinatarios
                msg.addRecipient(Message.RecipientType.CC,
                        new InternetAddress(map.getKey(), map.getValue())
                );
            }
        }

        // Adiciona um Assunto a Mensagem
        msg.setSubject(mail.getSubjectMail());

        // Cria o objeto que recebe o texto do corpo do email
        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setHeader("Content-Type", "text/html; charset=\"iso-8859-1\"");
        textPart.setContent(mail.getBodyMail(), mail.getTypeTextMail());
        textPart.setHeader("Content-Transfer-Encoding", "quoted-printable");

        // Monta a mensagem SMTP  inserindo o conteudo, texto e anexos
        Multipart mps = new MimeMultipart();
        if (mail.getFileMails() != null) {
            for (int index = 0; index < mail.getFileMails().size(); index++) {

                // Cria um novo objeto para cada arquivo, e anexa o arquivo
                MimeBodyPart attachFilePart = new MimeBodyPart();
                FileDataSource fds = new FileDataSource(
                        mail.getFileMails().get(index)
                );
                attachFilePart.setDataHandler(new DataHandler(fds));
                attachFilePart.setFileName(fds.getName());

                //adiciona os anexos da mensagem
                mps.addBodyPart(attachFilePart, index);

            }
        }

        //adiciona o corpo texto da mensagem
        mps.addBodyPart(textPart);

        //adiciona a mensagem o conteúdo texto e anexo
        msg.setContent(mps);

        // Envia a Mensagem
        Transport.send(msg);
    }
}
