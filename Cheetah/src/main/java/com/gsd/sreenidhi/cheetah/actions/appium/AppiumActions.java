package com.gsd.sreenidhi.cheetah.actions.appium;

import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.gsd.sreenidhi.cheetah.actions.selenium.SeleniumActions;
import com.gsd.sreenidhi.cheetah.engine.CheetahEngine;
import com.gsd.sreenidhi.cheetah.exception.CheetahException;
import com.gsd.sreenidhi.forms.Constants;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

/**
 * @author Sreenidhi, Gundlupet
 *
 */
public class AppiumActions extends SeleniumActions {

	/**
	 * AppiumActions is a subclass of the SeleniumActions class. AppiumActions
	 * class implements methods that support mobile interfacing.
	 */
	public AppiumActions() {
		// Empty constructor
	}

	/**
	 * This method overrides the Selenium Actions. It is used to enter values in
	 * text fields for Appium test cases
	 * 
	 * @param locator
	 *            The element to which the text should be entered.
	 * @param value
	 *            The text value that needs to be entered.
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public static void enter_text(By locator, String value) throws CheetahException {

		try {
			wait_for_element(locator);
			WebElement element = CheetahEngine.getDriverInstance().findElement(locator);
			element.sendKeys(value);
			element.sendKeys(Keys.ENTER);
		} catch (Exception e) {
			throw new CheetahException(e);
		}

	}

	/**
	 * This function is used to enter values in text fields
	 * 
	 * @param locator
	 *            The element to which the text should be entered.
	 * @param value
	 *            The text value that needs to be entered.
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public static void android_enter_text(By locator, String value) throws CheetahException {

		try {
			wait_for_element(locator);
			WebElement element = CheetahEngine.getDriverInstance().findElement(locator);
			element.sendKeys(value);
		} catch (Exception e) {
			throw new CheetahException(e);
		}

	}

	/**
	 * This method is used to scroll up a page based on the number of steps
	 * 
	 * @param locator
	 *            The element with which you need to interface.
	 * @param swipes
	 *            The number of swipes that need to be performed.
	 * @param power
	 *            The speed with which swipes should execute.
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	@SuppressWarnings("rawtypes")
	public static void scroll_up(By locator, int swipes, int power) throws CheetahException {
		Properties properties = CheetahEngine.props;

		WebElement body = CheetahEngine.getDriverInstance().findElement(locator);
		int wide = body.getSize().width;
		int hgt = body.getSize().height;
		int startx = wide / 2;
		int endx = wide / 2;
		int starty = (int) (hgt * (0.8));
		int endy = (int) (hgt * (0.2));
		CheetahEngine.logger.logMessage(null, "AppiumActions",
				"sx= " + startx + ",sy= " + starty + ",endx= " + endx + ",endy= " + endy, Constants.LOG_INFO, false);

		// To swipe from bottom to top
		for (int x = 0; x < swipes; x = x + 1) {
			if ("ANDROID".equalsIgnoreCase(properties.getProperty("os.platform"))) {
				// ((AndroidDriver)CheetahEngine.getDriverInstance()).swipe(startx,
				// starty, endx, endy, power);
				new TouchAction((AndroidDriver) CheetahEngine.getDriverInstance()).longPress(startx, starty)
						.moveTo(endx, endy).release().perform();
			} else {
				// ((IOSDriver)CheetahEngine.getDriverInstance()).swipe(startx,
				// starty, endx, endy, power);
				new TouchAction((IOSDriver) CheetahEngine.getDriverInstance()).longPress(startx, starty)
						.moveTo(endx, endy).release().perform();
			}
		}

	}

	/**
	 * This method is used to scroll down a page based on the number of steps
	 * 
	 * @param locator
	 *            The element with which you need to interface.
	 * @param swipes
	 *            The number of swipes that need to be performed.
	 * @param power
	 *            The speed with which swipes should execute.
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	@SuppressWarnings("rawtypes")
	public static void scroll_down(By locator, int swipes, int power) throws CheetahException {
		Properties properties = CheetahEngine.props;

		WebElement body = CheetahEngine.getDriverInstance().findElement(locator);
		int wide = body.getSize().width;
		int hgt = body.getSize().height;
		int startx = wide / 2;
		int endx = wide / 2;
		int starty = (int) (hgt * (0.3));
		int endy = (int) (hgt * (0.7));
		CheetahEngine.logger.logMessage(null, "AppiumActions",
				"sx= " + startx + ",sy= " + starty + ",endx= " + endx + ",endy= " + endy, Constants.LOG_INFO, false);

		// To swipe from top to bottom
		for (int x = 0; x < swipes; x = x + 1) {
			if ("ANDROID".equalsIgnoreCase(properties.getProperty("os.platform"))) {
				// ((AndroidDriver)
				// CheetahEngine.getDriverInstance()).swipe(startx, starty, endx,
				// endy, power);
				new TouchAction((AndroidDriver) CheetahEngine.getDriverInstance()).longPress(startx, starty)
						.moveTo(endx, endy).release().perform();
			} else {
				// ((IOSDriver) CheetahEngine.getDriverInstance()).swipe(startx,
				// starty, endx, endy, power);
				new TouchAction((IOSDriver) CheetahEngine.getDriverInstance()).longPress(startx, starty)
						.moveTo(endx, endy).release().perform();
			}
		}
	}

}