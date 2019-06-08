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
public class SendEmailBadFormating implements JavaDelegate {
    
	@Autowired
	private UserService userService;
	
	@Autowired
	private NotificationService notificationService;
 	
    @Override
    public void execute(DelegateExecution execution) throws Exception {
    	
    	Long authorId = Long.parseLong(execution.getVariable("authorId").toString());
    	System.out.println("SendEmailBadFormating sending bad formating error email to author with ID: " + authorId);
    	User author = userService.findById(authorId);
    	
    	String mailSubject = "Bad formating notification email";
    	String mailContent = "Dear Sir/Madam, Formating of your scientific paper is bad, please fix it and resubmit your paper.";
    	notificationService.sendEmail(mailSubject, mailContent, author.getEmail());
    	
	}
}
