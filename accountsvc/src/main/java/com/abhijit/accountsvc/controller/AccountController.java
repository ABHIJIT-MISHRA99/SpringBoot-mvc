package com.abhijit.accountsvc.controller;

import com.abhijit.accountsvc.dto.AccountDTO;
import com.abhijit.accountsvc.dto.AddAccountDTO;
import com.abhijit.accountsvc.dto.UpdateAccountDTO;
import com.abhijit.accountsvc.exception.AccountFileGenException;
import com.abhijit.accountsvc.exception.AccountFileUploadException;
import com.abhijit.accountsvc.exception.AppAccountNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping(value = "/accounts",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
@Validated
@Tag(name = "Account Management API",description = "API for all account related operations.")
public interface AccountController {
    @PostMapping
    @Operation(summary = "Create new account")
    ResponseEntity<AccountDTO> add(@RequestBody @Valid AddAccountDTO addaccountDTO);

    @GetMapping("/{accountId}")
    @Operation(summary = "Get information about a given account")
    ResponseEntity<AccountDTO> get(@PathVariable("accountId")@NotNull @Length(min=10,max=10) String accountId) throws AppAccountNotFoundException;

    @PutMapping("/{accountId}")
    @Operation(summary = "Update existing account")
    ResponseEntity<AccountDTO> update(@PathVariable("accountId")@NotNull @Length(min=10,max=10) String accountId,
                                      @RequestBody @Valid UpdateAccountDTO updateAccountDTO) throws AppAccountNotFoundException;

    @PatchMapping("/{accountId}")
    @Operation(summary = "add notes")
    ResponseEntity<AccountDTO> addNotes(@PathVariable("accountId")@NotNull @Length(min=10,max=10) String accountId,
                                      @RequestBody  List<@NotNull @Length(min=10,max =500) String> notesToAdd) throws AppAccountNotFoundException;
    @DeleteMapping("/{accountId}")
    @Operation(summary = "Delete account")
    ResponseEntity<Void> delete(@PathVariable("accountId")@NotNull @Length(min=10,max=10) String accountId) ;

    @GetMapping
    @Operation(summary = "get all accounts")
    ResponseEntity<List<AccountDTO>> getAccounts(@RequestParam(value = "region",required = false)String region);

    @GetMapping("/file")
    @Operation(summary = "Download all accounts")
    ResponseEntity<Object>getAccFile() throws AccountFileGenException;

    @PostMapping(value="/file",consumes =MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "AC file upload")
    ResponseEntity<String> fileUpload(@RequestParam("uploadfile")MultipartFile uploadfile) throws AccountFileUploadException;






}
