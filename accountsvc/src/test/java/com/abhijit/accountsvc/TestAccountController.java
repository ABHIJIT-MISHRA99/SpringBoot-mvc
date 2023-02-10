package com.abhijit.accountsvc;

import com.abhijit.accountsvc.dto.*;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class TestAccountController {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    AccountRepo accountRepo;
    @Autowired
    AccountService accountService;

    private AddAccountDTO testAddAc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() throws JsonProcessingException  {
        accountRepo.deleteAll();
        List notes=new ArrayList<>();
        notes.add("test note 1");

        testAddAc = new AddAccountDTO();
        testAddAc.setCustomerName("Test Cust1");
        testAddAc.setAccountStatus(AccountStatus.ACTIVE);
        testAddAc.setNotes(notes);
        testAddAc.setAccountType(AccountType.SAVING);
        testAddAc.setRegion("TESTREGION");

        objectMapper=new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());


    }
    @Test
    @DisplayName("Add account Test ")
    void testAddAccount() throws Exception {
        String testAddAcJson=objectMapper.writeValueAsString(testAddAc);

        MvcResult mvcResult=mockMvc.perform(
                post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(testAddAcJson)

        )
                .andDo(print())
                .andReturn();


        String contentAsString=mvcResult.getResponse().getContentAsString();
        AccountDTO accountDTO=objectMapper.readValue(contentAsString, AccountDTO.class);

        log.info("accountDTO:"+accountDTO);
        Assertions.assertAll(
                ()->Assertions.assertEquals(testAddAc.getCustomerName(),accountDTO.getCustomerName()),
                ()->Assertions.assertNotNull(accountDTO.getAccountId())
        );


    }

    @Test
    @DisplayName("Add account test with missing information")
    void testAddAccountMissingInformation() throws Exception{

        testAddAc.setNotes(new ArrayList<>());
         String testAddAcJsonMissingInfo=objectMapper.writeValueAsString(testAddAc);
        mockMvc.perform(
                post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(testAddAcJsonMissingInfo)
        )
                .andDo(print())
                .andExpect(status().is4xxClientError());

    }
    @Test
    @DisplayName("Get Account Test with valid Name")
    void testGetAccount() throws Exception {
        AccountDTO dbAcc=accountService.add(testAddAc);
        String accId=dbAcc.getAccountId();
        MvcResult mvcResult=mockMvc.perform(
                get("/accounts"+"/"+accId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andDo(print())
                .andReturn();
        String contentAsString=mvcResult.getResponse().getContentAsString();
        AccountDTO accountDTO=objectMapper.readValue(contentAsString, AccountDTO.class);


        Assertions.assertAll(
                ()->Assertions.assertEquals(testAddAc.getCustomerName(),accountDTO.getCustomerName()),
                ()->Assertions.assertNotNull(accountDTO.getAccountId())
        );
    }
    @Test
    @DisplayName("Get Account Test with missing account")
    void testGetAcWithUnknownAc() throws Exception {
        mockMvc.perform(
                        get("/accounts"+"/9999999999")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE)

                )
                .andDo(print())
                .andExpect(status().is4xxClientError());

    }
@Test
@DisplayName("Update account test")
    void testUpdateAcc() throws Exception {
    AccountDTO dbAcc=accountService.add(testAddAc);
    UpdateAccountDTO updateAccountDTO=new UpdateAccountDTO();
    updateAccountDTO.setCustomerName("Erica");
    updateAccountDTO.setRegion("TESTREGION");
    updateAccountDTO.setAccountStatus(AccountStatus.ACTIVE);

    String updateAcJsonStr= objectMapper.writeValueAsString(updateAccountDTO);

    String accId=dbAcc.getAccountId();
    MvcResult mvcResult=mockMvc.perform(
                    put("/accounts"+ "/" +accId)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .accept(MediaType.APPLICATION_JSON_VALUE)
                            .content(updateAcJsonStr)
            )
            .andDo(print())
            .andReturn();

    String contentAsString=mvcResult.getResponse().getContentAsString();
    AccountDTO accountDTO=objectMapper.readValue(contentAsString, AccountDTO.class);


    Assertions.assertAll(
            ()->Assertions.assertEquals("Erica",accountDTO.getCustomerName()),
            ()->Assertions.assertNotNull(accId,accountDTO.getAccountId())
    );

    }


    @Test
    @DisplayName("Add Notes to existing account")
    void testAddNotes() throws Exception {
        AccountDTO dbAcc=accountService.add(testAddAc);

        List<String> notes=new ArrayList<>();
        notes.add("Test note A");
        notes.add("Test note B");

        String notesJson=objectMapper.writeValueAsString(notes);

        String accId=dbAcc.getAccountId();
        MvcResult mvcResult=mockMvc.perform(
                        patch("/accounts"+ "/" +accId)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .content(notesJson)
                )
                .andDo(print())
                .andReturn();

        String contentAsString=mvcResult.getResponse().getContentAsString();
        AccountDTO accountDTO=objectMapper.readValue(contentAsString, AccountDTO.class);


        Assertions.assertAll(
                ()->Assertions.assertTrue(accountDTO.getNotes().contains("Test note A")),
                ()->Assertions.assertTrue(accountDTO.getNotes().contains("Test note B")),
                ()->Assertions.assertTrue(accountDTO.getNotes().size()==3),
                ()->Assertions.assertEquals(accId,accountDTO.getAccountId())
        );

    }
//@Test
//@DisplayName("Delete Account")
//    void testDeleteAc() throws Exception{
//    AccountDTO dbAcc=accountService.add(testAddAc);
//    mockMvc.perform(
//                    delete("/accounts"+ "/" +dbAcc.getAccountId())
//                            .contentType(MediaType.APPLICATION_JSON_VALUE)
//                            .accept(MediaType.APPLICATION_JSON_VALUE)
//            )
//            .andExpect(status().is2xxSuccessful());
//
//
//    }
    @Test
    @DisplayName("Get all accounts ")
    void testGetAccounts() throws Exception {
        accountService.add(testAddAc);
        accountService.add(testAddAc);
        accountService.add(testAddAc);
        accountService.add(testAddAc);

        MvcResult mvcResult=mockMvc.perform(
                        get("/accounts")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andReturn();
        String contentAsString=mvcResult.getResponse().getContentAsString();
        List<AccountDTO> accountDTOs=objectMapper.readValue(contentAsString, new TypeReference<>(){});
        accountDTOs.forEach(a->log.info("account:"+a));


      Assertions.assertEquals(4,accountDTOs.size());
    }


}
