package com.abhijit.accountsvc.mapper;

import com.abhijit.accountsvc.dto.AccountDTO;
import com.abhijit.accountsvc.dto.AddAccountDTO;
import com.abhijit.accountsvc.entity.Account;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-08T11:44:28+0530",
    comments = "version: 1.5.0.Final, compiler: javac, environment: Java 17.0.5 (Private Build)"
)
@Component
public class AccountMapperImpl implements AccountMapper {

    @Override
    public Account convertAddAcToAc(AddAccountDTO accountDTO) {
        if ( accountDTO == null ) {
            return null;
        }

        Account account = new Account();

        account.setAccountType( accountDTO.getAccountType() );
        account.setCustomerName( accountDTO.getCustomerName() );
        List<String> list = accountDTO.getNotes();
        if ( list != null ) {
            account.setNotes( new ArrayList<String>( list ) );
        }
        account.setAccountStatus( accountDTO.getAccountStatus() );
        account.setRegion( accountDTO.getRegion() );

        return account;
    }

    @Override
    public AccountDTO convertAddAcToAcDto(Account account) {
        if ( account == null ) {
            return null;
        }

        AccountDTO accountDTO = new AccountDTO();

        accountDTO.setAccountId( account.getAccountId() );
        accountDTO.setAccountType( account.getAccountType() );
        accountDTO.setCustomerName( account.getCustomerName() );
        List<String> list = account.getNotes();
        if ( list != null ) {
            accountDTO.setNotes( new ArrayList<String>( list ) );
        }
        accountDTO.setAccountStatus( account.getAccountStatus() );
        accountDTO.setRegion( account.getRegion() );
        accountDTO.setCreateDate( account.getCreateDate() );

        return accountDTO;
    }
}
