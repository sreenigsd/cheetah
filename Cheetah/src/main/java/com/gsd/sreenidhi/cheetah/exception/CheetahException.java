package com.gsd.sreenidhi.cheetah.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.sql.SQLException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.json.JSONException;
import org.openqa.selenium.*;
import org.w3c.dom.DOMException;

import com.gsd.sreenidhi.cheetah.engine.CheetahEngine;
import com.gsd.sreenidhi.cheetah.reporting.Log;
import com.gsd.sreenidhi.forms.Constants;

/**
 * This is the Core Exception Class for Exception handling
 */
@SuppressWarnings("serial")
public class CheetahException extends Exception {
    private Exception rootException;
    private String errorCode = "Unknown_Exception";
    public static Log logger;
    public String exceptionMessage = "";
    
    private static final String CAUSE = "Cause: ";

    public CheetahException() {
        super();
    }

    public CheetahException(Exception exception) {
        super(exception);
        this.rootException = exception;
        this.exceptionMessage = createExceptionMessage(exception);
        logException();
    }

    public CheetahException(String message) {
        super(message);
    }

    public CheetahException(String message, Throwable cause) {
        super(message, cause);
        if (cause instanceof Exception) {
            this.rootException = (Exception) cause;
        }
        this.exceptionMessage = message;
        logException();
    }

    private String createExceptionMessage(Exception exception) {
        String message = exception.getClass().getSimpleName() + " ====> Cause: ";
        String cause = (exception.getCause() != null) ? exception.getCause().toString() : "Unknown Cause";
        message += cause + "\n " + exception.getMessage();
        return message;
    }

    private void logException() {
        try {
			CheetahEngine.logger.logMessage(null, "CheetahException", 
			    exceptionMessage + "\n" + getStackTrace(this), Constants.LOG_FATAL, false);
		} catch (CheetahException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public static String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }

    public Exception getRootException() {
        return rootException;
    }
}
