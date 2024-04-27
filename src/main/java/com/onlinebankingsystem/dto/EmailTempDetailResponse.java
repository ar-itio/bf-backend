package com.onlinebankingsystem.dto;

import java.util.ArrayList;
import java.util.List;

import com.onlinebankingsystem.entity.EmailTempDetails;

public class EmailTempDetailResponse  extends CommonApiResponse {
	List<EmailTempDetails> emailTempDetails = new ArrayList<>();               
    
	public List<EmailTempDetails> getEmailTempDetails() {                      
		return emailTempDetails;                                        
	}                                                             
                                                                  
	public void setEmailTempDetails(List<EmailTempDetails> emailTempDetails) {       
		this.emailTempDetails = emailTempDetails;                             
	}                                                             
                                                                  

}
