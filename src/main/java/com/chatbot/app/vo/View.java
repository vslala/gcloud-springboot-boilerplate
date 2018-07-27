package com.chatbot.app.vo;

public class View {
	private String page;
	private String component;
	
	public View(String page, String component) {
		this.page = page;
		this.component = component;
	}
	
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getComponent() {
		return component;
	}
	public void setComponent(String component) {
		this.component = component;
	}
}
