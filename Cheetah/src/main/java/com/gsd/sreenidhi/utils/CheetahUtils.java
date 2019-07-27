package com.gsd.sreenidhi.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.UUID;

import com.gsd.sreenidhi.forms.Constants;
import com.gsd.sreenidhi.forms.Hosts;
import com.gsd.sreenidhi.cheetah.actions.Cognator;
import com.gsd.sreenidhi.cheetah.engine.CheetahEngine;
import com.gsd.sreenidhi.cheetah.engine.CheetahForm;
import com.gsd.sreenidhi.cheetah.exception.CheetahException;

/**
 * @author Sreenidhi, Gundlupet
 *
 */
public class CheetahUtils extends CheetahEngine {
	static Random generator = new Random();
	static int portStartRange = 4000;
	static int portEndRange = 7999;

	/**
	 * Constructor
	 */
	public CheetahUtils() {
		// Constructor
	}

	/**
	 * @param startRange range start
	 * @param endRange   range end
	 * @return String Random number
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static String generateRandomNumberString(int startRange, int endRange) throws CheetahException {
		CheetahEngine.logger.logMessage(null, "CheetahUtils", "Generating random number string", Constants.LOG_INFO,
				false);
		int randomNumber = generator.nextInt(endRange - startRange) + startRange;
		String randomNumberString = Integer.toString(randomNumber);
		CheetahEngine.logger.logMessage(null, "CheetahUtils", "Random number string: " + randomNumberString,
				Constants.LOG_INFO, false);
		return randomNumberString;
	}

	/**
	 * @return Random Number
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	private static int generateRandomPort() throws CheetahException {
		CheetahEngine.logger.logMessage(null, "CheetahUtils", "Generating new Port Number", Constants.LOG_INFO, false);

		long range = (long) portEndRange - (long) portStartRange + 1;
		// compute a fraction of the range, 0 <= frac < range
		long fraction = (long) (range * generator.nextDouble());
		int randomNumber = (int) (fraction + portStartRange);
		randomNumber = validatePort(randomNumber);
		return randomNumber;
	}

	/**
	 * @return Random Number
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	private static int generateRandomServer(int range) throws CheetahException {
		CheetahEngine.logger.logMessage(null, "CheetahUtils", "Randomizing Server selection..", Constants.LOG_INFO,
				false);
		Random rand = new Random();
		int n = rand.nextInt(range) + 1;
		return n;
	}

	/**
	 * @param portNum
	 * @return
	 * @throws CheetahException
	 */
	private static int validatePort(int portNum) throws CheetahException {
		CheetahEngine.logger.logMessage(null, "CheetahUtils", "Validating new Port Number: " + portNum,
				Constants.LOG_INFO, false);
		if (portNum != 0 && portNum != 4444 && portNum != 5555) {
			return portNum;
		} else {
			return 0;
		}
	}

	/**
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 * 
	 */
	public void createNewNode() throws CheetahException {
		if (Constants.GRID_CONNECTOR.equalsIgnoreCase(CheetahEngine.configurator.executionConfigurator.getExecutionEnv())
				&& ("WEB".equalsIgnoreCase(CheetahEngine.props.getProperty("test.type"))
						|| "WEBSERVICE".equalsIgnoreCase(CheetahEngine.props.getProperty("test.type"))
						|| "WEB_SERVICE".equalsIgnoreCase(CheetahEngine.props.getProperty("test.type")))) {
			int portNum = 0;
			try {
				String command = "START_NEW_NODE";
				// String host = Constants.remoteServerIP;
				String host = "";
				if (CheetahForm.getRemoteServerLink() == null) {
					host = getServerIP();
					CheetahForm.setRemoteServerLink(host);
				} else {
					host = CheetahForm.getRemoteServerLink();
				}

				int port = Integer.parseInt(CheetahEngine.configurator.executionConfigurator.getGeniePort());

				CheetahEngine.logger.logMessage(null, this.getClass().getName(),
						"Attempting socket connection for new node", Constants.LOG_INFO, false);
				Socket socket = new Socket(host, port);

				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				if (socket.isConnected()) {
					CheetahEngine.logger.logMessage(null, "CheetahUtils", "Connected to Server Successfully ",
							Constants.LOG_INFO, false);
				}
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

				while (portNum == 0) {
					portNum = generateRandomPort();
				}
				out.println(command + " " + String.valueOf(portNum));
				Thread.sleep(5000);
				CheetahEngine.nodePortNumber = portNum;
				socket.close();

			} catch (Exception e) {
				CheetahEngine.logger.logMessage(e, "CheetahUtils",
						"Exception" + e.getMessage() + "\n" + CheetahEngine.getExceptionTrace(e), Constants.LOG_ERROR,
						true);
			}
		}

	}

