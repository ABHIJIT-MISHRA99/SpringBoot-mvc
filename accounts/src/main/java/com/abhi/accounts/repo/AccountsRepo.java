package com.abhi.accounts.repo;

import com.abhi.accounts.entity.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountsRepo extends MongoRepository<Account,String> {

    Account findByAccountNumber(String accountNumber);
    List<Account> findByAccountStatus(String acStatus);
}
