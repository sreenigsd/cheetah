package com.gsd.sreenidhi.cheetah.actions.selenium;

import static org.junit.Assert.assertEquals;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.codehaus.plexus.util.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.Augmenter;
//import org.openqa.selenium.security.Credentials;
//import org.openqa.selenium.security.UserAndPassword;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;
import com.gsd.sreenidhi.forms.Constants;
import com.gsd.sreenidhi.cheetah.actions.FormationStringStack;
import com.gsd.sreenidhi.cheetah.engine.CheetahEngine;
import com.gsd.sreenidhi.cheetah.engine.WindowHandleStack;
import com.gsd.sreenidhi.cheetah.exception.CheetahException;
import com.gsd.sreenidhi.utils.DriverUtils;

/**
 * This is the Core Actions for Selenium - web app functionality
 * 
 * @author Sreenidhi, Gundlupet
 *
 */

public class SeleniumActions {

	static DriverUtils driverUtils = new DriverUtils();

	protected static final int DEFAULT_TIMEOUT = 10;
	protected static WebDriver driver;
	protected static String testName = new String();

	/**
	 * This function navigates to url
	 * 
	 * @param url The URL of the application that the driver should launch
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void navigate_to(String url) throws CheetahException {
		try {
			CheetahEngine.getDriverInstance().navigate().to(url);
		} catch (Exception e) {
			throw new CheetahException(e);
		}
	}

	/**
	 * This function verifies an element is displayed
	 * 
	 * @param locator HTML element locator
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void verify_element_is_displayed(By locator) throws CheetahException {
		try {
			wait_for_element(locator);
			WebElement element = CheetahEngine.getDriverInstance().findElement(locator);
			driverUtils.highlightElement(element);
			driverUtils.unHighlightElement(element);
			assertEquals(true, element.isDisplayed());
		} catch (Exception e) {
			throw new CheetahException(e);

		}

	}

	/**
	 * This function verifies an element is NOT displayed
	 * 
	 * @param locator HTML element locator
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void verify_element_is_not_displayed(By locator) throws CheetahException {
		try {
			WebElement element = CheetahEngine.getDriverInstance().findElement(locator);
			assertEquals(false, element.isDisplayed());
		} catch (Exception e) {
			throw new CheetahException(e);
		}
	}

	/**
	 * This function is used to enter values in text fields
	 * 
	 * @param locator HTML element locator
	 * @param value   The String value that needs to be entered in the HTML element
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void enter_text(By locator, String value) throws CheetahException {
		try {
			wait_for_element(locator);
			WebElement element = CheetahEngine.getDriverInstance().findElement(locator);
			driverUtils.highlightElement(element);
			driverUtils.unHighlightElement(element);
			element.sendKeys(value);
		} catch (Exception e) {
			throw new CheetahException(e);

		}

	}

	/**
	 * @param locator HTML element locator
	 * @return The test displayed for the HTML element
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static String gettext(By locator) throws CheetahException {
		try {
			String value;
			wait_for_element(locator);
			WebElement element = CheetahEngine.getDriverInstance().findElement(locator);
			driverUtils.highlightElement(element);
			driverUtils.unHighlightElement(element);
			value = element.getText();
			// value=element.getAttribute(arg0)
			return value;
		} catch (Exception e) {
			throw new CheetahException(e);

		}

	}

	/**
	 * This method is used to simulate the Keyboard "Enter" key
	 * 
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void enter() throws CheetahException {
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		} catch (Exception e) {
			throw new CheetahException(e);

		}

	}

	/**
	 * This method is used to simulate the Keyboard "Esc" key
	 * 
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void escape_key() throws CheetahException {
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_ESCAPE);
			robot.keyRelease(KeyEvent.VK_ESCAPE);
		} catch (Exception e) {
			throw new CheetahException(e);

		}

	}

	/**
	 * This function is used to clear text in text fields
	 * 
	 * @param locator HTML element locator
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void clear_text(By locator) throws CheetahException {
		try {
			wait_for_element(locator);
			WebElement element = CheetahEngine.getDriverInstance().findElement(locator);
			driverUtils.highlightElement(element);
			driverUtils.unHighlightElement(element);
			element.clear();
		} catch (Exception e) {
			throw new CheetahException(e);

		}

	}

	/**
	 * This function is used to click on element
	 * 
	 * @param locator HTML element locator
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void click(By locator) throws CheetahException {
		try {
			wait_for_element(locator);
			WebElement element = CheetahEngine.getDriverInstance().findElement(locator);
			driverUtils.highlightElement(element);
			driverUtils.unHighlightElement(element);
			element.click();
		} catch (Exception e) {
			throw new CheetahException(e);
		}
	}

	/**
	 * This function is used to double-click on element
	 * 
	 * @param locator HTML element locator
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void doubleClick(By locator) throws CheetahException {
		try {
			wait_for_element(locator);
			WebElement element = CheetahEngine.getDriverInstance().findElement(locator);
			driverUtils.highlightElement(element);
			driverUtils.unHighlightElement(element);

			Actions action = new Actions(CheetahEngine.getDriverInstance());
			// Double click
			action.doubleClick(element).perform();
		} catch (Exception e) {
			throw new CheetahException(e);
		}
	}

	/**
	 * This function is used to fetch the text from an element
	 * 
	 * @param locator      HTML element locator
	 * @param expectedText The text value against which the element text should be
	 *                     verified
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void verify_element_text(By locator, String expectedText) throws CheetahException {
		try {
			wait_for_element(locator);
			WebElement element = CheetahEngine.getDriverInstance().findElement(locator);
			driverUtils.highlightElement(element);
			driverUtils.unHighlightElement(element);
			assertEquals(element.getText().contains(expectedText.trim()), true);
		} catch (Exception e) {
			throw new CheetahException(e);
		}
	}

	/**
	 * This function is used for fetch the value from an object using ID
	 * 
	 * @param partialUrl The subset value that has to be verified in the URL
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void verify_url(CharSequence partialUrl) throws CheetahException {
		try {
			WebDriverWait wait = new WebDriverWait(CheetahEngine.getDriverInstance(), 15);
			wait.until(ExpectedConditions.urlContains((String) partialUrl));
		} catch (Exception e) {
			throw new CheetahException(e);
		}
	}

	/**
	 * This function is used for fetch the value from an object using ID
	 * 
	 * @param expectedTitle The title of the browser
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void verify_browser_title(String expectedTitle) throws CheetahException {
		try {
			WebDriverWait wait = new WebDriverWait(CheetahEngine.getDriverInstance(), 2);
			wait.until(ExpectedConditions.titleContains(expectedTitle));

		} catch (NoSuchElementException e) {
			throw new CheetahException(e);
		} catch (Exception e) {
			throw new CheetahException(e);
		}
	}

	/**
	 * @return Browser Title
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static String get_browser_title() throws CheetahException {
		String browserTitle;
		try {
			WebDriverWait wait = new WebDriverWait(CheetahEngine.getDriverInstance(), 2);
			browserTitle = CheetahEngine.getDriverInstance().getTitle();
		} catch (Exception e) {
			throw new CheetahException(e);
		}
		return browserTitle;
	}

	/**
	 * This function is used for selecting an option by value
	 * 
	 * @param locator HTML element locator
	 * @param value   The value that needs to be selected
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void select_by_value(By locator, String value) throws CheetahException {
		try {
			wait_for_element(locator);
			Select element = new Select(CheetahEngine.getDriverInstance().findElement(locator));

			WebElement elem = CheetahEngine.getDriverInstance().findElement(locator);
			driverUtils.highlightElement(elem);
			driverUtils.unHighlightElement(elem);

			element.selectByVisibleText(value);

		} catch (Exception e) {
			throw new CheetahException(e);
		}
	}

	/**
	 * This function is used for selecting an option by index
	 * 
	 * @param locator HTML element locator
	 * @param index   The index of the value that needs to be selected. Index always
	 *                starts from 0.
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void select_by_index(By locator, int index) throws CheetahException {
		try {
			wait_for_element(locator);
			Select element = new Select(CheetahEngine.getDriverInstance().findElement(locator));
			WebElement elem = CheetahEngine.getDriverInstance().findElement(locator);
			driverUtils.highlightElement(elem);
			driverUtils.unHighlightElement(elem);
			element.selectByIndex(index);
		} catch (Exception e) {
			throw new CheetahException(e);
		}
	}

	/**
	 * This function is used for selecting an option by visibletext
	 * 
	 * @param locator HTML element locator
	 * @param value   The value that needs to be selected
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void select_by_visible_text(By locator, String value) throws CheetahException {
		try {
			wait_for_element(locator);
			Select element = new Select(CheetahEngine.getDriverInstance().findElement(locator));
			WebElement elem = CheetahEngine.getDriverInstance().findElement(locator);
			driverUtils.highlightElement(elem);
			driverUtils.unHighlightElement(elem);

			element.selectByVisibleText(value);

		} catch (Exception e) {
			throw new CheetahException(e);
		}
	}

	/**
	 * This function is used to close the browser
	 * 
	 * @param driver The driver that needs to be closed.
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void close_browser(WebDriver driver) throws CheetahException {
		try {
			CheetahEngine.getDriverInstance().close();
		} catch (Exception e) {
			throw new CheetahException(e);
		}
	}

	/**
	 * This function verifies a element is selected
	 * 
	 * @param locator HTML element locator
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void verify_element_is_selected(By locator) throws CheetahException {
		try {
			wait_for_element(locator);
			WebElement element = CheetahEngine.getDriverInstance().findElement(locator);
			driverUtils.highlightElement(element);
			driverUtils.unHighlightElement(element);
			assertEquals(true, element.isSelected());

		} catch (Exception e) {
			throw new CheetahException(e);
		}

	}

	/**
	 * This function is used to refresh the page
	 * 
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public void refresh_page() throws CheetahException {
		try {
			CheetahEngine.getDriverInstance().navigate().refresh();
		} catch (Exception e) {
			throw new CheetahException(e);
		}

	}

	/**
	 * This function is used to navigate back
	 * 
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public void navigate_back() throws CheetahException {
		try {
			CheetahEngine.getDriverInstance().navigate().back();
		} catch (Exception e) {
			throw new CheetahException(e);
		}
	}

	/**
	 * This function is used to navigate forward
	 * 
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public void navigate_forward() throws CheetahException {
		try {
			CheetahEngine.getDriverInstance().navigate().forward();
		} catch (Exception e) {
			throw new CheetahException(e);
		}
	}

	/**
	 * This function is used to refresh the page
	 * 
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public void navigate_pageRefresh() throws CheetahException {
		try {
			CheetahEngine.getDriverInstance().navigate().refresh();
		} catch (Exception e) {
			throw new CheetahException(e);
		}
	}

	/**
	 * This function is used to open the new tab
	 * 
	 * @param locator HTML element locator
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void new_tab(By locator) throws CheetahException {
		try {
			WebElement element = CheetahEngine.getDriverInstance().findElement(locator);
			driverUtils.highlightElement(element);
			driverUtils.unHighlightElement(element);
			element.sendKeys(Keys.CONTROL, "t");
		} catch (Exception e) {
			throw new CheetahException(e);
		}

	}

	/**
	 * This function is used to close the current tab
	 * 
	 * @param locator HTML element locator
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void close_current_tab(By locator) throws CheetahException {
		try {
			WebElement element = CheetahEngine.getDriverInstance().findElement(locator);
			driverUtils.highlightElement(element);
			driverUtils.unHighlightElement(element);
			element.sendKeys(Keys.CONTROL, "w");
		} catch (Exception e) {
			throw new CheetahException(e);
		}
	}

	/**
	 * This function is used to handle the alerts
	 * 
	 * @param action Alert Action - ACCEPT - DISMISS
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void handle_alert(String action) throws CheetahException {
		try {
			WebDriverWait wait = new WebDriverWait(CheetahEngine.getDriverInstance(), 2);
			wait.until(ExpectedConditions.alertIsPresent());
			if ("dismiss".equalsIgnoreCase(action)) {
				CheetahEngine.getDriverInstance().switchTo().alert().dismiss();
			} else if ("accept".equalsIgnoreCase(action)) {
				CheetahEngine.getDriverInstance().switchTo().alert().accept();
			}
		} catch (Exception e) {
			throw new CheetahException(e);
		}
	}

	/**
	 * This function is used to wait for the elements
	 * 
	 * @param locator HTML element locator
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void wait_for_element(By locator) throws CheetahException {
		try {
			CheetahEngine.logger.logMessage(null, "SeleniumActions", "Locator: " + locator, Constants.LOG_INFO, false);
			WebDriverWait wait = new WebDriverWait(CheetahEngine.getDriverInstance(), Constants.GLOBAL_TIMEOUT);
			fluent_wait(locator);
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		} catch (Exception e) {
			throw new CheetahException(e);
		}
	}

	/**
	 * This function is used for selecting the Check box
	 * 
	 * @param locator HTML element locator
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void check_box(By locator) throws CheetahException {
		try {
			wait_for_element(locator);
			WebElement element = CheetahEngine.getDriverInstance().findElement(locator);
			driverUtils.highlightElement(element);
			driverUtils.unHighlightElement(element);
			element.click();

		} catch (Exception e) {
			throw new CheetahException(e);
		}
	}

	/**
	 * This function is used for unchecking a checkbox
	 * 
	 * @param locator HTML element locator
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void uncheck_box(By locator) throws CheetahException {
		try {
			wait_for_element(locator);
			WebElement element = CheetahEngine.getDriverInstance().findElement(locator);
			driverUtils.highlightElement(element);
			driverUtils.unHighlightElement(element);
			element.click();

		} catch (Exception e) {
			throw new CheetahException(e);
		}

	}

	/**
	 * This function is used to upload a file
	 * 
	 * @param locator  HTML element locator
	 * @param filePath Name of the file that must be used for the upload.
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void upload_file(By locator, String filePath) throws CheetahException {
		try {
			wait_for_element(locator);
			WebElement element = CheetahEngine.getDriverInstance().findElement(locator);
			driverUtils.highlightElement(element);
			driverUtils.unHighlightElement(element);
			element.sendKeys(filePath);

		} catch (Exception e) {
			throw new CheetahException(e);
		}
	}

	/**
	 * This function verifies the element is enabled
	 * 
	 * @param locator HTML element locator
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void verify_element_is_enabled(By locator) throws CheetahException {
		try {
			wait_for_element(locator);
			WebElement element = CheetahEngine.getDriverInstance().findElement(locator);
			driverUtils.highlightElement(element);
			driverUtils.unHighlightElement(element);
			assertEquals(true, element.isEnabled());

		} catch (Exception e) {
			throw new CheetahException(e);
		}
	}

	/**
	 * This function verifies the element is enabled
	 * 
	 * @param locator HTML element locator
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void verify_element_is_disabled(By locator) throws CheetahException {
		try {
			wait_for_element(locator);
			WebElement element = CheetahEngine.getDriverInstance().findElement(locator);
			driverUtils.highlightElement(element);
			driverUtils.unHighlightElement(element);
			assertEquals(true, !element.isEnabled());

		} catch (Exception e) {
			throw new CheetahException(e);
		}

	}

	/**
	 * @param locator HTML element locator
	 * @return boolean
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static boolean element_is_disabled(By locator) throws CheetahException {
		try {
			boolean disable;
			wait_for_element(locator);
			WebElement element = CheetahEngine.getDriverInstance().findElement(locator);
			driverUtils.highlightElement(element);
			driverUtils.unHighlightElement(element);
			disable = !element.isEnabled();
			return disable;

		} catch (Exception e) {
			throw new CheetahException(e);
		}

	}

	/**
	 * This function handles alert box
	 * 
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void alertbox_Accept() throws CheetahException {
		try {
			System.out.println("Step into alert box");
			Alert simpleAlert = CheetahEngine.getDriverInstance().switchTo().alert();
			simpleAlert.accept();
		} catch (Exception e) {
			throw new CheetahException(e);
		}
	}

	/**
	 * This function handles alert box
	 * 
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void alertbox_Reject() throws CheetahException {
		try {
			Alert simpleAlert = CheetahEngine.getDriverInstance().switchTo().alert();
			simpleAlert.dismiss();
		} catch (Exception e) {
			throw new CheetahException(e);
		}
	}

	/**
	 * This function handles alert box
	 * 
	 * @param text Test value that must be sent to the alert box
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void alertbox_sendText(String text) throws CheetahException {
		try {
			Alert simpleAlert = CheetahEngine.getDriverInstance().switchTo().alert();
			simpleAlert.sendKeys(text);
			simpleAlert.accept();
		} catch (Exception e) {
			throw new CheetahException(e);
		}
	}

	/**
	 * This function handles frames - switch to new frame
	 * 
	 * @param frameName Name of the frame to which you need to switch.
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void switch_to_frame(String frameName) throws CheetahException {
		CheetahEngine.logger.logMessage(null, "SeleniumActions", "Attempting to Switch frame: " + frameName,
				Constants.LOG_INFO);
		try {
			CheetahEngine.getDriverInstance().switchTo().frame(frameName);
		} catch (Exception e) {
			throw new CheetahException(e);
		}
	}

	/**
	 * This function handles frames - switch to previous frame
	 * 
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void switch_back_frame() throws CheetahException {
		CheetahEngine.logger.logMessage(null, "SeleniumActions", "Attempting to Switch back from frame",
				Constants.LOG_INFO);

		try {
			CheetahEngine.getDriverInstance().switchTo().defaultContent();
		} catch (Exception e) {
			throw new CheetahException(e);
		}
	}

	/**
	 * This function is used for Window handling - switch to new window
	 * 
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void switchtoWindows() throws CheetahException {
		CheetahEngine.logger.logMessage(null, "SeleniumActions", "Attempting to Switch to Window", Constants.LOG_INFO);
		try {
			Thread.sleep(3000);
			String winHandleBefore = CheetahEngine.getDriverInstance().getWindowHandle();
			CheetahEngine.cheetahForm.setParentHandle(winHandleBefore);
			CheetahEngine.handleStack.push(winHandleBefore);
			CheetahEngine.logger.logMessage(null, "SeleniumActions", "Parent Window Handle: " + winHandleBefore,
					Constants.LOG_INFO);
			Set<String> handles = CheetahEngine.getDriverInstance().getWindowHandles();
			for (String handle : handles) {
				if (!handle.equalsIgnoreCase(winHandleBefore) && !checkHandleInStack(handle)) {
					Thread.sleep(5000);
					CheetahEngine.logger.logMessage(null, "SeleniumActions",
							"Switching to Child Windows Handle: " + handle, Constants.LOG_INFO);
					CheetahEngine.getDriverInstance().switchTo().window(handle);
					String winHandleChild = handle;
					CheetahEngine.cheetahForm.setChildHandle(winHandleChild);
				}
				Thread.sleep(3000);
			}
		} catch (InterruptedException e) {
			throw new CheetahException(e);
		} catch (Exception e) {
			throw new CheetahException(e);
		}
	}

	/**
	 * @param handle Window Handle
	 * @return boolean
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	private static boolean checkHandleInStack(String handle) throws CheetahException {
		boolean itemExists = false;
		FormationStringStack stack = new FormationStringStack(Constants.windowStackSize);
		if (CheetahEngine.handleStack != null) {
			while (!CheetahEngine.handleStack.isEmpty()) {
				String item = CheetahEngine.handleStack.pop();
				if (!item.trim().equalsIgnoreCase(handle.trim())) {
					stack.push(item);
				} else {
					itemExists = true;
				}
			}
			CheetahEngine.handleStack = new WindowHandleStack(Constants.windowStackSize);

			while (!stack.isEmpty()) {
				CheetahEngine.handleStack.push(stack.pop());
			}
		} else {
			Exception e = new Exception("!!!!!!!!!!!!!!!!  --- Window Handle Stack is empty. --- !!!!!!!!!!!!!!!!");
			throw new CheetahException(e);
		}

		return itemExists;
	}

	/**
	 * This function is used for Window handling - switch to parent window
	 * 
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void switchBackWindows() throws CheetahException {
		CheetahEngine.logger.logMessage(null, "SeleniumActions", "Attempting to Switch Back to parent Window",
				Constants.LOG_INFO);
		String parentHandle;
		try {
			Thread.sleep(3000);
			parentHandle = CheetahEngine.handleStack.pop();
			CheetahEngine.logger.logMessage(null, "SeleniumActions", "Switching to parent Window Handle: " + parentHandle,
					Constants.LOG_INFO);
			CheetahEngine.getDriverInstance().switchTo().window(parentHandle);
		} catch (InterruptedException e) {
			throw new CheetahException(e);
		}

	}

	/**
	 * @return integer
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static int getcountopenWindows() throws CheetahException {
		int count;
		try {
			Set<String> handles = CheetahEngine.getDriverInstance().getWindowHandles();
			count = handles.size();
			return count;
		} catch (Exception e) {
			throw new CheetahException(e);
		}
	}

	/**
	 * This function is used for Window handling - close new window
	 * 
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void closeswitchedWindows() throws CheetahException {
		String parentHandle;
		try {
			Thread.sleep(5000);
			CheetahEngine.getDriverInstance().switchTo().window(CheetahEngine.cheetahForm.getChildHandle()).close();
			Thread.sleep(3000);
			parentHandle = CheetahEngine.handleStack.pop();
			CheetahEngine.getDriverInstance().switchTo().window(CheetahEngine.cheetahForm.getParentHandle());
			Thread.sleep(2000);
		} catch (Exception e) {
			throw new CheetahException(e);
		}
	}

	/**
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void downloadfile() throws CheetahException {

		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);

			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		} catch (Exception e) {
			throw new CheetahException(e);
		}
	}

	/**
	 * This function is used to press any key on the keyboard
	 * 
	 * @param keyEvent Keyboard Key value
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void Keyboard_button(int keyEvent) throws CheetahException {
		try {
			Robot robot = new Robot();

			robot.keyPress(keyEvent);
			robot.keyRelease(keyEvent);
		} catch (Exception e) {
			throw new CheetahException(e);
		}

	}

	/**
	 * This method is used to retrieve the current date in MM/dd/yyyy format
	 * 
	 * @return current date in String format MM/dd/yyyy
	 */
	public static String currentdate() {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		return dateFormat.format(date);

	}

