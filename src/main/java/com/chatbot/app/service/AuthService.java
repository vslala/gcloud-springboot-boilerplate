package com.chatbot.app.service;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatbot.app.dao.Database;
import com.chatbot.app.vo.MerchantAuthorization;
import com.chatbot.app.vo.MerchantLoginVO;
import com.chatbot.app.vo.MerchantVO;

@Service
public class AuthService {
	
	private static Logger logger = Logger.getLogger(AuthService.class);
	private Database db;
	private String loginEmail;
	private boolean isAuth = false;
	private MerchantVO authMerchant;
	
	@Autowired
	public AuthService(Database db) {
		this.db = db;
	}
	
	public boolean authenticateMerchant(@Valid MerchantLoginVO merchantLogin) {
		// call db to fetch user data
		loginEmail = merchantLogin.getEmail();
		int merchCount = db.selectMerchCountWhereMerchantEmailIs(loginEmail);
		if (merchCount > 0) {
			isAuth = merchantLogin.getPassword()
					.equals(
							db.selectMerchPasswordWhereMerchantEmailIs(merchantLogin.getEmail()));
		}
		return isAuth;
	}

	public MerchantAuthorization getAuthorizationData() {
		// call auth service to get authrization
		MerchantAuthorization merchAuth = null;
		if (isAuth) {
			merchAuth = new MerchantAuthorization();
			merchAuth.add("readDeposit");
			merchAuth.add("readFundsTransfer");
		}
		
		return merchAuth;
	}
	
	public MerchantVO getAuthMerchant() {
		authMerchant = db.selectMerchDetailsWhereMerchantEmailIs(loginEmail);
		authMerchant.setMerchAuth(getAuthorizationData());
		return isAuth ? authMerchant : null;
	}
	
	public boolean isMerchAuth() {
		return isAuth;
	}

}
