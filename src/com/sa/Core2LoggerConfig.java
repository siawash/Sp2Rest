package com.sa.sp2rest;


public class Core2LoggerConfig extends Clazz {	
	
	public Core2LoggerConfig(String applicationPkg) {

		this.pkg = applicationPkg + ".config";

		this.imports.add("org.springframework.context.annotation.Bean");
		this.imports.add("org.springframework.context.annotation.Configuration");
		this.imports.add("org.springframework.context.annotation.PropertySource");	
		this.imports.add("org.springframework.boot.web.client.RestTemplateBuilder");	
		this.imports.add("org.springframework.web.client.RestTemplate");

		this.annotations.add("@Configuration");
		this.annotations.add("@PropertySource(ignoreResourceNotFound = true, encoding = \"UTF-8\", value = { \"classpath:core2logger.properties\" })");

		this.setName("Core2LoggerConfig");
		
		Method method = new Method();
		method.annotations.add("@Bean");
		method.setAccessor(Accessor.Public);
		method.setReturnType("RestTemplate");
		method.setName("core2loggerRestTemplate");
		method.setParameter("RestTemplateBuilder builder");
		method.getBody().add("\treturn builder.build();");
		method.getBody().add("}\r\n");
		this.methods.add(method);
		
	}
}