	/**
	 * Method calculates last working day for last day of month as input
	 * 
	 * @return previous working date in String format
	 */
	public static String previousworkingdate() {
		LocalDate lastbusinessday = getLastWorkingDayOfMonth(LocalDate.now());
		int x = lastbusinessday.getDayOfMonth();
		String y = String.valueOf(x);
		if (y.length() == 1)
			y = "0" + y;
		return String.valueOf(y);

	}

	/**
	 * Method calculates last working day for last day of month as input
	 * 
	 * @param lastDayOfMonth Last day of the Month
	 * @return LocalDate instance containing last working day
	 */
	public static LocalDate getLastWorkingDayOfMonth(LocalDate lastDayOfMonth) {
		LocalDate lastWorkingDayofMonth;
		switch (DayOfWeek.of(lastDayOfMonth.get(ChronoField.DAY_OF_WEEK))) {
		case MONDAY:
			lastWorkingDayofMonth = lastDayOfMonth.minusDays(3);
			break;
		case SUNDAY:
			lastWorkingDayofMonth = lastDayOfMonth.minusDays(2);
			break;
		default:
			lastWorkingDayofMonth = lastDayOfMonth.minusDays(1);
		}
		return lastWorkingDayofMonth;
	}

	/**
	 * This method is used for checking if dropdown accept multiple selections
	 *
	 * @param locator HTML element locator
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void ismultple(By locator) throws CheetahException {
		wait_for_element(locator);
		Select element = new Select(CheetahEngine.getDriverInstance().findElement(locator));
		WebElement elem = CheetahEngine.getDriverInstance().findElement(locator);
		driverUtils.highlightElement(elem);
		driverUtils.unHighlightElement(elem);

		element.isMultiple();

	}

	/**
	 * This method is used for mouse_over actions. Click on a sub-element after
	 * hovering on main element
	 * 
	 * @param locator  HTML Locator that identifies the menu item
	 * @param locator1 HTML Locator that identifies the link within the menu
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void mouse_over(By locator, By locator1) throws CheetahException {
		try {
			Actions action = new Actions(CheetahEngine.getDriverInstance());
			WebDriverWait wait = new WebDriverWait(CheetahEngine.getDriverInstance(), 15);
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			WebElement element = CheetahEngine.getDriverInstance().findElement(locator);
			driverUtils.highlightElement(element);
			driverUtils.unHighlightElement(element);
			action.moveToElement(element).moveToElement(CheetahEngine.getDriverInstance().findElement(locator1)).click()
					.build().perform();
		} catch (Exception e) {
			throw new CheetahException(e);
		}
	}

	/**
	 * @param locator HTML element locator
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void selectradio(By locator) throws CheetahException {
		{

			try {
				List<WebElement> radioGroup1 = CheetahEngine.getDriverInstance().findElements(locator);

				driverUtils.highlightElement(radioGroup1.get(1));
				driverUtils.unHighlightElement(radioGroup1.get(1));

				radioGroup1.get(1).click();
			} catch (Exception e) {
				throw new CheetahException(e);
			}
		}

	}

	/**
	 * @param locator1 HTMl Locator that identifies the date picker
	 * @param locator2 HTMl Locator that identifies the value of the date picker
	 * @param mon      String Value for month. Example: "January", "February"...
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void Date_picker(By locator1, By locator2, String mon) throws CheetahException {
		try {

			WebDriverWait wait = new WebDriverWait(CheetahEngine.getDriverInstance(), 15);
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator1));
			WebElement element = CheetahEngine.getDriverInstance().findElement(locator1);
			driverUtils.highlightElement(element);
			driverUtils.unHighlightElement(element);
			element.click();
			Thread.sleep(2000);
			element.sendKeys(mon);
			Thread.sleep(2000);
			element.click();
			Thread.sleep(2000);
			element = CheetahEngine.getDriverInstance().findElement(locator2);
			element.click();

		} catch (Exception e) {
			throw new CheetahException(e);
		}
	}

	/**
	 * @param locator1 HTML Locator that identifies the menu item
	 * @param locator2 HTML Locator that identifies the link within the menu
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void mouse_over1(By locator1, By locator2) throws CheetahException {

		Actions a1 = new Actions(CheetahEngine.getDriverInstance());
		a1.moveToElement(CheetahEngine.getDriverInstance().findElement(locator1)).build().perform();
		WebElement we1 = CheetahEngine.getDriverInstance().findElement(locator2);

		if (we1.isDisplayed()) {
			driverUtils.highlightElement(we1);
			driverUtils.unHighlightElement(we1);
			we1.click();
		}

	}

	/**
	 * @param time - sleep time (in secs)
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 * 
	 */
	public static void time_wait(int time) throws CheetahException {

		try {
			Thread.sleep(time * 1000);
		} catch (InterruptedException e) {
			throw new CheetahException(e);
		}
	}

