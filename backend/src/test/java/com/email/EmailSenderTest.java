
package com.email;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest; 


import com.email.services.EmailService;

import jakarta.mail.Message;

@SpringBootTest(classes = EmailApplication.class)
public class EmailSenderTest {
	
	

    @Autowired
    private EmailService emailService;

//    @Test
//    void emailSendTest() {
//        System.out.println("Sending email... ");
//        emailService.sendEmail("mehtasneha131@gmail.com", "App", "I have launched my app");
//    }
//    
//    
//    @Test
//    void sendEmailWithHtml() {
//    	String html = "" + 
//                      "<h1 style='color:red;border:1px solid black'>Welcome to Priyasha email service! </h1>" +
//    			"";
//    	emailService.sendEmailWithHtml("mehtasneha131@gmail.com", "App2",html);
//    }
    
    
//    @Test
//    void sendEmailWithFile() {
//    	
//    	emailService.sendEmailWithFile("mehtasneha131@gmail.com", "App3", "See my file",new File("C:\\Users\\DELL\\OneDrive\\Documents\\Priyasha\\Springboot projects\\EmailSender\\src\\main\\resources\\static\\icon.png"));
//    }
    
    
//    @Test
//    void sendFile() {
//    	
//    	File file = new File("C:\\Users\\DELL\\OneDrive\\Documents\\Priyasha\\Springboot projects\\EmailSender\\src\\main\\resources\\static\\icon.png");
//    	FileInputStream is;
//		try {
//			is = new FileInputStream(file);
//
//	    	emailService.sendEmailWithFile("mehtasneha131@gmail.com", "App3", "See my file",
//	    			is);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
//    	
//    }
    
    
    
    @Test
    void getInbox() {
    	List<com.email.helper.Message> msgs = emailService.getInboxMessages();
    	msgs.forEach(item->{
    		System.out.println(item.getSubjects());
    		System.out.println(item.getContent());
    		System.out.println(item.getFiles());
    	});
    }
}

