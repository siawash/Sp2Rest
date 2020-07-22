package com.sa.sp2rest;

import java.util.ArrayList;
import java.util.List;

public class Verifier_old {

	private static String fileContent = 
			"package %s.gateway.common;\r\n" + 
			"\r\n" + 
			"import java.security.PublicKey;\r\n" + 
			"import java.util.Iterator;\r\n" + 
			"import java.util.Map;\r\n" + 
			"import javax.annotation.PostConstruct;\r\n" + 
			"import org.springframework.beans.factory.annotation.Autowired;\r\n" + 
			"import org.springframework.beans.factory.annotation.Value;\r\n" + 
			"import org.springframework.context.annotation.Lazy;\r\n" + 
			"import org.springframework.context.annotation.Profile;\r\n" + 
			"import org.springframework.context.annotation.PropertySource;\r\n" + 
			"import org.springframework.stereotype.Component;\r\n" + 
			"import io.jsonwebtoken.Claims;\r\n" + 
			"import io.jsonwebtoken.Jws;\r\n" + 
			"import io.jsonwebtoken.JwtParser;\r\n" + 
			"import io.jsonwebtoken.Jwts;\r\n" + 
			"import %s.common.AppLogger;\r\n" + 
			"import com.ibm.websphere.crypto.KeySetHelper;\r\n" + 
			"\r\n" + 
			"@Component\r\n" + 
			"@PropertySource(ignoreResourceNotFound = true, encoding = \"UTF-8\", value = { \"classpath:verification.properties\" })\r\n" + 
			"//@Profile({ \"dev-jwt\", \"opr\" })\r\n" + 
			"@Lazy\r\n" + 
			"public class Verifier {\r\n" + 
			"\r\n" + 
			"	@Autowired\r\n" + 
			"	private AppLogger logger;\r\n" + 
			"\r\n" + 
			"	@Value(\"${keyset}\")\r\n" + 
			"	private String keySet;\r\n" + 
			"\r\n" + 
			"	@Value(\"${keystore}\")\r\n" + 
			"	private String keyStore;\r\n" + 
			"\r\n" + 
			"	private PublicKey publicKey;\r\n" + 
			"	private JwtParser parser;\r\n" + 
			"\r\n" + 
			"	@PostConstruct\r\n" + 
			"	public void intiPublicKey() throws Exception {\r\n" + 
			"\r\n" + 
			"		logger.log(\"keySetName is \" + keySet + \" and keyStoreName is \" + keyStore);\r\n" + 
			"\r\n" + 
			"		Map<?, ?> generationKeys = null;\r\n" + 
			"\r\n" + 
			"		KeySetHelper ksh = KeySetHelper.getInstance();\r\n" + 
			"		generationKeys = (Map<?, ?>) ksh.getAllKeysForKeySet(keySet);\r\n" + 
			"		if (generationKeys == null) {\r\n" + 
			"			logger.log(\"keyset \" + keySet + \" not found\");\r\n" + 
			"			throw new Exception(\"keyset \" + keySet + \" not found\");\r\n" + 
			"		} else {\r\n" +
			"			if (generationKeys.isEmpty()) {\r\n" + 
			"				logger.log(\"key set is empty\");\r\n" + 
			"				throw new Exception(\"key set is empty\");\r\n" + 
			"			}\r\n" +  
			"			Iterator<?> iKeySet = generationKeys.keySet().iterator();\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"			while (iKeySet.hasNext()) {\r\n" + 
			"				String keyAlias = (String) iKeySet.next();\r\n" + 
			"				if (!keyAlias.equals(keyStore))\r\n" + 
			"					continue;\r\n" + 
			"\r\n" + 
			"				Object key = generationKeys.get(keyAlias);\r\n" + 
			"\r\n" + 
			"				if (key instanceof com.ibm.websphere.crypto.KeyPair) {\r\n" + 
			"					this.publicKey = (PublicKey) ((com.ibm.websphere.crypto.KeyPair) key).getPublicKey();\r\n" + 
			"					parser = Jwts.parser().setSigningKey(this.publicKey);\r\n" + 
			"					return;\r\n" + 
			"				}\r\n" + 
			"			}\r\n" + 
			"			logger.log(\"keystore \" + keyStore + \" not found\");\r\n" + 
			"			throw new Exception(\"keystore \" + keyStore + \" not found\");\r\n" + 
			"		}\r\n" + 
			"	}\r\n" + 
			"\r\n" + 
			"	public Jws<Claims> parse(String jwt) {\r\n" + 
			"		return this.parser.parseClaimsJws(jwt);\r\n" + 
			"	}\r\n" + 
			"}\r\n";
	
	public static List<String> getContent(String pkg) {
		fileContent = fileContent.formatted(pkg, pkg);
		List<String> content = new ArrayList<String>();
		content.add(fileContent);
		
		return content;
	}
}
