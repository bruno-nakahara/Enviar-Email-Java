package sending.email;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class ObjectSendEmail {

	private String userName = "xxx@gmail.com";
	private String password = "xxx";
	
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
	
	public void SendEmail(boolean sendHtml) throws MessagingException, UnsupportedEncodingException {
		
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
		
		if (sendHtml) {
			message.setContent(emailText, "text/html; charset=utf-8");
		} else {
			message.setText(emailText);
		}
		
		Transport.send(message);
	
	}
	
	/* Método Simulador de PDF anexado ao email enviado*/
	private FileInputStream simuladorDePDF() throws Exception{
		Document document = new Document();
		File file = new File("anexo.pdf");
		file.createNewFile();
		PdfWriter.getInstance(document, new FileOutputStream(file));
		document.open();
		document.add(new Paragraph("Conteudo do PDF anexo com Java Mail"));
		document.close();
		
		return new FileInputStream(file);
	}
	
	public void SendEmailAttachment (boolean sendHtml) throws IOException, Exception {
		
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
		
		/*Texto e a descrição do email*/
		MimeBodyPart emailBody = new MimeBodyPart();
		
		if (sendHtml) {
			emailBody.setContent(emailText, "text/html; charset=utf-8");
		} else {
			emailBody.setText(emailText);
		}
		
		List<FileInputStream> files = new ArrayList<FileInputStream>();
		files.add(simuladorDePDF());
		files.add(simuladorDePDF());
		files.add(simuladorDePDF());
		
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(emailBody);
		
		int index = 0;
		for (FileInputStream fileInputStream : files) {
			/*Anexo em pdf*/
			MimeBodyPart emailAttachment = new MimeBodyPart();
			
			emailAttachment.setDataHandler(new DataHandler(new ByteArrayDataSource(fileInputStream, "application/pdf")));
			emailAttachment.setFileName("emailAttachment" + index + ".pdf");
			
			multipart.addBodyPart(emailAttachment);
			
			index++;
		}
		
		message.setContent(multipart);
		
		Transport.send(message);
	
	}
}
