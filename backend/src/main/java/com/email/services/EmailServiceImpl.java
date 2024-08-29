package com.email.services;

import java.lang.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.email.helper.Message;

import jakarta.mail.BodyPart;
import jakarta.mail.Folder;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.NoSuchProviderException;
import jakarta.mail.Part;
import jakarta.mail.Session;
import jakarta.mail.Store;
import jakarta.mail.internet.MimeMessage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;


@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    private JavaMailSender mailSender;

    

    private Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Override
    public void sendEmail(String to, String subject, String message) {
        SimpleMailMessage simpleMailMessage =  new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        simpleMailMessage.setFrom("xxx@gmail.com");

        mailSender.send(simpleMailMessage);

        //informational log
        logger.info("Email has been sent");
    }

    @Override
    public void sendEmail(String[] to, String subject, String message) {
       
    	SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
    	simpleMailMessage.setTo(to);
    	simpleMailMessage.setSubject(subject);
    	simpleMailMessage.setText(message);
    	simpleMailMessage.setFrom("xxx@gmail.com");
    	
    	mailSender.send(simpleMailMessage);
    	 logger.info("Email has been sent");
    }

    @Override
    public void sendEmailWithHtml(String to, String subject, String htmlContent) {
    	MimeMessage simpleMailMessage = mailSender.createMimeMessage();
    	
    	try {
    	MimeMessageHelper helper = new MimeMessageHelper(simpleMailMessage,true,"UTF-8");
    	helper.setTo(to);
    	helper.setSubject(subject);
    	helper.setFrom("xxx@gmail.com");
    	helper.setText(htmlContent,true);
    	
    	mailSender.send(simpleMailMessage);
    	
    	 logger.info("Email has been sent");
    	}
    	catch(MessagingException e) {
    		throw new RuntimeException(e);
    	}
    	
    	
    	
    }

    @Override
    public void sendEmailWithFile(String to, String subject, String message, File file) {
        
    	MimeMessage Mimemessage = mailSender.createMimeMessage();
    	
    	
		try {
			MimeMessageHelper helper = new MimeMessageHelper(Mimemessage,true,"UTF-8");
			
			helper.setTo(to);
	    	helper.setSubject(subject);
	    	helper.setText(message);
	    	helper.setFrom("xxx@gmail.com");
	    	
	    	 if (file != null && file.exists()) {
	                helper.addAttachment(file.getName(), file);
	            }
	    	
	    	mailSender.send(Mimemessage);
	    	logger.info("Email sent with file");
	    	
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }

	@Override
	public void sendEmailWithFile(String to, String subject, String message, InputStream is) {
MimeMessage Mimemessage = mailSender.createMimeMessage();
    	
    	
		try {
			MimeMessageHelper helper = new MimeMessageHelper(Mimemessage,true,"UTF-8");
			
			helper.setTo(to);
	    	helper.setSubject(subject);
	    	helper.setText(message);
	    	helper.setFrom("xxx@gmail.com");
	    	
	    	File file = new File("/email/src/main/resources/static/images/test.png");
	    	Files.copy(is, file.toPath(),StandardCopyOption.REPLACE_EXISTING);
	    	
	    	FileSystemResource res = new FileSystemResource(file);
	                helper.addAttachment(res.getFilename(),file);
	         
	    	
	    	mailSender.send(Mimemessage);
	    	logger.info("Email sent with file");
	    	
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Value("${mail.store.protocol}")
	String protocol;
	
	@Value("${mail.imaps.host}")
	String host;
	
	@Value("${mail.imaps.port}")
	String port;
	
	@Value("${spring.mail.username}")
	String username;
	
	@Value("${spring.mail.password}")
	String password;

	@Override
	public List<Message> getInboxMessages() {
		
		//code to receive emails
		Properties configurations = new Properties();
		
		//setting the email properties
		configurations.setProperty("mail.store.protocol",protocol);
		configurations.setProperty("mail.imaps.host",host);
		configurations.setProperty("mail.imaps.port",port);
		
		Session session = Session.getDefaultInstance(configurations);
		List<Message> list = new ArrayList<>();
		
		try {
			Store store = session.getStore();
			
			//for authentication
			store.connect(username,password);
			
			//getting inbox folder
			Folder inbox = store.getFolder("INBOX");
			
			//reading inbox folder
			inbox.open(Folder.READ_ONLY);
			
			//fetching messages of inbox
			jakarta.mail.Message[] messages = inbox.getMessages();
			
			
			
			
			for(jakarta.mail.Message msg:messages) {
				
				//to get the content of msg
				String content = getContentFromEmailMessage(msg);
				
				//to get the attachments of msg
				List<String> files = getFilesFromEmailMessage(msg);
				
				
				list.add(Message.builder().subjects(msg.getSubject()).content(content).files(files).build());
			}
			
			;
			
		} catch (NoSuchProviderException e) {
			
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
		
	
	}

	private List<String> getFilesFromEmailMessage(jakarta.mail.Message msg) throws MessagingException, IOException {
	   
		List<String> files = new ArrayList<>();
         if(msg.isMimeType("multipart/*")) {
			
			Multipart part = (Multipart) msg.getContent();
			for(int i=0;i<part.getCount();i++) {
				BodyPart bodyPart = part.getBodyPart(i);
				if(Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())) {
					InputStream inputStream = bodyPart.getInputStream();
					File file = new File("C:\\Users\\DELL\\email\\src\\main\\resources\\static\\images"+bodyPart.getFileName());
					
					//save the file
					Files.copy(inputStream, file.toPath(),StandardCopyOption.REPLACE_EXISTING);
					
					//urls to access the file
					files.add(file.getAbsolutePath());
				}
			}
         }
		return files;
		
	}

	private String getContentFromEmailMessage(jakarta.mail.Message msg) throws MessagingException, IOException {
		
		//if text is plain/html type, getting that content in string format
		if(msg.isMimeType("text/plain") || msg.isMimeType("text/html")) {
			
			String content = (String) msg.getContent();
			return content;
		}
		
		//else if that content is with file, getting here
		else if(msg.isMimeType("multipart/*")) {
			
			Multipart part = (Multipart) msg.getContent();
			for(int i=0;i<part.getCount();i++) {
				BodyPart bodyPart = part.getBodyPart(i);
				if(bodyPart.isMimeType("text/plain")) {
					return (String) bodyPart.getContent();
				}
			}
			
		}
		return null;
	}
}
