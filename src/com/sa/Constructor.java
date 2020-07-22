package com.sa.sp2rest;

import java.util.*;


public class Constructor
{
	protected Accessor accessor = Accessor.values()[0];
	protected String name;
	protected String parameter;
	protected ArrayList<String> body = new ArrayList<String>();

	public final String getAccessor()
	{
		return Convertor.Accessor2String(accessor);
	}
	public final void setAccessor(Accessor accessor)
	{
		this.accessor = accessor;
	}

	public final String getName()
	{
		return name;
	}

	public final void setName(String name)
	{
		this.name = name;
	}

	public final String getParameter()
	{
		return parameter;
	}

	public final void setParameter(String parameter)
	{
		this.parameter = parameter;
	}


	public final ArrayList<String> getBody()
	{
		return body;
	}
	public final void setBody(ArrayList<String> body)
	{
		this.body = body;
	}

	public Constructor()
	{
	}

	public Constructor(String name)
	{
		this.name = name;
	}

	public Constructor(String name, String parameter)
	{
		this(name);
		this.parameter = parameter;
	}

	@Override
	public String toString()
	{

		StringBuilder content = new StringBuilder();
		content.append("\t" + this.accessor.toString().toLowerCase() + " " + this.name + "(" + this.parameter + ") {" + "\r\n");
		for (String bodyPart : this.body)
		{
			content.append("\t\t" + bodyPart + "\r\n");
		}
		content.append("\t}" + "\r\n");
		return content.toString();
	}
}