package com.sa.sp2rest;

import java.util.ArrayList;
import java.util.List;

public class Response {

	private static String fileContent = "package %s.dto.common;\r\n\r\n\r\n" + 
			"public class Response {\r\n" + 
			"\r\n" + 
			"	private int errorCode;\r\n" + 
			"    private String exception = \"\";\r\n" + 
			"	\r\n" + 
			"    public int getErrorCode() {\r\n" + 
			"        return errorCode;\r\n" + 
			"    }\r\n" + 
			"    public void setErrorCode(int errorCode) {\r\n" + 
			"        this.errorCode = errorCode;\r\n" + 
			"    }\r\n" + 
			"\r\n" + 
			"    public String getException() {\r\n" + 
			"        return this.exception;\r\n" + 
			"    }\r\n" + 
			"    public void setException(String exception) {\r\n" + 
			"        this.exception = exception;\r\n" + 
			"    }\r\n" + 
			"\r\n" + 
			"    public Response() {}\r\n" + 
			"    \r\n" + 
			"	private Response(int errorCode) {\r\n" + 
			"		this.errorCode = errorCode;\r\n" + 
			"	}\r\n" + 
			"\r\n" + 
			"	private Response(String exception) {\r\n" + 
			"		this.exception = exception;\r\n" + 
			"	}\r\n" + 
			"	\r\n" + 
			"	private Response(int errorCode, String exception) {\r\n" + 
			"		this.errorCode = errorCode;\r\n" + 
			"		this.exception = exception;\r\n" + 
			"	}\r\n" + 
			"\r\n" + 
			"    public static Response getInstance(int errorCode) {\r\n" + 
			"    	return new Response(errorCode);\r\n" + 
			"    }\r\n" + 
			"\r\n" + 
			"    public static Response getInstance(String exception) {\r\n" + 
			"    	return new Response(exception);\r\n" + 
			"    }\r\n" + 
			"        \r\n" + 
			"    public static Response getInstance(int errorCode, String exception) {\r\n" + 
			"    	return new Response(errorCode, exception);\r\n" + 
			"    }\r\n" + 
			"    \r\n" + 
			"}";
	

	public static List<String> getContent(String pkg) {
		fileContent = fileContent.formatted(pkg);
		List<String> content = new ArrayList<String>();
		content.add(fileContent);
		
		return content;
	}
}
