package com.sa.sp2rest;

import java.util.ArrayList;
import java.util.List;

public class ServletInitializer {
	
	private static String fileContent = 
			"package %s;\r\n" + 
			"\r\n" + 
			"import org.springframework.context.ApplicationContext;\r\n" + 
			"import org.springframework.boot.ExitCodeGenerator;\r\n" + 
			"import org.springframework.boot.SpringApplication;\r\n" + 
			"import org.springframework.boot.autoconfigure.SpringBootApplication;\r\n" + 
			"import org.springframework.boot.builder.SpringApplicationBuilder;\r\n" + 
			"import org.springframework.boot.web.support.SpringBootServletInitializer;\r\n" + 
			"import org.springframework.web.bind.annotation.GetMapping;\r\n" + 
			"import org.springframework.web.bind.annotation.RestController;\r\n" + 
			"//import io.swagger.annotations.ApiOperation;\r\n" + 
			"\r\n" + 
			"@SpringBootApplication\r\n" + 
			"public class ServletInitializer extends SpringBootServletInitializer {\r\n" + 
			"\r\n" + 
			"	public static void main(String[] args) {\r\n" + 
			"\r\n" + 
			"		SpringApplication.run(ServletInitializer.class, args);\r\n" + 
			"\r\n" + 
			"		System.out.println(\"***********************************************************************************\");\r\n" + 
			"		System.out.println(\"***********************************************************************************\");\r\n" + 
			"		System.out.println(\"***********************************************************************************\");\r\n" + 
			"		System.out.println(\"*                                                                                 *\");\r\n" + 
			"		System.out.println(\"                   %s application ready to use\");\r\n" + 
			"		System.out.println(\"*                                                                                 *\");\r\n" + 
			"		System.out.println(\"***********************************************************************************\");\r\n" + 
			"		System.out.println(\"***********************************************************************************\");\r\n" + 
			"		System.out.println(\"***********************************************************************************\");\r\n" + 
			"	}\r\n" + 
			"\r\n" + 
			"	@Override\r\n" + 
			"	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {\r\n" + 
			"		return application.sources(ServletInitializer.class);\r\n" + 
			"	}\r\n" + 
			"\r\n" + 
			"	public static void exitApplication(ApplicationContext context, int exitReason) {\r\n" + 
			"		int exitCode = SpringApplication.exit(context, new ExitCodeGenerator() {\r\n" + 
			"			\r\n" + 
			"			@Override\r\n" + 
			"			public int getExitCode() {\r\n" + 
			"				return exitReason;\r\n" + 
			"			}\r\n" + 
			"		});\r\n" + 
			"		\r\n" + 
			"		System.out.println(\"application stopped with error code \" + exitReason);\r\n" + 
			"		System.exit(exitCode);\r\n" + 
			"	}\r\n" + 
			"}\r\n" + 
			"\r\n" + 
			"@RestController\r\n" + 
			"class RESTController {\r\n" + 
			"\r\n" + 
			"//	@ApiOperation(value = \"is %s up and running?\", tags = \"isup\")\r\n" + 
			"	@GetMapping(\"/isup\")\r\n" + 
			"	public String isUp() {\r\n" + 
			"		return \"%s is up and running\";\r\n" + 
			"	}\r\n" + 
			"}\r\n";
	

	public static List<String> getContent(String applicationName, String applicationPkg) {
		fileContent = fileContent.formatted(applicationPkg, applicationName, applicationName, applicationName);
		List<String> content = new ArrayList<String>();
		content.add(fileContent);
		
		return content;
	}
}
