package com.gsd.sreenidhi.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.gsd.sreenidhi.cheetah.engine.CheetahEngine;

/**
 * @author Sreenidhi, Gundlupet
 *
 */
public class DriverUtils extends CheetahEngine {

	/**
	 * @param element
	 *            HTML Element
	 */
	public void highlightElement(WebElement element) {

		if(CheetahEngine.cheetahForm.isHighlights()) {
			if ("WEB".equalsIgnoreCase(CheetahEngine.props.getProperty("test.type"))) {
				CheetahEngine.original_style = element.getAttribute("style");
				WebDriver driver = CheetahEngine.getDriverInstance();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style",
						"color: red; border: 3px solid red;");
			}

		}

	}

	/**
	 * @param element
	 *            HTMl Element
	 */
	public void unHighlightElement(WebElement element) {
		if(CheetahEngine.cheetahForm.isHighlights()) {
			if ("WEB".equalsIgnoreCase(CheetahEngine.props.getProperty("test.type"))) {
				WebDriver driver = CheetahEngine.getDriverInstance();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style",
						CheetahEngine.original_style);
			}

		}

	}
}