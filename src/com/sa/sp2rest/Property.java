package com.sa.sp2rest;

public class Property {
	protected Accessor accessor = Accessor.values()[0];
	protected JavaType type = JavaType.values()[0];
	protected String nonJavaType = "";
	protected String name;

	private boolean makeGetterAndSetter = true;
	
	
	public final String getAccessor() {
		return Convertor.Accessor2String(this.accessor);
	}

	public final void setAccessor(Accessor accessor) {
		this.accessor = accessor;
	}

	public final String getType() {
		if (Utility.isNullOrEmpty(nonJavaType)) {
			return Convertor.JavaType2String(this.type);
		} else {
			return nonJavaType;
		}
	}

	public final void setType(JavaType type) {
		this.type = type;
	}

	public final void setNonJavaType(String nonJavaType) {
		this.nonJavaType = nonJavaType;
	}

	public final String getName() {

		if (Utility.isNullOrEmpty(this.name)) {
			return "";
		}

		return this.name.replace("PI_", "").replace("PO_", "").toLowerCase();
	}

	public final void setName(String name) {
		this.name = name;
	}

	public boolean getMakeGetterAndSetter() {
		return makeGetterAndSetter;
	}

	public void setMakeGetterAndSetter(boolean makeGetterAndSetter) {
		this.makeGetterAndSetter = makeGetterAndSetter;
	}
	
	public final String getName4GetterSetter() {

		String name4gs = this.getName();
		if (Utility.isNullOrEmpty(name4gs)) {
			return "";
		}

		return String.valueOf(name4gs.charAt(0)).toUpperCase() + name4gs.substring(1);
	}

	public Property(Accessor accessor, JavaType type, String name) {

		this.accessor = accessor;
		this.type = type;
		this.name = name;
	}

	public Property(Accessor accessor, String nonJavaType, String name) {

		this.accessor = accessor;
		this.nonJavaType = nonJavaType;
		this.name = name;
	}

	public Property(Accessor accessor, JavaType type, String name, boolean makeGetterAndSetter) {
		this(accessor, type, name);
		this.makeGetterAndSetter = makeGetterAndSetter;
	}
	
	public Property(Accessor accessor, String nonJavaType, String name, boolean makeGetterAndSetter) {
		this(accessor, nonJavaType, name);
		this.makeGetterAndSetter = makeGetterAndSetter;	
	}
	
	@Override
	public String toString() {
		return this.accessor.toString().toLowerCase() + " " + this.getType() + " " + this.getName();
	}

}