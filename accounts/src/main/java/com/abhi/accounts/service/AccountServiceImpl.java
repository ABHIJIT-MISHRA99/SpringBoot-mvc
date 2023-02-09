package com.abhi.accounts.service;

import com.abhi.accounts.entity.Account;
import com.abhi.accounts.entity.AccountStatus;
import com.abhi.accounts.exception.AppException;
import com.abhi.accounts.repo.AccountsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountsRepo accountsRepo;
    private AccountNumberService accountNumberService;

    public AccountServiceImpl(AccountsRepo accountsRepo, AccountNumberService accountNumberService) {
        this.accountsRepo = accountsRepo;
        this.accountNumberService = accountNumberService;
    }

    @Override
    public Account addAccount(Account account) throws AppException {
        if (account.getAccountNumber() != null) {
            throw new AppException("Client can't send account number when creating new account.");
        }
        String newAccountNumber = accountNumberService.getNewAccountNumber();
        account.setAccountNumber(newAccountNumber);
        account.setCreateDate(LocalDate.now());
        Account savedAccount = accountsRepo.save(account);
        return savedAccount;
    }

    @Override
    public Account getAccount(String accountNumber) {
        return accountsRepo.findByAccountNumber(accountNumber);
    }

    @Override
    public Account updateAcount(Account account) throws AppException {
        Account acFromdb = accountsRepo.findByAccountNumber(account.getAccountNumber());
        if (null != acFromdb) {
            account.setId(acFromdb.getId());
            account.setVersion(acFromdb.getVersion());
        } else {
            throw new AppException("Account not found");
        }
        Account savedAccount = accountsRepo.save(account);
        return savedAccount;
    }

    @Override
    public void deleteAccount(String accountNumber) {
        Account acFromDb = accountsRepo.findByAccountNumber(accountNumber);
        accountsRepo.deleteById(acFromDb.getId());
    }

    @Override
    public List<Account> getAccounts() {
        return accountsRepo.findAll();
    }

    @Override
    public void blockAccounts(List<String> accountIds) {
        accountIds.forEach(a->{
            Account dbAc=accountsRepo.findByAccountNumber(a);
            if(null!=dbAc){
                dbAc.setAccountStatus(AccountStatus.BLOCKED);
                accountsRepo.save(dbAc);
            }
        });
    }
}
