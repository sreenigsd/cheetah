package com.gsd.sreenidhi.cheetah.actions.appium;

import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.gsd.sreenidhi.cheetah.actions.selenium.SeleniumActions;
import com.gsd.sreenidhi.cheetah.engine.CheetahEngine;
import com.gsd.sreenidhi.cheetah.exception.CheetahException;
import com.gsd.sreenidhi.forms.Constants;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.offset.PointOption;

import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.time.Duration;
import java.util.Arrays;

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
		for (int x = 0; x < swipes; x++) {
	        scroll(CheetahEngine.getDriverInstance(), startx, starty, endx, endy);
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
	private static void scroll(WebDriver driver, int startX, int startY, int endX, int endY) {
	    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
	    Sequence sequence = new Sequence(finger, 0);

	    sequence.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
	    sequence.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
	    sequence.addAction(new Pause(finger, Duration.ofMillis(200))); // Pause duration can be adjusted
	    sequence.addAction(finger.createPointerMove(Duration.ofMillis(600), PointerInput.Origin.viewport(), endX, endY));
	    sequence.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

	    ((RemoteWebDriver) driver).perform(Arrays.asList(sequence));
	}

}