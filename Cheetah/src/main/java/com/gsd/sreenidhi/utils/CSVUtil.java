package com.gsd.sreenidhi.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.gsd.sreenidhi.cheetah.engine.CheetahEngine;
import com.gsd.sreenidhi.cheetah.exception.CheetahException;
import com.gsd.sreenidhi.cheetah.reporting.Log;
import com.gsd.sreenidhi.forms.Constants;

/**
 * @author Sreenidhi, Gundlupet
 *
 */
public class CSVUtil extends CheetahEngine {

	private static char defaultSeparator = Constants.DEFAULT_SEPARATOR;
	private static final String LOG_ERROR = Constants.LOG_ERROR;

	/**
	 * @param filePath
	 *            Path of the CSV file
	 * @param fileName
	 *            Name pf the CSV File
	 * @param createNewFile
	 *            boolean flag to indicate whether to overwrite existing file
	 *            (delete and create new file)
	 * @param header
	 *            The Header line of the csv file
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public void checkCSVExists(String filePath, String fileName, boolean createNewFile, String header)
			throws CheetahException {
		File path = new File(filePath);
		if (!path.exists()) {
			logger.logMessage(null, CSVUtil.class.getName(), "Creating Report Directories: " + path.toString(),
					Constants.LOG_INFO, false);
			path.mkdirs();
		}
		String fileaddress = filePath + File.separator + fileName;
		File csvFile = new File(fileaddress);
		if (csvFile.exists()) {
			// File Exists
			if (createNewFile) {
				csvFile.delete();
				createCSVFile(fileaddress, header);
			}

		} else {
			createCSVFile(fileaddress, header);
		}

	}

	/**
	 * @param fileaddress
	 *            the file address (path+name) of the csv file
	 * @param header
	 *            Header of the csv file
	 * @throws CheetahException
	 */
	private void createCSVFile(String fileaddress, String header) throws CheetahException {
		logger.logMessage(null, CSVUtil.class.getName(), "Creating CSV File: " + fileaddress, Constants.LOG_INFO,
				false);
		try {

			FileWriter fw = new FileWriter(fileaddress);
			PrintWriter out = new PrintWriter(fw);
			if (header != null && !"".equalsIgnoreCase(header)) {
				out.println(header);
			}
			out.flush();
			out.close();
			fw.close();

		} catch (IOException e) {
			throw new CheetahException(e);
		} catch (Exception e) {
			throw new CheetahException(e);

		}

	}

	/**
	 * @param fileName
	 *            Name of the csv file
	 * @param values
	 *            values to be written to the csv file
	 * @param separators
	 *            value separatation character. Default is ",".
	 * @param customQuote
	 *            Quotations for the value, if required. Default is ' '
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public static void writeLine(String fileName, List<String> values, char separators, char customQuote)
			throws CheetahException {

		try {
			FileWriter fw = new FileWriter(fileName, true);
			PrintWriter out = new PrintWriter(fw);

			boolean first = true;
			char pathSeparator;
			// default customQuote is empty

			if (separators == ' ') {
				pathSeparator = defaultSeparator;
			} else {
				pathSeparator = separators;
			}

			StringBuilder sb = new StringBuilder();
			for (String value : values) {
				if (!first) {
					sb.append(pathSeparator);
				}
				if (customQuote == ' ') {
					sb.append(followCVSformat(value));
				} else {
					sb.append(customQuote).append(followCVSformat(value)).append(customQuote);
				}

				first = false;
			}
			sb.append("\n");
			out.append(sb.toString());
			out.flush();
			out.close();
			fw.close();

		} catch (IOException e) {
			logger.logMessage(e, CSVUtil.class.getName(),
					"Write File - IOException: \n" + e.getMessage() + "\n" + getExceptionTrace(e), Constants.LOG_ERROR,
					true);
		} catch (Exception e) {
			logger.logMessage(e, CSVUtil.class.getName(),
					"Write File - Exception: \n" + e.getMessage() + "\n" + getExceptionTrace(e), Constants.LOG_ERROR,
					true);

		}
	}

	/**
	 * @param value
	 *            Value
	 * @return Result
	 */
	private static String followCVSformat(String value) {

		String result = value;
		if (result.contains("\"")) {
			result = result.replace("\"", "\"\"");
		}
		return result;

	}

