package com.sa.sp2rest;

public enum JavaType {
	Long, byteArray, String, Date, BigDecimal, Float, Double, Integer, Short, Object;

	public static final int SIZE = java.lang.Integer.SIZE;

	public int getValue() {
		return this.ordinal();
	}

	public static JavaType forValue(int value) {
		return values()[value];
	}
}