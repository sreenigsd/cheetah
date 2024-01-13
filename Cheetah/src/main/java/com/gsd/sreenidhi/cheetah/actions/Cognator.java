package com.gsd.sreenidhi.cheetah.actions;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;

import com.gsd.sreenidhi.cheetah.engine.CheetahEngine;
import com.gsd.sreenidhi.cheetah.database.DBExecutor;
import com.gsd.sreenidhi.cheetah.exception.CheetahException;
import com.gsd.sreenidhi.forms.Constants;
import com.gsd.sreenidhi.utils.CheetahUtils;
import com.gsd.sreenidhi.utils.SendMail;

import java.security.SecureRandom;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Sreenidhi, Gundlupet
 *
 */
public class Cognator extends CheetahEngine {

	/**
	 * @param digCount Integer value that specifies the number of digits in the
	 *                 random Number
	 * @return Random Number
	 */
	public static String generateRandomNumber(final int digCount) {
		return getRandomNumber(digCount, new Random()).toString();
	}

	/**
	 * @param digCount Integer value that specifies the number of digits in the
	 *                 random Number
	 * @param rnd      Random
	 * @return Random Number
	 */
	public static BigInteger getRandomNumber(final int digCount, Random rnd) {
		final char[] ch = new char[digCount];
		for (int i = 0; i < digCount; i++) {
			ch[i] = (char) ('0' + (i == 0 ? rnd.nextInt(9) + 1 : rnd.nextInt(10)));
		}
		return new BigInteger(new String(ch));
	}

	/**
	 * Creates a random string whose length is the number of characters specified
	 * Characters will be chosen from the set of Latin alphabetic characters (a-z,
	 * A-Z).
	 * 
	 * @param length Length of String Required.
	 * @return Random String
	 */
	public static String generateRandomStringAlphabetsOnly(final int length) {
	    if (length <= 0) {
	        throw new IllegalArgumentException("Length must be positive");
	    }

	    Random random = new SecureRandom();
	    return IntStream.range(0, length)
	                    .map(i -> 'A' + random.nextInt(26)) // Generate a random integer and add to 'A' for uppercase letters
	                    .mapToObj(c -> String.valueOf((char) c)) // Convert integer to character and then to String
	                    .collect(Collectors.joining()); // Join everything into a single string
	}

	/**
	 * Creates a random string whose length is the number of characters specified.
	 * Characters will be chosen from the set of Latin alphabetic characters (a-z,
	 * A-Z) and the digits 0-9.
	 * 
	 * @param length Length of String required
	 * @return Random String
	 */
	public static String generateRandomStringAlphaNumeric(final int length) {
	    if (length <= 0) {
	        throw new IllegalArgumentException("Length must be positive");
	    }

	    Random random = new SecureRandom();
	    return IntStream.range(0, length)
	                    .map(i -> random.nextInt(26 * 2)) // Generate a random integer for alphabets (uppercase + lowercase)
	                    .mapToObj(n -> {
	                        if (n < 26) return (char) ('A' + n); // Uppercase letters
	                        else if (n < 52) return (char) ('a' + (n - 26)); // Lowercase letters
	                        else return (char) ('0' + (n - 52)); // Numbers
	                    })
	                    .map(Object::toString)
	                    .collect(Collectors.joining()); // Join everything into a single string
	}

	/**
	 * Creates a random string whose length is the number of characters specified.
	 * Characters will be chosen from the set of characters whose ASCII value is
	 * between 32 and 126 (inclusive).
	 * 
	 * @param length Length of String required
	 * @return Random String
	 */
	public static String generateRandomStringSpecialAlphaNumeric(final int length) {
	    if (length <= 0) {
	        throw new IllegalArgumentException("Length must be positive");
	    }

	    Random random = new SecureRandom();
	    // Define the range of ASCII printable characters (from space to ~)
	    int startAscii = 32; // ASCII for space
	    int endAscii = 126; // ASCII for ~

	    return IntStream.range(0, length)
	                    .map(i -> startAscii + random.nextInt(endAscii - startAscii + 1))
	                    .mapToObj(c -> String.valueOf((char) c))
	                    .collect(Collectors.joining());
	}

