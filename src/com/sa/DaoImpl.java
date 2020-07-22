package com.sa.sp2rest;

public class DaoImpl extends Clazz {

	public DaoImpl(String pkg, Operator operator) {
		
		this.pkg = pkg + ".dao.impl";
		this.name = operator.getName() + "DaoImpl";

		this.imports.add("org.springframework.beans.factory.annotation.Value");
		this.imports.add("org.springframework.jdbc.core.simple.SimpleJdbcCall");
		this.imports.add("org.springframework.stereotype.Repository");
		this.imports.add("org.springframework.beans.factory.annotation.Autowired");
		this.imports.add("org.springframework.context.ApplicationContext");
		this.imports.add("org.springframework.jdbc.core.SqlOutParameter");
		this.imports.add("org.springframework.jdbc.core.SqlParameter");
		this.imports.add("javax.annotation.PostConstruct");
		this.imports.add("java.sql.Types");
		this.imports.add("java.util.HashMap");
		this.imports.add("java.util.Map");
		this.imports.add(pkg + ".dto.common.RowMapperClass");
//		this.imports.add(pkg + ".common.AppUtil");
		this.imports.add(pkg + ".dao.common.Sp");
		this.imports.add(pkg + ".dao." + operator.getName() + "Dao");

		operator.getOperations().forEach(operation -> {
			if (!operation.getInputParameters().isEmpty())
				this.imports.add(pkg + ".dto." + operation.getName() + "Request");
			
			this.imports.add(pkg + ".dto." + operation.getName() + "Response");
		
			this.getImportesByType2222(pkg, operation.getParameters());
		});
		

		operator.getOperations().forEach(operation -> {
			if (operation.getResultSetCount() > 0 ) {
				String imprt = pkg + ".dto.common.RowMapperClass";
				if (!this.imports.contains(imprt))
					this.imports.add(imprt);
			}
		});

		this.annotations.add("@Repository");
		this.impls.add(operator.getName() + "Dao");

		this.injections.add(new Injection("@Autowired", "private ApplicationContext ctx"));
		this.injections.add(new Injection("@Autowired", "private Sp sp"));
		operator.getSchemas().forEach(schema -> this.injections.add(new Injection("@Value(\"${schema." + schema.toLowerCase() + "}\")", "private String " + schema)));		
		
		operator.getOperations().forEach(operation -> this.properties.add(new Property(Accessor.Private, "SimpleJdbcCall", operation.getName() + "sjc", false)));
		
		operator.getOperations().forEach(operation -> {
			
			Method method = new Method();
			method.setAccessor(Accessor.Private);
			method.setReturnType("void");
			method.setName("init" + operation.getName() + "sjc");
			
			method.getBody().add("\t" + operation.getName().toLowerCase() + "sjc = ctx.getBean(\"" + operation.getRepositoryName().toLowerCase() + "sjc\", SimpleJdbcCall.class)");
			method.getBody().add("\t\t.withProcedureName(sp." + operation.getName() + ")");
			method.getBody().add("\t\t.withSchemaName(" + operation.getSchema() + ")");
			method.getBody().add("\t\t.withoutProcedureColumnMetaDataAccess()");
			
			StringBuilder sb = new StringBuilder();
			if (!operation.getParameters().isEmpty()) {
				sb.append("\t\t.declareParameters(");
				
				operation.getParameters().forEach(param -> {
					if (param.getDirection() == OperationParameterDirection.O) {
						if (param.getJavaType() == JavaType.String)	
							sb.append("\r\n\t\t\t\tnew SqlOutParameter(\"" + param.getName() + "\", Types." + param.getType() + ", \"VARCHAR\", new OutParamVarcharCorrector()),");
						else
							sb.append("\r\n\t\t\t\tnew SqlOutParameter(\"" + param.getName() + "\", Types." + param.getType() + "),");
					} else
						sb.append("\r\n\t\t\t\tnew SqlParameter(\"" + param.getName() + "\", Types." + param.getType() + "),");	
				});
				
				sb.replace(sb.length()-1, sb.length(), "");
			}
			sb.append("\r\n\t\t\t);");
			method.getBody().add(sb.toString());

			for (int i = 0; i < operation.getResultSetCount(); i++)
				method.getBody().add("\t" + operation.getName().toLowerCase() + "sjc.returningResultSet(\"resultset" + i + "\", new RowMapperClass());");		
			
			method.getBody().add("}");
			this.methods.add(method);			
		});
				
		// init method
		Method initMethod = new Method();
		initMethod.annotations.add("@PostConstruct");
		initMethod.setAccessor(Accessor.Private);
		initMethod.setReturnType("void");
		initMethod.setName("init");
//		StringBuilder sb = new StringBuilder();
		operator.getOperations().forEach(operation -> {
			initMethod.getBody().add("\tinit" + operation.getName() + "sjc();");
		});
		initMethod.getBody().add("}");
		this.methods.add(initMethod);
		
		
		operator.getOperations().forEach(operation -> {
			Method method = new Method();
			method.setAccessor(Accessor.Public);
			method.setReturnType(operation.getName() + "Response");
			method.setName(operation.getName());
	
			if (!operation.getInputParameters().isEmpty()) {
				method.setParameter(operation.getName() + "Request request");
			}
					
			method.getBody().add("\r\n\t\tMap<String, Object> inParamMap = new HashMap<String, Object>();");

			operation.getInputParameters().forEach(param -> {
//				if (Config.USER_IDS.contains(param.getName())) {
//					method.getBody().add("\tinParamMap.put(\"" + param.getName() + "\", request.getUserId());");
//					return;
//				}
				
				if (param.getJavaType() == JavaType.String)
					method.getBody().add("\tinParamMap.put(\"" + param.getName() + "\", AppUtil.convert1256To1252(request.get" + param.getName4Class() + "()));");
				else
					method.getBody().add("\tinParamMap.put(\"" + param.getName() + "\", request.get" + param.getName4Class() + "());");
			});
				
			method.getBody().add("\r\n\t\tMap<String, Object> result = " + operation.getName().toLowerCase() + "sjc.execute(inParamMap);");
			method.getBody().add("\r\n\t\t" + operation.getName() + "Response response = new " + operation.getName() + "Response();");
			
						
			method.getBody().add("\tresponse.setErrorCode(Integer.valueOf(result.get(\"" + operation.getErrorParameterName() + "\").toString()));");
						
			if (operation.getOutputParameters().size() > 1 || operation.getResultSetCount() > 0) {
				method.getBody().add("\r\n\t\t//if (response.getErrorCode() == 0) {");
				
				StringBuilder temp = new StringBuilder();
				operation.getOutputParameters().forEach(param -> {
					if (param.getName().equals(operation.getErrorParameterName())) {
						return;
					}
					
					if (param.getName().contentEquals(Config.ACCOUNTING_MESSAGE_PARAMETER_NAME) && ! this.impls.contains("%s.config.AccountingMessageSender".formatted(pkg))) {
				
						this.imports.add("%s.config.AccountingMessageSenderService".formatted(pkg));
						if (!this.containsInjection("private AccountingMessageSenderService accountingMessageSenderService")) {
							this.injections.add(new Injection("@Autowired", "private AccountingMessageSenderService accountingMessageSenderService"));
						}
						
						temp.append("\r\n\t\t\tObject accountingMessage = result.get(\"PO_ACCOUNTING_MESSAGE\");");
						temp.append("\r\n\t\t\tif (!AppUtil.isAccountingMessageEmpty(accountingMessage))");
						temp.append("\r\n\t\t\t\taccountingMessageSenderService.send(accountingMessage.toString());\r\n");
					} else {						
					
						temp.append("\t\tresponse.set" + param.getName4Class() + "((result.get(\"" + param.getName() + "\") == null) ? null : ");
						
						if (param.getJavaType() == JavaType.Integer) {
							temp.append("Integer.valueOf(result.get(\"" + param.getName() + "\").toString()));");
						} else if (param.getJavaType() == JavaType.Short) {
							temp.append("Short.valueOf(result.get(\"" + param.getName() + "\").toString()));");
						} else if (param.getJavaType() == JavaType.BigDecimal) {
							temp.append("new BigDecimal(result.get(\"" + param.getName() + "\").toString()).setScale("+ param.getScale() + "));");
						} else if (param.getJavaType() == JavaType.Double) {
							temp.append("Double.valueOf(result.get(\"" + param.getName() + "\").toString()));");
						} else if (param.getJavaType() == JavaType.Float) {
							temp.append("Float.valueOf(result.get(\"" + param.getName() + "\").toString()));");
						} else if (param.getJavaType() == JavaType.byteArray) {
							if (Control.dontChangeByteArray) {
								temp.append("(byte[])result.get(\"" + param.getName() + "\"));");
							} else {
								temp.append("result.get(\"" + param.getName() + "\").toString());");
							}
						} else if (param.getJavaType() == JavaType.String) {
							temp.append("result.get(\"" + param.getName() + "\").toString());");
		//			         temp.addpend("(result.getBytes(i) != null) ? (new String(callableStatement.getBytes(i), "Cp1256")) : callableStatement.getObject(i)");			           
						} else if (param.getJavaType() == JavaType.Date) {
							temp.append("(Date)result.get(\"" + param.getName() + "\"));");
						}
						
//						temp.append("\t\tresponse.set" + param.getName4Class() + "(");
//		
//						if (param.getJavaType() == JavaType.Integer) {
//							temp.append("Integer.valueOf(result.get(\"" + param.getName() + "\").toString()));");
//						} else if (param.getJavaType() == JavaType.Short) {
//							temp.append("Short.valueOf(result.get(\"" + param.getName() + "\").toString()));");
//						} else if (param.getJavaType() == JavaType.BigDecimal) {
//							temp.append("new BigDecimal(result.get(\"" + param.getName() + "\").toString()).setScale("+ param.getScale() + "));");
//						} else if (param.getJavaType() == JavaType.Double) {
//							temp.append("Double.valueOf(result.get(\"" + param.getName() + "\").toString()));");
//						} else if (param.getJavaType() == JavaType.Float) {
//							temp.append("Float.valueOf(result.get(\"" + param.getName() + "\").toString()));");
//						} else if (param.getJavaType() == JavaType.byteArray) {
//							if (Control.dontChangeByteArray) {
//								temp.append("(byte[])result.get(\"" + param.getName() + "\"));");
//							} else {
//								temp.append("result.get(\"" + param.getName() + "\").toString());");
//							}
//						} else if (param.getJavaType() == JavaType.String) {
//							temp.append("result.get(\"" + param.getName() + "\").toString());");
//		//			         temp.addpend("(result.getBytes(i) != null) ? (new String(callableStatement.getBytes(i), "Cp1256")) : callableStatement.getObject(i)");			           
//						} else if (param.getJavaType() == JavaType.Date) {
//							temp.append("(Date)result.get(\"" + param.getName() + "\"));");
//						}
					}
					method.getBody().add(temp.toString());
					temp.setLength(0);
				});
				
				if (operation.getResultSetCount() > 0)
					method.getBody().add("");
				
				for (int i = 0; i < operation.getResultSetCount(); i++)
					method.getBody().add("\t\tresponse.setResultset%s(result.get(\"resultset%s\"));".formatted(i,i));
				
				if (operation.getOutputParameters().size() > 1 || (operation.getOutputParameters().size() == 1 && operation.getResultSetCount() > 0)) {
					if (Config.STEP_PARAMETER_NAME.contains(operation.getStepParameterName())) {
						method.getBody().add("\t//} else {");
						if (operation.getStepParameter().getJavaType() == JavaType.Integer) 
							method.getBody().add("\t\tresponse.set" + operation.getStepParameter().getName4Class() + "(Integer.valueOf(result.get(\"" +  operation.getStepParameterName() + "\").toString()));");
						else
							method.getBody().add("\t\tresponse.set" + operation.getStepParameter().getName4Class() + "(Short.valueOf(result.get(\"" +  operation.getStepParameterName() + "\").toString()));");
	
						method.getBody().add("\t//}");
					} else
						method.getBody().add("\t//}");
				}
			}
			
			method.getBody().add("\r\n\t\treturn response;");

			method.getBody().add("}");
			this.methods.add(method);
		});
	}
}