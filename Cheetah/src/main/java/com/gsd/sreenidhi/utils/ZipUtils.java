package com.gsd.sreenidhi.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.gsd.sreenidhi.cheetah.engine.CheetahEngine;
import com.gsd.sreenidhi.cheetah.exception.CheetahException;
import com.gsd.sreenidhi.forms.Constants;

/**
 * @author Sreenidhi, Gundlupet
 *
 */
public class ZipUtils {

	/**
	 * @param filePath Path of File
	 * @throws CheetahException Generic Exception Object that handles all exceptions
	 */
	public static void archiveFile(String filePath) throws CheetahException {
		String sourceFile = filePath;
		File f = new File(filePath);
		FileOutputStream fos;

		String fileLocation = com.gsd.sreenidhi.utils.FileUtils.getReportsPath() + File.separator + "VIDEO"
				+ File.separator + CheetahEngine.app_name;

		try {
			fos = new FileOutputStream(fileLocation + File.separator + f.getName() + ".zip");

			ZipOutputStream zipOut = new ZipOutputStream(fos);
			File fileToZip = new File(sourceFile);
			FileInputStream fis = new FileInputStream(fileToZip);
			ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
			zipOut.putNextEntry(zipEntry);
			final byte[] bytes = new byte[1024];
			int length;
			while ((length = fis.read(bytes)) >= 0) {
				zipOut.write(bytes, 0, length);
			}
			zipOut.close();
			fis.close();
			fos.close();
			copy_to_target(fileLocation + File.separator + f.getName() + ".zip");
		} catch (IOException e) {
			throw new CheetahException(e);
		}
	}

	/**
	 * @param dirPath Path of Directory
	 * @throws CheetahException Generic Exception Object that handles all exceptions
	 */
	public static void archiveDirectory(String dirPath) throws CheetahException {
		File directoryToZip = new File(dirPath);
		String fileLocation = com.gsd.sreenidhi.utils.FileUtils.getReportsPath() + File.separator + "VIDEO"
				+ File.separator + CheetahEngine.app_name;

		List<File> fileList = new ArrayList<File>();
		try {
			CheetahEngine.logger.logMessage(null, "ZipUtils",
					"---Getting references to all files in: " + directoryToZip.getCanonicalPath(), Constants.LOG_INFO,
					false);

			getAllFiles(directoryToZip, fileList);
			System.out.println("---Creating zip file");
			CheetahEngine.logger.logMessage(null, "ZipUtils", "---Creating zip file", Constants.LOG_INFO, false);
			writeZipFile(directoryToZip, fileList);
			CheetahEngine.logger.logMessage(null, "ZipUtils", "Archival Process complete.", Constants.LOG_INFO, false);
			copy_to_target(fileLocation + File.separator + directoryToZip.getName() + ".zip");
		} catch (IOException e) {
			throw new CheetahException(e);
		}
	}

	/**
	 * @param dir      Directory
	 * @param fileList fileList
	 * @throws CheetahException  Generic Exception Object that handles all exceptions
	 */
	public static void getAllFiles(File dir, List<File> fileList) throws CheetahException {
		try {
			File[] files = dir.listFiles();
			if (files != null) {
				for (File file : files) {
					fileList.add(file);
					if (file.isDirectory()) {
						System.out.println("directory:" + file.getCanonicalPath());
						getAllFiles(file, fileList);
					} else {
						System.out.println("     file:" + file.getCanonicalPath());
					}
				}
			} else {
				CheetahEngine.logger.logMessage(null, "ZipUtils", "There are no files to archive.", Constants.LOG_INFO,
						false);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param directoryToZip directoryToZip
	 * @param fileList       List of Files
	 * @throws CheetahException Generic Exception Object that handles all exceptions
	 */
	public static void writeZipFile(File directoryToZip, List<File> fileList) throws CheetahException {

		try {
			String filePath = com.gsd.sreenidhi.utils.FileUtils.getReportsPath() + File.separator + "VIDEO"
					+ File.separator + CheetahEngine.app_name;

			if (fileList != null) {
				FileOutputStream fos = new FileOutputStream(
						filePath + File.separator + directoryToZip.getName() + ".zip");
				ZipOutputStream zos = new ZipOutputStream(fos);

				for (File file : fileList) {
					if (!file.isDirectory()) { // we only zip files, not directories
						addToZip(directoryToZip, file, zos);
					}
				}

				zos.close();
				fos.close();
			}
		} catch (FileNotFoundException e) {
			throw new CheetahException(e);
		} catch (IOException e) {
			throw new CheetahException(e);
		}
	}

	/**
	 * @param zipFile String path of zip file
	 * @throws CheetahException Generic Exception Object that handles all exceptions
	 */
	private static void copy_to_target(String zipFile) throws CheetahException {
		File sourceFile = new File(zipFile);
		File destFile = new File("." + File.separator + "target" + File.separator + "VIDEO");
		if (!destFile.exists()) {
			destFile.mkdirs();
		}

		sourceFile.renameTo(new File(
				"." + File.separator + "target" + File.separator + "VIDEO" + File.separator + sourceFile.getName()));

	}

	/**
	 * @param directoryToZip directoryToZip
	 * @param file           File
	 * @param zos            ZipOutputStream
	 * @throws CheetahException Generic Exception Object that handles all exceptions
	 * 
	 */
	public static void addToZip(File directoryToZip, File file, ZipOutputStream zos) throws CheetahException {

		FileInputStream fis;
		try {
			fis = new FileInputStream(file);

			// we want the zipEntry's path to be a relative path that is relative
			// to the directory being zipped, so chop off the rest of the path
			String zipFilePath = file.getCanonicalPath().substring(directoryToZip.getCanonicalPath().length() + 1,
					file.getCanonicalPath().length());
			System.out.println("Writing '" + zipFilePath + "' to zip file");
			ZipEntry zipEntry = new ZipEntry(zipFilePath);
			zos.putNextEntry(zipEntry);

			byte[] bytes = new byte[1024];
			int length;
			while ((length = fis.read(bytes)) >= 0) {
				zos.write(bytes, 0, length);
			}

			zos.closeEntry();
			fis.close();
		} catch (FileNotFoundException e) {
			throw new CheetahException(e);
		} catch (IOException e) {
			throw new CheetahException(e);
		}
	}

}