	/**
	 * This function is used to the screenshot on-demand
	 * 
	 * @param fileName Name to assign the image capture
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void captureScreenshot(String fileName) throws CheetahException {
		String path;
		try {

			StringBuffer imagePath = new StringBuffer(Paths.get(".").toAbsolutePath().normalize().toString()
					+ File.separator + "target" + File.separator + "images" + File.separator + fileName + ".png");
			WebDriver augmentedDriver = new Augmenter().augment(CheetahEngine.getDriverInstance());
			File source = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
			path = imagePath.toString();
			FileUtils.copyFile(source, new File(path));
			java.nio.file.Path p = Paths.get(path);
			byte[] data = java.nio.file.Files.readAllBytes(p);
			DBExecutor.recordScreenshot(data, Calendar.getInstance().getTime().toString(), "ON_DEMAND",
					retrieveScenarioExecutionId() + "fileName");

		} catch (IOException e) {
			throw new CheetahException(e);
		}

	}

	/**
	 * @param toEmailAddress To Email Address
	 * @param ccEmailAddress CC Email Address
	 * @param emailSubject   Email Subject
	 * @param emailBody      Email Body
	 * @param ccAdmin        Boolean flag to indicate if Admin should be CC'ed in
	 *                       the email
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */

	public static void sendEmail(String toEmailAddress, String ccEmailAddress, String emailSubject, String emailBody,
			boolean ccAdmin) throws CheetahException {
		SendMail email = new SendMail();
		email.sendMessage(
				CheetahEngine.configurator.mailConfigurator.getDefaultTemplate()
						.replace(CheetahEngine.configurator.mailConfigurator.getTemplatePlaceholder(), emailBody),
				toEmailAddress, ccEmailAddress, emailSubject, ccAdmin);
	}

	/**
	 * @param fileName Name of the file in the folder structure that has the details
	 *                 about the email needed to be sent
	 * @param ccAdmin  Boolean flag to indicate if ccAdmin team should be CC'ed in
	 *                 the email
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void sendEmailUsingFile(String fileName, boolean ccAdmin) throws CheetahException {
		CheetahUtils.processNotification(fileName, ccAdmin);
	}

	/**
	 * @param fileName Name of the file to verify
	 * @return boolean
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static boolean verifyFileDownload(String fileName) throws CheetahException {
		FileUtils fu = new FileUtils();
		String downloadFilepath = "";
		boolean fileExists;

		if (CheetahEngine.props.getProperty("download.file.location") != null) {
			downloadFilepath = new File(CheetahEngine.props.getProperty("download.file.location")).getAbsolutePath();
			CheetahEngine.logger.logMessage(null, "GenericActions", "Using download file property... ",
					Constants.LOG_INFO);
		} else {
			String home = System.getProperty("user.home");
			downloadFilepath = home + File.separator + "Downloads";
		}

		File f = new File(downloadFilepath + File.separator + fileName);

		if (f.exists()) {
			fileExists = true;
		} else {
			fileExists = false;
		}
		return fileExists;

	}

	/**
	 * @param downloadFilepath User-defined file path
	 * @param fileName         Name of the file to verify
	 * @return boolean
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static boolean verifyFileDownload(String downloadFilepath, String fileName) throws CheetahException {
		FileUtils fu = new FileUtils();
		boolean fileExists;

		File f = new File(downloadFilepath + File.separator + fileName);

		if (f.exists()) {
			fileExists = true;
		} else {
			fileExists = false;
		}
		return fileExists;

	}

	/**
	 * @param location Trigger Location
	 * @param scenario Scenario Name
	 * @param step     Step
	 * @param data     Data
	 * @param results  result
	 * @param adds     Array of additional parameters
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void captureData(String location, String scenario, String step, String data, String results,
			String[] adds) throws CheetahException {
		if (CheetahEngine.props.getProperty("capture.data") != null
				&& "FALSE".equalsIgnoreCase(CheetahEngine.props.getProperty("capture.data"))) {

		} else {
			com.gsd.sreenidhi.utils.FileUtils fu = new com.gsd.sreenidhi.utils.FileUtils();
			fu.appendTestDataFile(location, scenario, step, data, results, adds);
		}

	}
}
