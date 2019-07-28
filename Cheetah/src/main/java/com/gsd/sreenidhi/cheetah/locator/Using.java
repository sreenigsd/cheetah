package com.gsd.sreenidhi.cheetah.locator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.openqa.selenium.JavascriptExecutor;

import com.gsd.sreenidhi.cheetah.engine.CheetahEngine;
import com.gsd.sreenidhi.cheetah.exception.CheetahException;
import com.gsd.sreenidhi.forms.Constants;
import com.gsd.sreenidhi.utils.FileUtils;

/**
 * @author Gundlupet Sreenidhi - sreenidhi.gsd@gmail.com
 *
 */
public class Using {

	public Using() {
		// Constructor;
	}

	public static String ID(String value) {
		return "document.getElementById(\"" + value + "\")";

	}

	public static String Name(String value) {
		return "document.getElementsByName(\"" + value + "\")[0]";

	}

	public static String ClassName(String value) {
		return "document.getElementsByClassName(\"" + value + "\")[0]";

	}

	public static String CSSSelector(String value) {
		return "document.querySelector(\"" + value + "\")";

	}

	public static String TagValue(String value) {
		return "document.getElementsByTagName(\"" + value + "\")[0]";

	}

	public static String XPath(String value) throws CheetahException {
		
		CheetahEngine.logger.logMessage(null, Using.class.getName(), "Evaluating XPath with wgxpath : " + value, Constants.LOG_INFO);

		FileUtils fu = new FileUtils();
		File f = fu.getScript("wgxpath.install.js");

		String line = null;
		StringBuffer sb = new StringBuffer();

		FileReader fileReader;
		String evaluate = null;
		try {
			fileReader = new FileReader(f.getAbsolutePath());

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				sb.append(line);
			}

			// Always close files.
			bufferedReader.close();

			JavascriptExecutor js = (JavascriptExecutor) CheetahEngine.getDriverInstance();
			js.executeScript(sb.toString());

			js.executeScript("wgxpath.install()");

			evaluate = "document.evaluate(\"" + value
					+ "\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null ).singleNodeValue";

		} catch (FileNotFoundException e) {
			throw new CheetahException(e);
		} catch (IOException e) {
			throw new CheetahException(e);
		}

		CheetahEngine.logger.logMessage(null, Using.class.getName(), "wgxpath evaluate expression:  " + evaluate, Constants.LOG_INFO);
		return evaluate;

	}

}
