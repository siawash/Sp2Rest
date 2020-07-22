package com.sa.sp2rest;

import java.util.ArrayList;
import java.util.List;

public class OutParamVarcharCorrector {
	
	private static String fileContent = "package %s.dao.common;\r\n" + 
			"\r\n" + 
			"import org.springframework.jdbc.core.SqlReturnType;\r\n" + 
			"\r\n" + 
			"import java.io.UnsupportedEncodingException;\r\n" + 
			"import java.sql.CallableStatement;\r\n" + 
			"import java.sql.SQLException;\r\n" + 
			"import java.sql.Types;\r\n" + 
			"\r\n" + 
			"public class OutParamVarcharCorrector implements SqlReturnType {\r\n" + 
			"\r\n" + 
			"    @Override\r\n" + 
			"    public Object getTypeValue(CallableStatement callableStatement, int i, int j, String s) throws SQLException {\r\n" + 
			"        try {\r\n" + 
			"\r\n" + 
			"            if (j == Types.VARCHAR || j == Types.CHAR) {\r\n" + 
			"                return ((callableStatement.getBytes(i) != null) ? (new String(callableStatement.getBytes(i), \"Cp1256\")) : callableStatement.getObject(i));\r\n" + 
			"            } else {\r\n" + 
			"                return callableStatement.getObject(i);\r\n" + 
			"            }\r\n" + 
			"        } catch (UnsupportedEncodingException e) {\r\n" + 
			"            e.printStackTrace();\r\n" + 
			"            return null;\r\n" + 
			"        }\r\n" + 
			"    }\r\n" + 
			"}\r\n";
	
	
	public static List<String> getContent(String pkg) {
		fileContent = fileContent.formatted(pkg);
		List<String> content = new ArrayList<String>();
		content.add(fileContent);
		
		return content;
	}
}
