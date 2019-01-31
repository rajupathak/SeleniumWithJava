package com.seleniumwithjavascript.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class PuttyCommandExecution {

	//public InputStream in;

	public static void deleteCommandExecution(String usr, String pwd, String ip) {
		Runtime runtime = Runtime.getRuntime();
		Process process = null;
		System.out.println("Execution of smanger.pl -status  and cat command Started...");
		// System.setOut(new PrintStream(out, true));

		String deleteCommand = " D:/putty.exe -l " + usr + " -pw " + pwd + " " + ip + " -m D:/DeleteCommand.txt";
		

		try {
			process = runtime.exec(deleteCommand);
			process.waitFor();
			System.out.println("command execution is finished");

			System.out.println("Exit Code: " + process.exitValue());

		}

		catch (IOException e) {
			e.printStackTrace();
		}

		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	
	public static void addCommandExecution(String usr, String pwd, String ip) {
		Runtime runtime = Runtime.getRuntime();
		Process process = null;
		System.out.println("Execution of smanger.pl -status  and cat command Started...");
		// System.setOut(new PrintStream(out, true));

		
		String addComand = " D:/putty.exe -l " + usr + " -pw " + pwd + " " + ip + " -m D:/AddCommand.txt";

		try {
			process = runtime.exec(addComand);
			process.waitFor();
			System.out.println("command execution is finished");

			System.out.println("Exit Code: " + process.exitValue());

		}

		catch (IOException e) {
			e.printStackTrace();
		}

		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
}
