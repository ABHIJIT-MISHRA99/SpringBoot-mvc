package com.abhijit.accountsvc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;
@Data
@Schema(description = "This is DTO object for New Account addition")
public class UpdateAccountDTO {
    @Schema(description = "Primary Customer Name for this account")
    @NotNull
    @Length(min=4,max =60)
    private String customerName;
    @Schema(description = "Indicates account status",defaultValue = "ACTIVE",allowableValues = "[ACTIVE,BLOCKED,CLOSED]")
    @NotNull
    private AccountStatus accountStatus;
    @Schema(description = "Region to which the account belongs")
    @NotNull
    @Length(min=2,max=15)
    private String region;
}
