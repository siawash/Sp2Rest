package com.sa.sp2rest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.JOptionPane;

public class Config {

	public static final List<String> ERROR_PARAMETER_NAMES = Arrays.asList(new String[] { "PO_ERROR", "PO_ERRORCODE", "P_ERROR", "PO_ERR" });
	public static final List<String> STEP_PARAMETER_NAME = Arrays.asList(new String[] { "PO_STEP", "PO_STEPCODE", "PO_ERRORSTEP" });
//	public static final List<String> USER_IDS = Arrays.asList(new String[] {"PI_USERID", "PI_USERCD", "PI_USERCODE", "P_USERID", "P_USERCD", "P_USERCODE" });
	public static final String ACCOUNTING_MESSAGE_PARAMETER_NAME = "PO_ACCOUNTING_MESSAGE";
	
	
//	private static String accMQHost;
//	private static int accMQPort;
//	private static String accMQQManager;
//	private static String accMQChannel;
//	private static String accMessagParameter;

//	private static String core2LoggerUrl;

//	private static String name;
//	private static String driver;
//	private static String url;
//	private static String username;
//	private static String password;

//	public static String getAccMQHost() {
//		return accMQHost;
//	}
//
//	public static int getAccMQPort() {
//		return accMQPort;
//	}
//
//	public static String getAccMQQManager() {
//		return accMQQManager;
//	}

//	public static String getAccMessagParameter() {
//		return accMessagParameter;
//	}

//	public static String getAccMQChannel() {
//		return accMQChannel;
//	}

//	public static String getCore2LoggerUrl() {
//		return core2LoggerUrl;
//	}

	static {
		try (InputStream input = Loader.class.getClassLoader().getResourceAsStream("config.properties")) {

			if (input == null) {
//				System.out.println("db2.properties file not exists");
				Logger.log("db2.properties file not exists");
			}

			Properties prop = new Properties();

			prop.load(input);

//			driver = prop.getProperty(name + ".driver");
//			url = prop.getProperty(name + ".url");
//			username = prop.getProperty(name + ".username");
//			password = prop.getProperty(name + ".password");
//
//			Class.forName(driver);

//			accMQHost = prop.getProperty("acc.mq.host");
//			accMQPort = Integer.valueOf(prop.getProperty("acc.mq.port"));
//			accMQQManager = prop.getProperty("acc.mq.queue.manager");
//			accMQChannel = prop.getProperty("acc.mq.channel");
//
//			accMessagParameter = prop.getProperty("acc.account.message.parameter");

//			core2LoggerUrl = prop.getProperty("core2.logger.url");

		} catch (IOException ex) {
//			System.out.println("can not read db2.properties file");
			Logger.log("There is something wrong with config.properties");
//			JOptionPane.showMessageDialog(null, "There is something wrong with config.properties");
			Thread.currentThread().stop();
//			System.exit(0);
		}
	}

	private static String driver = "";
	public static Map<String, String> getRepositoryProperties(String repositoryName) throws IOException {

		try {
			InputStream input = Loader.class.getClassLoader().getResourceAsStream("config.properties");

			Properties prop = new Properties();

			prop.load(input);

			Map<String, String> result = new HashMap<String, String>();

			if(Utility.isNullOrEmpty(driver))
				driver = prop.getProperty("driver");
			
			result.put("driver", driver);			
			result.put("url", prop.getProperty(repositoryName + ".url"));
			result.put("username", prop.getProperty(repositoryName + ".username"));
			result.put("password", prop.getProperty(repositoryName + ".password"));

			return result;

		} catch (IOException ex) {
			Logger.log("There is something wrong with config.properties");
//			JOptionPane.showMessageDialog(null, "There is something wrong with config.properties");
			Thread.currentThread().stop();
//			System.exit(0);
			return null;
		}
	}

}
