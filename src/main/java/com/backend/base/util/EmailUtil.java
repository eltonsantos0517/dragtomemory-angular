package com.backend.base.util;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtil {

	public static final String MAIL_FROM = "brungcm@gmail.com";

	public static void sendEmail(String toEmail, String toName, String subject, String messageBody)
			throws UnsupportedEncodingException, MessagingException {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		try {
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(MAIL_FROM, "From Name"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail, toName));
			msg.setSubject(subject);
			msg.setContent(messageBody, "text/html; charset=UTF-8");
			Transport.send(msg);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
