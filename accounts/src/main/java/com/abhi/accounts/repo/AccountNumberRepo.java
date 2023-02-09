package com.abhi.accounts.repo;

import com.abhi.accounts.entity.AccountNumber;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountNumberRepo extends MongoRepository<AccountNumber,String> {
}
