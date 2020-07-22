package com.sa.sp2rest;

public class OperationParameter {

	private String name;
	private String direction;
	private String type;
	private int length;
	private short scale;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public OperationParameterDirection getDirection() {
		return OperationParameterDirection.valueOf(this.direction);
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public short getScale() {
		return scale;
	}

	public void setScale(short scale) {
		this.scale = scale;
	}

	public JavaType getJavaType() {

		return Convertor.DbType2JavaType(type);
	}

	public String getName4Class() {

		if (Utility.isNullOrEmpty(name))
			return "";

		String name4class = name.replace("PI_", "").replace("PO_", "").toLowerCase();
		return String.valueOf(name4class.charAt(0)).toUpperCase() + name4class.substring(1);
	}

	@Override
	public String toString() {
		return "SpParameter [name=" + name + ", direction=" + direction + ", type=" + type + ", length=" + length + ", scale=" + scale + "]";
	}
}
