package com.onlinebankingsystem.service;

import java.util.List;

import com.onlinebankingsystem.entity.EmailTempDetails;

public interface EmailTempService {
	
	EmailTempDetails addEmailTempDetails(EmailTempDetails emailTempDetails);
	
	EmailTempDetails getEmailTempDetailById(int EmailTempId);
	
	EmailTempDetails getEmailTempDetailsByCode(String type);
	
	List<EmailTempDetails> getAllEmailTempDetails();
}
