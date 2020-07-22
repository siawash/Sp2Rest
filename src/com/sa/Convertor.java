package com.sa.sp2rest;

import java.time.*;
import java.math.*;

public final class Convertor {
	public static String Accessor2String(Accessor accessor) {
		return accessor.toString().toLowerCase();
	}

	public static String JavaType2String(JavaType type) {

		if (type == JavaType.Date || type == JavaType.BigDecimal) {
			return type.toString();
		} else if (type == JavaType.byteArray) {
			return "byte[]";
		} else if (type == JavaType.String) {
			return type.toString();
		} else if (type == JavaType.Object) {
			return type.toString();
		} else {
			return type.toString();//.toLowerCase();
		}
	}

	public static JavaType DbType2JavaType(String dbType) {
		JavaType javaType = JavaType.String;

		if (dbType.equals("BIGINT")) {
			javaType = JavaType.Long;
		} else if (dbType.equals("BLOB") || dbType.equals("CLOB") || dbType.equals("VARBIN")) {
			if (Control.dontChangeByteArray) {
				javaType = JavaType.byteArray;
			} else {
				javaType = JavaType.String;
			}
		} else if (dbType.equals("CHAR") || dbType.equals("VARCHAR")) {
			javaType = JavaType.String;
		} else if (dbType.equals("DATE") || dbType.equals("TIMESTAMP") || dbType.equals("TIME")) {
			javaType = JavaType.Date;
		} else if (dbType.equals("DECIMAL")) {
			javaType = JavaType.BigDecimal;
		} else if (dbType.equals("FLOAT")) {
			javaType = JavaType.Float;
		} else if (dbType.equals("DOUBLE")) {
			javaType = JavaType.Double;
		} else if (dbType.equals("INTEGER")) {
			javaType = JavaType.Integer;
		} else if (dbType.equals("SMALLINT")) {
			javaType = JavaType.Short;
		}

		return javaType;
	}

	public static String DbType2JavaTypeString(String dbType) {
		JavaType javaType = JavaType.Integer;

		if (dbType.equals("BIGINT")) {
			javaType = JavaType.Long;
		} else if (dbType.equals("BLOB") || dbType.equals("CLOB") || dbType.equals("VARBIN")) {
			javaType = JavaType.byteArray;
		} else if (dbType.equals("CHAR") || dbType.equals("VARCHAR")) {
			javaType = JavaType.String;
		} else if (dbType.equals("DATE") || dbType.equals("TIMESTMP") || dbType.equals("TIME")) {
			javaType = JavaType.Date;
		} else if (dbType.equals("DECIMAL")) {
			javaType = JavaType.BigDecimal;
		} else if (dbType.equals("FLOAT")) {
			javaType = JavaType.Float;
		} else if (dbType.equals("INTEGER")) {
			javaType = JavaType.Integer;
		} else if (dbType.equals("SMALLINT")) {
			javaType = JavaType.Short;
		}

		return Convertor.JavaType2String(javaType);
	}

//	public static JavaType CSharpType2JavaType(Class type) {
//		JavaType result = JavaType.String;
//
//		if (type == Long.class) {
//			result = JavaType.Long;
//		} else if (type == Integer.class) {
//			result = JavaType.Int;
//		} else if (type == Short.class) {
//			result = JavaType.Short;
//		} else if (type == BigDecimal.class) {
//			result = JavaType.BigDecimal;
//		} else if (type == Float.class || type == Float.class) {
//			result = JavaType.Float;
//		} else if (type == Double.class) {
//			result = JavaType.Double;
//		}
////C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
////ORIGINAL LINE: else if(type == typeof(byte[]))
//		else if (type == byte[].class) {
//			if (Control.dontChangeByteArray) {
//				result = JavaType.byteArray;
//			} else {
//				result = JavaType.String;
//			}
//		} else if ((type == Character.class) || (type == String.class)) {
//			result = JavaType.String;
//		} else if (type == LocalDateTime.class) {
//			result = JavaType.Date;
//		}
//
//		return result;
//	}
}