	/**
	 * @param locator HTML element locator
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 * 
	 */
	public static void scroll_home(By locator) throws CheetahException {
		WebElement element = CheetahEngine.getDriverInstance().findElement(locator);
		element.sendKeys(Keys.HOME);

	}

	/**
	 * @param locator HTML element locator
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 *
	 */
	public static void scroll_end(By locator) throws CheetahException {
		WebElement element = CheetahEngine.getDriverInstance().findElement(locator);
		element.sendKeys(Keys.END);
	}

	/**
	 * @param locator HTML element locator
	 * @param counter Integer value for number of iterations
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 * 
	 */
	public static void scroll_down_element(By locator, int counter) throws CheetahException {
		WebElement element = CheetahEngine.getDriverInstance().findElement(locator);

		for (int i = 0; i < counter; i++) {
			// click(locator);
			element.sendKeys(Keys.PAGE_DOWN);
		}

	}

	/**
	 * @param locator HTML element locator
	 * @param counter Integer value for number of iterations
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 *
	 */
	public static void scroll_up_element(By locator, int counter) throws CheetahException {
		WebElement element = CheetahEngine.getDriverInstance().findElement(locator);
		for (int i = 0; i < counter; i++) {
			// click(locator);
			element.sendKeys(Keys.PAGE_UP);
		}

	}

