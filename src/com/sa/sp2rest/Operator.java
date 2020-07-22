package com.sa.sp2rest;

import java.util.ArrayList;
import java.util.List;

public class Operator extends Element {

	private List<Operation> operations = new ArrayList<Operation>();
	
	public Operator(String name, /* String nickName, */String url) {
		super(name, /* nickName, */url);
	}
	
	public List<Operation> getOperations() {
		return operations;
	}

	public List<String> getSchemas() {
		List<String> schemas = new ArrayList<String>();
		this.operations.forEach(operation -> {
			if (!schemas.contains(operation.getSchema()))
				schemas.add(operation.getSchema());
		});
		return schemas;
	}

	public List<String> getRepositories() {
		List<String> databases = new ArrayList<String>();
		this.operations.forEach(operation -> {
			if (!databases.contains(operation.getRepositoryName()))
				databases.add(operation.getRepositoryName());
		});
		return databases;
	}

	public void add(Operation operation) {
		this.operations.add(operation);
	}

}
