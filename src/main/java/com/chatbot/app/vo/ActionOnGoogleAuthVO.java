package com.chatbot.app.vo;

import java.io.Serializable;

public class ActionOnGoogleAuthVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String clientId;
	public String redirectUri;
	public String state;
	public String resType;
	
}
