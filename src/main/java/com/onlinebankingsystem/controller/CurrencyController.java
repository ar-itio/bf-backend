package com.onlinebankingsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.onlinebankingsystem.dao.CurrencyDao;
import com.onlinebankingsystem.dto.CommonApiResponse;
import com.onlinebankingsystem.dto.CurrencyResponse;
import com.onlinebankingsystem.entity.Currency;

@RestController
@RequestMapping("/api/currencies/")
@CrossOrigin
public class CurrencyController {

    @Autowired
    private CurrencyDao currencyRepository;

    @PostMapping("add")
	public ResponseEntity<CommonApiResponse> addCurrency(@RequestBody Currency currency) {
		if (currencyRepository.findByCode(currency.getCode()) == null) {
			currencyRepository.save(currency);
		} else {
			Currency currencyUpdate=currencyRepository.findByCode(currency.getCode()) ;
			currencyUpdate.setCode(currency.getCode());
			currencyUpdate.setName(currency.getName());
			currencyUpdate.setStatus(currency.getStatus());
			currencyUpdate.setTerritory(currency.getTerritory());
			currencyUpdate.setIcon(currency.getIcon());	
			currencyRepository.save(currencyUpdate);
		}
		return ResponseEntity.ok().build();
	}
    @GetMapping("fatch")
    public ResponseEntity<CurrencyResponse> getCurrency() {
    	CurrencyResponse currencyRes=new CurrencyResponse();
       List<Currency> a=currencyRepository.findAll();
       currencyRes.setCurrencyDetails(a);
       currencyRes.setResponseMessage("get Data  successfully");
       currencyRes.setSuccess(true);
		return new ResponseEntity<CurrencyResponse>(currencyRes, HttpStatus.OK);
    }

    @PostMapping("delete")
    public ResponseEntity<CommonApiResponse> deleteCurrency(@RequestBody Long id) {
        currencyRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
    
    // Additional endpoints for fetching, updating, and deleting currencies can be added here
}
