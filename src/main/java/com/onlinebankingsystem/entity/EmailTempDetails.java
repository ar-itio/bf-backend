package com.onlinebankingsystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class EmailTempDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id; // ID field
	    private String emailSubject;
	    @Column(length = 1000) 
	    private String emailMessage;
	    private String code;


	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getEmailSubject() {
	        return emailSubject;
	    }

	    public void setEmailSubject(String emailSubject) {
	        this.emailSubject = emailSubject;
	    }

	    public String getEmailMessage() {
	        return emailMessage;
	    }

	    public void setEmailMessage(String emailMessage) {
	        this.emailMessage = emailMessage;
	    }

	    public String getCode() {
	        return code;
	    }

	    public void setCode(String code) {
	        this.code = code;
	    }

}
