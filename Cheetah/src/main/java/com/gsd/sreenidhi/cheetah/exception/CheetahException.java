/**
 * Created on May 15, 2017
 */
package com.gsd.sreenidhi.cheetah.exception;

import java.awt.AWTException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.sql.SQLException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.json.JSONException;
import org.openqa.selenium.*;
import org.w3c.dom.DOMException;

import com.gsd.sreenidhi.cheetah.engine.CheetahEngine;
import com.gsd.sreenidhi.cheetah.database.DBExecutor;
import com.gsd.sreenidhi.cheetah.reporting.Log;
import com.gsd.sreenidhi.forms.Constants;
import com.sun.deploy.net.proxy.ProxyConfigException;
import com.sun.deploy.net.proxy.ProxyUnavailableException;

/**
 * This is the Core Exception Class for Exception handling
 *
 * @author Sreenidhi, Gundlupet
 *
 */
@SuppressWarnings("serial")
public class CheetahException extends Exception {
	/**
	 * Root cause Exception
	 */
	private Exception exception;
	private String errorCode = "Unknown_Exception";
	public static Log logger;
	public String exceptionMessage = "";
	
	private static final String CAUSE = "Cause: ";

	/**
	 * Constructor for the CheetahException object.
	 *
	 */
	public CheetahException() {
		super();
	}

	/**
	 * Creates a new CheetahException wrapping another exception, and with no detail
	 * message.
	 * 
	 * @param exception
	 *            the wrapped exception.
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public CheetahException(Exception exception) throws CheetahException {

		this.exception = exception;

		String cause = (exception.getCause() != null) ? exception.getCause().toString() : "Unknown Cause";

		if (exception instanceof IllegalArgumentException) {
			exceptionMessage = "IllegalArgumentException ====>" + CAUSE + cause + "\n " + exception.getMessage();
		} else if (exception instanceof ConfigurationException) {
			exceptionMessage = "ConfigurationException ====>" + CAUSE + cause + "\n " + exception.getMessage();
		} else if (exception instanceof IllegalAccessException) {
			exceptionMessage = "IllegalAccessException ====>" + CAUSE + cause + "\n " + exception.getMessage();
		} else if (exception instanceof UnknownHostException) {
			exceptionMessage = "UnknownHostException ====>" + CAUSE + cause + "\n " + exception.getMessage();
		} else if (exception instanceof SQLException) {
			exceptionMessage = "SQL Exception ====>" + CAUSE + cause + "\n " + exception.getMessage();
		} else if (exception instanceof ElementNotVisibleException) {
			exceptionMessage = "ElementNotVisibleException====>" + CAUSE + cause + "\n " + exception.getMessage();
		} else if (exception instanceof NoSuchElementException) {
			exceptionMessage = "NoSuchElementException====>" + CAUSE + cause + "\n " + exception.getMessage();
		}  else if (exception instanceof JavascriptException ) {
			exceptionMessage = "JavascriptException====>" + CAUSE + cause + "\n " + exception.getMessage();
		}else if (exception instanceof ElementNotInteractableException) {
			exceptionMessage = "ElementNotInteractableException====>" + CAUSE + cause + "\n "
					+ exception.getMessage();
		} else if (exception instanceof ElementNotSelectableException) {
			exceptionMessage = "ElementNotSelectableException====>" + CAUSE + cause + "\n "
					+ exception.getMessage();
		} else if (exception instanceof InvalidArgumentException) {
			exceptionMessage = "InvalidArgumentException====>" + CAUSE + cause + "\n " + exception.getMessage();
		} else if (exception instanceof InvalidElementStateException) {
			exceptionMessage = "InvalidElementStateException====>" + CAUSE + cause + "\n " + exception.getMessage();
		} else if (exception instanceof InvalidSelectorException) {
			exceptionMessage = "InvalidSelectorException====>" + CAUSE + cause + "\n " + exception.getMessage();
		} else if (exception instanceof NoAlertPresentException) {
			exceptionMessage = "NoAlertPresentException====>" + CAUSE + cause + "\n " + exception.getMessage();
		} else if (exception instanceof NoSuchFrameException) {
			exceptionMessage = "NoSuchFrameException====>" + CAUSE + cause + "\n " + exception.getMessage();
		} else if (exception instanceof NoSuchWindowException) {
			exceptionMessage = "NoSuchWindowException====>" + CAUSE + cause + "\n " + exception.getMessage();
		} else if (exception instanceof StaleElementReferenceException) {
			exceptionMessage = "StaleElementReferenceException====>" + CAUSE + cause + "\n "
					+ exception.getMessage();
		} else if (exception instanceof WebDriverException) {
			exceptionMessage = "WebDriverException====>" + CAUSE + cause + "\n " + exception.getMessage();
		} else if (exception instanceof AWTException) {
			exceptionMessage = "AWTException====>" + CAUSE + cause + "\n " + exception.getMessage();
		} else if (exception instanceof InterruptedException) {
			exceptionMessage = "InterruptedException====>" + CAUSE + cause + "\n " + exception.getMessage();
		} else if (exception instanceof ProxyConfigException) {
			exceptionMessage = "ProxyConfigException====>" + CAUSE + cause + "\n " + exception.getMessage();
		} else if (exception instanceof ProxyUnavailableException) {
			exceptionMessage = "ProxyUnavailableException====>" + CAUSE + cause + "\n " + exception.getMessage();
		} else if (exception instanceof MalformedURLException) {
			exceptionMessage = "MalformedURLException====>" + CAUSE + cause + "\n " + exception.getMessage();
		} else if (exception instanceof ParserConfigurationException) {
			exceptionMessage = "ParserConfigurationException====>" + CAUSE + cause + "\n " + exception.getMessage();
		} else if (exception instanceof DOMException) {
			exceptionMessage = "DOMException====>" + CAUSE + cause + "\n " + exception.getMessage();
		} else if (exception instanceof TransformerConfigurationException) {
			exceptionMessage = "TransformerConfigurationException====>" + CAUSE + cause + "\n "
					+ exception.getMessage();
		} else if (exception instanceof TransformerException) {
			exceptionMessage = "TransformerException====>" + CAUSE + cause + "\n " + exception.getMessage();
		} else if (exception instanceof UnsupportedEncodingException) {
			exceptionMessage = "UnsupportedEncodingException====>" + CAUSE + cause + "\n " + exception.getMessage();
		} else if (exception instanceof TimeoutException) {
			exceptionMessage = "TimeoutException====>" + CAUSE + cause + "\n " + exception.getMessage();
		} else if (exception instanceof JSONException) {
			exceptionMessage = "JSONException====>" + CAUSE + cause + "\n " + exception.getMessage();
		} else if (exception instanceof NullPointerException) {
			exceptionMessage = "NullPointerException====>" + CAUSE + cause + "\n " + exception.getMessage();
		} else if (exception instanceof MavenInvocationException) {
			exceptionMessage = "MavenInvocationException====>" + CAUSE + cause + "\n " + exception.getMessage();
		} else if (exception instanceof IOException) {
			exceptionMessage = "IOException====>" + CAUSE + cause + "\n " + exception.getMessage();
		} else if (exception instanceof UnknownHostException) {
			exceptionMessage = "UnknownHostException====>" + CAUSE + cause + "\n " + exception.getMessage();
		} else {
			exceptionMessage = "Exception ====> \n " + CAUSE + cause + "\n " + exception.getMessage();
		}
		CheetahEngine.logger.logMessage(null, "CheetahException",
				exceptionMessage + "\n" + CheetahEngine.getExceptionTrace(exception), Constants.LOG_FATAL, false);
		DBExecutor.recordError(exceptionMessage, CheetahEngine.getExceptionTrace(exception));

		 if (exception instanceof ProxyConfigException) {
			 CheetahEngine.logger.logMessage(null, this.getClass().toString(), "Could not read PAC file. Terminating Test.", Constants.LOG_INFO);
				System.exit(1);
			}
		 
		return;
	}

	/**
	 * Creates a CheetahException with the specified detail message.
	 * 
	 * @param message
	 *            the detail message.
	 */
	public CheetahException(String message) {
		this(message, null);
		return;
	}