	/**
	 * @param locator HTML element locator
	 * @param counter Integer value for number of iterations
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 * 
	 */
	public static void scroll_left_element(By locator, int counter) throws CheetahException {
		WebElement element = CheetahEngine.getDriverInstance().findElement(locator);
		for (int i = 0; i < counter; i++) {
			// click(locator);
			element.sendKeys(Keys.LEFT);
		}

	}

	/**
	 * @param locator HTML element locator
	 * @param counter Integer value for number of iterations
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 * 
	 */
	public static void scroll_right_element(By locator, int counter) throws CheetahException {
		WebElement element = CheetahEngine.getDriverInstance().findElement(locator);

		for (int i = 0; i < counter; i++) {
			// click(locator);
			element.sendKeys(Keys.RIGHT);
		}

	}

	/**
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void validate_jQuery_load() throws CheetahException {
		CheetahEngine.logger.logMessage(null, "SeleniumActions", "Validating AJAX and JQuery load.", Constants.LOG_INFO,
				false);
		Boolean isJqueryUsed = (Boolean) ((JavascriptExecutor) CheetahEngine.getDriverInstance())
				.executeScript("return (typeof(jQuery) != 'undefined')");
		if (isJqueryUsed) {
			CheetahEngine.logger.logMessage(null, "SeleniumActions", "JQuery identified", Constants.LOG_INFO, false);
			while (true) {
				// JavaScript test to verify jQuery is active or not
				Boolean ajaxIsComplete = (Boolean) (((JavascriptExecutor) CheetahEngine.getDriverInstance())
						.executeScript("return jQuery.active == 0"));
				if (ajaxIsComplete)
					break;
				try {
					CheetahEngine.logger.logMessage(null, "SeleniumActions", "AJAX load in progress.", Constants.LOG_INFO,
							false);
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					throw new CheetahException(e);
				}
			}
		}
	}

	/**
	 * This method is used to check if ajax and jQuery loads exist on the page
	 * before an operation can be performed.
	 * 
	 * @return boolean flag indication whether JQuery / AJAX / JS load is
	 *         application for the page
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static boolean validate_ajax_jQuery_load() throws CheetahException {
		WebDriverWait wait = new WebDriverWait(CheetahEngine.getDriverInstance(), 30);
		CheetahEngine.logger.logMessage(null, "SeleniumActions", "Validating AJAX and JQuery load.", Constants.LOG_INFO,
				false);

		// wait for jQuery to load
		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					CheetahEngine.logger.logMessage(null, "SeleniumActions", "JQuery identified", Constants.LOG_INFO,
							false);
					return ((Long) ((JavascriptExecutor) CheetahEngine.getDriverInstance())
							.executeScript("return jQuery.active") == 0);
				} catch (Exception e) {
					try {
						CheetahEngine.logger.logMessage(null, "SeleniumActions", "No Query Present", Constants.LOG_INFO,
								false);
					} catch (CheetahException e1) {
						e1.printStackTrace();
					}
					return true;
				}
			}
		};

		// wait for Javascript to load
		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					CheetahEngine.logger.logMessage(null, "SeleniumActions", "JS identified", Constants.LOG_INFO, false);
				} catch (CheetahException e) {
					e.printStackTrace();
				}

				return ((JavascriptExecutor) CheetahEngine.getDriverInstance())
						.executeScript("return document.readyState").toString().equals("complete");

			}
		};

		return wait.until(jQueryLoad) && wait.until(jsLoad);
	}

	/**
	 * @param locator HTML element locator
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void scroll_element_to_view(By locator) throws CheetahException {
		WebElement element = CheetahEngine.getDriverInstance().findElement(locator);
		((JavascriptExecutor) CheetahEngine.getDriverInstance()).executeScript("arguments[0].scrollIntoView(true);",
				element);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			throw new CheetahException(e);
		}

	}

	/**
	 * @param locator HTML element locator
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void move_to_element_action(By locator) throws CheetahException {
		WebElement element = CheetahEngine.getDriverInstance().findElement(locator);
		Actions actions = new Actions(CheetahEngine.getDriverInstance());
		actions.moveToElement(element);
		actions.perform();

	}

	/**
	 * @param userName User Name
	 * @param password Password
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void uac_login_robot(String userName, String password) throws CheetahException {
		Robot rb;
		try {
			rb = new Robot();

			Thread.sleep(200);

			// Enter user name by ctrl-v
			StringSelection username = new StringSelection(userName);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(username, null);
			rb.keyPress(KeyEvent.VK_CONTROL);
			rb.keyPress(KeyEvent.VK_V);
			rb.keyRelease(KeyEvent.VK_V);
			rb.keyRelease(KeyEvent.VK_CONTROL);
			Thread.sleep(2000);

			// tab to password entry field
			rb.keyPress(KeyEvent.VK_TAB);
			rb.keyRelease(KeyEvent.VK_TAB);
			// Enter password by ctrl-v
			StringSelection pwd = new StringSelection(password);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(pwd, null);
			rb.keyPress(KeyEvent.VK_CONTROL);
			rb.keyPress(KeyEvent.VK_V);
			rb.keyRelease(KeyEvent.VK_V);
			rb.keyRelease(KeyEvent.VK_CONTROL);
			// press enter
			rb.keyPress(KeyEvent.VK_ENTER);
			rb.keyRelease(KeyEvent.VK_ENTER);

		} catch (AWTException | InterruptedException e) {
			throw new CheetahException(e);
		}
	}

	/**
	 * This method is used to login tp the website when prompted by the User Access
	 * Control (UAC).
	 * 
	 * @param userName User Name
	 * @param password Password
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 * 
	 * @deprecated This method has been disabled since release of Selenium 3.8
	 */
	@Deprecated
	public static void uac_log_in(String userName, String password) throws CheetahException {
		WebDriverWait wait = new WebDriverWait(CheetahEngine.getDriverInstance(), 10);
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		// alert.authenticateUsing(new UserAndPassword(userName, password));
	}

