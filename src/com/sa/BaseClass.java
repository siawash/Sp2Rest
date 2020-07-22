package com.sa.sp2rest;

import java.util.*;

public class BaseClass {
	protected String pkg;
	protected List<String> imports = new ArrayList<String>();
	protected List<String> annotations = new ArrayList<String>();
	protected String name;
	protected String extend;
	protected List<String> impls = new ArrayList<String>();
	protected List<Property> properties = new ArrayList<Property>();
	protected List<Injection> injections = new ArrayList<Injection>();

	public final String getPackage() {
		return pkg;
	}

	public final void setPackage(String pkg) {
		this.pkg = pkg;
	}

	public final List<String> getImports() {
		return imports;
	}

	public final void setImports(List<String> imports) {
		this.imports = imports;
	}

	public final List<String> getAnnotations() {
		return annotations;
	}

	public final void setAnnotations(List<String> annotations) {
		this.annotations = annotations;
	}

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final String getExtend() {
		return extend;
	}

	public final void setExtend(String extend) {
		this.extend = extend;
	}

	public final List<String> getImplements() {
		return impls;
	}

	public final void setImplements(List<String> impls) {
		this.impls = impls;
	}

	public final List<Injection> getInjections() {
		return injections;
	}

	public final void setInjections(List<Injection> injections) {
		this.injections = injections;
	}

	public final List<Property> getProperties() {
		return properties;
	}

	public final void setProperties(List<Property> properties) {
		this.properties = properties;
	}

	protected List<String> content = null;

	private String temp = "";

	public boolean containsInjection(final String injectionName) {
		return this.injections.stream().map(Injection::getParameterPart).filter(injectionName::equals).findFirst().isPresent();
	}
	
	protected List<String> getContent() {
		if (this.content != null) {
			return this.content;
		}

		List<String> content = new ArrayList<String>();

		content.add("package " + this.pkg + ";");

		content.add("");
		content.add("");

		for (String imprt : this.imports) {
			content.add("import " + imprt + ";");
		}

		if (!this.imports.isEmpty()) {
			content.add("");
		}

		for (String annotation : this.annotations) {
			content.add(annotation);
		}

		temp = "public " + ((this instanceof Interface) ? "interface " : "class ") + this.name;
		if (!Utility.isNullOrEmpty(this.extend)) {
			temp += " extends " + this.extend;
		}

		if (!this.impls.isEmpty()) {
			temp += " implements ";
			for (int i = 0; i < this.impls.size(); i++) {
				if (i < this.impls.size() - 1) {
					temp += this.impls.get(i) + ", ";
				} else {
					temp += this.impls.get(i);
				}
			}
		}

		content.add(temp + " {");

		for (Property property : this.properties) {
			content.add("\t" + property.toString() + ";");
		}

		if (!this.properties.isEmpty()) {
			content.add("");
		}

		for (Injection injection : this.injections) {
			content.add("\t" + injection.getAnnotationPart());
			content.add("\t" + injection.getParameterPart() + ";\r\n");
		}

		if (!this.injections.isEmpty()) {
			content.add("");
		}

		for (Property property : this.properties) {
			if (!property.getMakeGetterAndSetter())
				continue;
			content.add("\tpublic " + property.getType() + " get" + property.getName4GetterSetter()
					+ "() {\r\n\t\treturn " + property.getName() + ";\r\n\t}");
			content.add("\tpublic void set" + property.getName4GetterSetter() + "(" + property.getType() + " "
					+ property.getName() + ") {\r\n\t\tthis." + property.getName() + " = " + property.getName()
					+ ";\r\n\t}\r\n");
		}

		content.add("}");

		this.content = content;
		return content;
	}
}