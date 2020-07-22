package com.sa.sp2rest;

import java.util.ArrayList;
import java.util.List;

public class AppUtil {
				
	private static String fileContent = 
			"package %s.common;\r\n" + 
			"\r\n" + 
			"import java.lang.reflect.Modifier;\r\n" + 
			"import java.util.Arrays;\r\n" + 
			"import java.util.Calendar;\r\n" + 
			"import java.util.List;\r\n" + 
			"import com.google.gson.Gson;\r\n" + 
			"import com.google.gson.JsonElement;\r\n" + 
			"import com.google.gson.GsonBuilder;\r\n" + 
			"public class AppUtil {\r\n" + 
			"		\r\n" + 
			"private static Calendar cal = Calendar.getInstance();\r\n" + 
			"	\r\n" + 
			"	private static Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.TRANSIENT).create();\r\n" + 
			"	\r\n" + 
			"	public static <T> T fromJson(String json, Class<T> classOfT) {\r\n" + 
			"		return gson.fromJson(json, classOfT);\r\n" + 
			"	}\r\n" + 
			"	\r\n" + 
			"	public static String toJson(Object object) {\r\n" + 
			"		return gson.toJson(object);\r\n" + 
			"	}\r\n" + 
			"\r\n" + 
			"	public static JsonElement toJsonElement(Object object) {\r\n" + 
			"		return gson.fromJson(gson.toJson(object), JsonElement.class);\r\n" + 
			"	}\r\n" + 
			"	\r\n" + 
			"	public static String get_Date() {\r\n" + 
			"		return cal.get(Calendar.YEAR) + \"-\" + (cal.get(Calendar.MONTH) < 10 ? \"0\" + cal.get(Calendar.MONTH) : cal.get(Calendar.MONTH)) + \"-\" + cal.get(Calendar.DATE);\r\n" + 
			"	}\r\n" + 
			"	\r\n" + 
			"	public static String get_Time() {	\r\n" + 
			"		return cal.get(Calendar.HOUR_OF_DAY) + \":\" + (cal.get(Calendar.MINUTE) < 10 ? \"0\" + cal.get(Calendar.MINUTE) : cal.get(Calendar.MINUTE)) + \":\" + cal.get(Calendar.SECOND);\r\n" + 
			"	}\r\n" + 
			"	 \r\n" + 
			"	public static String get_TimeZone() {\r\n" + 
			"		return \"IRDT\";\r\n" + 
			"	}\r\n" + 
			"	\r\n" + 
			"	public static int get_DayOfWeek() {\r\n" + 
			"		return cal.get(Calendar.DAY_OF_WEEK);\r\n" + 
			"	}\r\n" + 
			"\r\n" + 
			"	public static boolean isNullOrEmpty(String value) {\r\n" + 
			"		return value == null || value.isEmpty();\r\n" + 
			"	}\r\n" + 
			"	\r\n" + 
			"    public static String convertBytesTo1256(byte[] bytes) {\r\n" + 
			"        if (bytes == null || bytes.length == 0) {\r\n" + 
			"            return \"\";\r\n" + 
			"        }\r\n" + 
			"        String result = \"\";\r\n" + 
			"        try { result = new String(bytes, \"Cp1256\"); }\r\n" + 
			"        catch(Exception e){result = \"\";}\r\n" + 
			"        return result.replace((char) 1740, (char) 1610).replace((char) 1705, (char) 1603).trim();\r\n" + 
			"    }\r\n" + 
			"\r\n" + 
			"    private static Integer[] cp1256 = { 1662, 1670, 1580, 1581, 1582, 1607, 1593, 1594, 1601, 1602, 1579, 1589, 1590, 1711, 1705, 1605, 1606, 1578, 1575, 1604, 1576, 1587, 1588, 1608, 1574, 1583, 1584, 1585, 1586, 1591, 1592, 1563, 1548, 1613, 1612, 1611, 1600, 1570, 1617, 1616, 1615, 1614, 1567, 1569, 1571, 1573, 1572, 1688, 1610, 1577, 1740 };\r\n" + 
			"    private static int[] cp1252     = { 129, 141, 204, 205, 206, 229, 218, 219, 221, 222, 203, 213, 214, 144, 223, 227, 228, 202, 199, 225, 200, 211, 212, 230, 198, 207, 208, 209, 210, 216, 217, 186, 161, 242, 241, 240, 220, 194, 248, 246, 245, 243, 191, 193, 195, 197, 196, 142, 237, 201,  246 };\r\n" + 
			"    private static List<Integer> cp1256List = Arrays.asList(cp1256);\r\n" + 
			"    \r\n" + 
			"	public static String convert1256To1252(String value) {\r\n" + 
			"		\r\n" + 
			"		if (value == null || value.length() == 0) {\r\n" + 
			"			return \"\";\r\n" + 
			"		}\r\n" + 
			"		\r\n" + 
			"		StringBuffer stringBuffer = new StringBuffer(value);\r\n" + 
			"\r\n" + 
			"        for (int i = 0; i < stringBuffer.length(); i++) {\r\n" + 
			"            int charIndexInCp1256List = cp1256List.indexOf((int) stringBuffer.charAt(i));\r\n" + 
			"            if (charIndexInCp1256List > -1)\r\n" + 
			"            	stringBuffer.replace(i, i + 1 , String.valueOf((char)cp1252[charIndexInCp1256List]));            	\r\n" + 
			"        }\r\n" + 
			"        \r\n" + 
			"        return stringBuffer.toString().replace((char) 223, (char) 732).replace((char) 1603, (char) 732).replace((char) 1740, (char) 237).replace((char) 1610, (char) 237);\r\n" + 
			"	}\r\n" + 
			"\r\n" + 
			"	public static boolean isAccountingMessageEmpty(Object accountingMessage) {\r\n" + 
			"		return accountingMessage == null || accountingMessage.toString().isEmpty() ||  accountingMessage.equals(\"<EmptyMessage>\");\r\n" + 
			"	}\r\n" +
			"}\r\n";

	
	public static List<String> getContent(String pkg) {
		fileContent = fileContent.formatted(pkg);
		List<String> content = new ArrayList<String>();
		content.add(fileContent);
		
		return content;
	}
}
