package com.onlinebankingsystem.dto;

import java.util.ArrayList;
import java.util.List;

import com.onlinebankingsystem.entity.UserAccounts;

public class UserAccountDto extends CommonApiResponse {

    private List<UserAccounts> Accounts = new ArrayList<>();

    public List<UserAccounts> getAccounts() {
        return Accounts;
    }

    public void setAccounts(List<UserAccounts> accounts) {
        Accounts = accounts;
    }

}
