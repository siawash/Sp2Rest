package com.sa.sp2rest;


public class DtoResponse extends Clazz
{

	public DtoResponse(String pkg, Operation operation) {	
		this.name = operation.getName() + "Response";
		this.pkg = pkg + ".dto";

		this.imports.add(pkg + ".dto.common.Response");
		this.getImportesByType2222(operation.getOutputParameters());
		this.extend = "Response";
		
		operation.getOutputParameters().forEach(parameter -> {
			if (! (Config.ERROR_PARAMETER_NAMES.contains(parameter.getName()) || Config.ACCOUNTING_MESSAGE_PARAMETER_NAME.equals(parameter.getName())))
				this.properties.add(new Property(Accessor.Private, parameter.getJavaType(), parameter.getName()));
			
//			if (Config.ACCOUNTING_MESSAGE_PARAMETER_NAME.equals(parameter.getName())) {
//				this.imports.add("com.fasterxml.jackson.annotation.JsonIgnore");
//				this.injections.add(new Injection("@JsonIgnore", "private String accounting_message"));
//				return;
//			}			
		});
				
		for (int i = 0; i < operation.getResultSetCount(); i++) {
			this.properties.add(new Property(Accessor.Private, JavaType.Object, "resultset" + i));
		}
		
	}
	
}