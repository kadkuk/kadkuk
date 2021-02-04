package ee.bcs.valiit.tasks.service;

import ee.bcs.valiit.tasks.*;
import ee.bcs.valiit.tasks.repository.BankRepository;
import liquibase.exception.DatabaseException;
import liquibase.exception.DatabaseIncapableOfOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.List;

@Service
public class BankService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BankRepository bankRepository;

    @Transactional
    public String createClient(BankClient client) {
        try {
            bankRepository.createClient(client.getFirstName(), client.getLastName(), client.getSocialNumber(), client.getAddress(), client.getEmail(), passwordEncoder.encode(client.getPassword()));
            return "New client creation successful.";
        } catch (DuplicateKeyException e) {
            throw new BankExceptions("Client with this social number already exists.");
        } catch (DataIntegrityViolationException e) {
            throw new BankExceptions("Social number cannot be empty");
        } catch (IllegalArgumentException e) {
            throw new BankExceptions("Password cannot be empty");
        }
    }


    @Transactional
    public String createAccount(BankAccount bankAccount) {
        try {
            int clientId = bankRepository.requestClientId(bankAccount.getClientId());
            bankRepository.createAccount(clientId, bankAccount.getAccNumber());
            return "Account created.";
        } catch (EmptyResultDataAccessException e) {
            throw new BankExceptions("Client ID not found. PLease create your client profile.");
        }
    }

    @Transactional
    public String getBalance(String accNumber) {
        try {
            BigDecimal balance = bankRepository.getBalance(accNumber);
            bankRepository.addToTransactionsBalance(accNumber);
            return "Balance is " + String.valueOf(balance) + " EUR.";
        } catch (EmptyResultDataAccessException e) {
            throw new BankExceptions("Account not found.");
        }
    }

    @Transactional
    public String deposit(BankAccount bankAccount) {
        try {
            BigDecimal balance = bankRepository.getBalance(bankAccount.getAccNumber());
            if (bankAccount.getTransactionAmount().compareTo(BigDecimal.ZERO) <= 0) {
                throw new BankExceptions("Entered amount is not legitimate");
            } else {
                BigDecimal transactionAmount = bankAccount.getTransactionAmount().setScale(2, RoundingMode.HALF_UP);
                BigDecimal newBalance = balance.add(transactionAmount).setScale(2, RoundingMode.HALF_UP);
                bankRepository.changeBalance(newBalance, bankAccount.getAccNumber());
                bankRepository.addToTransactionsDeposit(transactionAmount, bankAccount.getAccNumber());
                return "Your new balance is " + String.valueOf(newBalance) + " EUR.";
            }
        } catch (EmptyResultDataAccessException e) {
            throw new BankExceptions("Account not found.");
        }
    }

    @Transactional
    public String withdraw(BankAccount bankAccount) {
        try {
            BigDecimal balance = bankRepository.getBalance(bankAccount.getAccNumber());
            if (bankAccount.getTransactionAmount().compareTo(BigDecimal.ZERO) <= 0) {
                throw new BankExceptions("Entered amount is not legitimate");
            } else {
                BigDecimal transactionAmount = bankAccount.getTransactionAmount().setScale(2, RoundingMode.HALF_UP);
                BigDecimal newBalance = balance.subtract(transactionAmount).setScale(2, RoundingMode.HALF_UP);
                if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
                    throw new BankExceptions("Not enough funds.");
                }
                bankRepository.changeBalance(newBalance, bankAccount.getAccNumber());
                bankRepository.addToTransactionsWithdraw(transactionAmount, bankAccount.getAccNumber());
                return "Your new balance is " + String.valueOf((newBalance)) + " EUR.";
            }
        } catch (EmptyResultDataAccessException e) {
            throw new BankExceptions("Account not found.");
        }
    }

    @Transactional
    public String transfer(Transfers transfers) {
        try {
            BigDecimal fromBalance = bankRepository.getBalance(transfers.getFromAccount());
            BigDecimal toBalance = bankRepository.getBalance(transfers.getToAccount());
            if (transfers.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
                throw new BankExceptions("Transfer amount is not legitimate.");
            } else {
                BigDecimal amount = transfers.getAmount().setScale(2, RoundingMode.HALF_UP);
                BigDecimal newBalanceFrom = fromBalance.subtract(amount).setScale(2, RoundingMode.HALF_UP);
                if (newBalanceFrom.compareTo(BigDecimal.ZERO) < 0) {
                    throw new BankExceptions("fromAccount does not have enough funds.");
                }
                bankRepository.changeBalance(newBalanceFrom, transfers.getFromAccount());
                BigDecimal newBalanceTo = toBalance.add(amount).setScale(2, RoundingMode.HALF_UP);
                bankRepository.changeBalance(newBalanceTo, transfers.getToAccount());
                bankRepository.addToTransactionsTransfers(amount, transfers.getFromAccount(), transfers.getToAccount());
                return "fromAccount new balance is " + String.valueOf(newBalanceFrom) + " EUR, and toAccount new balance is " + String.valueOf(newBalanceTo) + " EUR.";
            }
        } catch (EmptyResultDataAccessException e) {
            throw new BankExceptions("Account(s) not found.");
        }

    }

    public List<BankClient> allClients() {
        return bankRepository.allClients();
    }

    public List<TotalDeposits> totalDeposits() {
        return bankRepository.totalDeposits();
    }

    public List<TransactionsHistory> transactionsHistory(String accNumber) {
        List <TransactionsHistory> newList = bankRepository.transactionsHistory(accNumber);
        if (newList.isEmpty()) {
            throw new BankExceptions("Account not found.");
        } else {
            return bankRepository.transactionsHistory(accNumber);
        }
    }
}
