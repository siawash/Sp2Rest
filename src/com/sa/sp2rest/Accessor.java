package com.sa.sp2rest;

public enum Accessor {
	Public, Private, Protected, Package;

	public int getValue() {
		return this.ordinal();
	}

	public static Accessor forValue(int value) {
		return values()[value];
	}
}