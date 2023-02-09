package com.abhijit.accountsvc.controller;

import com.abhijit.accountsvc.dto.AccountDTO;
import com.abhijit.accountsvc.dto.AddAccountDTO;
import com.abhijit.accountsvc.dto.UpdateAccountDTO;
import com.abhijit.accountsvc.exception.AppAccountNotFoundException;
import com.abhijit.accountsvc.service.AccountService;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class AccountControllerImpl implements AccountController {

    private AccountService accountService;

    public AccountControllerImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public ResponseEntity<AccountDTO> add(AddAccountDTO accountDTO) {
        log.info("account:"+accountDTO);
        AccountDTO newAc=accountService.add(accountDTO);
        return new ResponseEntity<>(newAc, HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity<AccountDTO> get(String accountId) throws AppAccountNotFoundException {
        AccountDTO accountDTO=accountService.get(accountId);
        return new ResponseEntity<>(accountDTO,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AccountDTO> update(String accountId, UpdateAccountDTO updateAccountDTO) throws AppAccountNotFoundException {
        AccountDTO accountDTO=accountService.update(accountId,updateAccountDTO);
        return new ResponseEntity<>(accountDTO,HttpStatus.OK);

    }

    @Override
    public ResponseEntity<AccountDTO> addNotes(String accountId, List<String> notesToAdd) throws AppAccountNotFoundException {
        AccountDTO accountDTO=accountService.addNotes(accountId,notesToAdd);
        return new ResponseEntity<>(accountDTO,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> delete(String accountId) {
        accountService.delete(accountId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<AccountDTO>> get() {
        List<AccountDTO> accounts=accountService.get();
        return new ResponseEntity<>(accounts,HttpStatus.OK);
    }


}


