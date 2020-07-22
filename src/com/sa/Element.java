package com.sa.sp2rest;

public class Element {

	private String name;
//	private String nickName;
	private String url;
	

	public String getName() {
		return name;
	}
	
	public String getName2() {
		return name.substring(0, name.length() - 1);
	}
	
	public void setName(String name) {
		this.name = name;
	}
//	public String getPkg() {
//		return nickName;
//	}
//	public void setPkg(String nickName) {
//		this.nickName = nickName;
//	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public Element(String name, /* String nickName, */String url) {
		super();
		this.name = name;
//		this.nickName = nickName;
		this.url = url;
	}
	
}
