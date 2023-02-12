package com.abhijit.accountsvc.controller;

import com.abhijit.accountsvc.dto.AccountDTO;
import com.abhijit.accountsvc.dto.AddAccountDTO;
import com.abhijit.accountsvc.dto.UpdateAccountDTO;
import com.abhijit.accountsvc.exception.AccountFileGenException;
import com.abhijit.accountsvc.exception.AccountFileUploadException;
import com.abhijit.accountsvc.exception.AppAccountNotFoundException;
import com.abhijit.accountsvc.service.AccountService;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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
    public ResponseEntity<List<AccountDTO>> getAccounts(String region) {
        List<AccountDTO> accounts=accountService.getAccounts(region);
        return new ResponseEntity<>(accounts,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getAccFile() throws AccountFileGenException {
        try {
            String acFile=accountService.createAcFile();
            String[] split=acFile.split("/");
            String fileName=split[split.length-1];

            Path path= Paths.get(URI.create("file://"+acFile));
            InputStream inputStream= Files.newInputStream(path, StandardOpenOption.READ);
            InputStreamResource resource=new InputStreamResource(inputStream);

            HttpHeaders headers=new HttpHeaders();
            headers.add(HttpHeaders.CACHE_CONTROL,"no-cache,no-store,must-revalidate");
            headers.add(HttpHeaders.PRAGMA,"no-cache");
            headers.add(HttpHeaders.EXPIRES,"0");
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
            headers.add(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=\""+fileName+"\"");
            return new ResponseEntity<>(resource,headers,HttpStatus.OK);


        }catch (IOException exception){
            String msg="Account file creation failed.";
            log.error(msg);
            throw  new AccountFileGenException(msg);
        }
    }

    @Override
    public ResponseEntity<String> fileUpload(MultipartFile uploadfile) throws AccountFileUploadException {
        log.info(uploadfile.getOriginalFilename());
        try {
            InputStream inputStream=uploadfile.getInputStream();
            byte[] bytes=inputStream.readAllBytes();
            String fileContent=new String(bytes);
            System.out.println(fileContent);

            return new ResponseEntity<>("file upoad Succesful",HttpStatus.OK);

        } catch (IOException e) {
            String msg="Account file upload failed.";
            log.error(msg);
            throw new AccountFileUploadException(msg);
        }
    }


}