	/**
	 * Creates a new CheetahException wrapping another exception, and with a detail
	 * message.
	 * 
	 * @param message
	 *            the detail message.
	 * @param exception
	 *            the wrapped exception.
	 */
	public CheetahException(Exception exception, String message) {
		super(message);
		this.exception = exception;
		return;
	}

	/**
	 * Constructs a CheetahException with a message string, and a base exception.
	 * 
	 * @param message
	 *            Exception Msssage
	 * @param cause
	 *            Base Exception
	 */

	public CheetahException(String message, Throwable cause) {
		super(message, cause);

	}

	/**
	 * Method to return the String representation of this exception
	 * 
	 * @return String representation of this exception
	 */
	@Override
	public String toString() {
		if (exception instanceof CheetahException) {
			return ((CheetahException) exception).toString();
		}
		return exception == null ? super.toString() : exception.toString();
	}

	/**
	 * Gets the wrapped exception.
	 *
	 * @return the wrapped exception.
	 */
	public Exception getException() {
		return exception;
	}

	/**
	 * Retrieves (recursively) the root cause exception.
	 *
	 * @return the root cause exception.
	 */
	public Exception getRootCause() {
		if (exception instanceof CheetahException) {
			return ((CheetahException) exception).getRootCause();
		}
		return exception == null ? this : exception;
	}

	/**
	 * Method to get the Stack Trace
	 * 
	 * @param t
	 *            Throwable Object, whose stack trace need to be collected
	 * @return Stack Trace as a string
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public static String getStackTrace(Throwable t) throws CheetahException {
		String ret = null;

		try {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			t.printStackTrace(pw);
			pw.flush();
			ret = sw.toString();
			sw.close();
			pw.close();
		} catch (IOException e) {
			logger.logMessage(e, "Base Exceptions",
					"IO Exception:" + e.getMessage() + "\n" + CheetahEngine.getExceptionTrace(e), Constants.LOG_ERROR,
					true);
		}
		return ret;

	}

}