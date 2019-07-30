package com.gsd.sreenidhi.cheetah.turf;

import java.io.BufferedReader;
import java.io.InputStreamReader;
 

public class SystemProcess {
	
	public static void getSystemprocesses(int i){
		try {
			String process;
			// getRuntime: Returns the runtime object associated with the current Java application.
			// exec: Executes the specified string command in a separate process.
			String cmd = System.getenv("windir") +"\\system32\\"+"netstat -nao |findstr :"+i;
			System.out.println("Identifying processes with " + cmd);
			Process p = Runtime.getRuntime().exec(cmd);
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((process = input.readLine()) != null) {
				System.out.println(process); // <-- Print all Process here line
												// by line
			}
			input.close();
		} catch (Exception err) {
			err.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		System.out.println("Retrieving System processes...");
		getSystemprocesses(8888);
		System.out.println("processes complete!");
	}
}
