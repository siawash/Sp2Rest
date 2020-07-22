package com.sa.sp2rest;

public class Injection {
	private String annotationPart;
	private String parameterPart;

	public final String getAnnotationPart() {
		return annotationPart;
	}

	public final void setAnnotationPart(String annotationPart) {
		this.annotationPart = annotationPart;
	}

	public final String getParameterPart() {
		return parameterPart;
	}

	public final void setParameterPart(String parameterPart) {
		this.parameterPart = parameterPart;
	}

	public Injection(String annotationPart, String parameterPart) {
		this.annotationPart = annotationPart;
		this.parameterPart = parameterPart;
	}
}