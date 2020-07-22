package com.sa.sp2rest;

import java.util.*;

public class Clazz extends BaseClass
{
	protected List<Constructor> constructors = new ArrayList<Constructor>();

	protected List<Method> methods = new ArrayList<Method>();

	public final List<Method> getMethods()
	{
		return methods;
	}

	public final void setMethods(List<Method> methods)
	{
		this.methods = methods;
	}

	protected final void getImportesByType(List<SpParameter> list)
	{

		for (SpParameter spParameter : list)
		{

			if (spParameter.getType().equals("DECIMAL"))
			{
				if (!this.imports.contains("java.math.BigDecimal"))
				{
					this.imports.add("java.math.BigDecimal");
				}
			}
			else if (spParameter.getType().equals("TIME") || spParameter.getType().equals("TIMESTAMP"))
			{
				if (!this.imports.contains("java.util.Date"))
				{
					this.imports.add("java.util.Date");
				}
			}
		}
	}
	
	protected final void getImportesByType2222(List<OperationParameter> list)
	{

		for (OperationParameter spParameter : list)
		{

			if (spParameter.getType().equals("DECIMAL"))
			{
				if (!this.imports.contains("java.math.BigDecimal"))
				{
					this.imports.add("java.math.BigDecimal");
				}
			}
			else if (spParameter.getType().equals("TIME") || spParameter.getType().equals("TIMESTAMP"))
			{
				if (!this.imports.contains("java.util.Date")) {
					this.imports.add("java.util.Date");
				}
			} 
		}
	}
	
	protected final void getImportesByType2222(String pkg, List<OperationParameter> list)
	{

		for (OperationParameter spParameter : list) {

			if (spParameter.getType().equals("DECIMAL"))
			{
				if (!this.imports.contains("java.math.BigDecimal"))
				{
					this.imports.add("java.math.BigDecimal");
				}
			}
			else if (spParameter.getType().equals("TIME") || spParameter.getType().equals("TIMESTAMP"))
			{
				if (!this.imports.contains("java.util.Date")) {
					this.imports.add("java.util.Date");
				}
			} else if (spParameter.getType().equals("CHAR") || spParameter.getType().equals("VARCHAR")) {

				if (spParameter.getDirection() == OperationParameterDirection.O) {
					if (!this.imports.contains("%s.dao.common.OutParamVarcharCorrector".formatted(pkg)))
						this.imports.add("%s.dao.common.OutParamVarcharCorrector".formatted(pkg));					
				} else {					
					if (!this.imports.contains(pkg + ".common.AppUtil"))
						this.imports.add(pkg + ".common.AppUtil");
				}				
			}
		}
	}

	public final List<String> getContent()
	{
		if (this.content != null)
		{
			return this.content;
		}

		this.content = super.getContent();

		int index = this.content.size() - 1;

		for (Constructor constructor : this.constructors)
		{
				this.content.add(index++, constructor.toString());
		}

		if (!this.constructors.isEmpty())
		{
			this.content.add(index++, "");
		}

		for (Method method : this.methods)
		{
			for (String methodPart : method.ToStringList())
			{
				this.content.add(index++, methodPart);
			}
		}

		return this.content;
	}
}