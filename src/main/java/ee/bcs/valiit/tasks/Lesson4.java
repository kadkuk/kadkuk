package ee.bcs.valiit.tasks;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.Scanner;

public class Lesson4 {
    static HashMap<String, BigDecimal> accountBalanceMap = new HashMap<>();

        public static void main(String[] args) {
            bankTransactions();
        }

        public static void bankTransactions() {
            while (true) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Please enter your command: ");
                String line = scanner.nextLine();

                if (line.equalsIgnoreCase("exit")) {
                    exit();
                    break;

                } else if (line.equalsIgnoreCase("createAccount")) {
                    createAccount();

                } else if (line.equalsIgnoreCase("getBalance")) {
                    getBalance();

                } else if (line.equalsIgnoreCase("depositMoney")) {
                    depositMoney();

                } else if (line.equalsIgnoreCase("withdrawMoney")) {
                    withdrawMoney();

                } else if (line.equalsIgnoreCase("transfer")) {
                    transfer();
                }
            }
        }
        private static void exit() {
            System.out.println("Thank you and bye!");
        }

        private static void createAccount(){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter your account number: ");
            String accNumber = scanner.nextLine();
            BigDecimal balance = new BigDecimal("0");
            accountBalanceMap.put(accNumber, balance);
        }

        private static void getBalance(){
            Scanner scanner = new Scanner (System.in);
            System.out.println("Please enter your account number: ");
            String accNumber = scanner.nextLine();
            if (!accountBalanceMap.containsKey(accNumber)) {
                System.out.println("Account not found.");
            } else {
                System.out.println("Your balance is: " + accountBalanceMap.get(accNumber) + " EUR");
            }
        }

        private static void depositMoney() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter your account number: ");
            String accNumber = scanner.nextLine();
            MathContext round = new MathContext(4);
            if (!accountBalanceMap.containsKey(accNumber)) {
                System.out.println("Account not found.");
            } else {
                System.out.println("Please enter the amount: ");
                String summa = scanner.nextLine();
                BigDecimal uus = new BigDecimal(summa);
                BigDecimal balance = accountBalanceMap.get(accNumber);
                if (uus.compareTo(BigDecimal.ZERO) > 0) {
                    accountBalanceMap.put(accNumber, (uus.add(balance, round)));
                    System.out.println("Your new balance is: " + (uus.add(balance)) + " EUR");
                } else {
                    System.out.println("Non-suitable entry");
                }
            }
        }

        private static void withdrawMoney() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter your account number: ");
            String accNumber = scanner.nextLine();
            MathContext round = new MathContext(4);
            if (!accountBalanceMap.containsKey(accNumber)) {
                System.out.println("Account not found.");
            } else {
                System.out.println("Please enter the amount: ");
                String summa = scanner.nextLine();
                BigDecimal uus = new BigDecimal(summa);
                BigDecimal balance = accountBalanceMap.get(accNumber);
                if ((uus.compareTo(BigDecimal.ZERO)) > 0 && (balance.compareTo(uus)) >= 0) {
                    accountBalanceMap.put(accNumber, (balance.subtract(uus, round)));
                    System.out.println("Your new balance is: " + accountBalanceMap.get(accNumber) + " EUR");
                } else {
                    System.out.println("Transaction not allowed.");
                }

            }
        }

        private static void transfer() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter your account number: ");
            String accNumber = scanner.nextLine();
            MathContext round = new MathContext(4);
            if (!accountBalanceMap.containsKey(accNumber)) {
                System.out.println("Account not found.");
            } else if (accountBalanceMap.containsKey(accNumber)) {
                System.out.println("Please enter destination account number: ");
                String toAccount = scanner.nextLine();
                accountBalanceMap.put(toAccount, BigDecimal.ZERO);
                BigDecimal balance = accountBalanceMap.get(accNumber);
                System.out.println("Please enter the amount: ");
                String summa = scanner.nextLine();
                BigDecimal uus = new BigDecimal(summa);
                if ((uus.compareTo(BigDecimal.ZERO)) > 0 && (balance.compareTo(uus)) >= 0) {
                    accountBalanceMap.put(toAccount, (BigDecimal.ZERO.add(uus, round)));
                    accountBalanceMap.put(accNumber, (balance.subtract(uus, round)));
                    System.out.println("Your new balance is: " + accountBalanceMap.get(accNumber) + " EUR");
                    System.out.println("Destination account new balance is: " + accountBalanceMap.get(toAccount) + " EUR");
                } else {
                    System.out.println("Transaction not allowed.");
                }
            }
        }
    }

