package com.onlinebankingsystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinebankingsystem.dao.EmailTempDetailsDao;
import com.onlinebankingsystem.entity.EmailTempDetails;
@Service
public class EmailTempServiceImpl implements EmailTempService{
	@Autowired
	private EmailTempDetailsDao emailTempDetailsDao;
	
	@Override
	public EmailTempDetails addEmailTempDetails(EmailTempDetails emailTempDetails) {
		// TODO Auto-generated method stub
		return emailTempDetailsDao.save(emailTempDetails);
	}

	@Override
	public EmailTempDetails getEmailTempDetailById(int emailTempId) {
		
		Optional<EmailTempDetails> optional = this.emailTempDetailsDao.findById(emailTempId);
		
		if(optional.isPresent()) {
			return optional.get();
		}
		
		return null;
	}

	@Override
	public EmailTempDetails getEmailTempDetailsByCode(String type) {
		// TODO Auto-generated method stub
		return this.emailTempDetailsDao.findByCode(type);
	}

	@Override
	public List<EmailTempDetails> getAllEmailTempDetails() {
		// TODO Auto-generated method stub
		return emailTempDetailsDao.findAll();
	}

}
