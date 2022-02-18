package com.getpass.Getpass.servicios;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

	@Autowired
	JavaMailSender mailSender;
	
	@Async
	public void sendMail(String email, String title, String body) {
		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setTo(email);
		message.setFrom("getpass2022@gmail.com");
		message.setSubject(title);
		message.setText(body);
		
		mailSender.send(message);
	}
	
}
