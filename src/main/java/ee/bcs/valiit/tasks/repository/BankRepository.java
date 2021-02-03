package ee.bcs.valiit.tasks.repository;

import ee.bcs.valiit.tasks.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Repository
public class BankRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public void createClient(String firstName, String lastName, String socialNumber, String address, String email, String password) {
        String sql = "INSERT INTO client (first_name, last_name, social_number, address, email, password) VALUES(:fnParam, :lnParam, :snParam, :addParam, :emailParam, :passwordParam)";
        HashMap<String, Object> paraMap = new HashMap<>();
        paraMap.put("fnParam", firstName);
        paraMap.put("lnParam", lastName);
        paraMap.put("snParam", socialNumber);
        paraMap.put("addParam", address);
        paraMap.put("emailParam", email);
        paraMap.put("passwordParam", password);
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

    public void addToTransactionsBalance(String accNumber) {
        String sql15 = "INSERT INTO transactions (from_account, time, transaction_type ) VALUES(:accParam, :timeParam, :typeParam)";
        HashMap<String, Object> paraMap15 = new HashMap<>();
        paraMap15.put("accParam", accNumber);
        paraMap15.put("timeParam", LocalDateTime.now());
        paraMap15.put("typeParam", "get_balance");
        jdbcTemplate.update(sql15, paraMap15);
    }

    public void changeBalance(BigDecimal newBalance, String accNumber) {
        String sql5 = "UPDATE account SET balance = :newBalance WHERE account_number = :accParam";
        HashMap<String, Object> paraMap5 = new HashMap<>();
        paraMap5.put("accParam", accNumber);
        paraMap5.put("newBalance", newBalance);
        jdbcTemplate.update(sql5, paraMap5);
    }

    public void addToTransactionsDeposit(BigDecimal transactionAmount, String accNumber) {
        String sql6 = "INSERT INTO transactions (to_account, amount, time, transaction_type ) VALUES(:accParam, :amountParam, :timeParam, :typeParam)";
        HashMap<String, Object> paraMap6 = new HashMap<>();
        paraMap6.put("accParam", accNumber);
        paraMap6.put("amountParam", transactionAmount);
        paraMap6.put("timeParam", LocalDateTime.now());
        paraMap6.put("typeParam", "deposit");
        jdbcTemplate.update(sql6, paraMap6);
    }

    public void addToTransactionsWithdraw(BigDecimal transactionAmount, String accNumber) {
        String sql9 = "INSERT INTO transactions (from_account, amount, time, transaction_type ) VALUES(:accParam, :amountParam, :timeParam, :typeParam)";
        HashMap<String, Object> paraMap9 = new HashMap<>();
        paraMap9.put("accParam", accNumber);
        paraMap9.put("amountParam", transactionAmount);
        paraMap9.put("timeParam", LocalDateTime.now());
        paraMap9.put("typeParam", "withdraw");
        jdbcTemplate.update(sql9, paraMap9);
    }

    public void addToTransactionsTransfers(BigDecimal amount, String fromAccount, String toAccount) {
        String sql14 = "INSERT INTO transactions (from_account, to_account, amount, time, transaction_type ) VALUES(:fromAccParam, :toAccParam, :amountParam, :timeParam, :typeParam)";
        HashMap<String, Object> paraMap14 = new HashMap<>();
        paraMap14.put("fromAccParam", fromAccount);
        paraMap14.put("toAccParam", toAccount);
        paraMap14.put("amountParam", amount);
        paraMap14.put("timeParam", LocalDateTime.now());
        paraMap14.put("typeParam", "transfer");
        jdbcTemplate.update(sql14, paraMap14);
    }

    public List<BankClient> allClients() {
        String sql15 = "SELECT * FROM client";
        List<BankClient> result = jdbcTemplate.query(sql15, new HashMap<>(), new BankClientRowMapper());
        return result;
    }

    public List<TotalDeposits> totalDeposits() {
        String sql15 = "SELECT last_name, first_name, account_number, sum(amount) AS total_deposits FROM client, account, transactions WHERE client.id=account.client_id AND account.account_number=transactions.to_account AND transaction_type = 'deposit' GROUP BY client.last_name, client.first_name, account.account_number ORDER BY 4 DESC";
        List<TotalDeposits> result = jdbcTemplate.query(sql15, new HashMap<>(), new BankRepository.TotalDepositsRowMapper());
        return result;
    }

    public List<TransactionsHistory> transactionsHistory (String accNumber) {
        String sql15 = "SELECT * FROM transactions WHERE from_account= :accParam OR to_account= :accParam";
        HashMap<String, Object> paraMap15 = new HashMap<>();
        paraMap15.put("accParam", accNumber);
        List<TransactionsHistory> result = jdbcTemplate.query(sql15, paraMap15, new TransactionsHistoryRowMapper());
        return result;
    }

    public String requestClientPassword(String email) {
        String sql20 = "SELECT password FROM client WHERE email = :emailParam";
        HashMap<String, Object> paraMap20 = new HashMap<>();
        paraMap20.put("emailParam", email);
        return jdbcTemplate.queryForObject(sql20, paraMap20, String.class);
    }

    private class BankClientRowMapper implements RowMapper<BankClient> {
        @Override
        public BankClient mapRow(ResultSet resultSet, int i) throws SQLException {
            BankClient clients = new BankClient();
            clients.setClientId(resultSet.getInt("id"));
            clients.setFirstName(resultSet.getString("first_name"));
            clients.setLastName(resultSet.getString("last_name"));
            clients.setSocialNumber(resultSet.getString("social_number"));
            clients.setAddress(resultSet.getString("address"));
            clients.setEmail(resultSet.getString("email"));
            return clients;
        }
    }


    private class TotalDepositsRowMapper implements RowMapper<TotalDeposits> {
        @Override
        public TotalDeposits mapRow(ResultSet resultSet, int i) throws SQLException {
            TotalDeposits deposits = new TotalDeposits();
            deposits.setLastName(resultSet.getString("last_name"));
            deposits.setFirstName(resultSet.getString("first_name"));
            deposits.setAccNumber(resultSet.getString("account_number"));
            deposits.setTotalDeposits(resultSet.getBigDecimal("total_deposits"));
            return deposits;
        }
    }

    private class TransactionsHistoryRowMapper implements RowMapper<TransactionsHistory> {
        @Override
        public TransactionsHistory mapRow(ResultSet resultSet, int i) throws SQLException {
            TransactionsHistory transactionsHistory = new TransactionsHistory();
            transactionsHistory.setTransactionId(resultSet.getInt("transaction_id"));
            transactionsHistory.setFromAccount(resultSet.getString("from_account"));
            transactionsHistory.setToAccount(resultSet.getString("to_account"));
            transactionsHistory.setAmount(resultSet.getBigDecimal("amount"));
            transactionsHistory.setTime(resultSet.getObject("time", LocalDateTime.class));
            transactionsHistory.setTransactionType(resultSet.getString("transaction_type"));
            return transactionsHistory;
        }
    }


}


