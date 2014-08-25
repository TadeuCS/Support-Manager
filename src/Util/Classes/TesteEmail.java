package Util.Classes;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class TesteEmail {
	
	private SimpleEmail email = new SimpleEmail();
	
        private String endereco;
        private String smtp;
        private String usuario;
        private String senha;
        private boolean ssl;
        private String assunto;
        private String menssagem;
        
	public TesteEmail() {
		 
	}
	
	public void enviarEmail(String destinatario, String assunto, String mensagem) {
		try {  
		    email.setDebug(true);  
		    email.setHostName("smtp.mail.yahoo.com.br");
                    email.setSmtpPort(587); 
		    email.setAuthentication("tadeucaixeta@yahoo.com.br","88015736");  
		    email.setSSL(true);  
		    email.addTo(destinatario); //pode ser qualquer um email  
		    email.setFrom("tadeucaixeta@yahoo.com.br"); //aqui necessita ser o email que voce fara a autenticacao  
		    email.setSubject(assunto);  
		    email.setMsg(mensagem);  
	        email.send();  
	  
	        } catch (EmailException e) {  
	  
	        	System.out.println("****ERRO**** :" + e.getMessage());  
	  
	        }
	}

}
