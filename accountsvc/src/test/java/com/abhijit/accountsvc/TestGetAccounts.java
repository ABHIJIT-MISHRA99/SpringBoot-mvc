package com.abhijit.accountsvc;

import com.abhijit.accountsvc.dto.AccountDTO;
import com.abhijit.accountsvc.dto.AccountStatus;
import com.abhijit.accountsvc.dto.AccountType;
import com.abhijit.accountsvc.dto.AddAccountDTO;
import com.abhijit.accountsvc.repo.AccountRepo;
import com.abhijit.accountsvc.service.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class TestGetAccounts {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    AccountRepo accountRepo;
    @Autowired
    AccountService accountService;

    private ObjectMapper objectMapper;
    private AddAccountDTO testAddAc1;
    private AddAccountDTO testAddAc2;
    private AddAccountDTO testAddAc3;
    private AddAccountDTO testAddAc4;

    @BeforeEach
    void setup() throws JsonProcessingException {
        accountRepo.deleteAll();
        testAddAc1=createTestAc("NA");
        testAddAc2=createTestAc("NA");
        testAddAc3=createTestAc("EU");
        testAddAc4=createTestAc("Asia");

        objectMapper=new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }
    @Test
    @DisplayName("Test Get accounts with region.")
    void testGetWithRegion() throws Exception {
        accountService.add(testAddAc1);
        accountService.add(testAddAc2);
        accountService.add(testAddAc3);
        accountService.add(testAddAc4);

        MvcResult mvcResult=mockMvc.perform(
                get("/accounts"+"?region=NA")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andDo(print())
                .andReturn();

        String contentAsString=mvcResult.getResponse().getContentAsString();
        List<AccountDTO> accountDTOs=objectMapper.readValue(contentAsString, new TypeReference<>() {
        });

        Assertions.assertEquals(2,accountDTOs.size());
    }


    private AddAccountDTO createTestAc(String region){
        List notes= new ArrayList<>();
        notes.add("test note get ac for region");

        AddAccountDTO testAddAc=new AddAccountDTO();
        testAddAc.setCustomerName("Test Cust1");
        testAddAc.setAccountStatus(AccountStatus.ACTIVE);
        testAddAc.setNotes(notes);
        testAddAc.setAccountType(AccountType.SAVING);
        testAddAc.setRegion(region);
        return  testAddAc;

    }




}
