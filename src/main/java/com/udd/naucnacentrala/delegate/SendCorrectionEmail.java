package com.udd.naucnacentrala.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import com.udd.naucnacentrala.domain.User;
import com.udd.naucnacentrala.service.NotificationService;
import com.udd.naucnacentrala.service.UserService;
import com.udd.naucnacentrala.service.impl.EmailService;

@Component
public class SendCorrectionEmail implements JavaDelegate {
    
	@Autowired
	private UserService userService;
	
	@Autowired
	private NotificationService notificationService;
 	
    @Override
    public void execute(DelegateExecution execution) throws Exception {
    	
    	
    	Long authorId = Long.parseLong(execution.getVariable("authorId").toString());
    	System.out.println("SendCorrectionEmail sending correction email to author with ID: " + authorId);
    	User author = userService.findById(authorId);
    	
    	String mailSubject = "Paper correction notification email";
    	String mailText = "Dear Sir/Madam, Please correct the mistakes in your scientific paper.";
    	notificationService.sendEmail(mailSubject, mailText, author.getEmail());

	}
}
