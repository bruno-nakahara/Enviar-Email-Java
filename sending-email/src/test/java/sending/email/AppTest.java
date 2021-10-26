package sending.email;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{	
	@org.junit.Test
    public void testEmail() throws IOException, Exception {
		
		 StringBuilder stringBuilderTextEmail = new StringBuilder();
		
		 stringBuilderTextEmail.append("Olá, <br/><br/>");
		 stringBuilderTextEmail.append("Você está recebendo um email no formato HTML <br/><br/>");
		 stringBuilderTextEmail.append("Para ter acesso ao curso, clique no botão abaixo. <br/><br/>");
		 stringBuilderTextEmail.append("<a target=\"_blank\" href=\"https://www.projetojavaweb.com/certificado-aluno/plataforma-curso/aula?codigoCurso=1\" style=\"color:#2525a7;\">Link para o Curso</a> <br/><br/>");
		
    	 ObjectSendEmail sendEmail = new ObjectSendEmail("xxx@gmail.com",
    			 										"JDev Treinamentos",
    			 										"Estudando sobre Java",
    			 										stringBuilderTextEmail.toString());
    	 
    	 sendEmail.SendEmailAttachment(true);
		
		
	}
}
