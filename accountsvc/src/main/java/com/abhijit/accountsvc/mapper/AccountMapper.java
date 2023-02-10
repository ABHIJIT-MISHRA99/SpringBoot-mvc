package com.abhijit.accountsvc.mapper;

import com.abhijit.accountsvc.dto.AccountDTO;
import com.abhijit.accountsvc.dto.AddAccountDTO;
import com.abhijit.accountsvc.dto.UpdateAccountDTO;
import com.abhijit.accountsvc.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mapping(target = "accountId",ignore = true)
    @Mapping(target = "version",ignore = true)
    @Mapping(target = "createDate",ignore = true)
    Account convertAddAcToAc(AddAccountDTO addAccountDTO);
    AccountDTO convertAcToAcDto(Account account);

    @Mapping(target = "accountId",ignore = true)
    @Mapping(target = "version",ignore = true)
    @Mapping(target="createDate",ignore = true)
    @Mapping(target = "accountType",ignore = true)
    @Mapping(target="notes",ignore = true)
    void updateAcFromUpdateAccount(@MappingTarget Account account, UpdateAccountDTO updateAccountDTO);
}
