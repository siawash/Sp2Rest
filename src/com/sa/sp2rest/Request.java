package com.sa.sp2rest;

import java.util.ArrayList;
import java.util.List;

public class Request {

	private static String fileContent = 
			"package %s.dto.common;\r\n" + 
			"\r\n" + 
			"import %s.common.AppUtil;\r\n" + 
			"import com.fasterxml.jackson.annotation.JsonIgnore;\r\n" + 
			"import com.google.gson.JsonObject;\r\n" + 
			"\r\n" + 
			"import io.jsonwebtoken.Claims;\r\n" + 
			"\r\n" + 
			"public class Request {\r\n" + 
			"\r\n" + 
			"	private transient Claims tokenInfo;\r\n" + 
			"\r\n" + 
			"	@JsonIgnore\r\n" + 
			"	public void setTokenInfo(Claims tokenInfo) {\r\n" + 
			"\r\n" + 
			"		this.tokenInfo = tokenInfo;\r\n" + 
			"	}\r\n" + 
			"\r\n" + 
//			"	@JsonIgnore\r\n" + 
//			"	public String getUserId() {\r\n" + 
//			"		return tokenInfo.get(\"userId\").toString();\r\n" + 
//			"	}\r\n\r\n" +			
			"	public JsonObject getLogInfo(String profile) {\r\n" + 
			"\r\n" + 
			"		JsonObject logInfo = new JsonObject();\r\n" + 
			"\r\n" + 
			"		logInfo.addProperty(\"app\", \"SubDraft\");\r\n" + 
			"\r\n" + 
			"		logInfo.addProperty(\"serviceName\", this.getClass().getSimpleName());\r\n" + 
			"		logInfo.addProperty(\"servicePackage\", this.getClass().getName());\r\n" + 
			"		logInfo.addProperty(\"contextPath\", tokenInfo.get(\"contextPath\").toString());\r\n" + 
			"		logInfo.addProperty(\"servicePath\", tokenInfo.get(\"servicePath\").toString());\r\n" + 
			"		logInfo.addProperty(\"date\", AppUtil.get_Date());\r\n" + 
			"		logInfo.addProperty(\"time\", AppUtil.get_Time());\r\n" + 
			"		\r\n" + 
			"		if (profile.equals(\"opr\")) {\r\n" + 
			"			logInfo.addProperty(\"clientIp\", tokenInfo.get(\"ip-address\").toString());\r\n" + 
			"			logInfo.addProperty(\"user\", tokenInfo.get(\"user-personal-id\").toString());\r\n" + 
			"			logInfo.addProperty(\"roleId\", tokenInfo.get(\"role-id\").toString());\r\n" + 
			"			logInfo.addProperty(\"org\", tokenInfo.get(\"branch-code\").toString());\r\n" + 
			"			logInfo.addProperty(\"orgType\", tokenInfo.get(\"current-org-type\").toString());\r\n" + 
			"			logInfo.addProperty(\"groups\", tokenInfo.get(\"groups\").toString());\r\n" + 
			"			\r\n" + 
			"			logInfo.addProperty(\"sub\", tokenInfo.get(\"sub\").toString());\r\n" + 
			"			logInfo.addProperty(\"iat\", tokenInfo.get(\"iat\").toString());\r\n" + 
			"			logInfo.addProperty(\"exp\", tokenInfo.get(\"exp\").toString());\r\n" + 
			"		}\r\n" + 
			"\r\n" + 
			"		return logInfo;\r\n" + 
			"	}\r\n" + 
			"}\r\n";
		
	public static List<String> getContent(String applicationName, String pkg) {
		fileContent = fileContent.formatted(pkg, pkg, applicationName);
		List<String> content = new ArrayList<String>();
		content.add(fileContent);
		
		return content;
	}
}
