package com.abhijit.accountsvc.service;

import com.abhijit.accountsvc.dto.AccountDTO;
import com.abhijit.accountsvc.dto.AddAccountDTO;
import com.abhijit.accountsvc.dto.UpdateAccountDTO;
import com.abhijit.accountsvc.exception.AppAccountNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


public interface AccountService {
    AccountDTO add(AddAccountDTO addAccountDTO);

    AccountDTO get(String accountId) throws AppAccountNotFoundException;

    AccountDTO update(String accountId, UpdateAccountDTO updateAccountDTO) throws AppAccountNotFoundException;

    AccountDTO addNotes(String accountId, List<String> notesToAdd) throws AppAccountNotFoundException;

    void delete(String accountId);

    List<AccountDTO> get();
}
