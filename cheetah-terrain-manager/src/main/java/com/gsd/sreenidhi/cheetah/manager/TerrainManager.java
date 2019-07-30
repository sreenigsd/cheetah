package com.gsd.sreenidhi.cheetah.manager;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.gsd.sreenidhi.utils.FileUtils;

/**
 * @author Gundlupet Sreenidhi
 *
 */
public class TerrainManager {

	private static String host;
	private static String command;
	private static String node_port;
	private static String manager_version = "VERSION"; //While Commiting, ensure the value is "V_E_R_S_I_O_N" (without the underscores). The value is dynamically updated during the build process.

	public static void main(String[] args) throws IOException {
		
		System.out.println("###########################################################################################");
		System.out.println("Cheetah Terrain Manager");
		System.out.println("Handler Version : "+ manager_version);
		System.out.println("Author: Gundlupet Sreenidhi");
		System.out.println("###########################################################################################");
		
		CommandLine line = parseArguments(args);
		HashMap<String,String> map = null;
		if (line.hasOption("k")) {
			CommandLine kline = parseArguments(args);
			map = validateArgsforOption("k", kline);

			if (map!=null) {

				String host = map.get("host");
				String port = map.get("port");
				String node_port = map.get("node_port");
				String command = map.get("cmd");

				System.out.println("h:" + host);
				System.out.println("p:" + port);
				System.out.println("n:" + node_port);
				System.out.println("c:" + command);

				Socket socket = null;
				BufferedReader br = null;
				PrintWriter out = null;
				
				try {

					System.out.println("Attempting socket connection... Host:" + host + " port:" + port);
					socket = new Socket(host, Integer.parseInt(port));

					br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					if (socket.isConnected()) {
						System.out.println("Connected to Server Successfully ");
					} else {
						System.out.println("Connection failed. Please validate your arguements and try again... ");
						System.exit(1);
					}

					out = new PrintWriter(socket.getOutputStream(), true);
					String cmd = command + " " + String.valueOf(node_port);
					System.out.println("Triggering command: " + cmd);
					out.println(cmd);
					Thread.sleep(5000);

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					br.close();
					out.close();
					socket.close();
				}
			} else {
				System.out.println("Could not identify required arguements.");
				System.out.println("USAGE: TerrainManager -k -h <HOST_IP> -p <HOST_PORT> -c <COMMAND> -n<NODE_PORT>");
				printAppHelp();
			}
		} else if (line.hasOption("t")) {
			CommandLine cline = parseArguments(args);
			map = validateArgsforOption("t", cline);

			if (map!=null) {

				String host = map.get("host");
				String port = map.get("port");
				String command = map.get("cmd");

				System.out.println("h:" + host);
				System.out.println("p:" + port);
				System.out.println("c:" + command);

				Socket socket = null;
				BufferedReader br = null;
				PrintWriter out = null;
				InputStream is = null;
				InputStreamReader isr = null;
				try {

					System.out.println("Attempting socket connection... Host:" + host + " port:" + port);
					socket = new Socket(host, Integer.parseInt(port));

					br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					if (socket.isConnected()) {
						System.out.println("Connected to Server Successfully ");
					} else {
						System.out.println("Connection failed. Please validate your arguements and try again... ");
						System.exit(1);
					}

					out = new PrintWriter(socket.getOutputStream(), true);
					String cmd = "CMD " + command;
					System.out.println("Triggering command: " + cmd);
					out.println(cmd);
					Thread.sleep(5000);

					byte[] messageByte = new byte[1000];
					boolean end = false;
					String dataString = "";

					try {
						DataInputStream in = new DataInputStream(socket.getInputStream());
						int bytesRead = 0;
						while ((bytesRead = in.read(messageByte)) > -1) {
							dataString += new String(messageByte, 0, bytesRead);

						}
						System.out.println("cmd response: " + dataString);
					} catch (Exception e) {
						e.printStackTrace();
					}

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					br.close();
					out.close();
					if (isr != null) {
						isr.close();
					}

					if (is != null) {
						is.close();
					}

					socket.close();
				}
			} else {
				System.out.println("Could not identify required arguements.");
				System.out.println("USAGE: TerrainManager -c -h <HOST_IP> -p <HOST_PORT> -c <COMMAND>");
				printAppHelp();
			}
		} else {
			System.out.println("Could not execute...");
			printAppHelp();

		}

	}

