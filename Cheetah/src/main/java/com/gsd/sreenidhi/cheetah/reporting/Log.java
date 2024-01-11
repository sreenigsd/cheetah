package com.gsd.sreenidhi.cheetah.reporting;

import java.io.PrintWriter;
import java.io.StringWriter;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gsd.sreenidhi.cheetah.engine.CheetahEngine;
import com.gsd.sreenidhi.cheetah.database.DBExecutor;
import com.gsd.sreenidhi.cheetah.exception.CheetahException;
import com.gsd.sreenidhi.forms.Constants;
import com.gsd.sreenidhi.utils.SendMail;

/**
 * This is the logger class
 * 
 * @author Sreenidhi, Gundlupet
 *
 */
public class Log {
	protected String applicationName;

	Logger log;

	public Log(String app_name) {
		// COnstructor
	}

	/**
	 * This method is used to Log messages
	 * 
	 * @param e
	 *            Exception
	 * @param location
	 *            The location (Class) which generated the log message
	 * @param message
	 *            The actual log message
	 * @param priority
	 *            Message priority: FATAL, ERROR, WARN, INFO
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public void logMessage(Exception e, String location, String message, String priority)
			throws CheetahException {

		log = LogManager.getLogger("APP");

		SendMail email = new SendMail();

		String exceptionStackTrace;
		String toEmailAddress = CheetahEngine.configurator.mailConfigurator.getDefaultTo();

		if (e != null) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			log.error("Exception: " + e.getMessage() + "\n" + CheetahEngine.getExceptionTrace(e));
			exceptionStackTrace = sw.toString();

		} else {
			exceptionStackTrace = "\n------- STACK TRACE NOT AVAILABLE ----------- \n "
					+ "Please contact the Administrator for Assistance.";
		}
		
		switch (priority) {
		case "fatal":
			log.fatal(location + ":" + message, e);
			break;
		case "error":
			log.error(location + ":" + message, e);
			break;
		case "warn":
			log.warn(location + ":" + message, e);
			break;
		case "info":
			log.info(location + ":" + message, e);
			break;
		case "debug":
			log.debug(location + ":" + message, e);
			break;
		default:
			log.warn(location + ":" + message, e);
			break;
		}
		DBExecutor.recordLog(location, message, false);
	}
	
	/**
	 * This method is used to Log messages
	 * 
	 * @param e
	 *            Exception
	 * @param location
	 *            The location (Class) which generated the log message
	 * @param message
	 *            The actual log message
	 * @param priority
	 *            Message priority: FATAL, ERROR, WARN, INFO
	 * @param sendMail
	 *            Boolean flag that checks whether an email message needs to be send
	 *            for the message being logged.
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public void logMessage(Exception e, String location, String message, String priority, boolean sendMail)
			throws CheetahException {

		log = LogManager.getLogger("APP");

		SendMail email = new SendMail();

		String exceptionStackTrace;
		String toEmailAddress = CheetahEngine.configurator.mailConfigurator.getDefaultTo();

		if (e != null) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			log.error("Exception: " + e.getMessage() + "\n" + CheetahEngine.getExceptionTrace(e));
			exceptionStackTrace = sw.toString();

		} else {
			exceptionStackTrace = "\n------- STACK TRACE NOT AVAILABLE ----------- \n "
					+ "Please contact the Administrator for Assistance.";
		}
		if (Constants.sendMail && CheetahEngine.configurator.mailConfigurator.getSendMail() && sendMail) {
			String mailBody = "<table style=\"table-layout: fixed;\" border=\"1\" width=\"90%\" align=\"center\" bgcolor=\"#FFFFFF\">\r\n" + 
					"<tbody>\r\n" + 
					"<tr bgcolor=\"#5e72e4\">\r\n" + 
					"<th align=\"left\"><span style=\"color: #ffffff;\"> Cheetah Automation Framework </span></th>\r\n" + 
					"</tr>\r\n" + 
					"<tr>\r\n" + 
					"<td align=\"left\"><br />\r\n" + 
					"<div>Hello! An exception has occurred. Please see below for details.</div>\r\n" + 
					"<br />\r\n" + 
					"<table style=\"table-layout: fixed; width: 90%;\" cellpadding=\"10\">\r\n" + 
					"<tbody>\r\n" + 
					"<tr>\r\n" + 
					"<th align=\"left\" valign=\"top\" bgcolor=\"#F2F5A9\" width=\"25%\">Message:</th>\r\n" + 
					"<td style=\"word-wrap: break-word;\" width=\"75%\">\r\n" + 
					"<div>"+message+"</div>\r\n" + 
					"</td>\r\n" + 
					"</tr>\r\n" + 
					"<tr>\r\n" + 
					"<th align=\"left\" valign=\"top\" bgcolor=\"#F2F5A9\" width=\"25%\">Source Location:</th>\r\n" + 
					"<td style=\"word-wrap: break-word;\" width=\"75%\">\r\n" + 
					"<div>"+location+"</div>\r\n" + 
					"</td>\r\n" + 
					"</tr>\r\n" + 
					"<tr>\r\n" + 
					"<th align=\"left\" valign=\"top\" bgcolor=\"#F2F5A9\" width=\"25%\">Stack Trace:</th>\r\n" + 
					"<td style=\"word-wrap: break-word;\" width=\"75%\">\r\n" + 
					"<div>"+exceptionStackTrace+"</div>\r\n" + 
					"</td>\r\n" + 
					"</tr>\r\n" + 
					"</tbody>\r\n" + 
					"</table>\r\n" + 
					"<br /> </td>\r\n" + 
					"</tr>\r\n" + 
					"<tr bgcolor=\"#5e72e4\">\r\n" + 
					"<td align=\"left\">\r\n" + 
					"<table width=\"100%\">\r\n" + 
					"<tbody>\r\n" + 
					"<tr>\r\n" + 
					"<td><span style=\"color: #ffffff;\"> <br /> -- Mail Handler </span></td>\r\n" + 
					"<td><img src=\"https://www.sreenidhi-gsd.com/resources/assets/img/brand/white.png\" width=\"100\" align=\"right\" /></td>\r\n" + 
					"</tr>\r\n" + 
					"</tbody>\r\n" + 
					"</table>\r\n" + 
					"</td>\r\n" + 
					"</tr>\r\n" + 
					"</tbody>\r\n" + 
					"</table>\r\n" + 
					"<table width=\"90%\" align=\"center\">\r\n" + 
					"<tbody>\r\n" + 
					"<tr align=\"center\">\r\n" + 
					"<td style=\"width=100%; font-size: 12px; color: #424242;\"><br />This is an automated message. <br />Please do not reply to this email. <br />Please contact the framework <strong>Administrator</strong> if you require assistance.</td>\r\n" + 
					"</tr>\r\n" + 
					"</tbody>\r\n" + 
					"</table>";
			email.sendMessage(mailBody, toEmailAddress, null, CheetahEngine.configurator.mailConfigurator.getDefaultSubject(), true);
		}

		switch (priority) {
		case "fatal":
			log.fatal(location + ":" + message, e);
			break;
		case "error":
			log.error(location + ":" + message, e);
			break;
		case "warn":
			log.warn(location + ":" + message, e);
			break;
		case "info":
			log.info(location + ":" + message, e);
			break;
		case "debug":
			log.debug(location + ":" + message, e);
			break;
		default:
			log.warn(location + ":" + message, e);
			break;
		}
		DBExecutor.recordLog(location, message, sendMail);
	}

	/**
	 * @return the log
	 */
	public Logger getLog() {
		return log;
	}

	/**
	 * @param log
	 *            the log to set
	 */
	public void setLog(Logger log) {
		this.log = log;
	}

	public void breakLine() {
		log.info("");
	}

}
