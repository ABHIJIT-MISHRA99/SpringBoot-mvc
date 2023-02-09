package com.abhi.accounts.service;

import com.abhi.accounts.entity.Account;
import com.abhi.accounts.exception.AppException;

import java.util.List;

public interface AccountService {
    Account addAccount(Account account) throws AppException;
    Account getAccount(String accountNumber);
    Account updateAcount(Account account) throws AppException;
    void deleteAccount(String accountNumber);
    List<Account> getAccounts();
    void blockAccounts(List<String> accountIds);

}
