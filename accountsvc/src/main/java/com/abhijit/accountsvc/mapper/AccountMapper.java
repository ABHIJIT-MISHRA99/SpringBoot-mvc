package com.abhijit.accountsvc.mapper;

import com.abhijit.accountsvc.dto.AccountDTO;
import com.abhijit.accountsvc.dto.AddAccountDTO;
import com.abhijit.accountsvc.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mapping(target = "accountId",ignore = true)
    @Mapping(target = "version",ignore = true)
    @Mapping(target = "createDate",ignore = true)
    Account convertAddAcToAc(AddAccountDTO accountDTO);
    AccountDTO convertAddAcToAcDto(Account account);
}
