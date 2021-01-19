package ee.bcs.valiit.tasks.controller;

import ee.bcs.valiit.tasks.Lesson4;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

@RequestMapping("bank")
@RestController
public class ControllerBank {
    static HashMap<String, BigDecimal> accountBalanceMap = new HashMap<>();

    @GetMapping("{exit}")
    public String exit(@PathVariable("exit") String e) {
        return "Thank you and bye!";
    }

    @GetMapping("createaccount")
    public String createaccount(@RequestParam("accNumber") String accNumber) {
        accountBalanceMap.put(accNumber, BigDecimal.ZERO);
        return (accNumber);
    }

    @GetMapping("getbalance")
    public String getbalance(@RequestParam("accNumber") String accNumber) {
        if (!accountBalanceMap.containsKey(accNumber)) {
            return "Account not found!";
        } else {
            return String.valueOf(accountBalanceMap.get(accNumber).setScale(2, RoundingMode.HALF_UP));
        }
    }

    @GetMapping("deposit")
    public String deposit(@RequestParam("accNumber") String accNumber, @RequestParam("sum") String sum) {
        if (!accountBalanceMap.containsKey(accNumber)) {
            return "Account not found!";
        } else {
            BigDecimal uus = new BigDecimal(sum);
            BigDecimal balance = accountBalanceMap.get(accNumber);
            if (uus.compareTo(BigDecimal.ZERO) > 0) {
                accountBalanceMap.put(accNumber, (uus.add(balance).setScale(2, RoundingMode.HALF_UP)));
                return "Account "+accNumber+" new balance is: " + accountBalanceMap.get(accNumber) + " EUR";
            } else {
                return "Non-suitable entry";
            }
        }
    }

    @GetMapping("withdraw")
    public String withdraw(@RequestParam("accNumber") String accNumber, @RequestParam("sum") String sum) {
        if (!accountBalanceMap.containsKey(accNumber)) {
            return "Account not found!";
        } else {
            BigDecimal uus = new BigDecimal(sum).setScale(2, RoundingMode.HALF_UP);
            BigDecimal balance = accountBalanceMap.get(accNumber).setScale(2, RoundingMode.HALF_UP);
            if ((uus.compareTo(BigDecimal.ZERO)) > 0 && (balance.compareTo(uus)) >= 0) {
                accountBalanceMap.put(accNumber, (balance.subtract(uus).setScale(2, RoundingMode.HALF_UP)));
                return "Account "+accNumber+" new balance is: " + accountBalanceMap.get(accNumber) + " EUR";
            } else {
                return "Non-suitable entry";
            }
        }
    }

    @GetMapping("transfer")
    public String transfer(@RequestParam("from") String from, @RequestParam("to") String to, @RequestParam("sum") String sum) {
        if (!accountBalanceMap.containsKey(from) || !accountBalanceMap.containsKey(to)) {
            return "Account(s) not found!";
        }
        BigDecimal balanceFrom = accountBalanceMap.get(from).setScale(2, RoundingMode.HALF_UP);
        BigDecimal balanceTo = accountBalanceMap.get(to).setScale(2, RoundingMode.HALF_UP);
        BigDecimal uus = new BigDecimal(sum).setScale(2, RoundingMode.HALF_UP);
        if ((uus.compareTo(BigDecimal.ZERO)) > 0 && (balanceFrom.compareTo(uus)) >= 0) {
            accountBalanceMap.put(to, (balanceTo.add(uus).setScale(2, RoundingMode.HALF_UP)));
            accountBalanceMap.put(from, (balanceFrom.subtract(uus).setScale(2, RoundingMode.HALF_UP)));
            return "Account " + from + " new balance is " + accountBalanceMap.get(from) + " EUR, and account "+ to +" new balance is " + accountBalanceMap.get(to) + " EUR";
        } else {
            return "Transaction not allowed.";
        }
    }
}




