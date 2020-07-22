package com.sa.sp2rest;


public class Dao extends Interface
{

	public Dao(String pkg, Operator operator) {

		this.name = operator.getName() + "Dao";
		this.pkg = pkg + ".dao"; 
//		this.pkg = "com.bmc." + applicationName.toLowerCase() + ".dao";

		operator.getOperations().forEach(operation -> {
			if (!operation.getInputParameters().isEmpty())
				this.imports.add(pkg + ".dto." + operation.getName() + "Request");
			
			this.imports.add(pkg + ".dto." + operation.getName() + "Response");
			
			if (!operation.getInputParameters().isEmpty())
				this.methodSignatures.add(new MethodSignature(operation.getName() + "Response", operation.getName(), operation.getName() + "Request request"));
			else
				this.methodSignatures.add(new MethodSignature(operation.getName() + "Response", operation.getName()));
			
		});
		
	}
}