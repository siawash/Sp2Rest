package com.sa.sp2rest;

import java.util.ArrayList;
import java.util.List;

public class SwaggerConfig {
	
	private static String fileContent = 
			"package %s.config;\r\n" + 
			"\r\n" + 
			"import org.springframework.context.annotation.Bean;\r\n" + 
			"import org.springframework.context.annotation.Configuration;\r\n" + 
			"import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;\r\n" + 
			"import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;\r\n" + 
			"import springfox.documentation.spi.DocumentationType;\r\n" +
			"import org.springframework.context.annotation.Profile;\r\n" +
			"import springfox.documentation.spring.web.plugins.Docket;\r\n" + 
			"import springfox.documentation.swagger2.annotations.EnableSwagger2;\r\n" + 
			"\r\n" + 
			"@Configuration\r\n" + 
			"@EnableSwagger2\r\n" + 
			"//@Profile({\"dev\", \"dev-jwt\"})\r\n" + 
			"@Profile({\"dev\"})\r\n" + 
			"public class SwaggerConfig extends WebMvcConfigurerAdapter  {\r\n" + 
			"	@Bean\r\n" + 
			"	public Docket api() {\r\n" + 
			"		return new Docket(DocumentationType.SWAGGER_2).select().build();\r\n" + 
			"	}\r\n" + 
			"	\r\n" + 
			"	@Override\r\n" + 
			"	public void addResourceHandlers(ResourceHandlerRegistry registry) {\r\n" + 
			"		registry.addResourceHandler(\"swagger-ui.html\").addResourceLocations(\"classpath:/META-INF/resources/\");\r\n" + 
			"	}\r\n" + 
			"}\r\n";

	public static List<String> getContent(String pkg) {
		fileContent = fileContent.formatted(pkg);
		List<String> content = new ArrayList<String>();
		content.add(fileContent);
		
		return content;
	}
}
