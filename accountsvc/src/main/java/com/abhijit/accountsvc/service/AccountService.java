package com.abhijit.accountsvc.service;

import com.abhijit.accountsvc.dto.AccountDTO;
import com.abhijit.accountsvc.dto.AddAccountDTO;
import org.springframework.stereotype.Service;


public interface AccountService {
    AccountDTO add(AddAccountDTO addAccountDTO);
}
