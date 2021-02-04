package ee.bcs.valiit.tasks.controller;

import ee.bcs.valiit.tasks.*;
import ee.bcs.valiit.tasks.service.BankService;
import ee.bcs.valiit.tasks.solution.controller.SolutionEmployeeController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RequestMapping("bank")
@RestController
public class ControllerBank {


    @Autowired
    private BankService bankService;

    //http://localhost:8080/bank/client

    @PostMapping("client")
    public String createClient(@RequestBody BankClient client) {
        return bankService.createClient(client);
    }

    //http://localhost:8080/bank/createaccount

    @PutMapping("createaccount")
    public String createAccount(@RequestBody BankAccount bankAccount) {
        return bankService.createAccount(bankAccount);
    }

    //http://localhost:8080/bank/getbalance?accNumber=

    @GetMapping("getbalance")
    public String getbalance(@RequestParam String accNumber) {
        return bankService.getBalance(accNumber);
    }

    //http://localhost:8080/bank/deposit

    @PutMapping("deposit")
    public String deposit(@RequestBody BankAccount bankAccount) {
        return bankService.deposit(bankAccount);
    }

    //http://localhost:8080/bank/withdraw

    @PutMapping("withdraw")
    public String withdraw(@RequestBody BankAccount bankAccount) {
        return bankService.withdraw(bankAccount);
    }

    //http://localhost:8080/bank/transfer

    @PutMapping("transfer")
    public String transfer(@RequestBody Transfers transfers) {
        return bankService.transfer(transfers);
    }


    //http://localhost:8080/bank/data/clients

    @GetMapping("data/clients")
    public List<BankClient> clients(){
        return bankService.allClients();
    }

    //http://localhost:8080/bank/data/depositors

    @GetMapping("data/depositors")
    public List<TotalDeposits> depositors(){
        return bankService.totalDeposits();
    }

    //http://localhost:8080/bank/data/history?accNumber=

    @GetMapping("data/history")
    public List<TransactionsHistory> transfersHistory(@RequestParam("accNumber") String accNumber){
        return bankService.transactionsHistory(accNumber);
    }

}




