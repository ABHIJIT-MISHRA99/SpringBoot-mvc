package com.abhijit.accountsvc.repo;

import com.abhijit.accountsvc.entity.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepo extends MongoRepository<Account,String> {

    List<Account> findByRegion(String region);
}
