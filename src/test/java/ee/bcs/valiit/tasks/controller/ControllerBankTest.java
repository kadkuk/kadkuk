package ee.bcs.valiit.tasks.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ee.bcs.valiit.tasks.BankAccount;
import ee.bcs.valiit.tasks.BankClient;
import ee.bcs.valiit.tasks.Transfers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc

class ControllerBankTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createClientTest() throws Exception  {
        ObjectMapper mapper = new ObjectMapper();
        BankClient client = new BankClient();
        client.setFirstName("Peeter");
        client.setLastName("Kukk");
        client.setAddress("Aardla 130-43, Tartu");
        client.setEmail("peeter@ut.ee");
        http://localhost:8080/bank/client
        mockMvc.perform(MockMvcRequestBuilders.post("/bank/client").contentType("application/Json").content(mapper.writeValueAsString(client))
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void createAccount() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        BankAccount bankAccount = new BankAccount();
        bankAccount.setClientId(4);
        bankAccount.setAccNumber("EE00777777");
        http://localhost:8080/bank/createaccount
        mockMvc.perform(MockMvcRequestBuilders.put("/bank/createaccount").contentType("application/Json").content(mapper.writeValueAsString(bankAccount))
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getBalance() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccNumber("EE00123456");
        http://localhost:8080/bank/getbalance
        mockMvc.perform(MockMvcRequestBuilders.put("/bank/getbalance").contentType("application/Json").content(mapper.writeValueAsString(bankAccount))
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deposit() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccNumber("EE00123456");
        bankAccount.setTransactionAmount(BigDecimal.valueOf(100.99));
        http://localhost:8080/bank/deposit
        mockMvc.perform(MockMvcRequestBuilders.put("/bank/deposit").contentType("application/Json").content(mapper.writeValueAsString(bankAccount))
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void withdraw() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccNumber("EE00123456");
        bankAccount.setTransactionAmount(BigDecimal.valueOf(55.64));
        http://localhost:8080/bank/withdraw
        mockMvc.perform(MockMvcRequestBuilders.put("/bank/withdraw").contentType("application/Json").content(mapper.writeValueAsString(bankAccount))
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void transfer() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Transfers transfers = new Transfers();
        transfers.setFromAccount("EE00123456");
        transfers.setToAccount("EE00111111");
        transfers.setAmount(BigDecimal.valueOf(2.05));
        http://localhost:8080/bank/transfer
        mockMvc.perform(MockMvcRequestBuilders.put("/bank/transfer").contentType("application/Json").content(mapper.writeValueAsString(transfers))
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }
}