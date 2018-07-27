package com.chatbot.app.vo;

import java.util.ArrayList;
import java.util.List;

public class MerchantAuthorization {
	private List<String> scopes;
	
	public void add(String scope) {
		if (null == scopes) {
			scopes = new ArrayList<>();
		}
		scopes.add(scope);
	}

	public List<String> getScopes() {
		return scopes;
	}
	
	public boolean hasScope(String scope) {
		return scopes.contains(scope);
	}
	
} 
