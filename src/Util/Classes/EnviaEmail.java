package Util.Classes;

import javax.swing.JOptionPane;
import org.apache.commons.mail.SimpleEmail;

public class EnviaEmail {

    private SimpleEmail email = new SimpleEmail();

    public void enviarEmail(String smtp,int porta,String usuario, String senha,boolean ssl,String emailDestinatario,String emailRemetente,String assunto,String mensagem) {
        try {
            email.setDebug(true);
            email.setHostName(smtp);
            email.setSmtpPort(porta);
            email.setAuthentication(usuario, senha);
            email.setSSL(ssl);
            email.addTo(emailDestinatario); //pode ser qualquer um email  
            email.setFrom(emailRemetente); //aqui necessita ser o email que voce fara a autenticacao  
            email.setSubject(assunto);
            email.setMsg(mensagem);
            email.send();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao Enviar Email...\n"+e.getMessage());
        }
    }

}
