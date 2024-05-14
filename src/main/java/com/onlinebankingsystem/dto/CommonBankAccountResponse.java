package com.onlinebankingsystem.dto;

import java.util.ArrayList;
import java.util.List;

import com.onlinebankingsystem.entity.AdminAccount;
import com.onlinebankingsystem.entity.CommonBankAccount;

public class CommonBankAccountResponse extends CommonApiResponse {

    List<CommonBankAccount> commonBankAccountDetais = new ArrayList<>();
    List<AdminAccount> adminAccountDetais = new ArrayList<>();

    public List<CommonBankAccount> getCommonBankAccountDetais() {
        return commonBankAccountDetais;
    }

    public void setCommonBankAccountDetais(List<CommonBankAccount> commonBankAccountDetais) {
        this.commonBankAccountDetais = commonBankAccountDetais;
    }

    public List<AdminAccount> getAdminAccountDetais() {
        return adminAccountDetais;
    }

    public void setAdminAccountDetais(List<AdminAccount> adminAccountDetais) {
        this.adminAccountDetais = adminAccountDetais;
    }

}
