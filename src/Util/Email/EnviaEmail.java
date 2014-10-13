package Util.Email;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnviaEmail {

    public static void main(String[] args) {

        MailJava mj = new MailJava();

        //configuracoes de envio
        mj.setSmtpHostMail("smtp.mail.yahoo.com");
        mj.setSmtpPortMail("587");
        mj.setSmtpAuth("true");
        mj.setSmtpStarttls("true");
        mj.setUserMail("tadeucaixeta@yahoo.com.br");
        mj.setFromNameMail("TadeuY");
        mj.setPassMail("88015736");
        mj.setCharsetMail("ISO-8859-1");
        mj.setSubjectMail("Teste Email");
        mj.setBodyMail("Teste envio de email");//aqui vc passa o texto do email, ou o texto em html usando o metodo "htmlMessage()".
        mj.setTypeTextMail(MailJava.TYPE_TEXT_HTML);

        //sete quantos destinatarios desejar
        Map<String, String> map = new HashMap<String, String>();
        map.put("suporte4.olivet@hotmail.com", "Suporte4");
//        map.put("destinatario2@msn.com", "email msn");
//        map.put("destinatario3@ig.com.br", "email ig");

        mj.setToMailsUsers(map);

        //seta quatos anexos desejar
        List<String> files = new ArrayList<>();
        files.add("C:/teste.txt");
//        files.add("C:/images/hover_next.png");
//        files.add("C:/images/hover_prev.png");

        mj.setFileMails(files);

        try {
            new MailJavaSender().senderMail(mj);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private static String htmlMessage() {
        return "<html>\n"
                + "<body>\n"
                + "<h1>Teste html</h1>\n"
                + "</body\n"
                + "</html>";
    }
}
