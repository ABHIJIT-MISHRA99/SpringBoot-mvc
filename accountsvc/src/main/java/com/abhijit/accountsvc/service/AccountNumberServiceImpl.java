package com.abhijit.accountsvc.service;

import com.abhijit.accountsvc.entity.AccountNumber;
import com.abhijit.accountsvc.repo.AccountNumberRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AccountNumberServiceImpl implements AccountNumberService{
    private AccountNumberRepo accountNumberRepo;

    public AccountNumberServiceImpl(AccountNumberRepo accountNumberRepo) {
        this.accountNumberRepo = accountNumberRepo;
    }

    @Override
    @Retryable(value= OptimisticLockingFailureException.class,maxAttempts = 15)
    public String getNewAccountNumber() {
        try {
            AccountNumber accountNumber = accountNumberRepo.findAll().get(0);
            long newAcNumber = accountNumber.getAccountNumber() + 1;
            accountNumber.setAccountNumber(newAcNumber);

            accountNumberRepo.save(accountNumber);
            return String.format("%10d", newAcNumber);
        }catch (OptimisticLockingFailureException e){
            log.error("exception in account creation.will retry.");
            throw e;
        }
    }
}