	/**
	 * @param execOption
	 * @param line
	 * @return
	 * @throws IOException 
	 */
	private static HashMap<String, String> validateArgsforOption(String execOption, CommandLine line) throws IOException {
		boolean hasOptions = false;
		//Enter data using BufferReader 
		BufferedReader reader =  new BufferedReader(new InputStreamReader(System.in));
		String host;
		String port;
		String cmd;
		String node_port;
		
		HashMap<String,String> map = new HashMap<String,String>();
		// create Options object
		Options options = new Options();
		
		if (execOption.equalsIgnoreCase("k")) {
			if (line.hasOption("h") && line.hasOption("p") && line.hasOption("n") && line.hasOption("c")) {
				hasOptions = true;
				map.put("host", line.getOptionValue("h"));
				map.put("port", line.getOptionValue("p"));
				map.put("node_port", line.getOptionValue("n"));
				map.put("cmd", line.getOptionValue("c"));
			} else {
				
				System.out.println("Could not identify required arguements...");
				if(!line.hasOption("h")) {
					System.out.println("Enter the host ip to connect to: ");
					host = reader.readLine(); 
					map.put("host", host);
				}else {
					map.put("host", line.getOptionValue("h"));
				}
				if(!line.hasOption("p")) {
					System.out.println("Enter the host port (listener port) to tunnel through: ");
					port = reader.readLine(); 
					map.put("port", port);
				}else {
					map.put("port", line.getOptionValue("p"));	
				}
				
				if(!line.hasOption("n")) {
					System.out.println("Enter the node port interact with: ");
					node_port = reader.readLine(); 
					map.put("node_port", node_port);
				}else {
					map.put("node_port", line.getOptionValue("n"));	
				}
				if(!line.hasOption("c")) {
					System.out.println("Enter the command you wish to execute on the remote machine: ");
					cmd = reader.readLine(); 
					map.put("cmd", cmd);
				}else {
					map.put("cmd", line.getOptionValue("c"));	
				}
				hasOptions = true;
			}
		} else if (execOption.equalsIgnoreCase("t")) {
			if (line.hasOption("h") && line.hasOption("p") && line.hasOption("c")) {
				hasOptions = true;
				map.put("host", line.getOptionValue("h"));
				map.put("port", line.getOptionValue("p"));
				map.put("cmd", line.getOptionValue("c"));
			} else {
				System.out.println("Could not identify required arguements...");
				if(!line.hasOption("h")) {
					System.out.println("Enter the host ip to connect to: ");
					host = reader.readLine(); 
					map.put("host", host);
				}else {
					map.put("host", line.getOptionValue("h"));
				}
				if(!line.hasOption("p")) {
					System.out.println("Enter the host port (listener port) to tunnel through: ");
					port = reader.readLine(); 
					map.put("port", port);
				}else {
					map.put("port", line.getOptionValue("p"));	
				}
				if(!line.hasOption("c")) {
					System.out.println("Enter the command you wish to execute on the remote machine: ");
					cmd = reader.readLine(); 
					map.put("cmd", cmd);
				}else {
					map.put("cmd", line.getOptionValue("c"));	
				}
				hasOptions = true;
			}
		}

		return map;
	}

	/**
	 * Parses application arguments
	 *
	 * @param args
	 * @return <code>CommandLine</code> which represents a list of application
	 *         arguments.
	 */
	private static CommandLine parseArguments(String[] args) {

		Options options = getOptions();
		CommandLine line = null;

		CommandLineParser parser = new DefaultParser();

		try {
			line = parser.parse(options, args);
		} catch (ParseException ex) {
			System.err.println(ex);
			printAppHelp();
			System.exit(1);
		}

		return line;
	}

	/**
	 * Generates application command line options
	 *
	 * @return application <code>Options</code>
	 */
	private static Options getOptions() {
		Options options = new Options();
		options.addOption("h", "host", true, "host to connect to");
		options.addOption("p", "port", true, "port number of the host to connect to");

		options.addOption("k", "nodekill", false, "Destroy Node");
		options.addOption("n", "nodeport", true, "Port number of node to destroy");
		options.addOption("c", "cmd", true, "Execute command");
		options.addOption("t", "task", false, "Execute command task");
		return options;
	}

	/**
	 * Prints application help
	 */
	private static void printAppHelp() {
		Options options = getOptions();
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("TerrainManager", options, true);
	}

}