	/**
	 * @param action Action to be performed: - OPEN - SAVE - CLOSE
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void ie_download_robot(String action) throws CheetahException {
		Robot rb;
		try {
			rb = new Robot();

			if ("SAVE".equalsIgnoreCase(action)) {
				rb.keyPress(KeyEvent.VK_CONTROL);
				rb.keyPress(KeyEvent.VK_S);
				rb.keyRelease(KeyEvent.VK_S);
				rb.keyRelease(KeyEvent.VK_CONTROL);
			} else if ("OPEN".equalsIgnoreCase(action)) {
				rb.keyPress(KeyEvent.VK_CONTROL);
				rb.keyPress(KeyEvent.VK_O);
				rb.keyRelease(KeyEvent.VK_O);
				rb.keyRelease(KeyEvent.VK_CONTROL);
			} else if ("CLOSE".equalsIgnoreCase(action)) {
				rb.keyPress(KeyEvent.VK_CONTROL);
				rb.keyPress(KeyEvent.VK_C);
				rb.keyRelease(KeyEvent.VK_C);
				rb.keyRelease(KeyEvent.VK_CONTROL);
			} else {
				rb.keyPress(KeyEvent.VK_CONTROL);
				rb.keyPress(KeyEvent.VK_C);
				rb.keyRelease(KeyEvent.VK_C);
				rb.keyRelease(KeyEvent.VK_CONTROL);
			}

		} catch (AWTException e) {
			throw new CheetahException(e);
		}

	}

	/**
	 * This method is used to retrieve attributes for HTML elements
	 * 
	 * @param locator HTML element locator
	 * @return Attribute Value
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static String getAttributeValue(By locator) throws CheetahException {
		String value;
		wait_for_element(locator);
		WebElement element = CheetahEngine.getDriverInstance().findElement(locator);
		driverUtils.highlightElement(element);
		driverUtils.unHighlightElement(element);
		value = element.getAttribute("value");
		return value;
	}

	/**
	 * This function is used to the screenshot
	 * 
	 * @param driver The driver for which screenshot must be taken.
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 * 
	 * @deprecated Use the
	 *             {@link com.gsd.sreenidhi.cheetah.actions.Cognator#captureScreenshot(String)}
	 *             captureScreenshot(fileName) method in
	 *             {@link com.gsd.sreenidhi.cheetah.actions.Cognator} class
	 *
	 * 
	 */
	@Deprecated
	public static void get_screenshot(WebDriver driver) throws CheetahException {
		String path;
		try {
			WebDriver augmentedDriver = new Augmenter().augment(driver);
			File source = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
			path = "./test" + testName + ".png";
			FileUtils.copyFile(source, new File(path));
		} catch (IOException e) {
			throw new CheetahException(e);
		}

	}

