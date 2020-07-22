package com.sa.sp2rest;

import java.util.ArrayList;
import java.util.List;

public class Monitor {
	
	private static String fileContent = 
			"package %s.common;\r\n" + 
			"\r\n" + 
			"import %s.dto.common.Request;\r\n" + 
			"import %s.dto.common.Response;\r\n" + 
			"import com.google.gson.JsonObject;\r\n" + 
			"import java.lang.reflect.Method;\r\n" + 
			"import org.aspectj.lang.ProceedingJoinPoint;\r\n" + 
			"import org.aspectj.lang.annotation.*;\r\n" + 
			"import org.aspectj.lang.reflect.MethodSignature;\r\n" + 
			"import org.springframework.beans.factory.annotation.Autowired;\r\n" + 
			"import org.springframework.beans.factory.annotation.Value;\r\n" + 
			"import org.springframework.stereotype.Component;\r\n" + 
			"\r\n" + 
			"@Aspect\r\n" + 
			"@Component\r\n" + 
			"public class Monitor {\r\n" + 
			"\r\n" + 
			"	@Autowired\r\n" + 
			"	private AppLogger appLogger;\r\n" + 
			"\r\n" + 
			"	@Value(\"${spring.profiles.active}\")\r\n" + 
			"	private String profile;\r\n" + 
			"\r\n" + 
			"	@Around(value = \"execution(* %s.dao.*.*(..)) && args(input)\")\r\n" + 
			"   public Object log(ProceedingJoinPoint joinPoint, Object input) {\r\n" + 
			"\r\n" + 
			"		JsonObject core2LogData = null;\r\n" + 
			"		Response response;\r\n" + 
			"\r\n" + 
			"		try {\r\n" + 
			"\r\n" + 
			"			if (input instanceof Request) {\r\n" + 
			"				core2LogData = ((Request) input).getLogInfo(profile);\r\n" + 
			"				response = (Response) joinPoint.proceed();\r\n" + 
			"				if (response.getErrorCode() != 0)\r\n" + 
			"					core2LogData.addProperty(\"errorType\", \"20\");\r\n" + 
			"			} else {\r\n" + 
			"				return joinPoint.proceed();\r\n" + 
			"			}			\r\n" + 
			"\r\n" + 
			"		} catch (Throwable throwable) {\r\n" + 
			"\r\n" + 
			"			if (core2LogData == null)\r\n" + 
			"				core2LogData = new JsonObject();\r\n" + 
			"\r\n" + 
			"			core2LogData.addProperty(\"exception\", throwable.getMessage() + \" - cause: \" + throwable.getCause());\r\n" + 
			"			core2LogData.addProperty(\"errorType\", \"30\");\r\n" + 
			"\r\n" + 
			"			Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();\r\n" + 
			"			@SuppressWarnings(\"unchecked\")\r\n" + 
			"			Class<Response> clazz = (Class<Response>) method.getReturnType();\r\n" + 
			"			try {\r\n" + 
			"				response = clazz.newInstance();\r\n" + 
			"				response.setException(throwable.getMessage());\r\n" + 
			"				response.setErrorCode(-100);\r\n" + 
			"			} catch (Exception e) {\r\n" + 
			"				response = Response.getInstance(-100, e.getMessage());\r\n" + 
			"			}\r\n" + 
			"		}\r\n" + 
			"\r\n" + 
			"		core2LogData.add(\"output\", AppUtil.toJsonElement(response));\r\n" + 
			"		appLogger.log(core2LogData);\r\n" + 
			"\r\n" + 
			"		return response;\r\n" + 
			"	}\r\n" + 
			"}\r\n";
	
	
	public static List<String> getContent(String pkg) {
		fileContent = fileContent.formatted(pkg, pkg, pkg, pkg);
		List<String> content = new ArrayList<String>();
		content.add(fileContent);
		
		return content;
	}
}
