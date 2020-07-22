package com.sa.sp2rest;

import java.util.*;

public class MethodSignature {
	protected String returnType;
	protected String name;
	protected String parameter;
	protected List<String> throwsList = new ArrayList<String>();

	public final String getReturnType() {
		return returnType;
	}

	public final void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final String getParameter() {
		return parameter;
	}

	public final void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public final List<String> getThrows() {
		return this.throwsList;
	}

	public final void setThrows(List<String> throwsList) {
		this.throwsList = throwsList;
	}

	public MethodSignature() {
	}

	public MethodSignature(String returnType, String name) {
		this.returnType = returnType;
		this.name = name;
	}

	public MethodSignature(String returnType, String name, String parameter) {
		this.returnType = returnType;
		this.name = name;
		this.parameter = parameter;
	}

	@Override
	public String toString() {
		String result = "\t" + this.returnType + " " + name + "("
				+ (Utility.isNullOrEmpty(this.parameter) ? "" : this.parameter) + ")";

		if (this.throwsList != null && !this.throwsList.isEmpty()) {
			result += " throws ";
			for (int i = 0; i < this.throwsList.size(); i++) {
				if (i < this.throwsList.size() - 1) {
					result += this.throwsList.get(i) + ", ";
				} else {
					result += this.throwsList.get(i);
				}
			}
		}
		return result += ";";
	}
}