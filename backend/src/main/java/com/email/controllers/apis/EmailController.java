package com.email.controllers.apis;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.email.helper.CustomResponse;
import com.email.helper.EmailRequest;
import com.email.services.EmailService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/email")
public class EmailController {
	
	
	@Autowired
	private EmailService emailService;
   
	@PostMapping("/send")
	public ResponseEntity<?> sendEmail(@RequestBody EmailRequest request){
		
		emailService.sendEmailWithHtml(request.getTo(), request.getSubject(), request.getMessage());
		
		return ResponseEntity.ok(
				
				CustomResponse.builder().message("Email sent successfully!!").httpStatus(HttpStatus.OK).success(true).build()
				
				);
	}
	
	
	@PostMapping("/send-with-file")
	public ResponseEntity<CustomResponse> sendWithFile(@RequestBody EmailRequest request,@RequestParam MultipartFile file) throws IOException{
		emailService.sendEmailWithFile(request.getTo(), request.getSubject(), request.getMessage(),file.getInputStream());
return ResponseEntity.ok(
				
				CustomResponse.builder().message("Email sent successfully!!").httpStatus(HttpStatus.OK).success(true).build()
				
				);
		
	}
}
