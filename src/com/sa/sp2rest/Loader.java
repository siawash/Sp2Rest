package com.sa.sp2rest;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import javax.swing.JOptionPane;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Loader {
	
//	private static String appName, appPackage, appUrl, spListPath;
//	
//	private static void getAppInfo() {
//		
//		Scanner sc= new Scanner(System.in);    //System.in is a standard input stream  
//		
//		System.out.print("Enter Application Name : ");  
//		appName = sc.next();  
//		
//		System.out.print("Enter Package : ");  
//		appPackage = sc.next();  
//		
//		System.out.print("Enter Application Url : ");  
//		appUrl = sc.next();  
//		
//		System.out.print("Enter Sp List Path : ");  
//		spListPath = sc.next();  
//
//	}
//	
//	public static void main2(String[] args) throws ClassNotFoundException, SQLException {
//		
////		Calendar cal = Calendar.getInstance();
//////		System.out.println(cal.getTime());
////		System.out.println(cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) < 10 ? "0" + cal.get(Calendar.MONTH) : cal.get(Calendar.MONTH)) + "-" + cal.get(Calendar.DATE));
////		System.out.println(cal.get(Calendar.HOUR) + ":" + (cal.get(Calendar.MINUTE) < 10 ? "0" + cal.get(Calendar.MINUTE) : cal.get(Calendar.MINUTE)) + ":" + cal.get(Calendar.SECOND));
////		System.out.println(cal.get(Calendar.DAY_OF_WEEK));
////		System.out.println("***************");		
//////		for(int i = 0; i < 20; i++)
//////			System.out.println(i + " -> " + cal.get(i));
////		
////		if (true) return;
//		
//		
//		
////		********************************************
////		********************************************
////		getAppInfo();
//		
//		appName = "SubDraft";
//		appPackage = "com.behsazan.dmnservice.fnrsubdraft";
//		appUrl = "/dmnService/fnrsubdraft";
//		spListPath = "E:\\current_projects\\splist.txt";
//
//		
//		try {					
//			
//			if (!Application.evaluateSpList(spListPath)) {
//
//				Logger.log("the splist file has bad format");
//				JOptionPane.showMessageDialog(null, "the splist file has bad format");	
//				System.exit(0);
//			}
//
//			Application.getInstance(appName, appPackage, appUrl, spListPath);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}

}
