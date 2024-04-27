package com.onlinebankingsystem.dto;

import java.util.ArrayList;
import java.util.List;

import com.onlinebankingsystem.entity.Currency;

public class CurrencyResponse extends CommonApiResponse {
	List<Currency> currencyDetails = new ArrayList<>();

	public List<Currency> getCurrencyDetails() {
		return currencyDetails;
	}

	public void setCurrencyDetails(List<Currency> currencyDetails) {
		this.currencyDetails = currencyDetails;
	}

}
