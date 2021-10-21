package sending.email;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ObjectSendEmail {

	private String userName = "XXX@gmail.com";
	private String password = "XXX";
	
	private String destinationList = "";
	private String nomeRemetente = "";
	private String emailTitle = "";
	private String emailText = "";
	
	public ObjectSendEmail(String destinationList, String nomeRemetente, String emailTitle, String emailText) {
		this.destinationList = destinationList;
		this.nomeRemetente = nomeRemetente;
		this.emailTitle = emailTitle;
		this.emailText = emailText;
		
	}
	
	public void SendEmail() throws MessagingException, UnsupportedEncodingException {
		
		Properties properties = new Properties();
		
		properties.put("mail.smtp.ssl.trust", "*");
		properties.put("mail.smtp.auth", "true");//Autorização
		properties.put("mail.smtp.starttls", "true");//Autenticação
		properties.put("mail.smtp.host", "smtp.gmail.com");//Servidor gmail
		properties.put("mail.smtp.port", "465");//Porta do servidor
		properties.put("mail.smtp.socketFactory.port", "465");//Especifica a porta a ser conectada pelo socket
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");//Classe socket de conexão ao SMTP
		
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				
				return new PasswordAuthentication(userName, password);
			}
		});
		
		Address[] toUser = InternetAddress.parse(destinationList); 
		
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(userName, nomeRemetente));//transmissor
		message.setRecipients(Message.RecipientType.TO, toUser);//destino
		message.setSubject(emailTitle);//Assunto
		message.setText(emailText);
		
		Transport.send(message);
		
		System.out.println(session);
	}
}
