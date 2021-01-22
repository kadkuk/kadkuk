package ee.bcs.valiit.tasks.controller;

import ee.bcs.valiit.tasks.BankAccount;
import ee.bcs.valiit.tasks.BankClient;
import ee.bcs.valiit.tasks.Lesson4;
import ee.bcs.valiit.tasks.Transfers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

@RequestMapping("bank")
@RestController
public class ControllerBank {
    //static HashMap<String, BigDecimal> accountBalanceMap = new HashMap<>();

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    //http://localhost:8080/bank/client
    @PostMapping("client")
    public String client(@RequestBody BankClient client) {
        String sql = "INSERT INTO client (first_name, last_name, address, email) VALUES(:fnParam, :lnParam, :addParam, :emailParam)";
        HashMap<String, Object> paraMap = new HashMap<>();
        paraMap.put("fnParam", client.getFirstName());
        paraMap.put("lnParam", client.getLastName());
        paraMap.put("addParam", client.getAddress());
        paraMap.put("emailParam", client.getEmail());
        jdbcTemplate.update(sql, paraMap);
        return "New client creation successful.";
    }

    //http://localhost:8080/bank/createaccount/
    @PutMapping("createaccount")
    public String createAccount(@RequestBody BankAccount bankAccount) {
        String sql1 = "SELECT id FROM client WHERE id = :idParam";
        HashMap<String, Object> paraMap1 = new HashMap<>();
        paraMap1.put("idParam", bankAccount.getClientId());
        Integer clientId = jdbcTemplate.queryForObject(sql1, paraMap1, Integer.class);
        String sql2 = "INSERT INTO account (account_number, balance, client_id) VALUES(:accParam, :balanceParam, :idParam)";
        HashMap<String, Object> paraMap2 = new HashMap<>();
        paraMap2.put("accParam", bankAccount.getAccNumber());
        paraMap2.put("balanceParam", BigDecimal.ZERO);
        paraMap2.put("idParam", clientId);
        jdbcTemplate.update(sql2, paraMap2);
        return "Account created.";
    }

    //http://localhost:8080/bank/getbalance
    @PutMapping ("getbalance")
    public String getbalance(@RequestBody BankAccount bankAccount) {
        String sql3 = "SELECT balance FROM account WHERE account_number = :accParam";
        HashMap<String, Object> paraMap3 = new HashMap<>();
        paraMap3.put("accParam", bankAccount.getAccNumber());
        BigDecimal balance = jdbcTemplate.queryForObject(sql3, paraMap3, BigDecimal.class);
        String sql15 = "INSERT INTO transactions (from_account, time, transaction_type ) VALUES(:accParam, :timeParam, :typeParam)";
        HashMap<String, Object> paraMap15 = new HashMap<>();
        paraMap15.put("accParam", bankAccount.getAccNumber());
        paraMap15.put("timeParam", LocalDateTime.now());
        paraMap15.put("typeParam", "get_balance");
        jdbcTemplate.update(sql15, paraMap15);
        return "Your balance is "+String.valueOf(balance)+" EUR.";
    }

    //http://localhost:8080/bank/deposit
    @PutMapping("deposit")
    public String deposit(@RequestBody BankAccount bankAccount) {
        if (bankAccount.getTransactionAmount().compareTo(BigDecimal.ZERO) >0) {
            String sql4 = "SELECT balance FROM account WHERE account_number = :accParam";
            HashMap<String, Object> paraMap4 = new HashMap<>();
            paraMap4.put("accParam", bankAccount.getAccNumber());
            BigDecimal balance = jdbcTemplate.queryForObject(sql4, paraMap4, BigDecimal.class);
            BigDecimal transactionAmount = bankAccount.getTransactionAmount();
            BigDecimal newBalance = balance.add(transactionAmount).setScale(2, RoundingMode.HALF_UP);
            String sql5 = "UPDATE account SET balance = :newBalance WHERE account_number = :accParam";
            HashMap<String, Object> paraMap5 = new HashMap<>();
            paraMap5.put("accParam", bankAccount.getAccNumber());
            paraMap5.put("newBalance", newBalance);
            jdbcTemplate.update(sql5, paraMap5);
            String sql6 = "INSERT INTO transactions (to_account, amount, time, transaction_type ) VALUES(:accParam, :amountParam, :timeParam, :typeParam)";
            HashMap<String, Object> paraMap6 = new HashMap<>();
            paraMap6.put("accParam", bankAccount.getAccNumber());
            paraMap6.put("amountParam", transactionAmount);
            paraMap6.put("timeParam", LocalDateTime.now());
            paraMap6.put("typeParam", "deposit");
            jdbcTemplate.update(sql6, paraMap6);
            return "Your new balance is "+String.valueOf((newBalance))+" EUR.";
        } else {
            return "Irrelevant amount.";
        }
    }

