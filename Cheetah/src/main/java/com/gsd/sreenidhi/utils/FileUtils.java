package com.gsd.sreenidhi.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.stream.Stream;

import com.gsd.sreenidhi.forms.Constants;
import com.gsd.sreenidhi.cheetah.engine.CheetahEngine;
import com.gsd.sreenidhi.cheetah.exception.CheetahException;

/**
 * @author Sreenidhi, Gundlupet
 *
 */
public class FileUtils extends CheetahEngine {

	private static final String LOG_ERROR = Constants.LOG_ERROR;

	
	/**
	 * @param settingsFile
	 *            Name of Settings File
	 * @return File Return the settings file
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public File getSettings(String settingsFile) throws CheetahException {
		File f = null;
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		StringBuffer path = new StringBuffer(
				"resources" + File.separator + "settings" + File.separator + settingsFile);
		f = new File(path.toString());
		if(f.exists()) {
			CheetahEngine.logger.logMessage(null, this.getClass().getName(),
					"Settings file identified: " + path.toString(), Constants.LOG_INFO, false);
		}else {
			CheetahEngine.logger.logMessage(null, this.getClass().getName(),
					"Failed to identify Configuration: " + path.toString(), Constants.LOG_FATAL, false);
		}
		return f;
	}
	
	/**
	 * @param confFile
	 *            Name of Config File
	 * @return File Return the config file
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public File getConfiguration(String confFile) throws CheetahException {
		File f = null;
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		StringBuffer path = new StringBuffer(
				"resources" + File.separator + "conf" + File.separator + confFile);
		f = new File(path.toString());
		if(f.exists()) {
			CheetahEngine.logger.logMessage(null, this.getClass().getName(),
					"Configuration file identified: " + path.toString(), Constants.LOG_INFO, false);
		}else {
			CheetahEngine.logger.logMessage(null, this.getClass().getName(),
					"Failed to identify Configuration: " + path.toString(), Constants.LOG_FATAL, false);
		}
		return f;
	}
	
	/**
	 * @param propFile
	 *            Name of Property File
	 * @return Properties
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public Properties getProperties(String propFile) throws CheetahException {
		Properties properties = null;

		try {
			properties = new Properties();
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			StringBuffer path = new StringBuffer(
					"resources" + File.separator + "properties" + File.separator + propFile);
			properties.load(new FileInputStream(path.toString()));
			CheetahEngine.logger.logMessage(null, this.getClass().getName(),
					"Loading property: " + path.toString() + " - " + properties.hashCode(), Constants.LOG_INFO, false);
			if (properties.isEmpty()) {
				CheetahEngine.logger.logMessage(null, this.getClass().getName(), "Unable to load the properties file",
						Constants.LOG_FATAL, false);
			} else {
				CheetahEngine.logger.logMessage(null, this.getClass().getName(), "Properties file loaded successfully",
						Constants.LOG_INFO, false);
			}
		} catch (IOException e) {
			logger.logMessage(e, FileUtils.class.getName(),
					":- Error reading Property file " + "\n" + getExceptionTrace(e), Constants.LOG_ERROR, true);
		}
		return properties;
	}

	/**
	 * @param scriptFile
	 *            Name of Script File
	 * @return Script File
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public File getScript(String scriptFile) throws CheetahException {

		Properties properties = null;
		File f = null;

		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		StringBuffer path = new StringBuffer("resources" + File.separator + "scripts" + File.separator + scriptFile);
		f = new File(path.toString());
		CheetahEngine.logger.logMessage(null, this.getClass().getName(),
				"Retrieving Script" + path.toString() + " - " + f.hashCode(), Constants.LOG_INFO, false);
		return f;

	}

	/**
	 * @param filetype
	 *            Type of File for processing. This parameter accepts only following
	 *            values - "INPUT", "OUTPUT" and "EMAIL"
	 * @param inputFile
	 *            String value of the File Name
	 * @return File after processing
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public File getFile(String filetype, String inputFile) throws CheetahException {
		File file = null;
		boolean fileExists = false;
		String subFiletype = null;
		if ("INPUT".equalsIgnoreCase(filetype)) {
			subFiletype = "input";
			fileExists = true;
		} else if ("UPLOAD".equalsIgnoreCase(filetype)) {
			subFiletype = "uploadFiles";
			fileExists = true;
		} else if ("EMAIL".equalsIgnoreCase(filetype)) {
			subFiletype = "email";
			fileExists = true;
		} else {
			subFiletype = filetype;
			File f = new File(Paths.get(".").toAbsolutePath().normalize().toString() + File.separator + "src"
					+ File.separator + "test" + File.separator + "data" + File.separator + subFiletype + File.separator + inputFile);
			if (f.exists()) {
				fileExists = true;
			} else {
				fileExists = false;
			}
		}

		if (fileExists) {
			StringBuffer path = new StringBuffer(
					Paths.get(".").toAbsolutePath().normalize().toString() + File.separator + "src" + File.separator
							+ "test" + File.separator + "data" + File.separator + subFiletype + File.separator + inputFile);
			file = new File(path.toString());
			CheetahEngine.logger.logMessage(null, this.getClass().getName(),
					"Input File: " + path.toString() + " - " + file.hashCode(), Constants.LOG_INFO, false);
			if (!file.exists()) {
				CheetahEngine.logger.logMessage(null, this.getClass().getName(), "Unable to load the Input file",
						Constants.LOG_ERROR, false);
			} else {
				CheetahEngine.logger.logMessage(null, this.getClass().getName(), "Input file loaded successfully",
						Constants.LOG_INFO, false);
			}
		} else {
			Exception e = new FileNotFoundException();
			throw new CheetahException(e);
		}

		return file;
	}

	/**
	 * @param appName
	 *            Name of the Mobile App
	 * @return Absolute Path of the App
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public String getMobileApp(String appName) throws CheetahException {

		StringBuffer path = new StringBuffer(Paths.get(".").toAbsolutePath().normalize().toString() + File.separator
				+ "src" + File.separator + "test" + File.separator + "app" + File.separator + appName);

		String appFilePath = path.toString();
		CheetahEngine.logger.logMessage(null, "FileUtils", "Checking App File Path: " + appFilePath, Constants.LOG_INFO);
		
		
		File f = new File(appFilePath);

		if (f.exists()) {
			String appPath = f.getAbsolutePath();
			CheetahEngine.logger.logMessage(null, "FileUtils", "App Path: " + appPath, Constants.LOG_INFO);
			return appPath;
		} else {
			CheetahEngine.logger.logMessage(null, "FileUtils", "App Path not found... :( ", Constants.LOG_WARN);
			IOException ioe = new IOException("Mobile App Not Found!");
			throw new CheetahException(ioe);
		}

	}

	/**
	 * @param fileName
	 *            File name of the file that needs to be created.
	 * @param content
	 *            Content of the file being created.
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public void createFile(String fileName, String content) throws CheetahException {

		File file = null;
		String subFiletype = "input";

		StringBuffer path = new StringBuffer(Paths.get(".").toAbsolutePath().normalize().toString() + File.separator
				+ "src" + File.separator + "test" + File.separator + "data" + File.separator + subFiletype + File.separator + fileName);
		file = new File(path.toString());

		if (file.exists()) {
			file.delete();
		}

		FileWriter fw;
		try {
			fw = new FileWriter(path.toString(), false);

			PrintWriter out = new PrintWriter(fw);

			out.append(content);
			out.flush();
			out.close();
			fw.close();
		} catch (IOException e) {
			throw new CheetahException(e);
		}
	}

	/**
	 * @param fileName
	 *            File name of the file that needs to be created.
	 * @param location
	 *            Location of File Output
	 * @param content
	 *            Content of the file being created.
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public void createFile(String fileName, String location, String content) throws CheetahException {

		File file = null;
		String subFiletype = location;

		StringBuffer path = new StringBuffer(Paths.get(".").toAbsolutePath().normalize().toString() + File.separator
				+ "src" + File.separator + "test" + File.separator + "data" + File.separator + subFiletype + File.separator + fileName);
		file = new File(path.toString());

		if (file.exists()) {
			file.delete();
		}

		FileWriter fw;
		try {
			fw = new FileWriter(path.toString(), false);

			PrintWriter out = new PrintWriter(fw);

			out.append(content);
			out.flush();
			out.close();
			fw.close();
		} catch (IOException e) {
			throw new CheetahException(e);
		}
	}
	
	
	/**
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public void createTestDataFile() throws CheetahException {

		File file = null;
			//String fileName = CheetahEngine.reportingForm.getTransaction()+".csv";
			String fileName = CheetahEngine.cheetahForm.getExecutionTimestamp()+".csv";
			
			fileName = fileName.replaceAll(" ", "_");
			fileName = fileName.replaceAll(",", "_");
			fileName = fileName.replaceAll(":", "_");
			
			StringBuffer path = new StringBuffer(Paths.get(".").toAbsolutePath().normalize().toString() + File.separator
					+ "target" + File.separator + "data");
			
			/*String filePath = FileUtils.getReportsPath() + File.separator + "CSV" + File.separator
					+ CheetahEngine.app_name + File.separator + "Data";*/
			
