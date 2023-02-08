package com.abhijit.accountsvc.repo;

import com.abhijit.accountsvc.entity.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepo extends MongoRepository<Account,String> {

}
