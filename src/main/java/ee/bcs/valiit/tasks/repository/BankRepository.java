package ee.bcs.valiit.tasks.repository;

import ee.bcs.valiit.tasks.BankAccount;
import ee.bcs.valiit.tasks.BankClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;

@Repository
public class BankRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public void createClient(String firstName, String lastName, String address, String email) {
        String sql = "INSERT INTO client (first_name, last_name, address, email) VALUES(:fnParam, :lnParam, :addParam, :emailParam)";
        HashMap<String, Object> paraMap = new HashMap<>();
        paraMap.put("fnParam", firstName);
        paraMap.put("lnParam", lastName);
        paraMap.put("addParam", address);
        paraMap.put("emailParam", email);
        jdbcTemplate.update(sql, paraMap);
    }

    public int requestClientId(int clientId) {
        String sql1 = "SELECT id FROM client WHERE id = :idParam";
        HashMap<String, Object> paraMap1 = new HashMap<>();
        paraMap1.put("idParam", clientId);
        return jdbcTemplate.queryForObject(sql1, paraMap1, Integer.class);
    }

    public void createAccount(int clientId, String accNumber) {
        String sql2 = "INSERT INTO account (account_number, balance, client_id) VALUES(:accParam, :balanceParam, :idParam)";
        HashMap<String, Object> paraMap2 = new HashMap<>();
        paraMap2.put("accParam", accNumber);
        paraMap2.put("balanceParam", BigDecimal.ZERO);
        paraMap2.put("idParam", clientId);
        jdbcTemplate.update(sql2, paraMap2);
    }

    public BigDecimal getBalance(String accNumber) {
        String sql3 = "SELECT balance FROM account WHERE account_number = :accParam";
        HashMap<String, Object> paraMap3 = new HashMap<>();
        paraMap3.put("accParam", accNumber);
        return jdbcTemplate.queryForObject(sql3, paraMap3, BigDecimal.class);
    }

    public void addToTransactionsBalance(String accNumber){
        String sql15 = "INSERT INTO transactions (from_account, time, transaction_type ) VALUES(:accParam, :timeParam, :typeParam)";
        HashMap<String, Object> paraMap15 = new HashMap<>();
        paraMap15.put("accParam", accNumber);
        paraMap15.put("timeParam", LocalDateTime.now());
        paraMap15.put("typeParam", "get_balance");
        jdbcTemplate.update(sql15, paraMap15);
    }

    public String changeBalance(BigDecimal newBalance, String accNumber) {
        String sql5 = "UPDATE account SET balance = :newBalance WHERE account_number = :accParam";
        HashMap<String, Object> paraMap5 = new HashMap<>();
        paraMap5.put("accParam", accNumber);
        paraMap5.put("newBalance", newBalance);
        jdbcTemplate.update(sql5, paraMap5);
        return "";
    }

    public void addToTransactionsDeposit (BigDecimal transactionAmount, String accNumber) {
        String sql6 = "INSERT INTO transactions (to_account, amount, time, transaction_type ) VALUES(:accParam, :amountParam, :timeParam, :typeParam)";
        HashMap<String, Object> paraMap6 = new HashMap<>();
        paraMap6.put("accParam", accNumber);
        paraMap6.put("amountParam", transactionAmount);
        paraMap6.put("timeParam", LocalDateTime.now());
        paraMap6.put("typeParam", "deposit");
        jdbcTemplate.update(sql6, paraMap6);
}

    public void addToTransactionsWithdraw (BigDecimal transactionAmount, String accNumber) {
        String sql9 = "INSERT INTO transactions (from_account, amount, time, transaction_type ) VALUES(:accParam, :amountParam, :timeParam, :typeParam)";
        HashMap<String, Object> paraMap9 = new HashMap<>();
        paraMap9.put("accParam", accNumber);
        paraMap9.put("amountParam", transactionAmount);
        paraMap9.put("timeParam", LocalDateTime.now());
        paraMap9.put("typeParam", "withdraw");
        jdbcTemplate.update(sql9, paraMap9);
    }

    public void addToTransactionsTransfers (BigDecimal amount, String fromAccount, String toAccount) {
        String sql14 = "INSERT INTO transactions (from_account, to_account, amount, time, transaction_type ) VALUES(:fromAccParam, :toAccParam, :amountParam, :timeParam, :typeParam)";
        HashMap<String, Object> paraMap14 = new HashMap<>();
        paraMap14.put("fromAccParam", fromAccount);
        paraMap14.put("toAccParam", toAccount);
        paraMap14.put("amountParam", amount);
        paraMap14.put("timeParam", LocalDateTime.now());
        paraMap14.put("typeParam", "transfer");
        jdbcTemplate.update(sql14, paraMap14);
    }
}

