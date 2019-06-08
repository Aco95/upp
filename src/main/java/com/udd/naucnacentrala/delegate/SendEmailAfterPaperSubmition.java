package com.udd.naucnacentrala.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.udd.naucnacentrala.domain.User;
import com.udd.naucnacentrala.service.NotificationService;
import com.udd.naucnacentrala.service.UserService;

@Component
public class SendEmailAfterPaperSubmition implements JavaDelegate {

	@Autowired
	private UserService userService;

	@Autowired
	private NotificationService notificationService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		String mailSubject = "New article submission notification email";
		
		Long authorId = Long.parseLong(execution.getVariable("authorId").toString());
		System.out.println("SendEmailAfterPaperSubmition sending email to author with ID: " + authorId);
		User author = userService.findById(authorId);
		String mailTextA = "Congratulations! You succesfully submitted an article. Please wait for a review.";
		notificationService.sendEmail(mailSubject, mailTextA, author.getEmail());
		
		Long mainEditorId = Long.parseLong(execution.getVariable("mainEditorId").toString());
		System.out.println("SendEmailAfterPaperSubmition sending email to main editor with ID: " + mainEditorId);
		User mainEditor = userService.findById(mainEditorId);
		String mailTextE = "There is a new article in the Naucna centrala. Please login to see it.";
		notificationService.sendEmail(mailSubject, mailTextE, mainEditor.getEmail());

	}

}
