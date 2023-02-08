package com.abhijit.accountsvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class AccountsvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsvcApplication.class, args);
	}

}