	/**
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 * 
	 */
	public void destroyNode() throws CheetahException {
		if (Constants.GRID_CONNECTOR.equalsIgnoreCase(CheetahEngine.configurator.executionConfigurator.getExecutionEnv())
				&& ("WEB".equalsIgnoreCase(CheetahEngine.props.getProperty("test.type"))
						|| "WEBSERVICE".equalsIgnoreCase(CheetahEngine.props.getProperty("test.type"))
						|| "WEB_SERVICE".equalsIgnoreCase(CheetahEngine.props.getProperty("test.type")))) {
			try {

				String command = "DESTROY_NODE";
				// String host = Constants.remoteServerIP;
				String host = "";
				if (CheetahForm.getRemoteServerLink() == null) {
					host = getServerIP();
					CheetahForm.setRemoteServerLink(host);
				} else {
					host = CheetahForm.getRemoteServerLink();
				}
				int port = Integer.parseInt(CheetahEngine.configurator.executionConfigurator.getGeniePort());

				CheetahEngine.logger.logMessage(null, this.getClass().getName(),
						"Attempting socket connection for node destruction", Constants.LOG_INFO, false);
				Socket socket = new Socket(host, port);

				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				if (socket.isConnected()) {
					CheetahEngine.logger.logMessage(null, "CheetahUtils", "Connected to Server Successfully ",
							Constants.LOG_INFO, false);
				}
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

				out.println(command + " " + String.valueOf(CheetahEngine.nodePortNumber));
				socket.close();
			} catch (Exception e) {
				CheetahEngine.logger.logMessage(e, "CheetahUtils",
						"Exception" + e.getMessage() + "\n" + CheetahEngine.getExceptionTrace(e), Constants.LOG_ERROR,
						true);
			}

		}

	}

	/**
	 * @return UUID
	 */
	public String generateTransaction() {
		UUID uniqueKey = UUID.randomUUID();
		System.out.println(uniqueKey);

		return uniqueKey.toString();
	}

