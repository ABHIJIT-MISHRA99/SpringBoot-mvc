package com.abhi.accounts.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "accountnumber")
@Data
@NoArgsConstructor
public class AccountNumber {
    @Id
    private String id;
    @Version
    private Long version;
    private Long accountNumber;
}
