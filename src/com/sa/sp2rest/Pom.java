package com.sa.sp2rest;

import java.util.ArrayList;
import java.util.List;

public class Pom extends BaseClass {

	private static String fileContent = 
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
			"<project xmlns=\"http://maven.apache.org/POM/4.0.0\"\r\n" + 
			"	xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\r\n" + 
			"	xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 \r\n" + 
			"						http://maven.apache.org/xsd/maven-4.0.0.xsd\">\r\n" + 
			"	<modelVersion>4.0.0</modelVersion>\r\n" + 
			"	<parent>\r\n" + 
			"		<groupId>org.springframework.boot</groupId>\r\n" + 
			"		<artifactId>spring-boot-starter-parent</artifactId>\r\n" + 
			"		<version>1.5.4.RELEASE</version>\r\n" + 
			"		<!-- <version>2.3.0.RELEASE</version> -->\r\n" + 
			"		<relativePath />\r\n" + 
			"	</parent>\r\n" + 
			"\r\n" + 
			"	<groupId>%s</groupId>\r\n" + 
			"	<artifactId>%s</artifactId>\r\n" + 
			"	<version>1.0.0</version>\r\n" + 
			"	<packaging>war</packaging>\r\n" + 
			"	<name>%s</name>\r\n" + 
			"	<description>%s application</description>\r\n" + 
			"\r\n" + 
			"	<properties>\r\n" + 
			"		<start-class>%s.ServletInitializer</start-class>\r\n" + 
			"		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>\r\n" + 
			"		<java.version>1.8</java.version>\r\n" + 
			"	</properties>\r\n" + 
			"\r\n" + 
			"	<dependencies>\r\n" + 
			"\r\n" + 
			"		<dependency>\r\n" + 
			"			<groupId>org.springframework.boot</groupId>\r\n" + 
			"			<artifactId>spring-boot-starter-web</artifactId>\r\n" + 
			"\r\n" + 
			"			<exclusions>\r\n" + 
			"				<exclusion>\r\n" + 
			"					<groupId>org.springframework.boot</groupId>\r\n" + 
			"					<artifactId>spring-boot-starter-logging</artifactId>\r\n" + 
			"				</exclusion>\r\n" + 
			"			</exclusions>\r\n" + 
			"		</dependency>\r\n" + 
			"\r\n" + 
			"		<dependency>\r\n" + 
			"			<groupId>org.springframework.boot</groupId>\r\n" + 
			"			<artifactId>spring-boot-starter-data-rest</artifactId>\r\n" + 
			"		</dependency>\r\n" + 
			"\r\n" + 
			"		<dependency>\r\n" + 
			"			<groupId>org.springframework.boot</groupId>\r\n" + 
			"			<artifactId>spring-boot-starter-tomcat</artifactId>\r\n" + 
			"			<scope>provided</scope>\r\n" + 
			"		</dependency>\r\n" + 
			"\r\n" + 
			"		<dependency>\r\n" + 
			"			<groupId>org.springframework</groupId>\r\n" + 
			"			<artifactId>spring-jdbc</artifactId>\r\n" + 
			"		</dependency>\r\n" + 
			"\r\n" + 
			"		<dependency>\r\n" + 
			"			<groupId>org.aspectj</groupId>\r\n" + 
			"			<artifactId>aspectjtools</artifactId>\r\n" + 
			"		</dependency>\r\n" + 
			"\r\n" + 
			"		<dependency>\r\n" + 
			"			<groupId>org.springframework.boot</groupId>\r\n" + 
			"			<artifactId>spring-boot-starter-log4j2</artifactId>\r\n" + 
			"		</dependency>\r\n" + 
			"\r\n" + 
			"		<dependency>\r\n" + 
			"			<groupId>io.springfox</groupId>\r\n" + 
			"			<artifactId>springfox-swagger2</artifactId>\r\n" + 
			"			<version>2.6.1</version>\r\n" + 
			"		</dependency>\r\n" + 
			"\r\n" + 
			"		<dependency>\r\n" + 
			"			<groupId>io.springfox</groupId>\r\n" + 
			"			<artifactId>springfox-swagger-ui</artifactId>\r\n" + 
			"			<version>2.6.1</version>\r\n" + 
			"		</dependency>\r\n" + 
			"\r\n" + 
			"		<dependency>\r\n" + 
			"			<groupId>com.lmax</groupId>\r\n" + 
			"			<artifactId>disruptor</artifactId>\r\n" + 
			"			<version>3.3.4</version>\r\n" + 
			"		</dependency>\r\n" + 
			"\r\n" + 
			"		<dependency>\r\n" + 
			"			<groupId>commons-dbcp</groupId>\r\n" + 
			"			<artifactId>commons-dbcp</artifactId>\r\n" + 
			"		</dependency>\r\n" + 
			"\r\n" + 
			"		<dependency>\r\n" + 
			"			<groupId>com.google.code.gson</groupId>\r\n" + 
			"			<artifactId>gson</artifactId>\r\n" + 
			"		</dependency>\r\n" + 
			"\r\n" + 
			"		<dependency>\r\n" + 
			"			<groupId>commons-io</groupId>\r\n" + 
			"			<artifactId>commons-io</artifactId>\r\n" + 
			"			<version>2.6</version>\r\n" + 
			"		</dependency>\r\n" + 
			"\r\n" +
			"\r\n" + 
			"		<dependency>\r\n" + 
			"			<groupId>io.jsonwebtoken</groupId>\r\n" + 
			"			<artifactId>jjwt</artifactId>\r\n" + 
			"			<version>0.9.1</version>\r\n" + 
			"		</dependency>\r\n" +
			"\r\n" + 
			"		<!-- these are necessary for jjwt don't remove -->\r\n" + 
			"		<dependency>\r\n" + 
			"			<groupId>com.fasterxml.jackson.core</groupId>\r\n" + 
			"			<artifactId>jackson-core</artifactId>\r\n" + 
			"<!-- 			<version>2.9.6</version> -->\r\n" + 
			"		</dependency>\r\n" + 
			"\r\n" + 
			"		<dependency>\r\n" + 
			"			<groupId>com.fasterxml.jackson.core</groupId>\r\n" + 
			"			<artifactId>jackson-annotations</artifactId>\r\n" + 
			"<!-- 			<version>2.9.6</version> -->\r\n" + 
			"		</dependency>\r\n" + 
			"\r\n" + 
			"		<dependency>\r\n" + 
			"			<groupId>com.fasterxml.jackson.core</groupId>\r\n" + 
			"			<artifactId>jackson-databind</artifactId>\r\n" + 
			"<!-- 			<version>2.9.6</version> -->\r\n" + 
			"		</dependency>\r\n" +
			"\r\n" + 
			"		<dependency>\r\n" + 
			"			<groupId>BankdariNovin</groupId>\r\n" + 
			"			<artifactId>db2jcc4</artifactId>\r\n" + 
			"			<version>10.1</version>\r\n" + 
			"		</dependency>\r\n" + 
			"\r\n" + 
			"		<dependency>\r\n" + 
			"			<groupId>BankdariNovin</groupId>\r\n" + 
			"			<artifactId>crypto</artifactId>\r\n" + 
			"			<version>1.0</version>\r\n" + 
			"			<scope>provided</scope>\r\n" + 
			"		</dependency>\r\n" + 
			"\r\n" + 
			"<dependency>\r\n" + 
			"			<groupId>org.springframework</groupId>\r\n" + 
			"			<artifactId>spring-jms</artifactId>\r\n" + 
			"		</dependency>\r\n" + 
			"\r\n" + 
			"		<dependency>\r\n" + 
			"			<groupId>com.ibm.mq</groupId>\r\n" + 
			"			<artifactId>mq-jms-spring-boot-starter</artifactId>\r\n" + 
			"			<version>2.0.0</version>\r\n" + 
			"		</dependency>\r\n" + 
			"\r\n" + 
			"	</dependencies>\r\n" + 
			"\r\n" + 
			"	<build>\r\n" + 
			"		<plugins>\r\n" + 
			"			<plugin>\r\n" + 
			"				<groupId>org.apache.maven.plugins</groupId>\r\n" + 
			"				<artifactId>maven-war-plugin</artifactId>\r\n" + 
			"				<configuration>\r\n" + 
			"					<failOnMissingWebXml>false</failOnMissingWebXml>\r\n" + 
			"				</configuration>\r\n" + 
			"			</plugin>\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"			<plugin>\r\n" + 
			"				<groupId>org.eclipse.jetty</groupId>\r\n" + 
			"				<artifactId>jetty-maven-plugin</artifactId>\r\n" + 
			"			</plugin>\r\n" + 
			"		</plugins>\r\n" + 
			"	</build>\r\n" + 
			"\r\n" + 
			"</project>\r\n";

	public static List<String> getContent(String applicationName, String applicationPackage) {
		int slashIndex = applicationPackage.lastIndexOf('.');
		String lastPackage = applicationPackage.substring(slashIndex + 1);
		String parentPackage = applicationPackage.substring(0, slashIndex);
		fileContent = fileContent.formatted(parentPackage, lastPackage, lastPackage, applicationName, applicationPackage);
		List<String> content = new ArrayList<String>();
		content.add(fileContent);
		
		return content;
	}
	
}
