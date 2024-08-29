package com.email.services;

import java.io.File;
import com.email.helper.Message;
import java.io.InputStream;
import java.lang.String;
import java.util.List;

public interface EmailService {

    //send email to single person
    void sendEmail(String to, String subject, String message);

    //send email to multiple persons
    void sendEmail(String[] to, String subject, String message);

    //send email with html content
    void sendEmailWithHtml(String to, String subject, String htmlContent);

    //send email with file
    void sendEmailWithFile(String to, String subject, String message, File file);
    
    //send email with dynamic file
    void sendEmailWithFile(String to, String subject, String message, InputStream ins);
    
    
    //to receive emails
    List<Message> getInboxMessages();



}
