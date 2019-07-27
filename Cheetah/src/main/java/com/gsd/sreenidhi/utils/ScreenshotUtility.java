package com.gsd.sreenidhi.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.gsd.sreenidhi.cheetah.engine.*;
import com.gsd.sreenidhi.cheetah.exception.CheetahException;
import com.gsd.sreenidhi.forms.Constants;

/**
 * @author Sreenidhi, Gundlupet
 *
 */
public class ScreenshotUtility {

	/**
	 * @param driver
	 *            WebDriver
	 * @param fileName
	 *            Name of the video file
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public static void captureScreenShotOnTestSuccess(WebDriver driver, String fileName) throws CheetahException {
		// If screenShotOnPass = yes, call captureScreenShot.
		CheetahEngine.logger.logMessage(null, "Screenshot Utility", "Capturing screenshot for Pass status",
				Constants.LOG_INFO, false);
		if ("Yes".equalsIgnoreCase(CheetahEngine.props.getProperty("screenShotOnPass"))) {
			captureScreenShot(driver, "pass", fileName);
		}
	}

	/**
	 * @param driver
	 *            WebDriver
	 * @param fileName
	 *            Name of the video flie
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public static void captureScreenShotOnTestFailure(WebDriver driver, String fileName) throws CheetahException {
		// If screenShotOnFail = yes, call captureScreenShot.
		CheetahEngine.logger.logMessage(null, "Screenshot Utility", "Capturing screenshot for Fail status",
				Constants.LOG_INFO, false);
		if ("Yes".equalsIgnoreCase(CheetahEngine.props.getProperty("screenShotOnFail"))) {
			captureScreenShot(driver, "fail", fileName);
		}
	}

	/**
	 * capture screenshot.
	 * 
	 * @param driver
	 *            WebDriver
	 * @param status
	 *            Status of test execution
	 * @param fileName
	 *            Name of the video file
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	private static void captureScreenShot(WebDriver driver, String status, String fileName) throws CheetahException {
		CheetahEngine.logger.logMessage(null, "Screenshot Utility", "Capturing screenshot", Constants.LOG_INFO, false);
		String destDir = "";
		// To capture screenshot.
		File screenShotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");
		// If status = fail then set folder name "screenshots/failures"
		if ("fail".equalsIgnoreCase(status)) {
			destDir = "screenshots/failures";
		}
		// If status = pass then set folder name "screenshots/success"
		else if ("pass".equalsIgnoreCase(status)) {
			destDir = "screenshots/success";
		}

		// To create folder to store screenshots
		new File(destDir).mkdirs();
		// Set file name with combination of test class name + date time.
		String destFile = fileName + " - " + dateFormat.format(new Date()) + ".png";

		try {
			// Store file at destination folder location
			FileUtils.copyFile(screenShotFile, new File(destDir + "/" + destFile));
		} catch (IOException e) {
			CheetahEngine.logger.logMessage(e, "CheetahUtils",
					"IOException" + e.getMessage() + "\n" + CheetahEngine.getExceptionTrace(e), Constants.LOG_ERROR,
					true);
		}
	}

	/**
	 * @param userId
	 *            UserID of user
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public static void zipAllFailures(String userId) throws CheetahException {
		String sourceDir = "screenshots/failures";
		String zipFile = "screenshots/failures/" + userId + "_Registration.zip";
		zipAllFiles(userId, sourceDir, zipFile);
		deleteFiles(sourceDir);
	}

	/**
	 * @param userId
	 *            UserID of user
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public static void zipAllSucess(String userId) throws CheetahException {
		String sourceDir = "screenshots/success";
		String zipFile = "screenshots/success/" + userId + "_Registration.zip";
		zipAllFiles(userId, sourceDir, zipFile);
		deleteFiles(sourceDir);
	}

	/**
	 * @param userId
	 *            UserID of user
	 * @param sourceFolder
	 *            Source folder of files
	 * @param zipFile
	 *            Name of the zip file
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	private static void zipAllFiles(String userId, String sourceFolder, String zipFile) throws CheetahException {
		List<String> fileList = new ArrayList<String>();
		byte[] buffer = new byte[1024];

		try {
			fileList = generateFileList(sourceFolder);
			if (fileList.size() > 0) {
				FileOutputStream fos = new FileOutputStream(zipFile);
				ZipOutputStream zos = new ZipOutputStream(fos);
				CheetahEngine.logger.logMessage(null, "CheetahUtils", "Output to Zip: " + zipFile, Constants.LOG_INFO,
						false);
				for (String file : fileList) {
					CheetahEngine.logger.logMessage(null, "CheetahUtils", "File Added:" + file, Constants.LOG_INFO,
							false);
					ZipEntry ze = new ZipEntry(file);
					zos.putNextEntry(ze);
					FileInputStream in = new FileInputStream(sourceFolder + File.separator + file);
					int len;
					while ((len = in.read(buffer)) > 0) {
						zos.write(buffer, 0, len);
					}
					in.close();
				}
				zos.closeEntry();
				zos.close();
				CheetahEngine.logger.logMessage(null, "CheetahUtils", "Done!", Constants.LOG_INFO, false);
			}
		} catch (IOException e) {
			CheetahEngine.logger.logMessage(e, "CheetahUtils",
					"IOException" + e.getMessage() + "\n" + CheetahEngine.getExceptionTrace(e), Constants.LOG_ERROR,
					true);
		}
	}

	/**
	 * @param sourceFolder
	 *            Source folder of files
	 * @return List
	 */
	private static List<String> generateFileList(String sourceFolder) {
		File node = new File(sourceFolder);
		List<String> fileList = new ArrayList<String>();

		if (node.isDirectory()) {
			String[] files = node.list();
			for (String file : files) {
				String extension = file.substring(file.lastIndexOf(".") + 1, file.length());
				if (!"zip".equalsIgnoreCase(extension)) {
					fileList.add(file);
				}
			}
		}
		return fileList;
	}

	/**
	 * @param sourceDir
	 *            Source of the video files
	 */
	private static void deleteFiles(String sourceDir) {
		File directory = new File(sourceDir);
		File[] files = directory.listFiles();
		if (files != null) {
			for (File file : files) {
				String fileName = file.getName();
				String extension = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
				if (!"zip".equalsIgnoreCase(extension)) {
					file.delete();
				}
			}
		}
	}

}