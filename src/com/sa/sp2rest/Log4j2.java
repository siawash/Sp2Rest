package com.sa.sp2rest;

import java.util.ArrayList;
import java.util.List;

public class Log4j2 extends BaseClass {
		
	private static String fileContent = 
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
			"<Configuration>\r\n" + 
			"       <Properties>\r\n" + 
			"              <property name=\"core2LogPath\">%sLog</property>\r\n" + 
			"       </Properties>\r\n" + 
			"\r\n" + 
			"       <Appenders>		 \r\n" + 
			" 		  <RollingFile name=\"core2LogAppender\" fileName=\"${core2LogPath}/%sCore2Log.log\" filePattern=\"${core2LogPath}/archive/%sCore2Log %d{yyyy-MM-dd} - %i.log.gz\" immediateFlush=\"false\" append=\"false\">\r\n" + 
			"				 <PatternLayout pattern =\"%m%n\" />\r\n" + 
			"				 <DefaultRolloverStrategy max=\"500\"/>\r\n" + 
			"				 <Policies>\r\n" + 
			"					 <TimeBasedTriggeringPolicy/>\r\n" + 
			"					 <SizeBasedTriggeringPolicy size=\"10 MB\"/>\r\n" + 
			"				 </Policies>\r\n" + 
			"		  </RollingFile>		\r\n" + 
			"       </Appenders>\r\n" + 
			"\r\n" + 
			"       <Loggers>\r\n" + 
			"          <AsyncLogger name=\"core2Logger\" level=\"debug\" includeLocation=\"true\"><AppenderRef ref=\"core2LogAppender\"/></AsyncLogger>\r\n" + 
			"       </Loggers>\r\n" + 
			"</Configuration>\r\n";

	public static List<String> getContent(String applicationName) {
		List<String> content = new ArrayList<String>();
		content.add(fileContent.replaceAll("%s", applicationName));
		
		return content;
	}
	
}