	/**
	 * @param csvFilePath
	 *            File to be archived
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public void processCSVArchival(String csvFilePath) throws CheetahException {

		Log l = new Log(csvFilePath);
		Exception exception = null;
		File csvFile = new File(csvFilePath);
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("YYYY_MM_DD");

		long fileSize = csvFile.length();

		if ((double) fileSize / (1024 * 1024) > 5) { // If file size is more
														// than 5 MB

			String archivePath = csvFilePath.substring(0, csvFilePath.indexOf('.')) + "/archive/" + ft.format(dNow)
					+ ".log";

			InputStream inStream = null;
			OutputStream outStream = null;
			try {

				File afile = new File(csvFilePath);
				File bfile = new File(archivePath);

				inStream = new FileInputStream(afile);
				outStream = new FileOutputStream(bfile);

				byte[] buffer = new byte[1024];

				int length;
				// copy the file content in bytes
				while ((length = inStream.read(buffer)) > 0) {

					outStream.write(buffer, 0, length);

				}

				inStream.close();
				outStream.close();

				boolean deleteFlag = afile.delete();

				if (deleteFlag) {
					l.logMessage(exception, CSVUtil.class.getName(),
							"processCSVArchival - CSV File Archival Successful. \n", "info", false);
				} else {
					l.logMessage(exception, CSVUtil.class.getName(),
							"processCSVArchival - CSV File Archival Failed. \n", LOG_ERROR, true);
				}

			} catch (IOException e) {
				throw new CheetahException(e);
			}
		}

	}

	/**
	 * @param fileName
	 *            Name of the csv file
	 * @param values
	 *            values to be written to the csv file
	 * @param separators
	 *            value separatation character. Default is ",".
	 * @param customQuote
	 *            Quotations for the value, if required. Default is ' '
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public void updateOrWriteLine(String fileName, List<String> values, char separators, char customQuote)
			throws CheetahException {

		boolean first = true;
		char pathSeparator;
		// default customQuote is empty

		if (separators == ' ') {
			pathSeparator = defaultSeparator;
		} else {
			pathSeparator = separators;
		}

		try {

			String oldFile = fileName + ".csv";
			File filReadMe = new File(oldFile);
			BufferedReader brReadMe = new BufferedReader(
					new InputStreamReader(new FileInputStream(filReadMe), "UTF-8"));
			System.out.println("Reader  old file:" + oldFile + "\n");

			String newFile = fileName + "_new.csv";
			FileWriter fw = new FileWriter(newFile, true);
			PrintWriter out = new PrintWriter(fw);
			System.out.println("Writer  old file: " + newFile + "\n");

			StringBuilder sb = new StringBuilder();
			int writeCurrent = 0;

			String strLine = brReadMe.readLine();
			// for all lines
			int count = 0;
			while (strLine != null) {
				System.out.println("While loop count: " + count++);
				System.out.println("StrLine:" + strLine + "\n");
				// if line contains "(see also"
				if (strLine.toUpperCase().contains(CheetahEngine.app_name.toUpperCase().trim())) {
					sb = new StringBuilder();
					writeCurrent++;
					System.out.println("Replacing Line:" + strLine + "\n");
					for (String value : values) {
						if (!first) {
							sb.append(pathSeparator);
						}
						if (customQuote == ' ') {
							sb.append(followCVSformat(value));
						} else {
							sb.append(customQuote).append(followCVSformat(value)).append(customQuote);
						}

						first = false;
					}
					sb.append("\n");
					out.append(sb.toString());
				} else {
					sb = new StringBuilder();
					System.out.println("Writing old Line:" + strLine + "\n");
					sb.append(strLine);
					sb.append("\n");
					System.out.println("StringBuffer:" + sb.toString());
					out.append(sb.toString());
				}

				// update line
				strLine = brReadMe.readLine();
			} // end for
			sb = new StringBuilder();
			if (writeCurrent == 0) {
				System.out.println("Writing new Line");
				for (String value : values) {
					if (!first) {
						sb.append(pathSeparator);
					}
					if (customQuote == ' ') {
						sb.append(followCVSformat(value));
					} else {
						sb.append(customQuote).append(followCVSformat(value)).append(customQuote);
					}

					first = false;
				}
				sb.append("\n");
				out.append(sb.toString());
			}

			out.flush();
			out.close();
			fw.close();
			brReadMe.close();

			File oldFile1 = new File(fileName + ".csv");
			if (oldFile1.exists()) {
				oldFile1.delete();
			}

			File renameFile = new File(fileName + "_new.csv");
			if (renameFile.exists()) {
				renameFile.renameTo(oldFile1);
			}

		} catch (IOException e) {
			throw new CheetahException(e);
		} catch (Exception e) {
			throw new CheetahException(e);
		}
	}
}
