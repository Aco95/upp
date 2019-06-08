package com.udd.naucnacentrala.service;

public interface NotificationService {

	void sendEmail(String subject, String content, String receiverMail);
}
