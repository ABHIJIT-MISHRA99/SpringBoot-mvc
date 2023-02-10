package com.abhijit.accountsvc.actuator;

import com.abhijit.accountsvc.repo.AccountNumberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class AccountSvcHealth implements HealthIndicator {
    @Autowired
    private AccountNumberRepo accountNumberRepo;

    @Override
    public Health getHealth(boolean includeDetails) {
        return HealthIndicator.super.getHealth(includeDetails);
    }

    @Override
    public Health health() {
        Long accountNumber=accountNumberRepo.findAll().get(0).getAccountNumber();
        if(accountNumber<10_000_000){
            return Health.up().withDetail("Account within Limit",accountNumber).build();
        }else {
            return Health.down().withDetail("Account number breach",accountNumber).build();
        }
    }
}
