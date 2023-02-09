package com.abhi.accounts.service;

import com.abhi.accounts.entity.AccountNumber;
import com.abhi.accounts.repo.AccountNumberRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AccountNumberServiceServiceImpl implements AccountNumberService{
    @Autowired
    private AccountNumberRepo accountNumberRepo;

    @Override
    @Retryable(value = OptimisticLockingFailureException.class,maxAttempts = 15)
    public String getNewAccountNumber() {
       AccountNumber accountNumber= accountNumberRepo.findAll().get(0);
       Long existingAccNumber= accountNumber.getAccountNumber();
       Long newAccNumber=existingAccNumber+1;
       accountNumber.setAccountNumber(newAccNumber);
       log.info("DB account:"+accountNumber+"New ac number:"+newAccNumber);
       AccountNumber savedAccount= accountNumberRepo.save(accountNumber);
       log.info("Saved account:"+savedAccount);
        return String.format("%010d",newAccNumber);
    }
}
