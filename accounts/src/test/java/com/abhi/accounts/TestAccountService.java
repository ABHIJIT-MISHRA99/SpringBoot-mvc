package com.abhi.accounts;

import com.abhi.accounts.entity.Account;
import com.abhi.accounts.entity.AccountStatus;
import com.abhi.accounts.entity.AccountType;
import com.abhi.accounts.exception.AppException;
import com.abhi.accounts.repo.AccountsRepo;
import com.abhi.accounts.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Slf4j
public class TestAccountService {
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountsRepo accountsRepo;

    @BeforeEach
    private void setup() {
        accountsRepo.deleteAll();
    }

    @AfterEach
    private void cleanup() {
        //accountsRepo.deleteAll();
    }

    @Test
    @Disabled
    @DisplayName("Test to add single account using account service")
    public void addAccount() throws AppException {
        List<String> notes = new ArrayList<>();
        notes.add("Account created without issues");
        Account account = new Account(AccountType.SAVING, "Cust Test 1", notes, AccountStatus.ACTIVE, "NA");
        log.info("addAccount test.new account" + account);
        Account newAc = accountService.addAccount(account);
        Assertions.assertAll(
                () -> assertNotNull(account.getAccountNumber()),
                () -> assertTrue(account.getAccountNumber().length() == 10)
        );
    }

    @Test
    @DisplayName("Test to add multiple accounts using account service ")
    public void addAccounts() throws InterruptedException {
        List<String> notes = new ArrayList<>();
        notes.add("Account created without issues");
        int threads = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(threads);
        CountDownLatch countDownLatch = new CountDownLatch(threads);
        for (int i = 0; i < threads; i++) {
            Account testAccount = new Account(AccountType.SAVING, "Cust Test", notes, AccountStatus.ACTIVE, "NA");
            testAccount.setCustomerName("test customer" + i);
            executorService.submit(() -> {
                try {
                    accountService.addAccount(testAccount);
                    countDownLatch.countDown();
                } catch (Exception e) {
                    log.error("EXCEPTION . " + e.getClass().getSimpleName() + ". " + e.getMessage());
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            });
        }
        countDownLatch.await(10, TimeUnit.SECONDS);
        int accountsInDB = accountsRepo.findAll().size();
        Assertions.assertEquals(10, accountsInDB);
    }

    @Test
    @DisplayName("Test getting account using account number")
    public void getAccount() throws AppException {
        List<String> notes = new ArrayList<>();
        notes.add("Account created without issues");
        Account testAccount = new Account(AccountType.SAVING, "Cust Test", notes, AccountStatus.ACTIVE, "NA");
        Account account = accountService.addAccount(testAccount);
        Account account1 = accountService.getAccount(account.getAccountNumber());
        Assertions.assertAll(
                () -> assertEquals(account.getAccountNumber(), account1.getAccountNumber()),
                () -> assertEquals(account.getCustomerName(), account1.getCustomerName()),
                () -> assertEquals(account.getAccountStatus(), account1.getAccountStatus()),
                () -> assertEquals(account.getCreateDate(), account1.getCreateDate())
        );
    }

    @Test
    @DisplayName("Test update account")
    public void updateAccount() throws AppException {
        List<String> notes = new ArrayList<>();
        notes.add("Account created without issues");
        Account testAccount = new Account(AccountType.SAVING, "Cust Test", notes, AccountStatus.ACTIVE, "NA");
        Account account = accountService.addAccount(testAccount);

        account.setAccountStatus(AccountStatus.BLOCKED);
        Account updatedAccount = accountService.updateAcount(account);
        Assertions.assertTrue(updatedAccount.getAccountStatus().equals(AccountStatus.BLOCKED));
    }

    @Test
    @DisplayName("Test delete account")
    public void deleteAccount() throws AppException {
        List<String> notes = new ArrayList<>();
        notes.add("Account created without issues");
        Account testAccount = new Account(AccountType.SAVING, "Cust Test", notes, AccountStatus.ACTIVE, "NA");
        Account account = accountService.addAccount(testAccount);

        accountService.deleteAccount(account.getAccountNumber());
        Account deletedAccount = accountsRepo.findByAccountNumber(account.getAccountNumber());
        Assertions.assertNull(deletedAccount);
    }

    @Test
    @DisplayName("Test get all accounts")
    public void getAccounts() throws AppException {
        List<String> notes = new ArrayList<>();
        notes.add("Account created without issues");
        Account testAccount1 = new Account(AccountType.SAVING, "Customer 1", notes, AccountStatus.NEW, "NA");
        Account testAccount2 = new Account(AccountType.CURRENT, "Customer 2", notes, AccountStatus.ACTIVE, "NA");
        Account testAccount3 = new Account(AccountType.SAVING, "Customer 3", notes, AccountStatus.BLOCKED, "NA");
        Account testAccount4 = new Account(AccountType.CURRENT, "Customer 4", notes, AccountStatus.CLOSED, "NA");
        Account testAccount5 = new Account(AccountType.CURRENT, "Customer 5", notes, AccountStatus.ACTIVE, "NA");

        Account testAc=accountService.addAccount(testAccount1);
        Account testAc2=accountService.addAccount(testAccount2);
        accountService.addAccount(testAccount3);
        accountService.addAccount(testAccount4);
        accountService.addAccount(testAccount5);

        List<String>accountsToBlock=new ArrayList<>();
        accountsToBlock.add(testAc.getAccountNumber());
        accountsToBlock.add(testAc2.getAccountNumber());

        accountService.blockAccounts(accountsToBlock);
        List<Account> acsFromDB=accountsRepo.findByAccountStatus(AccountStatus.BLOCKED.toString());
        Assertions.assertTrue(acsFromDB.size()==3);




    }
}
