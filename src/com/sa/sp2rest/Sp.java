package com.sa.sp2rest;

import java.util.List;

public class Sp extends BaseClass {

	public Sp(String applicationName, String pkg, List<Operator> operators) {
		this.setName("Sp");
		this.setPackage(pkg + ".dao.common");
		this.imports.add("org.springframework.beans.factory.annotation.Value");
		this.imports.add("org.springframework.stereotype.Component");
		
		this.annotations.add("@Component");
		operators.forEach(operator -> {
			operator.getOperations().forEach(operation -> this.injections.add(new Injection("@Value(\"${sp." + operation.getName() + "}\")", "public String " + operation.getName())));
		});		
	}
	
}
