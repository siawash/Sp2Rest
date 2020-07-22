package com.sa.sp2rest;

import java.util.*;

public class Interface extends BaseClass {
	
	protected List<MethodSignature> methodSignatures = new ArrayList<MethodSignature>();

	public final List<MethodSignature> getMethods() {
		return methodSignatures;
	}

	public final void setMethods(ArrayList<MethodSignature> methodSignatures) {
		this.methodSignatures = methodSignatures;
	}

	public final List<String> getContent() {
		if (this.content != null) {
			return this.content;
		}

		this.content = super.getContent();
		int index = this.content.size() - 1;
		for (MethodSignature methodSignature : this.methodSignatures) {
			this.content.add(index++, methodSignature.toString());
		}

		return this.content;
	}
}