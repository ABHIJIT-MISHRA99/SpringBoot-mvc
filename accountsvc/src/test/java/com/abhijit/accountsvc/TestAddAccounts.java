package com.abhijit.accountsvc;

import com.abhijit.accountsvc.dto.AccountStatus;
import com.abhijit.accountsvc.dto.AccountType;
import com.abhijit.accountsvc.dto.AddAccountDTO;
import com.abhijit.accountsvc.repo.AccountRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class TestAddAccounts {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    AccountRepo accountRepo;

    private ObjectMapper objectMapper;
    private AddAccountDTO testAddAc;
    private String testAddAcJson;
    @BeforeEach
    void setup() throws JsonProcessingException {
        accountRepo.deleteAll();
        List notes=new ArrayList<>();
        notes.add("test note 1");

        testAddAc=new AddAccountDTO();
        testAddAc.setCustomerName("Test Cust1");
        testAddAc.setAccountStatus(AccountStatus.ACTIVE);
        testAddAc.setNotes(notes);
        testAddAc.setAccountType(AccountType.SAVING);
        testAddAc.setRegion("TESTREGION");

        objectMapper=new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        testAddAcJson=objectMapper.writeValueAsString(testAddAc);

    }
    @Test
    void addMultipleAccounts() throws InterruptedException {
        int parallelThreads = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(parallelThreads);
        for (int i = 0; i < parallelThreads; i++) {
            executorService.submit(getRunnable());

        }
        executorService.awaitTermination(5, TimeUnit.SECONDS);

        Assertions.assertTrue(accountRepo.findAll().size()==10);
    }
        private Runnable getRunnable(){
            return new Runnable(){
                @Override
                public void run() {
                    try {
                        mockMvc.perform(
                                post("/accounts")
                                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                                        .accept(MediaType.APPLICATION_JSON_VALUE)
                                        .content(testAddAcJson)
                        );
                    } catch (Exception e) {
                        log.error("error in mockmvc.",e);
                    }
                }
            };
        }
    }



