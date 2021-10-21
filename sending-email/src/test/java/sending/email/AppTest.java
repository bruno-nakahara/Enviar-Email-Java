package sending.email;

import static org.junit.Assert.assertTrue;

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
    public void testEmail() throws UnsupportedEncodingException, MessagingException {
    	 ObjectSendEmail sendEmail = new ObjectSendEmail("XXX@gmail.com",
    			 										"JDev Treinamentos",
    			 										"Estudando sobre Java",
    			 										"Testando envio de email utilizando Java");
    	 
    	 sendEmail.SendEmail();
		
		
	}
}
