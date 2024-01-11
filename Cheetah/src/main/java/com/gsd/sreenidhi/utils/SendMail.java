package com.gsd.sreenidhi.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import jakarta.mail.Message;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import com.gsd.sreenidhi.cheetah.engine.CheetahEngine;
import com.gsd.sreenidhi.cheetah.exception.CheetahException;
import com.gsd.sreenidhi.cheetah.reporting.Log;
import com.gsd.sreenidhi.forms.Constants;


/**
 * @author Sreenidhi, Gundlupet
 *
 */
public class SendMail {

	/**
	 * @param message
	 *            Mail Message
	 * @param toEmail
	 *            TO Email address to send message
	 * @param ccEmail
	 *            CC Email address to send message
	 * @param subject
	 *            Email Subject
	 * @param sendToAdmin
	 *            Boolean flag to indicate whether the Framework administrator should be CC'ed
	 *            in the notification
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public void sendMessage(String message, String toEmail, String ccEmail, String subject, boolean sendToAdmin)
			throws CheetahException {

		FileUtils fileUtils = new FileUtils();

		Properties outlookProp;
		Log l = new Log(toEmail);
		Exception exception = null;

		try {
			l.logMessage(exception, SendMail.class.getName(), "Starting Mail process", "info", false);
			Properties props = new Properties();
			
			props.put("mail.smtp.host", CheetahEngine.configurator.mailConfigurator.getSmtpHost());// for gmail use smtp.gmail.com
			props.put("mail.smtp.auth", "true");
			props.put("mail.debug", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.port", CheetahEngine.configurator.mailConfigurator.getSmtpPort());
			props.put("mail.smtp.socketFactory.port", CheetahEngine.configurator.mailConfigurator.getSmtpPort());
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.socketFactory.fallback", "true");

			final String user = CheetahEngine.configurator.mailConfigurator.getSmtpUsername();
			String pwd = CheetahEngine.configurator.mailConfigurator.getSmtpPassword();
			if(CheetahEngine.configurator.mailConfigurator.isRetrieveSmtpCredentialFromFile()){
				File passwordFile;
				
				CheetahEngine.logger.logMessage(null, "SendMail", "Retrieving credentials from file...:"+CheetahEngine.configurator.mailConfigurator.getSmtpCredentialFile(), Constants.LOG_INFO, false);
				passwordFile = new File(CheetahEngine.configurator.mailConfigurator.getSmtpCredentialFile());
				
				if(passwordFile!=null && passwordFile.exists()){
					CheetahEngine.logger.logMessage(null, "SendMail", "File Found...", Constants.LOG_INFO, false);	
					BufferedReader br = null;
					StringBuffer sb = null;
					try{
						br = new BufferedReader(new FileReader(passwordFile));
						sb = new StringBuffer();
						String line = br.readLine();
						if(line!=null && line.length()>1){
							pwd = line.trim();
						}else{
							pwd = CheetahEngine.configurator.mailConfigurator.getSmtpPassword(); 
						}
					}catch(IOException e){
						throw new CheetahException(e);
					}finally {
						br.close();
					}
					
				}else{
					CheetahEngine.logger.logMessage(null, "SendMail", "Credential File NOT Found...", Constants.LOG_INFO, false);
					
				}
				
			}
			
			final String passwd = pwd;
			Session mailSession = Session.getInstance(props, new jakarta.mail.Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(user, passwd);
				}
			});

			mailSession.setDebug(true); // Enable the debug mode

			Message msg = new MimeMessage(mailSession);

			// --[ Set the FROM, TO, DATE and SUBJECT fields
			msg.setFrom(new InternetAddress(CheetahEngine.configurator.mailConfigurator.getDefaultFrom()));

			String[] toReceipients = toEmail.split(";");
			for (String addr : toReceipients) {
				msg.addRecipients(Message.RecipientType.TO, InternetAddress.parse(addr.trim()));
			}

			if (sendToAdmin && !toEmail.contains(CheetahEngine.configurator.mailConfigurator.getDefaultTo())) {
				msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(toEmail.trim()));
			}

			if (ccEmail != null && !"".equalsIgnoreCase(ccEmail)) {

				String[] ccReceipients = ccEmail.split(";");
				for (String addr : ccReceipients) {
					msg.addRecipients(Message.RecipientType.CC, InternetAddress.parse(addr.trim()));
				}
			}

			msg.setSentDate(new Date());
			if (subject != null && !"".equalsIgnoreCase(subject)) {
				msg.setSubject(subject);
			} else {
				msg.setSubject(CheetahEngine.configurator.mailConfigurator.getDefaultSubject());
			}

			// --[ Create the body of the mail
			Multipart mp = new MimeMultipart();
			MimeBodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent(message, "text/html");
			mp.addBodyPart(htmlPart);
			msg.setContent(mp);

			// --[ Ask the Transport class to send our mail message
			Transport.send(msg);
			l.logMessage(exception, SendMail.class.getName(), "Mail Sent - " + msg.getSubject() + "\n" + message + "\n",
					"info", false);

		} catch (Exception e) {
			throw new CheetahException(e);
		}
	}
}