package com.abhijit.accountsvc.controller;

import com.abhijit.accountsvc.dto.AccountDTO;
import com.abhijit.accountsvc.dto.AddAccountDTO;
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
    @Override
    @PostMapping
    public ResponseEntity<AccountDTO> add(AddAccountDTO accountDTO) {
        log.info("account:"+accountDTO);
        return new ResponseEntity<>(new AccountDTO(), HttpStatus.CREATED);

    }
}


