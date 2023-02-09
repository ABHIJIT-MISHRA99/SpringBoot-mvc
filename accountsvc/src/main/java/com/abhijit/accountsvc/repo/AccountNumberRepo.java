package com.abhijit.accountsvc.repo;

import com.abhijit.accountsvc.entity.AccountNumber;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountNumberRepo extends MongoRepository<AccountNumber,String> {

}
