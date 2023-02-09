package com.abhi.accounts.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "accounts")
@Data
@NoArgsConstructor
public class Account {
    @Id
    private String id;
    @Version
    private Long version;
    private String accountNumber;
    private AccountType accountType;
    private String customerName;
    private List<String> notes;
    private AccountStatus accountStatus;
    private String region;
    private LocalDate createDate;

    public Account(AccountType accountType, String customerName, List<String> notes, AccountStatus accountStatus, String region) {
        this.accountType = accountType;
        this.customerName = customerName;
        this.notes = notes;
        this.accountStatus = accountStatus;
        this.region = region;
    }
}
