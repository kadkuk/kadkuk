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
    //static HashMap<String, BigDecimal> accountBalanceMap = new HashMap<>();

    @Autowired
    private BankService bankService;

    //http://localhost:8080/bank/client
    @PostMapping("client")
    public String createClient(@RequestBody BankClient client) {
        return bankService.createClient(client);
    }

    //http://localhost:8080/bank/createaccount/
    @PutMapping("createaccount")
    public String createAccount(@RequestBody BankAccount bankAccount) {
        return bankService.createAccount(bankAccount);
    }

    //http://localhost:8080/bank/getbalance
    @PutMapping ("getbalance")
    public String getbalance(@RequestBody BankAccount bankAccount) {
        return bankService.getBalance(bankAccount);
    }

    //http://localhost:8080/bank/deposit
    @PutMapping("deposit")
    public String deposit(@RequestBody BankAccount bankAccount) {
        return bankService.deposit(bankAccount);
    }

    //http://localhost:8080/bank/withdraw
    @PutMapping("withdraw")
    public String withdraw (@RequestBody BankAccount bankAccount) {
        return bankService.withdraw(bankAccount);
    }

    //http://localhost:8080/bank/transfer
    @PutMapping("transfer")
    public String transfer(@RequestBody Transfers transfers) {
        return bankService.transfer(transfers);
    }




    //http://localhost:8080/bank
  //  @GetMapping()
   // public List<BankClient> clients(){
     //   String sql15="SELECT * FROM client";
       // List<BankClient> result = jdbcTemplate.query(sql15, new HashMap<>(), new BankClientRowMapper());
        //return result;
   // }

    //private class BankClientRowMapper implements RowMapper<BankClient> {
      //  @Override
        //public BankClient mapRow(ResultSet resultSet, int i) throws SQLException {
          //  BankClient clients = new BankClient();
            //clients.setClientId(resultSet.getInt("id"));
            //clients.setFirstName(resultSet.getString("first_name"));
            //clients.setLastName(resultSet.getString("last_name"));
            //clients.setAddress(resultSet.getString("address"));
            //clients.setEmail(resultSet.getString("email"));
            //return clients;
        //}
    //}
}




