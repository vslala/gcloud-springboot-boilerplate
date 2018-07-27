package com.chatbot.app.vo;

import java.io.Serializable;

public class MerchantVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String merchId;
	private String merchPassword;
	private String firstName;
	private String lastName;
	private String email;
	private MerchantAuthorization merchAuth;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMerchId() {
		return merchId;
	}
	public void setMerchId(String merchId) {
		this.merchId = merchId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMerchPassword() {
		return merchPassword;
	}
	public void setMerchPassword(String merchPassword) {
		this.merchPassword = merchPassword;
	}
	public MerchantAuthorization getMerchAuth() {
		return merchAuth;
	}
	public void setMerchAuth(MerchantAuthorization merchAuth) {
		this.merchAuth = merchAuth;
	}
	
}
