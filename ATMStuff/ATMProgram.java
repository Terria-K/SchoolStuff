package org.yourcompany.yourproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class ATMProgram {

    private BufferedReader reader;
    private final String separator = "========================================";
    private double balance;
    private double bankBalance;

    private ArrayList<TransactionLog> logs;

    private final double InitialBalance = 50000;
    private boolean isLogin;
    private int id;

    public ATMProgram() {
        reader = new BufferedReader(new InputStreamReader(System.in));
        balance = InitialBalance;
        logs = new ArrayList<TransactionLog>();
    }

    public boolean run() {
        System.out.println(separator);
        if (!isLogin) {
            String pinCode = "";
            System.out.println("Please secretly enter your PIN code." + 
                " (4 Digits)");
            System.out.print(">>: ");
            while (true) {
                pinCode = stringInput();

                if (pinCode.matches("[0-9]{4}")) {
                    isLogin = true;
                    System.out.println("You are now login as User!");
                    System.out.println(separator);
                    break;
                } else {
                    System.out.print("Is not a valid PIN code, try again >>: ");
                }
            }
        }
        char c;

        System.out.println("Select what you wanted to do?");
        System.out.printf("Current Money: $%.2f\n", balance);
        System.out.println("[W]ithdraw");
        System.out.println("[D]eposit");
        System.out.println("[C]heck Balance");
        System.out.println("[V]iew History");
        System.out.println("[E]xit");
        System.out.print(">>: ");

        c = charInput();

        switch (c) {
            case 'w':
                withdrawMoney();
                break;
            case 'd':
                depositMoney();
                break;
            case 'c':
                checkBalance();
                break;
            case 'v':
                viewHistory();
                break;
            case 'e':
                return false;
        }
        return true;
    }

    private void withdrawMoney() {
        double money = 0;
        System.out.println(separator);
        System.out.print("Enter the amount of money to withdraw >>: ");
        money = numberInput();

        if (money > bankBalance) {
            System.out.println("You don't have sufficient money to withdraw " +
                "with this kind of amount.");           
        } else {
            balance += money;
            bankBalance -= money;
            System.out.printf("The $%.2f has successfully been withdrew.\n", 
                money);
            
            logs.add(new TransactionLog(id++, "Withdraw", money));
        }
    }

    private void depositMoney() {
        double money = 0;
        System.out.println(separator);
        System.out.print("Enter the amount of money to deposit >>: ");
        money = numberInput();

        if (money > balance) {
            System.out.println("You don't have sufficient money to deposit " +
                "with this kind of amount.");           
        } else {
            bankBalance += money;
            balance -= money;
            System.out.printf("The $%.2f has successfully been deposited.\n", 
                money);
            logs.add(new TransactionLog(id++, "Deposit", money));
        }
    }

    private void checkBalance() {
        System.out.println(separator);
        System.out.printf("Your current balance is: $%.2f\n", bankBalance);
    }

    private void viewHistory() {
        System.out.println(separator);

        if (logs.isEmpty()) {
            System.out.println("No transaction history yet.");
            return;
        }

        for (int i = 0; i < logs.size(); i++) {
            TransactionLog log = logs.get(i); 
            String type = log.getType();

            System.out.print("ID: " + log.getID() + "\t\t");
            System.out.print("Type: " + log.getType() + "\t\t");
            if (type.equals("Withdraw")) {
                System.out.printf("Amount Withdrew: %.2f\t\t\n", 
                    log.getMoney());
            } else {
                System.out.printf("Amount Deposited: %.2f\t\t\n", 
                    log.getMoney());
            }
        }
    }

    private char charInput() {
        String input = "";
        char c;
        try {
            input = reader.readLine().trim().toLowerCase();

            if (input.isEmpty()) {
                return charInput();
            }

            c = input.charAt(0);
            return c;
        } catch (IOException e) {
            System.out.println("Something went wrong.");
            return charInput();
        }
    }

    private String stringInput() {
        String input = "";
        try {
            input = reader.readLine().trim().toLowerCase();

            if (input.isEmpty()) {
                return stringInput();
            }

            return input;
        } catch (IOException e) {
            System.out.println("Something went wrong.");
            return stringInput();
        }
    }

    private double numberInput() {
        String input = "";
        double num = 0;
        try {
            input = reader.readLine().trim().toLowerCase();

            if (input.isEmpty()) {
                return numberInput();
            }

            num = Double.parseDouble(input);

            return num;
        } catch (IOException e) {
            System.out.println("Something went wrong.");
            return numberInput();
        }
    }

    public static void main(String[] args)  {
        ATMProgram practice = new ATMProgram();

        while (practice.run()) {}
        System.out.println("Program Terminated!");
    }
}
