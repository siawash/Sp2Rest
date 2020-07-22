package com.sa.sp2rest;

import java.util.ArrayList;
import java.util.List;

public class AppLogger {
	
	private static String fileContent = 
			"package %s.common;\r\n" + 
			"\r\n" + 
			"import com.google.gson.JsonObject;\r\n" + 
			"import io.jsonwebtoken.Claims;\r\n" + 
			"import org.apache.logging.log4j.LogManager;\r\n" + 
			"import org.apache.logging.log4j.Logger;\r\n" + 
			"import org.springframework.stereotype.Service;\r\n" + 
			"\r\n" + 
			"@Service\r\n" + 
			"public class AppLogger {\r\n" + 
			"\r\n" + 
			"    private Logger core2Logger = LogManager.getLogger(\"core2Logger\");    \r\n" + 
			"\r\n" + 
			"	public void log(JsonObject info) {\r\n" + 
			"		core2Logger.info(info);\r\n" + 
			"	}	\r\n" + 
			"\r\n" + 
			"	public void log(Claims claims) {\r\n" + 
			"		core2Logger.info(AppUtil.toJson(claims));\r\n" + 
			"	}	\r\n" + 
			"	\r\n" + 
			"	public void log(String message) {\r\n" + 
			"		core2Logger.info(message);\r\n" + 
			"	}\r\n" + 
			"}\r\n";
	

	public static List<String> getContent(String pkg) {
		fileContent = fileContent.formatted(pkg);
		List<String> content = new ArrayList<String>();
		content.add(fileContent);
		
		return content;
	}
}
