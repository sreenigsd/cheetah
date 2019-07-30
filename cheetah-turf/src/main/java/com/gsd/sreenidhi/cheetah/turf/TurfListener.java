package com.gsd.sreenidhi.cheetah.turf;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.Random;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.ClassNotFoundException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.net.ServerSocket;

import com.gsd.sreenidhi.utils.FileUtils;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT;
import java.lang.reflect.Field;
import java.time.LocalDateTime; // Import the LocalDateTime class
import java.time.format.DateTimeFormatter; // Import the DateTimeFormatter class

/**
 * @author Sreenidhi, Gundlupet
 *
 */
public class TurfListener {
	
	private static String turf_version = "VERSION"; //DO NOT MODIFY. CHECK-IN with value "VERSION"

	public static void main(String[] args) {
		
		Random portGenerator = new Random();
		int PORT_START_RANGE = 5556;
		int PORT_END_RANGE = 7999;
		int portNum = generateRandomPort(PORT_START_RANGE, PORT_END_RANGE, portGenerator);
		String pathCommand = "PATH=";
		final String newNodeCommand = "D:\\Selenium\\startnode.cmd";
		final String destroyNodeCommand = "D:\\Selenium\\destroynode.cmd";
		final String nodeIdentifierCommand = "D:\\Selenium\\nodeIdentifier.cmd";
		final HashMap hmap = new HashMap<String, Long>();
		
		LocalDateTime mDO = LocalDateTime.now();
		DateTimeFormatter mFO = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String fD = mDO.format(mFO);
		
		System.out.println("###########################################################################################");
		System.out.println("Cheetah Turf Program");
		System.out.println("Turf Version : "+ turf_version);
		System.out.println("Author: Gundlupet Sreenidhi");
		System.out.println("Listener Start Timestamp: " + fD);
		System.out.println("###########################################################################################");

		try {
			final ServerSocket serverSocket = new ServerSocket(5555);
			new Thread("Device Listener") {
				public void run() {
					Socket socket = null;
					BufferedReader input;
					String hubPid = null;

					ArrayList<CheetahObject> hubValues;
					try {

						while (hubPid == null) {
							hubValues = getCheetahObjects(Integer.toString(4444));
							for (int i = 0; i < hubValues.size(); i++) {
								try {
									String[] destination = hubValues.get(i).getDestination().toString().split(":");
									if (destination[1].equals("0")) {
										hubPid = hubValues.get(i).getPid();
									}
								} catch (Exception e) {
									System.out.println("Error identifying Hub PID.");
								}
							}
							Thread.sleep(5000);
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}

					while (true) {
						try {
							System.out.println("\n");
							System.out.println("********************************************************************************************");
							System.out.println("**************************          Listener Running . . .  ********************************");
							System.out.println("                                      version: " + listener_version );
							System.out.println("********************************************************************************************");
							while ((socket = serverSocket.accept()) != null) {
								LocalDateTime myDateObj = LocalDateTime.now();
								DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
								String formattedDate = myDateObj.format(myFormatObj);

								System.out.println("Timestamp: " + formattedDate + "\nIncoming : " + socket.toString());
								System.out.println("--------------------------------------------------------------------");

								BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
								String commnd = null;
								String commndPort = null;
								String portAppend = "";

								while ((commnd = in.readLine()) != null) {
									myDateObj = LocalDateTime.now();
									formattedDate = myDateObj.format(myFormatObj);

									System.out.println("Command: " + commnd);
									System.out.println("-------");

									if (commnd.toUpperCase().contains("START_NEW_NODE")) {
										commndPort = commnd.substring(15);
										if (commndPort != null && !commndPort.equalsIgnoreCase("")
												&& Integer.valueOf(commndPort) > 0) {
											portAppend = "-port " + commndPort + " -unregisterIfStillDownAfter 20000";
										}
										try {
											System.out.println(formattedDate + "Attempting to run command: " + newNodeCommand + " " + portAppend);
											Runtime rt = Runtime.getRuntime();
											Process pr = rt.exec("cmd.exe /c start " + newNodeCommand + " " + portAppend);
											System.out.println("command executed");
											long pid = getPidOfProcess(pr);
											System.out.println("PID of new node: " + pid);
											hmap.put(commndPort, pid);

											int exitVal = pr.waitFor();
											System.out.println("Exited with code " + exitVal);
											break;
										} catch (Exception e) {
											System.out.println(e.toString());
											e.printStackTrace();
										}
									} else if (commnd.toUpperCase().contains("DESTROY_NODE")) {
										commndPort = commnd.substring(13);
										if (commndPort != null && !commndPort.equalsIgnoreCase("")
												&& Integer.valueOf(commndPort) > 0) {
											portAppend = "-port " + commndPort;
										}
										try {

											ArrayList<CheetahObject> nodeValues = getCheetahObjects(commndPort);

											if (nodeValues != null) {
												System.out.println(
														"Attempting to kill " + nodeValues.size() + " processes");
												for (int i = 0; i < nodeValues.size(); i++) {
													try {
														System.out.println(
																"Kill Process PID: " + nodeValues.get(i).getPid());
														if (!nodeValues.get(i).getPid().equals(hubPid)) {
															ProcessBuilder killerProcess = new ProcessBuilder("cmd.exe",
																	"/c", "TASKKILL /F /PID "
																			+ nodeValues.get(i).getPid() + " && EXIT");
															killerProcess.redirectErrorStream(true);
															Process pKiller = killerProcess.start();
														} else {
															System.out.println("Cannot delete pid: " + hubPid
																	+ " as it is the Hub process...");
														}
													} catch (Exception e) {
														System.out.println("Could not kill pid.");
													}
												}
											} else {
												System.out.print("Failed to retrieve Cheetah Node Objects!!");
											}

											System.out.println("command executed");

											break;
										} catch (Exception e) {
											System.out.println(e.toString());
											e.printStackTrace();
										}
									} else if (commnd.toUpperCase().contains("CMD")) {
										String cmd = commnd.substring(4);
										cmd = cmd.replaceAll("\"", "");
										cmd = cmd.trim();
										OutputStream os = socket.getOutputStream();
										OutputStreamWriter osw = new OutputStreamWriter(os);
										BufferedWriter bw = new BufferedWriter(osw);
										BufferedOutputStream bout = null;
										String content = executeCommand(cmd);

										try {
											System.out.println("CMD Response sent to the client is: \n" + content);
											bw.write(content);
											bw.flush();

										} catch (Exception e) {
											e.printStackTrace();
										} finally {
											bw.close();
											osw.close();
											os.close();
										}

									}
									System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
								}
								break;
							}
						} catch (Exception e) {
							if(e.getMessage().contains("socket closed")) {
								System.out.println("Socket response complete...");
							}else {
								e.printStackTrace();	
							}
							
						}
					}

				}

			}.start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static String executeCommand(String cmd) throws IOException, InterruptedException {
		BufferedOutputStream bout = null;
		String content = "";
		LocalDateTime myDateObj = LocalDateTime.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String formattedDate = myDateObj.format(myFormatObj);

		String path = "set PATH=C:\\WINDOWS\\system32;C:\\WINDOWS;C:\\WINDOWS\\System32\\Wbem;C:\\WINDOWS\\System32\\WindowsPowerShell\\v1.0\\";
		cmd = path + " && " + cmd;

		System.out.println(formattedDate + "  Attempting to run command: " + cmd);

		ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", cmd);
		builder.redirectErrorStream(true);
		Process p = builder.start();

		/* p.waitFor();
			content = processCommandOutput(p.getInputStream());*/
		
		BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while (true) {
            line = r.readLine();
            if (line == null) { break; }
            content = content +"\n"+ line;
        }
        System.out.println("ok!");

		return content;
	}

	/**
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	private static String processCommandOutput(InputStream inputStream) throws IOException {
		int bytesRead = -1;
		byte[] bytes = new byte[1024];
		String output = "";
		String[] lines = null;
		while ((bytesRead = inputStream.read(bytes)) > -1) {

			String out = new String(bytes, 0, bytesRead);
			System.out.println(out);
			lines = out.split("\r\n");
		}

		for (int i = 0; i < lines.length; i++) {
			output = output + "\n" + lines[i];
		}
		return output;
	}

	/**
	 * @param commndPort
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private static ArrayList<CheetahObject> getCheetahObjects(String commndPort)
			throws IOException, InterruptedException {
		LocalDateTime myDateObj = LocalDateTime.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String formattedDate = myDateObj.format(myFormatObj);
		String cmd;

		String path = "set PATH=C:\\WINDOWS\\system32;C:\\WINDOWS;C:\\WINDOWS\\System32\\Wbem;C:\\WINDOWS\\System32\\WindowsPowerShell\\v1.0\\";
		cmd = path + " && netstat -nao | findstr :" + commndPort;

		System.out.println(formattedDate + "  Attempting to run command: " + cmd);

		ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", cmd);
		builder.redirectErrorStream(true);
		Process p = builder.start();

		p.waitFor();
		ArrayList<CheetahObject> values = printProcessStream(p.getInputStream());

		return values;
	}

	/**
	 * @param pORT_START_RANGE
	 * @param pORT_END_RANGE
	 * @param portGenerator
	 * @return
	 */
	private static int generateRandomPort(int pORT_START_RANGE, int pORT_END_RANGE, Random portGenerator) {
		long range = (long) pORT_END_RANGE - (long) pORT_START_RANGE + 1;
		// compute a fraction of the range, 0 <= frac < range
		long fraction = (long) (range * portGenerator.nextDouble());
		int randomNumber = (int) (fraction + pORT_START_RANGE);
		return randomNumber;
	}

	/**
	 * @param p
	 * @return
	 */
	public static synchronized long getPidOfProcess(Process p) {
		long result = -1;
		try {
			// for windows
			if (p.getClass().getName().equals("java.lang.Win32Process")
					|| p.getClass().getName().equals("java.lang.ProcessImpl")) {
				Field f = p.getClass().getDeclaredField("handle");
				f.setAccessible(true);
				long handl = f.getLong(p);
				Kernel32 kernel = Kernel32.INSTANCE;
				WinNT.HANDLE hand = new WinNT.HANDLE();
				hand.setPointer(Pointer.createConstant(handl));
				result = kernel.GetProcessId(hand);
				f.setAccessible(false);
			}
			// for unix based operating systems
			else if (p.getClass().getName().equals("java.lang.UNIXProcess")) {
				Field f = p.getClass().getDeclaredField("pid");
				f.setAccessible(true);
				result = f.getLong(p);
				f.setAccessible(false);
			}
		} catch (Exception ex) {
			result = -1;
		}
		return result;
	}

	/**
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	private static ArrayList<CheetahObject> printProcessStream(InputStream inputStream) throws IOException {
		ArrayList<CheetahObject> values = new ArrayList<CheetahObject>();

		CheetahObject fo;

		int bytesRead = -1;
		byte[] bytes = new byte[1024];
		String output = "";
		while ((bytesRead = inputStream.read(bytes)) > -1) {
			String[] lines;
			String out = new String(bytes, 0, bytesRead);
			System.out.println(out);
			lines = out.split("\r\n");

			for (int j = 0; j < lines.length; j++) {
				fo = new CheetahObject();
				String[] v = lines[j].trim().replaceAll(" +", " ").split(" ");
				fo.setProtocol(v[0].toString());
				fo.setSource(v[1].toString());
				fo.setDestination(v[2].toString());

				fo.setConnection_type(v[3].toString());
				fo.setPid(v[4].toString());

				output = output + out;

				if (!fo.getPid().equals("0")) {
					values.add(fo);
				}
			}

		}
		return values;
	}
}
