package com.abhijit.accountsvc.controller;

import com.abhijit.accountsvc.dto.AccountDTO;
import com.abhijit.accountsvc.dto.AddAccountDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/accounts",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
@Validated
@Tag(name = "Account Management API",description = "API for all account related operations.")
public interface AccountController {
    @PostMapping
    @Operation(summary = "Create new account")
    ResponseEntity<AccountDTO> add(@RequestBody @Valid AddAccountDTO accountDTO);
}
