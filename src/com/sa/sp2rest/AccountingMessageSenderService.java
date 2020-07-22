package com.sa.sp2rest;

import java.util.ArrayList;
import java.util.List;

public class AccountingMessageSenderService extends Clazz {	
	
	private static String fileContent = 
			"package %s.config;\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"import java.io.UnsupportedEncodingException;\r\n" + 
			"import javax.annotation.PostConstruct;\r\n" + 
			"import javax.jms.JMSException;\r\n" + 
			"import javax.jms.Message;\r\n" + 
			"import javax.jms.Queue;\r\n" + 
			"import javax.jms.QueueConnectionFactory;\r\n" + 
			"import javax.jms.Session;\r\n" + 
			"import javax.jms.TextMessage;\r\n" + 
			"import javax.naming.Context;\r\n" + 
			"import javax.naming.InitialContext;\r\n" + 
			"import org.springframework.beans.factory.annotation.Autowired;\r\n" + 
			"import org.springframework.beans.factory.annotation.Value;\r\n" + 
			"import org.springframework.context.ApplicationContext;\r\n" + 
			"import org.springframework.context.annotation.Lazy;\r\n" + 
			"import org.springframework.context.annotation.PropertySource;\r\n" + 
			"import org.springframework.jms.connection.CachingConnectionFactory;\r\n" + 
			"import org.springframework.jms.core.JmsTemplate;\r\n" + 
			"import org.springframework.jms.core.MessageCreator;\r\n" + 
			"import org.springframework.stereotype.Service;\r\n" + 
			"import %s.ServletInitializer;\r\n" + 
			"import %s.common.AppLogger;\r\n" + 
			"\r\n" + 
			"@Service\r\n" + 
			"@Lazy\r\n" + 
			"@PropertySource(ignoreResourceNotFound = true, encoding = \"UTF-8\", value = { \"classpath:accjms.properties\" })\r\n" + 
			"public class AccountingMessageSenderService {\r\n" + 
			"	\r\n" + 
			"	@Value(\"${acc.queue.name}\")\r\n" + 
			"	private String queueName;\r\n" + 
			"	\r\n" + 
			"	@Value(\"${acc.connection.factory.name}\")\r\n" + 
			"	private String connectionFactoryName;\r\n" + 
			"\r\n" + 
			"	@Autowired\r\n" + 
			"	private ApplicationContext context;\r\n" + 
			"	\r\n" + 
			"	@Autowired\r\n" + 
			"	private AppLogger logger;\r\n" + 
			"	\r\n" + 
			"	private JmsTemplate accJmsTemplate;\r\n" + 
			"	\r\n" + 
			"	@PostConstruct\r\n" + 
			"	public void init() {\r\n" + 
			"		QueueConnectionFactory factory = null;\r\n" + 
			"		\r\n" + 
			"		try {\r\n" + 
			"			Context namingContext = new InitialContext();\r\n" + 
			"			factory = (QueueConnectionFactory) namingContext.lookup(connectionFactoryName);\r\n" + 
			"		    CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(factory);\r\n" + 
			"		    cachingConnectionFactory.setSessionCacheSize(20);\r\n" + 
			"		    Queue queue = (Queue) namingContext.lookup(queueName);\r\n" + 
			"		    accJmsTemplate = new JmsTemplate(factory);\r\n" + 
			"		    accJmsTemplate.setDefaultDestination(queue);	    \r\n" + 
			"		    \r\n" + 
			"		} catch (Exception e) {\r\n" + 
			"			logger.log(\"exception inside AccountingMessageService.init(): \" + e.getMessage());\r\n" + 
			"			ServletInitializer.exitApplication(context, -200);\r\n" + 
			"		}\r\n" + 
			"	}\r\n" + 
			"	\r\n" + 
			"	public void send(final String message) {\r\n" + 
			"		try {\r\n" + 
			"			accJmsTemplate.send(new MessageCreator() {				\r\n" + 
			"				@Override\r\n" + 
			"				public Message createMessage(Session session) throws JMSException {\r\n" + 
			"					TextMessage msg = null;\r\n" + 
			"					try {\r\n" + 
			"						msg = session.createTextMessage(new String(message.getBytes(\"UTF-8\")));\r\n" + 
			"					} catch (UnsupportedEncodingException e) {\r\n" + 
			"						logger.log(\"exception inside AccountingMessageService.createMessage(): \" + e.getMessage());\r\n" + 
			"					}\r\n" + 
			"\r\n" + 
			"					logger.log(msg.toString());\r\n" + 
			"					\r\n" + 
			"					return msg;\r\n" + 
			"				}\r\n" + 
			"			});\r\n" + 
			"		} catch (Exception e) {\r\n" + 
			"			logger.log(\"exception inside AccountingMessageService.send(): \" + e.getMessage());\r\n" + 
			"		}\r\n" + 
			"	}\r\n" + 
			"}\r\n";
	
	public static List<String> getContent(String pkg) {
		fileContent = fileContent.formatted(pkg, pkg, pkg);
		List<String> content = new ArrayList<String>();
		content.add(fileContent);
		
		return content;
	}
}