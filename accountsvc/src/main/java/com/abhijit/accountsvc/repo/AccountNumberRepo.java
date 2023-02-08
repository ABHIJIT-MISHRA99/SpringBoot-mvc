package com.abhijit.accountsvc.repo;

import com.abhijit.accountsvc.entity.AccountNumber;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountNumberRepo extends MongoRepository<AccountNumber,String> {

}