	/**
	 * @param ccAdmin Boolean flag to indicate whether the Admin should be
	 *                 CC'ed in the notification
	 * @param fileName Name of the file that contains the email data
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public static void processNotification(String fileName, boolean ccAdmin) throws CheetahException {

		if (CheetahEngine.props.getProperty("send.test.complete.notification") != null
				&& "TRUE".equalsIgnoreCase(CheetahEngine.props.getProperty("send.test.complete.notification"))) {
			// Send email on test completion
			if (CheetahEngine.props.getProperty("email.file.name") != null) {
				try {
					File emailFile = new FileUtils().getFile("EMAIL", fileName);

					FileReader fileReader;
					String line = null;
					StringBuffer sb = new StringBuffer();

					fileReader = new FileReader(emailFile);
					// Always wrap FileReader in BufferedReader.
					BufferedReader bufferedReader = new BufferedReader(fileReader);
					while ((line = bufferedReader.readLine()) != null) {
						sb.append(line);
					}

					String[] emailArray = sb.toString().split(",");

					String toEmail = "";
					String ccEmail = "";
					String emailSubject = "";
					String emailBody = "";

					for (int i = 0; i < emailArray.length; i++) {
						if (emailArray[i].contains("toEmail")) {
							toEmail = emailArray[i].substring(emailArray[i].indexOf("=") + 1);
						} else if (emailArray[i].contains("ccEmail")) {
							ccEmail = emailArray[i].substring(emailArray[i].indexOf("=") + 1);
						} else if (emailArray[i].contains("emailSubject")) {
							emailSubject = emailArray[i].substring(emailArray[i].indexOf("=") + 1);
						} else if (emailArray[i].contains("emailBody")) {
							emailBody = emailArray[i].substring(emailArray[i].indexOf("=") + 1);
						}
					}
					// Always close files.
					bufferedReader.close();

					// Trigger Email
					Cognator.sendEmail(toEmail, ccEmail, emailSubject, emailBody, ccAdmin);

				} catch (FileNotFoundException e) {
					throw new CheetahException(e);
				} catch (IOException e) {
					throw new CheetahException(e);
				}

			}

		}
	}

	/**
	 * @return String Server IP Address
	 * @throws CheetahException Generic Exception Object that handles all
	 *                          exceptions
	 */
	public String getServerIP() throws CheetahException {
		String srvIP = null;
	
		String requestedExecutionServer = CheetahEngine.configurator.executionConfigurator.getGenieLink();

		if (requestedExecutionServer != null) {
			boolean hubStatus;
			boolean listenerStatus;
				hubStatus = javaPing(requestedExecutionServer, Integer.parseInt(CheetahEngine.configurator.executionConfigurator.getHubPort())); //Test Hub
				listenerStatus = javaPing(requestedExecutionServer, Integer.parseInt(CheetahEngine.configurator.executionConfigurator.getGeniePort())); // Test Listener
		
			if (hubStatus && listenerStatus) {
				CheetahEngine.logger.logMessage(null, this.getClass().getName(),
						"!**********************************************************************!"
								+ "\n!**********************************************************************!"
								+ "\nTest for Application: " + CheetahEngine.props.getProperty("app.name").trim()
								+ "\nRequested Server Mapping: " + requestedExecutionServer
								+ "\n!**********************************************************************!"
								+ "\n!**********************************************************************!",
						Constants.LOG_INFO);
				srvIP = requestedExecutionServer;
			} else {
				CheetahEngine.logger.logMessage(null, this.getClass().getName(),
						"!**********************************************************************!"
								+ "\n!**********************************************************************!"
								+ "\nTest for Application: " + CheetahEngine.props.getProperty("app.name").trim()
								+ "\nExecution on the requested server failed. " + "\n Hub Status: " + hubStatus
								+ "\n Listener Status: " + listenerStatus
								+ "\n!**********************************************************************!"
								+ "\n!**********************************************************************!",
						Constants.LOG_FATAL);
				srvIP = null;
			}

		}
		if (srvIP != null) {
			return srvIP;
		} else {
			CheetahEngine.logger.logMessage(null, this.getClass().getName(),
					"!**********************************************************************!"
					+"\n   Failed to map Grid Hub for execution ..."
							+ "\n!**********************************************************************!",
							Constants.LOG_FATAL);
			System.exit(1);
		}
		return srvIP;

	}

	private boolean validateIPv4Address(String address) {
		if (address.isEmpty()) {
			return false;
		}
		try {
			Object res = InetAddress.getByName(address);
			return res instanceof Inet4Address;
		} catch (final UnknownHostException ex) {
			return false;
		}

	}

	private boolean javaPing(String ip, int port) throws CheetahException {
		Socket pingSocket = null;
		PrintWriter out = null;
		BufferedReader in = null;
		boolean status = false;

		try {
			pingSocket = new Socket(ip, port);
			out = new PrintWriter(pingSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(pingSocket.getInputStream()));
			status = true;
		} catch (IOException e) {
			CheetahEngine.logger.logMessage(null, "CheetahUtils", "Ping: Connection failed- " + ip + ":" + port,
					Constants.LOG_INFO, false);
			status = false;
		} finally {
			out.close();
			try {
				in.close();
				pingSocket.close();
			} catch (IOException e) {
				throw new CheetahException(e);
			}
		}
		CheetahEngine.logger.logMessage(null, "CheetahUtils", "Ping: Successful connection - " + ip + ":" + port,
				Constants.LOG_INFO, false);

		return status;
	}

}
