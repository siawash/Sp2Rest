package com.sa.sp2rest;


import java.util.*;
import javax.swing.JOptionPane;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Control {
	
	private List<OperationRepository> repositories;
	private List<Operator> operators;
		
	private String applicationName;
	private String applicationUrl;
	private String applicationPkg;
	public static boolean dontChangeByteArray = false;
	private Db2Config db2Config;
	private AccountingMessageSenderService accountingMessageSenderService; 
//	private Core2LoggerConfig core2LoggerConfig;
	private Sp sp;
	private List<String> schemas = new ArrayList<String>();
	private List<String> appProperties = new ArrayList<String>();
	private List<String> jdbcProperties = new ArrayList<String>();
	private List<String> accJmsProperties = new ArrayList<String>();
	private List<String> verificationProperties = new ArrayList<String>();
	
	private List<String> spProperties = new ArrayList<String>();
	private List<DtoRequest> dtoRequests = new ArrayList<DtoRequest>();
	private List<DtoResponse> dtoResponses = new ArrayList<DtoResponse>();
	private List<Dao> daos = new ArrayList<Dao>();
	private List<DaoImpl> daoImpls = new ArrayList<DaoImpl>();
	private List<Service> services = new ArrayList<Service>();
	private List<ServiceImpl> serviceImpls = new ArrayList<ServiceImpl>();
	private List<RestController> restControllers = new ArrayList<RestController>();
	private List<String> content, daoImplContent = new ArrayList<String>();
	
	private String resultPath;
	
	public Control(Application application, boolean dontChangeByteArray, List<OperationRepository> repositories, List<Operator> operators, String resultPath) {
		
		this.applicationName = application.getName();
		this.applicationPkg = application.getPkg();
		this.applicationUrl = application.getUrl();
		this.resultPath = resultPath;
		
		Control.dontChangeByteArray = dontChangeByteArray;
		this.repositories = repositories;
		this.operators = operators;
		
		init();
	}

	private void init() {
		
		String indent = "     |_", twoIndent = "          |_";

//		Logger.log(" ... initializing ...");
		
		this.repositories.forEach(repository -> {
			String repositoryName = repository.getName().toLowerCase();
			this.jdbcProperties.add("#******************************* %s ************************************".formatted(repositoryName));
			this.jdbcProperties.add("#%s.jndi.name=%sJdni".formatted(repositoryName, repositoryName));
			this.jdbcProperties.add("");
			this.jdbcProperties.add("%s.driver=com.ibm.db2.jcc.DB2Driver".formatted(repositoryName));
			this.jdbcProperties.add("%s.url=%s".formatted(repositoryName, repository.getUrl()));
			this.jdbcProperties.add("%s.username=%s".formatted(repositoryName, repository.getUsername()));
			this.jdbcProperties.add("%s.password=%s".formatted(repositoryName, repository.getPassword()));
			this.jdbcProperties.add("");
		});
		
		this.operators.forEach(operator -> {
		
			Logger.log("making  %s service".formatted(operator.getName()));
			operator.getOperations().forEach(operation -> {
				
				if (operation.getOutputParameters().isEmpty()) {
					Logger.log(operation.getName() + " has no output parameters. some thing is wrong");
					return;
				}
				
				if (!schemas.contains(operation.getSchema()))
					this.schemas.add(operation.getSchema());
				
				Logger.log(indent + "preparing %s operation".formatted(operation.getName()));
				
				Logger.log(twoIndent + "adding %s info to sp.properties file".formatted(operation.getName()));
				this.spProperties.add("sp.%s=%s".formatted(operation.getName(), operation.getName()));
						
				if (!operation.getInputParameters().isEmpty()) {
					
//					Logger.log("           *** adding %s info to sp.properties file".formatted(operation.getName()));
//					this.spProperties.add("sp.%s=%s".formatted(operation.getName(), operation.getName()));
					
					Logger.log(twoIndent + "preparing %s DtoRequest".formatted(operation.getName()));
					this.dtoRequests.add(new DtoRequest(this.applicationPkg, operation));
				}

				if (operation.getOutputParameters().isEmpty()) {
					Logger.log(operation.getName() + " has no output parameters. some thing is wrong");
					return;
				}
					
				Logger.log(twoIndent + "preparing %s DtoResponse".formatted(operation.getName()));
				this.dtoResponses.add(new DtoResponse(this.applicationPkg, operation));
			});

			Logger.log(indent + "preparing %s Dao".formatted(operator.getName()));
			this.daos.add(new Dao(this.applicationPkg, operator));

			Logger.log(indent + "preparing %s DaoImpl".formatted(operator.getName()));
			this.daoImpls.add(new DaoImpl(this.applicationPkg, operator));

			Logger.log(indent + "preparing  %s Service".formatted(operator.getName()));
			this.services.add(new Service(this.applicationPkg, operator));

			Logger.log(indent + "preparing %s ServiceImpl".formatted(operator.getName()));
			this.serviceImpls.add(new ServiceImpl(this.applicationPkg, operator));

			Logger.log(indent + "preparing %s controller".formatted(operator.getName()));
			this.restControllers.add(new RestController(this.applicationPkg, operator));
		});

//		Logger.log(indent + "adding sp injection");
		this.sp = new Sp(this.applicationName, this.applicationPkg, this.operators);

//		Logger.log("making Db2Config");
		this.db2Config = new Db2Config(this.applicationPkg, this.repositories);
						
//		Logger.log("           --->>>>>> making Core2LoggerConfig started ---      ");
//		this.core2LoggerConfig = new Core2LoggerConfig(this.applicationPkg);

//		//sa
//		Logger.log("AccountingMessageSenderService.java");
//		this.services.add(new Service(this.applicationPkg, "AccountingMessageSender", new MethodSignature("void", "send", "final String message")));

//		Logger.log("           --->>>>>> making Core2LoggerService.java ---      ");
//		this.services.add(new Service(this.applicationPkg, "Core2Logger", new MethodSignature("void", "log", "final String message")));

//		//sa
//		Logger.log("AccountingMessageSenderServiceImpl.java file");
//		this.serviceImpls.add(new ServiceImpl(this.applicationPkg, "AccountingMessageSender"));

//		Logger.log("           --->>>>>> making Core2LoggerServiceImpl.java ---      ");
//		this.serviceImpls.add(new ServiceImpl(this.applicationPkg, "Core2Logger"));
		
	}
	
	public void makeFiles() {
				
//		String codePath = applicationName + "/src/main/java/" + this.applicationPkg.replace('.', '/');
//		String resourcePath = applicationName + "/src/main/resources";

		String codePath = resultPath + "\\" + applicationName + "\\src\\main\\java\\" + this.applicationPkg.replace('.', '\\');
		String resourcePath = resultPath + "\\" + applicationName + "\\src\\main\\resources";
				
		if ((new File(codePath)).isDirectory()) {
			try {
				Utility.deleteDir(new File(codePath));
			} catch (Exception e) {
				Logger.log(e.getMessage());
//				JOptionPane.showMessageDialog(null, e.getMessage());
				Thread.currentThread().stop();
//				System.exit(0);
			}
		}

		Logger.log("making packages");

		(new File(codePath)).mkdirs();
		(new File(resourcePath)).mkdirs();

		(new File(codePath + "\\common")).mkdirs();
		(new File(codePath + "\\config")).mkdirs();
		(new File(codePath + "\\dto\\common")).mkdirs();
		(new File(codePath + "\\dao\\impl")).mkdirs();
		(new File(codePath + "\\dao\\common")).mkdirs();
		(new File(codePath + "\\service\\impl")).mkdirs();
		(new File(codePath + "\\gateway\\common")).mkdirs();
		

//		Logger.log("making files");
		
//		Path src = Paths.get("./lib/crypto.jar"); 
//		Path dest = Paths.get(resourcePath + "/crypto.jar"); 
//		try {
//			Files.copy(src, dest);
//		} catch (IOException e) {
//			Logger.log(e.getMessage());
//		}
//
//		src = Paths.get("./lib/db2jcc4-10.1.jar"); 
//		dest = Paths.get(resourcePath + "/db2jcc4-10.1.jar"); 
//		try {
//			Files.copy(src, dest);
//		} catch (IOException e) {
//			Logger.log(e.getMessage());
//		}
		
		// making pom.xml file
		Logger.log("making pom.xml");		
		Utility.write2File(resultPath + "\\" + applicationName + "\\pom.xml", Pom.getContent(this.applicationName, this.applicationPkg));

		// making ServletInitializer.java file
		Logger.log("making ServletInitializer.java");		
		Utility.write2File(codePath + "\\ServletInitializer.java", ServletInitializer.getContent(this.applicationName, this.applicationPkg));

		// making common package files
		// making AppLogger.java file
		Logger.log("making AppLogger.java");		
		Utility.write2File(codePath + "\\common\\AppLogger.java", AppLogger.getContent(this.applicationPkg));

		// making Monitor.java file
		Logger.log("making Monitor.java");		
		Utility.write2File(codePath + "\\common\\Monitor.java", Monitor.getContent(this.applicationPkg));
		
		// making AppUtil.java file
		Logger.log("making AppUtil.java");		
		Utility.write2File(codePath + "\\common\\AppUtil.java", AppUtil.getContent(this.applicationPkg));
		
		
		// making AppUtil.java file
		Logger.log("making SwaggerConfig.java");		
		Utility.write2File(codePath + "\\config\\SwaggerConfig.java", SwaggerConfig.getContent(this.applicationPkg));
		
		
		
		// making application.properties file
		Logger.log("making application.Properties");		
		this.appProperties.add("# set dev for test without isam token and dev-jwt for test with isam token and opr for running on main");
		this.appProperties.add("spring.profiles.active=dev\r\n");
		
		this.appProperties.add("# comment these two lines when installing on server");
		this.appProperties.add("#server.contextPath=%s".formatted(this.applicationUrl));
		this.appProperties.add("#server.port=%s".formatted(8080));	
		this.appProperties.add("# swagger url on local is : http://localhost:8080%s/swagger-ui.html".formatted(applicationUrl));
				

		Utility.write2File(resourcePath + "/application.properties", this.appProperties);

		// making application.properties file
		Logger.log("making jdbc.Properties");
		this.schemas.forEach(schema -> this.jdbcProperties.add(0, "schema.%s=%s".formatted(schema.toLowerCase(), schema)));
		this.jdbcProperties.add(this.schemas.size(), "");
		Utility.write2File(resourcePath + "\\jdbc.properties", this.jdbcProperties);

		// making accjms.properties file
		Logger.log("making accjms.Properties");
		this.accJmsProperties.add("acc.queue.name=jms/logs\r\n" + 
								  "acc.connection.factory.name=jms/connectionfactory");
		
		/*		
acc.mq.host=tcp://1072.20.146.185
acc.mq.port=1418
acc.mq.queue.manager=QM.ACC2
acc.mq.channel=system.DEV.SVRCONN

acc.queue.name=rap_mq22
acc.connection.factory.name=rap_mq


ibm.mq.queueManager=QM.ACC2
ibm.mq.queue=DEV.QUEUE.1
ibm.mq.channel=system.DEV.SVRCONN
ibm.mq.host=1072.20.146.185
ibm.mq.port=1418		
		*/
		
		
//		this.accJmsProperties.add("account.message.parameters=PO_ACCOUNTING_MESSAGE,PO_ACCOUNTING_MSG\r\n" + 
//								  "send.accounting.message=true");
		Utility.write2File(resourcePath + "\\accjms.properties", this.accJmsProperties);
		
		
		// making verification.properties file
		Logger.log("making verification.Properties file");
		this.verificationProperties.add("keystore.file.path=/opt/rap/Branch-STS-2.cer");
//		this.verificationProperties.add("keyset=rap_test_keyset\r\n" + 
//					 "keystore=rap_test");
		Utility.write2File(resourcePath + "\\verification.properties", this.verificationProperties);
		
		
		// making log4j2 file
		Logger.log("making log4j2.xml file");		
		Utility.write2File(resourcePath + "\\log4j2.xml", Log4j2.getContent(applicationName));

		// making sp.properties file
		Logger.log("making sp.Properties file");
		Utility.write2File(resourcePath + "\\sp.properties", this.spProperties);

		// making sp.java file
		Logger.log("making sp.java file");
		sp.getName();
		content = sp.getContent();
		Utility.write2File(codePath + "\\dao\\common\\" + sp.getName() + ".java", content);

		// making OurParamVarcharCorrector.java file
		Logger.log("making OurParamVarcharCorrector.java file");
		sp.getName();
		content = OutParamVarcharCorrector.getContent(this.applicationPkg);
		Utility.write2File(codePath + "/dao/common/OutParamVarcharCorrector.java", content);
		
		// making Db2Config.java file
		Logger.log("making Db2Config.java file");
		Utility.write2File(codePath + "\\config\\Db2Config.java", this.db2Config.getContent());
		
		// making AccountingMessageSenderService.java file
		Logger.log("making AccountingMessageSenderService.java file");
		Utility.write2File(codePath + "\\config\\AccountingMessageSenderService.java", AccountingMessageSenderService.getContent(this.applicationPkg));
	
//		// making qConfig.java file
//		Logger.log("making Core2LoggerConfig.java file");
//		Utility.write2File(codePath + "/config/Core2LoggerConfig.java", this.core2LoggerConfig.getContent());

//		// making AccountingMessageSenderConfig.java file
//		Logger.log("making AccountingMessageSenderConfig.java file");
//		Utility.write2File(codePath + "/config/AccountingMessageSenderConfig.java", this.accountingMessageSenderService.getContent());
				
		// making Request.java file
		Logger.log("making Request.java file");
		Utility.write2File(codePath + "\\dto\\common\\Request.java", Request.getContent(this.applicationName, this.applicationPkg));
		
		// making Response.java file
		Logger.log("making Response.java file");
		Utility.write2File(codePath + "\\dto\\common\\Response.java", Response.getContent(this.applicationPkg));

		// making RowMapperClass.java file
		Logger.log("making RowMapperClass.java file");
		Utility.write2File(codePath + "\\dto\\common\\RowMapperClass.java", RowMapperClass.getContent(this.applicationPkg));
				
		//*****
		
		// making dtoRequest files
		this.dtoRequests.forEach(dtoRequest -> {
			Logger.log("making " + dtoRequest.getName() + " file");
			dtoRequest.getName();
			content = dtoRequest.getContent();
			Utility.write2File(codePath + "\\dto\\" + dtoRequest.getName() + ".java", content);
		});

		// making dtoResponse files
		this.dtoResponses.forEach(dtoResponse -> {
			Logger.log("making " + dtoResponse.getName() + ".java");
			dtoResponse.getName();
			content = dtoResponse.getContent();
			Utility.write2File(codePath + "\\dto\\" + dtoResponse.getName() + ".java", content);
		});
		
		// making dao files
		this.daos.forEach(dao -> {			
			Logger.log("making " + dao.getName() + ".java");
			dao.getName();
			content = dao.getContent();
			Utility.write2File(codePath + "\\dao\\" + dao.getName() + ".java", content);
		});
		
		// making daoImpl files
		this.daoImpls.forEach(daoImpl -> {
			Logger.log("making " + daoImpl.getName() + ".java");
			daoImplContent.clear();
			daoImpl.getName();
			daoImpl.getContent().forEach(line -> {

				daoImplContent.add(line);
			});
			Utility.write2File(codePath + "\\dao\\impl\\" + daoImpl.getName() + ".java", daoImplContent);
		});

		
		// making srevice files
		this.services.forEach(service -> {			
			Logger.log("making " + service.getName() + ".java");

			service.getName();
			content = service.getContent();

			Utility.write2File(codePath + "\\service\\" + service.getName() + ".java", content);
		}); 

		
		// making serviceImpl files
		this.serviceImpls.forEach(serviceImpl -> {
			Logger.log("making " + serviceImpl.getName() + ".java");
			serviceImpl.getName();
			content = serviceImpl.getContent();

			Utility.write2File(codePath + "\\service\\impl\\" + serviceImpl.getName() + ".java", content);			
		});

		// making Verifier.java file
		Logger.log("making Verifier.java.java");
		Utility.write2File(codePath + "\\gateway\\common\\Verifier.java", Verifier.getContent(this.applicationPkg));
		
		// making VnLFilter.java file
		Logger.log("making VnLFilter.java file");
		Utility.write2File(codePath + "\\gateway\\common\\VnLFilter.java", VnLFilter.getContent(this.applicationPkg));
	
		// making restController files
		this.restControllers.forEach(restController -> {
			Logger.log("making " + restController.getName() + ".java");

			restController.getName();
			content = restController.getContent();

			Utility.write2File(codePath + "\\gateway\\" + restController.getName() + ".java", content);
			
		});
		

		Logger.log("making application is done");
		JOptionPane.showMessageDialog(null, "application is ready!");	
		
		Logger.closeFileWriter();
	}
}