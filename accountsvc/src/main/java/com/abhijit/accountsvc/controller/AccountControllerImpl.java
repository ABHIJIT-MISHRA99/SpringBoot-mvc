package com.abhijit.accountsvc.controller;

import com.abhijit.accountsvc.dto.AccountDTO;
import com.abhijit.accountsvc.dto.AddAccountDTO;
import com.abhijit.accountsvc.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}


