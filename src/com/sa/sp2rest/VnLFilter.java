package com.sa.sp2rest;

import java.util.ArrayList;
import java.util.List;

public class VnLFilter {

	private static String fileContent = 
			"package %s.gateway.common;\r\n" + 
			"\r\n" + 
			"import java.io.IOException;\r\n" + 
			"import javax.servlet.Filter;\r\n" + 
			"import javax.servlet.FilterChain;\r\n" + 
			"import javax.servlet.FilterConfig;\r\n" + 
			"import javax.servlet.ServletException;\r\n" + 
			"import javax.servlet.ServletRequest;\r\n" + 
			"import javax.servlet.ServletResponse;\r\n" + 
			"import javax.servlet.http.HttpServletRequest;\r\n" + 
			"import javax.servlet.http.HttpServletResponse;\r\n" + 
			"import javax.servlet.http.HttpSession;\r\n" + 
			"import org.springframework.beans.factory.BeanCreationException;\r\n" + 
			"import org.springframework.beans.factory.annotation.Autowired;\r\n" + 
			"import org.springframework.beans.factory.annotation.Value;\r\n" + 
			"import org.springframework.context.ApplicationContext;\r\n" + 
			"import org.springframework.stereotype.Component;\r\n" + 
			"import io.jsonwebtoken.Claims;\r\n" + 
			"import io.jsonwebtoken.Jwts;\r\n" + 
			"import %s.ServletInitializer;\r\n" + 
			"import %s.common.AppLogger;\r\n" + 
			"\r\n" + 
			"@Component\r\n" + 
			"public class VnLFilter implements Filter {\r\n" + 
			"\r\n" + 
			"//	@Autowired\r\n" + 
			"//	private HttpSession httpSession;\r\n" + 
			"\r\n" + 
			"	@Autowired\r\n" + 
			"	private ApplicationContext context;\r\n" + 
			"\r\n" + 
			"	@Autowired\r\n" + 
			"	private AppLogger logger;\r\n" + 
			"\r\n" + 
			"	@Value(\"${spring.profiles.active}\")\r\n" + 
			"	private String profile;\r\n" + 
			"\r\n" + 
			"//	private boolean disableJwt;\r\n" + 
			"\r\n" + 
			"//	public boolean noJwt(HttpServletRequest hsRequest) {\r\n" + 
			"//		String path = hsRequest.getRequestURI().substring(hsRequest.getContextPath().length());\r\n" + 
			"//		return (path.startsWith(\"/swagger-\") || path.startsWith(\"/webjars\") || path.startsWith(\"/v2/api-docs\")\r\n" + 
			"//				|| path.startsWith(\"/configuration\") || path.equals(\"/isup\"));\r\n" + 
			"//	}\r\n" + 
			"\r\n" + 
			"	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)\r\n" + 
			"			throws IOException, ServletException {\r\n" + 
			"\r\n" + 
			"		HttpServletRequest hsRequest = null;\r\n" + 
			"		Claims claims = null;\r\n" + 
			"\r\n" + 
			"		hsRequest = (HttpServletRequest) servletRequest;\r\n" + 
			"		\r\n" + 
//			"		logger.log(\"caller is \" + hsRequest.getHeader(\"User-Agent\"));\r\n" + 
//			"\r\n" + 
//			"		logger.log(\"inside VnLFilter - ServiceURL is : \" + hsRequest.getRequestURI().substring(hsRequest.getContextPath().length()));\r\n" + 
			"\r\n" + 
			"//		if (!profile.equals(\"opr\")) {\r\n" + 
			"//			if (hsRequest.getRequestURI().substring(hsRequest.getContextPath().length()).equals(\"/swagger-ui.html\")) {\r\n" + 
			"//				httpSession.setAttribute(\"disableJwt\", true);\r\n" + 
			"//			}\r\n" + 
			"//			try {\r\n" + 
			"//				disableJwt = (boolean) httpSession.getAttribute(\"disableJwt\");\r\n" + 
			"//			} catch(Exception e) { }\r\n" + 
			"//		}\r\n" + 
			"\r\n" + 
			"//		if (profile.equals(\"opr\") || (profile.equals(\"dev-jwt\") && !noJwt(hsRequest))) {\r\n" + 
			"//		if (profile.equals(\"opr\") || (profile.equals(\"dev-jwt\") && !disableJwt)) {\r\n" + 
			"		if (profile.equals(\"opr\") || (profile.equals(\"dev\") && !hsRequest.getHeader(\"User-Agent\").startsWith(\"Mozilla\"))) {\r\n" + 
			"			try {\r\n" + 
			"				String jwt = hsRequest.getHeader(\"Authorization\");\r\n" + 
			"				Verifier verifier = context.getBean(Verifier.class);\r\n" + 
			"				claims = verifier.parse(jwt).getBody();\r\n" + 
			"				claims.put(\"contextPath\", hsRequest.getContextPath());\r\n" + 
			"				claims.put(\"servicePath\", hsRequest.getRequestURI().substring(hsRequest.getContextPath().length()));\r\n" + 
			"				servletRequest.setAttribute(\"jwt\", claims);\r\n" + 
			"				filterChain.doFilter(servletRequest, servletResponse);\r\n" + 
			"			} catch (BeanCreationException e) {				\r\n" + 
			"				logger.log(e.getMessage());\r\n" + 
			"				((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, e.getMessage());\r\n" + 
			"				if (profile.equals(\"opr\"))\r\n" + 
			"					ServletInitializer.exitApplication(context, -100);\r\n" + 
			"			} catch (Exception e) {\r\n" + 
			"				claims = Jwts.claims();\r\n" + 
			"				claims.put(\"contextPath\", hsRequest.getContextPath());\r\n" + 
			"				claims.put(\"servicePath\", hsRequest.getRequestURI().substring(hsRequest.getContextPath().length()));\r\n" + 
			"				claims.put(\"exception\", e.getMessage());\r\n" + 
			"				logger.log(claims);\r\n" + 
			"				((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, e.getMessage());\r\n" + 
			"			}\r\n" + 
			"		} else {\r\n" + 
			"			claims = Jwts.claims();\r\n" + 
			"			claims.put(\"contextPath\", hsRequest.getContextPath());\r\n" + 
			"			claims.put(\"servicePath\", hsRequest.getRequestURI().substring(hsRequest.getContextPath().length()));\r\n" + 
			"			servletRequest.setAttribute(\"jwt\", claims);\r\n" + 
			"			filterChain.doFilter(servletRequest, servletResponse);\r\n" + 
			"		}\r\n" + 
			"	}\r\n" + 
			"\r\n" + 
			"	public void init(FilterConfig filterConfiguration) throws ServletException { }\r\n" + 
			"\r\n" + 
			"	public void destroy() { }\r\n" + 
			"}\r\n";
	

	public static List<String> getContent(String pkg) {
		fileContent = fileContent.formatted(pkg, pkg, pkg);
		List<String> content = new ArrayList<String>();
		content.add(fileContent);
		
		return content;
	}
}
