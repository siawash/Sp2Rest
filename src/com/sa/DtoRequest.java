package com.sa.sp2rest;


public class DtoRequest extends Clazz
{

	public DtoRequest(String pkg, Operation operation) {
		
		this.name = operation.getName() + "Request";
		this.pkg = pkg + ".dto";
		
		this.imports.add("%s.dto.common.Request".formatted(pkg));
		
//		this.imports.add("java.io.Serializable");
		this.getImportesByType2222(operation.getInputParameters());
//		this.impls.add("Serializable");
		
		this.setExtend("Request");
		operation.getInputParameters().forEach(parameter-> this.properties.add(new Property(Accessor.Private, parameter.getJavaType(), parameter.getName())));			
		
		//		operation.getInputParameters().forEach(parameter-> {
//			if (! Config.USER_IDS.contains(parameter.getName()))
//				this.properties.add(new Property(Accessor.Private, parameter.getJavaType(), parameter.getName()));			
//		});
	}
}