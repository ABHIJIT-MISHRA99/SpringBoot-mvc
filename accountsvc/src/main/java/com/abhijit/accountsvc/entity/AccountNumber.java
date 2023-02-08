package com.abhijit.accountsvc.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "accountnumber")
@Data
public class AccountNumber {
    @Id
    private String id;
    @Version
    private Long version;
    private Long accountNumber;
}
