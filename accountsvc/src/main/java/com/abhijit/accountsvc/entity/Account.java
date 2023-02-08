package com.abhijit.accountsvc.entity;

import com.abhijit.accountsvc.dto.AccountStatus;
import com.abhijit.accountsvc.dto.AccountType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "accounts")
@Data
public class Account {
    @Id
    private String accountId;
    @Version
    private String version;
    private AccountType accountType;
    private String customerName;
    private List<String> notes;
    private AccountStatus accountStatus;
    private String region;
    private LocalDate createDate;

}
