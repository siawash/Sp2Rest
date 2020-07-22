package com.sa.sp2rest;

import java.util.*;


public class Method extends MethodSignature
{
	protected List<String> annotations = new ArrayList<String>();
	protected Accessor accessor = Accessor.values()[0];
	protected List<String> body = new ArrayList<String>();

	public final List<String> getAnnotations()
	{
		return annotations;
	}

	public final void setAnnotations(List<String> annotations)
	{
		this.annotations = annotations;
	}

	public final String getAccessor()
	{
		return Convertor.Accessor2String(accessor);
	}
	public final void setAccessor(Accessor accessor)
	{
		this.accessor = accessor;
	}

	public final List<String> getBody()
	{
		return body;
	}
	public final void setBody(List<String> body)
	{
		this.body = body;
	}

	public final List<String> ToStringList()
	{
		List<String> result = new ArrayList<String>();

		for (String anotation : this.annotations)
		{
			result.add("\t" + anotation);
		}

		String temp = "";
		if (this.throwsList != null && !this.throwsList.isEmpty())
		{
			temp += " throws ";
			for (int i = 0; i < this.throwsList.size(); i++)
			{
				if (i < this.throwsList.size() - 1)
				{
					temp += this.throwsList.get(i) + ", ";
				}
				else
				{
					temp += this.throwsList.get(i);
				}
			}
		}

		result.add("\t" + this.getAccessor() + " " + this.returnType + " " + name + "(" + (Utility.isNullOrEmpty(this.parameter) ? "":this.parameter) + ")" + temp + " {");

		for (String bodyPart : this.body)
		{
				result.add("\t" + bodyPart);
		}

		return result;
	}
}