	/**
	 * @param locator          HTML Element Location
	 * @param cssAttributeName Name of the css attribute to retrieve value for
	 * @return the value of the provided css attribute
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static String getCssValue(By locator, String cssAttributeName) throws CheetahException {
		String value;
		WebElement element = CheetahEngine.getDriverInstance().findElement(locator);
		driverUtils.highlightElement(element);
		driverUtils.unHighlightElement(element);
		value = element.getCssValue(cssAttributeName);
		return value;
	}

	/**
	 * @param jsAction JavaScript Action
	 * @return String value
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static String javaScriptExecutor(String jsAction) throws CheetahException {
		JavascriptExecutor js = (JavascriptExecutor) CheetahEngine.getDriverInstance();
		String sgVal = (String) js.executeScript(jsAction);
		return sgVal;
	}

	/**
	 * This function verifies the element is enabled
	 * 
	 * @param locator HTML element locator
	 * @return boolean
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static boolean is_displayed(By locator) throws CheetahException {
		boolean displayed = false;
		try {
			wait_for_element(locator);

			WebElement element = CheetahEngine.getDriverInstance().findElement(locator);

			if (element.isDisplayed()) {
				driverUtils.highlightElement(element);
				driverUtils.unHighlightElement(element);
				displayed = true;
			}
		} catch (Exception e) {
			throw new CheetahException(e);
		}
		return displayed;
	}

	/**
	 * @param locator HTML element locator
	 */
	public static void fluent_wait(By locator) {

		if ((CheetahEngine.props.getProperty("use.fluent.wait") == null)
				|| (CheetahEngine.props.getProperty("use.fluent.wait") != null
						&& "TRUE".equalsIgnoreCase(CheetahEngine.props.getProperty("use.fluent.wait")))) {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(CheetahEngine.getDriverInstance())
					.withTimeout(Constants.GLOBAL_TIMEOUT, TimeUnit.SECONDS).pollingEvery(100, TimeUnit.MILLISECONDS)
					.ignoring(NoSuchElementException.class);

			WebElement element = wait.until(new Function<WebDriver, WebElement>() {
				public WebElement apply(WebDriver driver) {
					return driver.findElement(locator);
				}
			});
		}
		
	}

