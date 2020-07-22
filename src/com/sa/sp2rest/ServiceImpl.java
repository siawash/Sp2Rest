package com.sa.sp2rest;

import java.util.ArrayList;
import java.util.List;

public class ServiceImpl extends Clazz {
	public ServiceImpl(String applicationPkg, Operator operator) {

		this.annotations.add("@Service");
		this.name = operator.getName() + "ServiceImpl";
		this.pkg = applicationPkg + ".service.impl";
		this.imports.add("org.springframework.beans.factory.annotation.Autowired");
		this.imports.add("org.springframework.stereotype.Service");
		this.imports.add(applicationPkg + ".dao." + operator.getName() + "Dao");
		this.imports.add(applicationPkg + ".service." + operator.getName() + "Service");

		operator.getOperations().forEach(operation -> {
			if (!operation.getInputParameters().isEmpty())
				this.imports.add(applicationPkg + ".dto." + operation.getName() + "Request");

			this.imports.add(applicationPkg + ".dto." + operation.getName() + "Response");
		});

		this.impls.add(operator.getName() + "Service");

		this.injections.add(
				new Injection("@Autowired", "private " + operator.getName() + "Dao " + operator.getName() + "Dao"));

		operator.getOperations().forEach(operation -> {
			Method method = new Method();
			method.getAnnotations().add("@Override");
			method.setAccessor(Accessor.Public);
			method.setReturnType(operation.getName() + "Response");
			method.setName(operation.getName());

			if (!operation.getInputParameters().isEmpty()) {
				method.setParameter(operation.getName() + "Request request");
				method.getBody().add("\treturn " + operator.getName() + "Dao." + operation.getName() + "(request);");
			} else {
				method.getBody().add("\treturn " + operator.getName() + "Dao." + operation.getName() + "();");
			}

			method.getBody().add("}");

			this.methods.add(method);
		});
	}

	public ServiceImpl(String applicationPkg, String serviceName) {

		if (serviceName.equals("AccountingMessageSender")) {
			
			this.annotations.add("@Service");
			this.name = serviceName + "ServiceImpl";
			this.pkg = applicationPkg + ".service.impl";
			
			this.imports.add("org.springframework.stereotype.Service");
			this.imports.add("org.springframework.beans.factory.annotation.Autowired");
//			this.imports.add("org.springframework.beans.factory.annotation.Value");
//			this.imports.add("org.springframework.jms.core.JmsTemplate");
//			this.imports.add("org.springframework.jms.JmsException");
			this.imports.add(applicationPkg + ".service." + serviceName + "Service");
			this.imports.add(applicationPkg + ".common.AppLogger");

			this.impls.add(serviceName + "Service");
			
//			this.injections.add(new Injection("@Autowired", "private JmsTemplate accJmsTemplate"));
			this.injections.add(new Injection("@Autowired", "private AppLogger appLogger"));
//			this.injections.add(new Injection("@Value(\"${mq.channel}\")", "private String channel"));

			Method method = new Method();
			method.getAnnotations().add("@Override");
			method.setAccessor(Accessor.Public);
			method.setReturnType("void");
			method.setName("send");
			method.setParameter("final String message");
//			List<String> throwsList = new ArrayList<String>();
//			throwsList.add("JmsException");
//			method.setThrows(throwsList);
			method.getBody().add("//\ttry {");
			method.getBody().add("//\t\taccJmsTemplate.convertAndSend(message);");
			method.getBody().add("//\t} catch (Exception e) {");
			method.getBody().add("//\t\tappLogger.logAccountingException(e, message);");
			method.getBody().add("//\t}");
			method.getBody().add("}");

			this.methods.add(method);
			
			/*
package com.behsazan.dmnservice.fnrsubdraft.service.impl;

import org.springframework.stereotype.Service;

import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;

import com.behsazan.dmnservice.fnrsubdraft.service.AccountingMessageSenderService;
import com.behsazan.dmnservice.fnrsubdraft.common.AppLogger;

@Service
public class AccountingMessageSenderServiceImpl implements AccountingMessageSenderService {
	
	@Value("${acc.queue.name}")
	private String queueName;
	
	@Value("${acc.connection.factory.name}")
	private String connectionFactoryName;
	
	@Autowired
	private AppLogger logger;
	
	@Autowired
	private JmsTemplate accJmsTemplate;
	
	@Override
	public void send(final String message) {
		try {
			QueueConnection queueConnection = null;
			MessageProducer messageProducer = null;
			Context context = new InitialContext();
			Queue queue = (Queue) context.lookup(queueName);
			QueueConnectionFactory connectionFactory = (QueueConnectionFactory) context.lookup(connectionFactoryName);
			queueConnection = connectionFactory.createQueueConnection("wasadmin", "AMDus12@");
			QueueSession queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			messageProducer = queueSession.createProducer(queue);

			messageProducer.send(queueSession.createTextMessage("Message from frnsubdraft!!!"));

			messageProducer.close();
			queueConnection.close();

		} catch (Exception e) {
			System.out.println("exception inside AccountingMessageServiceImpl : " + e.getMessage());
			logger.log("inside send exception : " + e.getMessage());
		}
	}
	
	public void send2(final String message) {

		 try {
			 accJmsTemplate.convertAndSend(message);
		 } catch (Exception e) {
			 logger.log(e.getMessage());
		 }
	}
}
			 */
			
		} /*
			 * else if (serviceName.equals("Core2Logger")){
			 * 
			 * this.annotations.add("@Service"); this.name = serviceName + "ServiceImpl";
			 * this.pkg = applicationPkg + ".service.impl";
			 * 
			 * this.imports.add("org.springframework.beans.factory.annotation.Value");
			 * this.imports.add("org.springframework.web.client.RestTemplate");
			 * this.imports.add("org.springframework.stereotype.Service");
			 * this.imports.add("org.springframework.beans.factory.annotation.Autowired");
			 * this.imports.add(applicationPkg + ".common.AppLogger");
			 * this.imports.add(applicationPkg + ".service." + serviceName + "Service");
			 * 
			 * this.impls.add(serviceName + "Service");
			 * 
			 * this.injections.add(new Injection("@Autowired",
			 * "private RestTemplate core2loggerRestTemplate")); this.injections.add(new
			 * Injection("@Autowired", "private AppLogger appLogger"));
			 * this.injections.add(new Injection("@Value(\"${core2.logger.url}\")",
			 * "private String core2LoggerUrl"));
			 * 
			 * Method method = new Method(); method.getAnnotations().add("@Override");
			 * method.setAccessor(Accessor.Public); method.setReturnType("void");
			 * method.setName("log"); method.setParameter("final String message");
			 * method.getBody().add("\ttry {"); method.getBody().
			 * add("\t\tcore2loggerRestTemplate.put(core2LoggerUrl, message);");
			 * method.getBody().add("\t} catch (Exception e) {");
			 * method.getBody().add("\t\tappLogger.logCore2LoggerException(e, message);");
			 * method.getBody().add("\t}"); method.getBody().add("}");
			 * 
			 * this.methods.add(method); }
			 */
	}
}