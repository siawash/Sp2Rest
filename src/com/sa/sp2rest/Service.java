package com.sa.sp2rest;

public class Service extends Interface {
	public Service(String pkg, Operator operator) {

		this.name = operator.getName() + "Service";
		this.pkg = pkg + ".service";

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

	public Service(String pkg, String name, MethodSignature signature) {

		this.name = name + "Service";
		this.pkg = pkg + ".service";
		this.methodSignatures.add(signature);

	}

}