			String filePath = path.toString();
			
			
			File f = new File(filePath);
			if(!f.exists()) {
				f.mkdirs();
			}
			
			file = new File(filePath+ File.separator + fileName);

			if (file.exists()) {
				file.delete();
			}
			
			FileWriter fw;
			try {
				String content = "Location, Scenario, Step, Data, Results, Additional Info..."+ System.lineSeparator();
				fw = new FileWriter(file.toString(), true);
				PrintWriter out = new PrintWriter(fw);

				out.append(content);
				out.flush();
				out.close();
				
				
				fw.close();
			} catch (IOException e) {
				throw new CheetahException(e);
			}
		
		
	}
	

	/**
	 * @param Location Trigger Location
	 * @param Scenario Scenario
	 * @param Step Step
	 * @param Data Data
	 * @param results result 
	 * @param adds Array of additional parameters
	 * @throws CheetahException Generic Exception Object that handles all exceptions
	 */
	public void appendTestDataFile(String Location, String Scenario, String Step, String Data, String results, String[] adds) throws CheetahException {
		
		File file = null;
		
		if(CheetahEngine.reportingForm.getTransaction()!=null) {
			//String fileName = CheetahEngine.reportingForm.getTransaction()+".csv";
			String fileName = CheetahEngine.cheetahForm.getExecutionTimestamp()+".csv";

			fileName = fileName.replaceAll(" ", "_");
			fileName = fileName.replaceAll(",", "_");
			fileName = fileName.replaceAll(":", "_");
			
			String content = null;
			
			StringBuffer path = new StringBuffer(Paths.get(".").toAbsolutePath().normalize().toString() + File.separator
					+ "target" + File.separator + "data");
			
			/*String filePath = FileUtils.getReportsPath() + File.separator + "CSV" + File.separator
					+ CheetahEngine.app_name + File.separator + "Data";*/
			
			String filePath = path.toString();
			
			File f = new File(filePath);
			 if(f.exists()) {
				 file = new File(filePath + File.separator + fileName);

					FileWriter fw;
					try {
						fw = new FileWriter(file.toString(), true);

						PrintWriter out = new PrintWriter(fw);

						content = Location + ", " + Scenario + ", " + Step + ", " + Data + ", " + results  ;
						
						if(adds!=null) {
							for (int i=0;i<adds.length;i++) {
								content = content + "," + adds[i];
							}
								
						}
						
						content = content + System.lineSeparator();
						out.append(content);
						out.flush();
						out.close();
						
						fw.close();
					} catch (IOException e) {
						throw new CheetahException(e);
					} 
			 }
			
			
		}else {
			throw new NullPointerException("Failed to retrieve transaction ID");
		}
		
	}

	/**
	 * @param filePath
	 *            Path of the PDF file
	 * @param fileName
	 *            Name of the PDF File
	 * @param createNewFile
	 *            boolean flag to check if the existing file can be overwritten
	 *            (delete and create)
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public void checkpdfFileExists(String filePath, String fileName, boolean createNewFile) throws CheetahException {
		File path = new File(filePath);
		if (!path.exists()) {
			logger.logMessage(null, FileUtils.class.getName(), "Creating Report Directories: " + path.toString(),
					Constants.LOG_INFO, false);
			path.mkdirs();
		}
		String fileaddress = filePath + File.separator + fileName;
		File pdfFile = new File(fileaddress);
		if (pdfFile.exists()) {
			// File Exists
			if (createNewFile) {
				pdfFile.delete();
				createpdfFile(fileaddress);
			}

		} else {
			createpdfFile(fileaddress);
		}

	}

	/**
	 * @param fileaddress
	 *            File address(path+name) of the file being created.
	 * @throws CheetahException
	 */
	private void createpdfFile(String fileaddress) throws CheetahException {
		logger.logMessage(null, CSVUtil.class.getName(), "Creating PDF File: " + fileaddress, Constants.LOG_INFO,
				false);
		try {

			FileWriter fw = new FileWriter(fileaddress);
			PrintWriter out = new PrintWriter(fw);
			out.flush();
			out.close();
			fw.close();

		} catch (IOException e) {
			logger.logMessage(e, CSVUtil.class.getName(),
					"Check File - IOException: \n" + e.getMessage() + "\n" + getExceptionTrace(e), LOG_ERROR, true);
		} catch (Exception e) {
			logger.logMessage(e, CSVUtil.class.getName(),
					"Check File - Exception: \n" + e.getMessage() + "\n" + getExceptionTrace(e), LOG_ERROR, true);

		}

	}

	/**
	 * Method to create directory structure specified by the path parameter
	 * 
	 * @param path
	 *            path of the directory structure
	 */
	public void createDirectories(String path) {
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}

	/**
	 * Return the path of the reports destination
	 * 
	 * @return filePath File Path
	 * @throws CheetahException
	 *             Generic Exception Object that handles all exceptions
	 */
	public static String getReportsPath() throws CheetahException {
		String filePath;
		
		if(CheetahEngine.props.getProperty("log.location")!=null) {
			filePath = CheetahEngine.props.getProperty("log.location") + File.separator + "Reports";
		}else {
			logger.logMessage(null, "FileUtils", "Logging location not specified. Base location will be used.",
					Constants.LOG_WARN, false);
			filePath = "." + File.separator + "target" + File.separator + "Reports";
		}

		return filePath;
	}

	/**
	 * @param path
	 *            Directory Path
	 * @return boolean Check path validity
	 */
	public boolean validateDirectoryPath(String path) {

		boolean valid = false;
		File f = new File(path);

		if (f.exists()) {
			valid = true;
		}
		return valid;
	}

	
	/**
	 * @param props Properties
	 * @throws CheetahException Generic Exception Object that handles all exceptions
	 */
	public void printProperties(Properties props) throws CheetahException {
		props.list(System.out);
	}
	
	/**
	 * @param f File
	 * @return String - File content
	 */
	public String getFileContent(File f) {
		 StringBuilder contentBuilder = new StringBuilder();
	        try (Stream<String> stream = Files.lines( Paths.get(f.getAbsolutePath()), StandardCharsets.UTF_8)){
	            stream.forEach(s -> contentBuilder.append(s).append("\n"));
	        }catch (IOException e){
	            e.printStackTrace();
	        }
        return contentBuilder.toString();
	}
}
