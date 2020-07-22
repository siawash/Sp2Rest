package com.sa.sp2rest;

import java.util.*;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import java.io.*;


public class Logger {
	
	private static JTextArea txtResult;
	private static FileWriter fw;
	
	
	private static final String logFileName = "WrapperGenerator.log";

	static {
//		if ((new File(logFileName)).isFile()) {
//			(new File(logFileName)).delete();
//		}		

		try {
			new File(logFileName).delete();
			fw = new FileWriter(logFileName);
		} catch (IOException e) {
//			JOptionPane.showMessageDialog(null, "log file could not be created");
		}
	}
	
	public static void setTxtResult(JTextArea txtResult) {
		Logger.txtResult = txtResult;
	}

	public static void closeFileWriter() {
		try {
			fw.close();
		} catch (IOException e) {
//			JOptionPane.showMessageDialog(null, "closing file Writer failed : " + e.getMessage());
		}	
	}
	
	public static void info(String info) {
		try {
			String log = new Date().toString() + " : " + info + "\r\n";
			
			txtResult.append(log);
			System.out.print(log);
			
//			FileWriter fw = new FileWriter(logFileName);
			fw.write(log);
//			fw.close();
		} catch (Exception e) {
//			JOptionPane.showMessageDialog(null, "logging failed : " + e.getMessage());
		}
	}
	
	public static void log(String info) {
		 SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	              Logger.info(info);
	            }
	          });
	}

}