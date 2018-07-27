package com.chatbot.app.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.chatbot.app.vo.MerchantVO;

@Repository
public class Database {

	private static Logger logger = Logger.getLogger(Database.class);
	private static List<MerchantVO> merchants;

	private static final String USERS_FILE_PATH = "C:\\tmp\\chatbotusers.csv";

	static {
		merchants = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(new File(USERS_FILE_PATH)))) {
			String line = null;
			while (null != (line = br.readLine())) {
				String[] parts = line.split(",");
				int index = 0;
				MerchantVO merchant = new MerchantVO();
				merchant.setId(Integer.parseInt(parts[index++]));
				merchant.setMerchId(parts[index++]);
				merchant.setMerchPassword(parts[index++]);
				merchant.setFirstName(parts[index++]);
				merchant.setLastName(parts[index++]);
				merchant.setEmail(parts[index]);
				merchants.add(merchant);
			}
		} catch (FileNotFoundException e) {
			logger.error("File Not Found. File Path: " + USERS_FILE_PATH, e);
		} catch (IOException e) {
			logger.error("IOException. File Path: " + USERS_FILE_PATH, e);
		}
	}
	
	public int selectMerchCountWhereMerchantEmailIs(String email) {
		int merchCount = 0;
		for (MerchantVO merch: merchants) {
			if (merch.getEmail().equals(email)) {
				merchCount++;
			}
		}
		return merchCount;
	}

	public String selectMerchPasswordWhereMerchantEmailIs(String email) {
		MerchantVO merch = selectMerchDetailsWhereMerchantEmailIs(email);
		return merch.getMerchPassword();
	}
	
	public MerchantVO selectMerchDetailsWhereMerchantEmailIs(String email) {
		MerchantVO merchant = null;
		for (MerchantVO merch: merchants) {
			if (merch.getEmail().equals(email)) {
				merchant = merch;
			}
		}
		return merchant;
	}
}
