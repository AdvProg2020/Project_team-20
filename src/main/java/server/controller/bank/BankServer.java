package server.controller.bank;

import client.model.account.Account;
import server.model.bank.BankAccount;
import server.network.Message;
import server.network.server.Server;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class BankServer extends Server {
    ArrayList<BankAccount> bankAccounts;
    private static int bankCount=0;

    public BankServer() {
        super(9000);
        bankAccounts = new ArrayList<>();
    }

    @Override
    protected void setMethods() {
        methods.add("createAccount");
    }

    public Message createAccount(String firstName, String lastName, String username, String password, String repeatPassword) {
        Message message;
        if (!password.equals(repeatPassword)) {
            message = new Message("Error");
            message.addToObjects("passwords do not match");
            return message;
        } else if (getBankAccountByUsername(username)!=null) {
            message = new Message("Error");
            message.addToObjects("username is not available");
            return message;
        } else {
            BankAccount account = new BankAccount(firstName, lastName, username, password);
            bankAccounts.add(account);
            message = new Message("Confirmation");
            LocalDateTime now = LocalDateTime.now();
            int random = ThreadLocalRandom.current().nextInt(10,  100);
            String accountNumber = now.getYear() + now.getMonth().toString() + now.getDayOfMonth() + random + bankCount;
            account.setAccountNumber(accountNumber);
            message.addToObjects(accountNumber);
            bankCount++;
            return message;
        }
    }

    private BankAccount getBankAccountByUsername(String username) {
        for (BankAccount bankAccount:bankAccounts) {
            if (bankAccount.getUsername().equals(username))
                return bankAccount;
        }
        return null;
    }


}
