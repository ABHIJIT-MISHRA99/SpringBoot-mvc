package com.abhijit.accountsvc.service;

import com.abhijit.accountsvc.dto.AccountDTO;
import com.abhijit.accountsvc.dto.AddAccountDTO;
import com.abhijit.accountsvc.entity.Account;
import com.abhijit.accountsvc.mapper.AccountMapper;
import com.abhijit.accountsvc.repo.AccountRepo;

import java.time.LocalDate;

public class AccountServiceImpl implements AccountService{
    private AccountRepo accountRepo;
    private AccountMapper accountMapper;
    private AccountNumberService accountNumberService;

    public AccountServiceImpl(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    @Override
    public AccountDTO add(AddAccountDTO addAccountDTO) {
        Account account=accountMapper.convertAddAcToAc(addAccountDTO);
        account.setCreateDate(LocalDate.now());
        account.setAccountId(accountNumberService.getNewAccountNumber());
        Account newAc=accountRepo.save(account);
        return accountMapper.convertAddAcToAcDto(newAc);
    }
}
