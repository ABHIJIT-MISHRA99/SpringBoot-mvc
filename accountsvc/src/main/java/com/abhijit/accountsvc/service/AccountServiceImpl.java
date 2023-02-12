package com.abhijit.accountsvc.service;

import com.abhijit.accountsvc.dto.AccountDTO;
import com.abhijit.accountsvc.dto.AddAccountDTO;
import com.abhijit.accountsvc.dto.UpdateAccountDTO;
import com.abhijit.accountsvc.entity.Account;
import com.abhijit.accountsvc.exception.AppAccountNotFoundException;
import com.abhijit.accountsvc.mapper.AccountMapper;
import com.abhijit.accountsvc.repo.AccountRepo;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService{
    private AccountRepo accountRepo;
    private AccountMapper accountMapper;
    private AccountNumberService accountNumberService;

    public AccountServiceImpl(AccountRepo accountRepo, AccountMapper accountMapper, AccountNumberService accountNumberService) {
        this.accountRepo = accountRepo;
        this.accountMapper = accountMapper;
        this.accountNumberService = accountNumberService;
    }

    @Override
    public AccountDTO add(AddAccountDTO addAccountDTO) {
        Account account=accountMapper.convertAddAcToAc(addAccountDTO);
        account.setCreateDate(LocalDate.now());
        account.setAccountId(accountNumberService.getNewAccountNumber());
        Account newAc=accountRepo.save(account);
        return accountMapper.convertAcToAcDto(newAc);
    }

    @Override
    public AccountDTO get(String accountId) throws AppAccountNotFoundException {
        Optional<Account> dbAc=accountRepo.findById(accountId);
        if(dbAc.isPresent()){
            Account account=accountRepo.findById(accountId).get();
            return accountMapper.convertAcToAcDto(account);
        }else {
            throw new AppAccountNotFoundException("Missing account. Ac : "+accountId);
        }

    }

    @Override
    @Retryable(value= OptimisticLockingFailureException.class,maxAttempts = 5)
    public AccountDTO update(String accountId, UpdateAccountDTO updateAccountDTO) throws AppAccountNotFoundException {
        Optional<Account> acFromDb=accountRepo.findById(accountId);
        if(acFromDb.isPresent()){
            Account account=acFromDb.get();
            accountMapper.updateAcFromUpdateAccount(account,updateAccountDTO);
            Account savedAccount=accountRepo.save(account);
            return accountMapper.convertAcToAcDto(savedAccount);
        }
        else {
            throw new AppAccountNotFoundException("Account not found.account:"+accountId);
        }
    }

    @Override
    public AccountDTO addNotes(String accountId, List<String> notesToAdd) throws AppAccountNotFoundException {
        Optional<Account> acFromDb=accountRepo.findById(accountId);
        if(acFromDb.isPresent()){
            Account account=acFromDb.get();
            account.getNotes().addAll(notesToAdd);
            Account savedAccount=accountRepo.save(account);
            return accountMapper.convertAcToAcDto(savedAccount);
        }
        else {
            throw new AppAccountNotFoundException("Account not found.account:"+accountId);
        }
    }


    @Override
    public void delete(String accountId) {
        accountRepo.deleteById(accountId);

    }

    @Override
    public List<AccountDTO> getAccounts(String region) {
        List<AccountDTO>accounts;
        if(null!=region){
            accounts=accountRepo.findByRegion(region).stream().map(a->accountMapper.convertAcToAcDto(a)).collect(Collectors.toList());;
        }else{
            accounts=accountRepo.findAll().stream().map(a->accountMapper.convertAcToAcDto(a)).collect(Collectors.toList());
        }
        return accounts;
    }

    @Override
    public String createAcFile() throws IOException {
        List<Account> allAccounts=accountRepo.findAll();
        String filePath="/tmp/acfile."+ UUID.randomUUID().toString().replace("-","");
        Path path= Paths.get(URI.create("file://"+filePath));
        try (BufferedWriter bufferedWriter=Files.newBufferedWriter(path, StandardOpenOption.CREATE_NEW)){
            for (Account account:allAccounts){
                bufferedWriter.write(account.toString());
            }
        }
        return  filePath;
    }
}
