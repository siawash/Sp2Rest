package com.sa.sp2rest;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Db2Config extends Clazz {	
	
	public Db2Config(String applicationPkg, List<OperationRepository> repositories) {

		this.pkg = applicationPkg + ".config";
		
		this.imports.add("org.apache.commons.dbcp.BasicDataSource");
		this.imports.add("org.springframework.beans.factory.annotation.Autowired");
		this.imports.add("org.springframework.beans.factory.annotation.Qualifier");
		this.imports.add("org.springframework.context.annotation.Bean");
		this.imports.add("org.springframework.context.annotation.Primary");
		this.imports.add("org.springframework.context.annotation.Configuration");
		this.imports.add("org.springframework.context.annotation.PropertySource");
		this.imports.add("org.springframework.core.env.Environment");
		this.imports.add("org.springframework.context.annotation.Scope");
		this.imports.add("org.springframework.context.annotation.Profile");
		this.imports.add("org.springframework.jdbc.core.simple.SimpleJdbcCall");
		this.imports.add("org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup");
		this.imports.add("javax.sql.DataSource");

		this.annotations.add("@Configuration");
		this.annotations.add("@PropertySource(ignoreResourceNotFound = true, encoding = \"UTF-8\", value = { \"classpath:jdbc.properties\", \"classpath:sp.properties\" })");

		this.setName("Db2Config");

		this.injections.add(new Injection("@Autowired", "private Environment env"));
				
		AtomicInteger index = new AtomicInteger(0);
		repositories.forEach(repository -> {
			String repositoryName_lower = repository.getName().toLowerCase();
			
			Method datasourceMethod = new Method();
			datasourceMethod.getAnnotations().add("\r\n// ******************************** %s **********************************".formatted(repository.getName()));
			if (index.get() == 0) {
				index.incrementAndGet();
				datasourceMethod.getAnnotations().add("@Primary");				
			}
			
			datasourceMethod.getAnnotations().add("@Bean(name = \"%sDataSource\")".formatted(repositoryName_lower));
			datasourceMethod.getAnnotations().add("//@Profile({\"dev\", \"dev-jwt\"})");
			datasourceMethod.getAnnotations().add("@Profile({\"dev\"})");
					
			
			datasourceMethod.setAccessor(Accessor.Public);
			datasourceMethod.setReturnType("DataSource");
			datasourceMethod.setName("%sDevDataSource".formatted(repositoryName_lower));
			datasourceMethod.getBody().add("\ttry {");
			datasourceMethod.getBody().add("\t\tBasicDataSource ds = new BasicDataSource();");
			datasourceMethod.getBody().add("\t\tds.setDriverClassName(env.getProperty(\"%s.driver\"));".formatted(repositoryName_lower));
			datasourceMethod.getBody().add("\t\tds.setUrl(env.getProperty(\"%s.url\"));".formatted(repositoryName_lower));
			datasourceMethod.getBody().add("\t\tds.setUsername(env.getProperty(\"%s.username\"));".formatted(repositoryName_lower));
			datasourceMethod.getBody().add("\t\t".formatted(repositoryName_lower));
			datasourceMethod.getBody().add("\t\tds.setPassword(env.getProperty(\"%s.password\"));".formatted(repositoryName_lower));
			datasourceMethod.getBody().add("\t return ds;");
			datasourceMethod.getBody().add("\t} catch (Exception e) {");
			datasourceMethod.getBody().add("\t\t System.out.println(\"exception => \" + e.getMessage());");
			datasourceMethod.getBody().add("\t\treturn null;");
			datasourceMethod.getBody().add("\t}");		
			datasourceMethod.getBody().add("}");	
			datasourceMethod.getBody().add("\r\n");		
			this.getMethods().add(datasourceMethod);
			
			
			Method jndiMethod = new Method();
			jndiMethod.getAnnotations().add("@Bean(name = \"%sDatasource\")".formatted(repositoryName_lower));
			jndiMethod.getAnnotations().add("@Profile(\"opr\")");
			jndiMethod.setAccessor(Accessor.Public);
			jndiMethod.setReturnType("DataSource");
			jndiMethod.setName("%sOprDataSource".formatted(repositoryName_lower));
			jndiMethod.getBody().add("JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();");
			jndiMethod.getBody().add("\treturn dataSourceLookup.getDataSource(env.getProperty(\"%s.jndi.name\"));".formatted(repositoryName_lower));
			jndiMethod.getBody().add("}\r\n");
//			jndiMethod.getBody().add("\r\n");
			this.getMethods().add(jndiMethod);
			
			Method jdbcCallMethod = new Method();
			jdbcCallMethod.getAnnotations().add("@Scope(\"prototype\")");
			jdbcCallMethod.getAnnotations().add("@Bean(\"%ssjc\")".formatted(repositoryName_lower));
			jdbcCallMethod.setAccessor(Accessor.Public);
			jdbcCallMethod.setReturnType("SimpleJdbcCall");
			jdbcCallMethod.setName("%ssjc".formatted(repositoryName_lower));
			jdbcCallMethod.setParameter("@Qualifier(\"%sDataSource\") DataSource %sDataSource".formatted(repositoryName_lower, repositoryName_lower));
			jdbcCallMethod.getBody().add("\treturn new SimpleJdbcCall(%sDataSource);".formatted(repositoryName_lower));
			jdbcCallMethod.getBody().add("}");
			jdbcCallMethod.getBody().add("\r\n");
			this.getMethods().add(jdbcCallMethod);
		});
	}
}