	/**
	 * Method to handle click action on Browser using JavaScript Executor
	 * 
	 * @param locatorValue HTML element locator
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 * 
	 */
	public static void jsClick(String locatorValue) throws CheetahException {

		try {
			JavascriptExecutor js = (JavascriptExecutor) CheetahEngine.getDriverInstance();
			String action = locatorValue + ".click();";
			CheetahEngine.logger.logMessage(null, SeleniumActions.class.getName(), "JavaScriptExecutor - Click",
					Constants.LOG_INFO);
			js.executeScript(action);
		} catch (Exception e) {
			throw new CheetahException(e);
		}

	}

	/**
	 * Method to handle generic Javascript Action on Browser using JavaScript
	 * Executor
	 * 
	 * @param locatorValue HTML element locator
	 * @param act          Generic userdefined javascript action
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void jsAction(String locatorValue, String act) throws CheetahException {
		try {
			JavascriptExecutor js = (JavascriptExecutor) CheetahEngine.getDriverInstance();
			String action = locatorValue + act;
			CheetahEngine.logger.logMessage(null, SeleniumActions.class.getName(),
					"JavaScriptExecutor - Generic (user defined)  Action: " + act, Constants.LOG_INFO);
			js.executeScript(action);
		} catch (Exception e) {
			throw new CheetahException(e);
		}
	}

	/**
	 * Method to check if alert is present
	 * 
	 * @return Boolean
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static boolean isAlertPresent() throws CheetahException {
		WebDriverWait wait = new WebDriverWait(CheetahEngine.getDriverInstance(), 5);
		try {
			if (wait.until(ExpectedConditions.alertIsPresent()) == null) {
				return false;
			} else {
				return true;
			}

		} catch (Exception e) {
			CheetahEngine.logger.logMessage(e, "SeleniumActions",
					"Exception cought while checking for Alert. Returning FALSE.", Constants.LOG_WARN);
			return false;
		}

	}

	/**
	 * @param locator HTML element locator
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void mouseAction_clickAndHold(By locator) throws CheetahException {
		try {
			wait_for_element(locator);
			WebElement element = CheetahEngine.getDriverInstance().findElement(locator);
			driverUtils.highlightElement(element);
			driverUtils.unHighlightElement(element);

			Actions action = new Actions(CheetahEngine.getDriverInstance());
			action.clickAndHold(element).build().perform();
		} catch (Exception e) {
			throw new CheetahException(e);
		}
	}

	/**
	 * @param locator HTML element locator
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void mouseAction_Release(By locator) throws CheetahException {
		try {
			Actions action = new Actions(CheetahEngine.getDriverInstance());
			action.release();
		} catch (Exception e) {
			throw new CheetahException(e);
		}
	}

	/**
	 * @param locator HTML element locator
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void mouseAction_MoveToElement(By locator) throws CheetahException {
		try {
			Actions action = new Actions(CheetahEngine.getDriverInstance());
			WebElement element = CheetahEngine.getDriverInstance().findElement(locator);
			action.moveToElement(element);
		} catch (Exception e) {
			throw new CheetahException(e);
		}
	}
}
