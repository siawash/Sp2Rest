package com.sa.sp2rest;

import java.util.ArrayList;
import java.util.List;

public class RowMapperClass {

	private static String fileContent = "package %s.dto.common;\r\n" + 
			"\r\n" + 
			"import java.sql.ResultSet;\r\n" + 
			"import java.sql.ResultSetMetaData;\r\n" + 
			"import java.sql.SQLException;\r\n" + 
			"import java.util.HashMap;\r\n" + 
			"import org.springframework.jdbc.core.RowMapper;\r\n" + 
			"import %s.common.AppUtil;\r\n" + 
			"\r\n" + 
			"public class RowMapperClass  implements RowMapper<HashMap<String, Object>> {\r\n" + 
			"	public HashMap<String, Object> mapRow(ResultSet resultSet, int i) throws SQLException {\r\n" + 
			"			\r\n" + 
			"		HashMap<String, Object> result = new HashMap<String, Object>();\r\n" + 
			"	\r\n" + 
			"		ResultSetMetaData meta = resultSet.getMetaData();\r\n" + 
			"		\r\n" + 
			"		for(int index = 1; index <= meta.getColumnCount(); index++) {\r\n" + 
			"			if (meta.getColumnTypeName(index).contains(\"CHAR\")) {\r\n" + 
			"				try {\r\n" + 
			"					result.put(meta.getColumnName(index), AppUtil.convertBytesTo1256(resultSet.getBytes(index)));\r\n" + 
			"				} catch(Exception e) {\r\n" + 
			"					result.put(meta.getColumnName(index), resultSet.getObject(index));\r\n" + 
			"				}\r\n" + 
			"			}\r\n" + 
			"			else\r\n" + 
			"				result.put(meta.getColumnName(index), resultSet.getObject(index));\r\n" + 
			"		}\r\n" + 
			"		return result;\r\n" + 
			"	}\r\n" + 
			"}\r\n";
	

	public static List<String> getContent(String pkg) {
		fileContent = fileContent.formatted(pkg, pkg);
		List<String> content = new ArrayList<String>();
		content.add(fileContent);
		
		return content;
	}
}
