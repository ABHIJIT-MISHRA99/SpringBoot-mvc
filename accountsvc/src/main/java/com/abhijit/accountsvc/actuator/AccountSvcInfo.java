package com.abhijit.accountsvc.actuator;

import com.abhijit.accountsvc.repo.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

@Component
public class AccountSvcInfo implements InfoContributor {
    @Autowired
    private AccountRepo accountRepo;
    @Override
    public void contribute(Info.Builder builder) {
        int size=accountRepo.findAll().size();
        builder.withDetail("Account count",size).build();

    }
}
