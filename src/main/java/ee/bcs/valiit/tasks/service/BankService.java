package ee.bcs.valiit.tasks.service;

import ee.bcs.valiit.tasks.BankAccount;
import ee.bcs.valiit.tasks.BankClient;
import ee.bcs.valiit.tasks.Transfers;
import ee.bcs.valiit.tasks.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashMap;

@Service
public class BankService {

    @Autowired
    private BankRepository bankRepository;

    public String createClient (BankClient client){
        bankRepository.createClient(client.getFirstName(), client.getLastName(), client.getAddress(), client.getEmail());
        return "New client creation successful.";
    }

    public String createAccount (BankAccount bankAccount){
        int clientId = bankRepository.requestClientId(bankAccount.getClientId());
        bankRepository.createAccount(clientId, bankAccount.getAccNumber());
        return "Account created.";
    }

    public String getBalance (BankAccount bankAccount) {
        BigDecimal balance = bankRepository.getBalance(bankAccount.getAccNumber());
        bankRepository.addToTransactionsBalance(bankAccount.getAccNumber());
        return "Balance is " + String.valueOf(balance) + " EUR.";
    }

    public String deposit(BankAccount bankAccount){
        if (bankAccount.getTransactionAmount().compareTo(BigDecimal.ZERO) >0) {
            BigDecimal balance = bankRepository.getBalance(bankAccount.getAccNumber());
            BigDecimal transactionAmount = bankAccount.getTransactionAmount();
            BigDecimal newBalance = balance.add(transactionAmount).setScale(2, RoundingMode.HALF_UP);
            bankRepository.changeBalance(newBalance, bankAccount.getAccNumber());
            bankRepository.addToTransactionsDeposit(transactionAmount, bankAccount.getAccNumber());
            return "Your new balance is "+String.valueOf((newBalance))+" EUR.";
        } else {
            return "Irrelevant amount.";
        }
    }

    public String withdraw (BankAccount bankAccount){
        if (bankAccount.getTransactionAmount().compareTo(BigDecimal.ZERO) >0) {
            BigDecimal balance = bankRepository.getBalance(bankAccount.getAccNumber());
            BigDecimal transactionAmount = bankAccount.getTransactionAmount();
            BigDecimal newBalance = balance.subtract(transactionAmount).setScale(2, RoundingMode.HALF_UP);
            bankRepository.changeBalance(newBalance, bankAccount.getAccNumber());
            bankRepository.addToTransactionsWithdraw(transactionAmount, bankAccount.getAccNumber());
            return "Your new balance is "+String.valueOf((newBalance))+" EUR.";
        } else {
            return "Irrelevant amount.";
        }
        }

    public String transfer (Transfers transfers) {
        if (transfers.getAmount().compareTo(BigDecimal.ZERO) >0) {
            BigDecimal amount = transfers.getAmount();
            BigDecimal fromBalance = bankRepository.getBalance(transfers.getFromAccount());
            BigDecimal newBalanceFrom = fromBalance.subtract(amount).setScale(2, RoundingMode.HALF_UP);
            if (newBalanceFrom.compareTo(BigDecimal.ZERO) < 0) {
                throw new RuntimeException();
            }
            bankRepository.changeBalance(newBalanceFrom, transfers.getFromAccount());
            BigDecimal toBalance = bankRepository.getBalance(transfers.getToAccount());
            BigDecimal newBalanceTo = toBalance.add(amount).setScale(2, RoundingMode.HALF_UP);
            bankRepository.changeBalance(newBalanceTo, transfers.getToAccount());
            bankRepository.addToTransactionsTransfers(amount, transfers.getFromAccount(), transfers.getToAccount());
            return "fromAccount new balance is "+String.valueOf(newBalanceFrom)+" EUR, and toAccount new balance is "+String.valueOf(newBalanceTo)+" EUR.";

        }
        return "Transaction not allowed.";
    }

}


