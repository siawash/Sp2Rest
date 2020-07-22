package com.sa.sp2rest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Operation extends Element {

	private String repositoryName;
	private short resultSetCount;
	private String schema;

	private List<OperationParameter> parameters;

	public String getRepositoryName() {
		return repositoryName;
	}

	public void setDbName(String dbName) {
		this.repositoryName = dbName;
	}

	public short getResultSetCount() {
		return resultSetCount;
	}

	public void setResultSetCount(short resultSetCount) {
		this.resultSetCount = resultSetCount;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public List<OperationParameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<OperationParameter> parameters) {
		this.parameters = parameters;
	}

	public List<OperationParameter> getInputParameters() {
		return this.parameters.stream().filter(p -> p.getDirection().equals(OperationParameterDirection.P))
				.collect(Collectors.toList());
	}

	public List<OperationParameter> getOutputParameters() {
		return this.parameters.stream().filter(p -> p.getDirection().equals(OperationParameterDirection.O))
				.collect(Collectors.toList());
	}

	public Operation(String repositoryName, String schema, String name, /* String nickName, */String url) {
		super(name, /* nickName, */url);
		this.repositoryName = repositoryName;
		this.schema = schema;

		this.setParameters(new ArrayList<OperationParameter>());
	}

	public void add(OperationParameter param) {
		this.parameters.add(param);
	}

	public OperationParameter getErrorParameter() {
		try {
			return this.getOutputParameters().stream().filter(param -> Config.ERROR_PARAMETER_NAMES.contains(param.getName())).findFirst().get();
		} catch (Exception e) {
			return null;
		}
	}

	public OperationParameter getStepParameter() {
		try {
			return this.getOutputParameters().stream().filter(param -> Config.STEP_PARAMETER_NAME.contains(param.getName())).findFirst().get();
		} catch (Exception e) {
			return null;
		}
	}
	
	public String getErrorParameterName() {
		try {
			return this.getOutputParameters().stream().filter(param -> Config.ERROR_PARAMETER_NAMES.contains(param.getName())).findFirst().get().getName();
		} catch (Exception e) {
			return null;
		}
	}

	public String getStepParameterName() {
		try {
			return this.getOutputParameters().stream().filter(param -> Config.STEP_PARAMETER_NAME.contains(param.getName())).findFirst().get().getName();
		} catch (Exception e) {
			return null;
		}
	}

}
