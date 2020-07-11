package server.controller.bank;

import client.model.account.Account;
import server.model.bank.BankAccount;
import server.model.bank.BankReceiptType;
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
        methods.add("createReceipt");
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

    public Message createReceipt(BankReceiptType receiptType, double money, String sourceID, String destID, String description) {
        Message message;
        if (receiptType!=BankReceiptType.DEPOSIT && receiptType!=BankReceiptType.MOVE
        && receiptType!=BankReceiptType.WITHDRAW) {
            message = new Message("Error");
            message.addToObjects("invalid receipt type");
            return message;
        }
        if (money<=0) {
            message = new Message("Error");
            message.addToObjects("invalid money");
            return message;
        }
        message = checkValidAccounts(destID, sourceID);
        if (message!=null)
            return message;
        if (!description.matches("\\w*")) {
            message = new Message("Error");
            message.addToObjects("your input contains invalid characters");
            return message;
        }
        BankAccount sourceBankAccount = getBankAccountByID(sourceID);
    }

    private Message checkValidAccounts(String destID, String sourceID) {
        Message message;
        BankAccount sourceBankAccount = getBankAccountByID(sourceID);
        BankAccount destBankAccount = getBankAccountByID(destID);
        if (getBankAccountByID(sourceID)==null) {
            message = new Message("Error");
            message.addToObjects("source account id is invalid");
            return message;
        }

        else if (getBankAccountByID(destID)==null) {
            message = new Message("Error");
            message.addToObjects("dest account id is invalid");
            return message;
        }

        else if (destID.equals(sourceID)) {
            message = new Message("Error");
            message.addToObjects("equal source and dest account");
            return message;
        }

        return null;
    }

    private BankAccount getBankAccountByID(String ID) {
        for (BankAccount bankAccount:bankAccounts) {
            if (bankAccount.getAccountNumber().equals(ID))
                return bankAccount;
        }
        return null;
    }

    private BankAccount getBankAccountByUsername(String username) {
        for (BankAccount bankAccount:bankAccounts) {
            if (bankAccount.getUsername().equals(username))
                return bankAccount;
        }
        return null;
    }


}