    //http://localhost:8080/bank/withdraw
    @PutMapping("withdraw")
    public String withdraw(@RequestBody BankAccount bankAccount) {
        if (bankAccount.getTransactionAmount().compareTo(BigDecimal.ZERO) >0) {
            String sql7 = "SELECT balance FROM account WHERE account_number = :accParam";
            HashMap<String, Object> paraMap7 = new HashMap<>();
            paraMap7.put("accParam", bankAccount.getAccNumber());
            BigDecimal balance = jdbcTemplate.queryForObject(sql7, paraMap7, BigDecimal.class);
            BigDecimal transactionAmount = bankAccount.getTransactionAmount();
            BigDecimal newBalance = balance.subtract(transactionAmount).setScale(2, RoundingMode.HALF_UP);
            String sql8 = "UPDATE account SET balance = :newBalance WHERE account_number = :accParam";
            HashMap<String, Object> paraMap8 = new HashMap<>();
            paraMap8.put("accParam", bankAccount.getAccNumber());
            paraMap8.put("newBalance", newBalance);
            jdbcTemplate.update(sql8, paraMap8);
            String sql9 = "INSERT INTO transactions (from_account, amount, time, transaction_type ) VALUES(:accParam, :amountParam, :timeParam, :typeParam)";
            HashMap<String, Object> paraMap9 = new HashMap<>();
            paraMap9.put("accParam", bankAccount.getAccNumber());
            paraMap9.put("amountParam", transactionAmount);
            paraMap9.put("timeParam", LocalDateTime.now());
            paraMap9.put("typeParam", "withdraw");
            jdbcTemplate.update(sql9, paraMap9);
            return "Your new balance is "+String.valueOf((newBalance))+" EUR.";
        } else {
            return "Irrelevant amount.";
        }
    }

    //http://localhost:8080/bank/transfer
    @PutMapping("transfer")
    public String transfer(@RequestBody Transfers transfers) {
        if (transfers.getAmount().compareTo(BigDecimal.ZERO) >0) {
            BigDecimal amount = transfers.getAmount();
            // ask fromAccount balance:
            String sql10 = "SELECT balance FROM account WHERE account_number = :fromAccParam";
            HashMap<String, Object> paraMap10 = new HashMap<>();
            paraMap10.put("fromAccParam", transfers.getFromAccount());
            BigDecimal fromBalance = jdbcTemplate.queryForObject(sql10, paraMap10, BigDecimal.class);
            BigDecimal newBalanceFrom = fromBalance.subtract(amount).setScale(2, RoundingMode.HALF_UP);
            if (newBalanceFrom.compareTo(BigDecimal.ZERO) < 0) {
                throw new RuntimeException();
            }
                // update fromBalance in database
            String sql12 = "UPDATE account SET balance = :newBalanceFrom WHERE account_number = :fromAccParam";
            HashMap<String, Object> paraMap12 = new HashMap<>();
            paraMap12.put("newBalanceFrom", newBalanceFrom);
            paraMap12.put("fromAccParam", transfers.getFromAccount());
            jdbcTemplate.update(sql12, paraMap12);

            // ask toAccount balance
            String sql11 = "SELECT balance FROM account WHERE account_number = :toAccParam";
            HashMap<String, Object> paraMap11 = new HashMap<>();
            paraMap11.put("toAccParam", transfers.getToAccount());
            BigDecimal toBalance = jdbcTemplate.queryForObject(sql11, paraMap11, BigDecimal.class);
            BigDecimal newBalanceTo = toBalance.add(amount).setScale(2, RoundingMode.HALF_UP);

            // update toBalance in database
            String sql13 = "UPDATE account SET balance = :newBalanceTo WHERE account_number = :toAccParam";
            HashMap<String, Object> paraMap13 = new HashMap<>();
            paraMap13.put("newBalanceTo", newBalanceTo);
            paraMap13.put("toAccParam", transfers.getToAccount());
            jdbcTemplate.update(sql13, paraMap13);

            // add an entry into transactions history
            String sql14 = "INSERT INTO transactions (from_account, to_account, amount, time, transaction_type ) VALUES(:fromAccParam, :toAccParam, :amountParam, :timeParam, :typeParam)";
            HashMap<String, Object> paraMap14 = new HashMap<>();
            paraMap14.put("fromAccParam", transfers.getFromAccount());
            paraMap14.put("toAccParam", transfers.getToAccount());
            paraMap14.put("amountParam", amount);
            paraMap14.put("timeParam", LocalDateTime.now());
            paraMap14.put("typeParam", "transfer");
            jdbcTemplate.update(sql14, paraMap14);
            return "fromAccount new balance is "+String.valueOf(newBalanceFrom)+" EUR, and toAccount new balance is "+String.valueOf(newBalanceTo)+" EUR.";

            }
        return "Transaction not allowed.";
    }

    //http://localhost:8080/bank/1112/2223/10
    // @GetMapping("transfer/{from}/{to}/{sum}")
    //public String transfer(@PathVariable("from") String from, @PathVariable("to") String to, @PathVariable("sum") String sum) {
      //  BigDecimal balanceFrom = accountBalanceMap.get(from).setScale(2, RoundingMode.HALF_UP);
        //BigDecimal balanceTo = accountBalanceMap.get(to).setScale(2, RoundingMode.HALF_UP);
        //BigDecimal uus = new BigDecimal(sum).setScale(2, RoundingMode.HALF_UP);
        //if ((uus.compareTo(BigDecimal.ZERO)) > 0 && (balanceFrom.compareTo(uus)) >= 0) {
         //   accountBalanceMap.put(to, (balanceTo.add(uus).setScale(2, RoundingMode.HALF_UP)));
          //  accountBalanceMap.put(from, (balanceFrom.subtract(uus).setScale(2, RoundingMode.HALF_UP)));
           // return "Account " + from + " new balance is " + accountBalanceMap.get(from) + " EUR, and account "+ to +" new balance is " + accountBalanceMap.get(to) + " EUR.";
        //} else {
          //  return "Transaction not allowed.";
        //}
    //}
}




