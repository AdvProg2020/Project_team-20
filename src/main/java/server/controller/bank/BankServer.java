package server.controller.bank;

import server.model.bank.BankAccount;
import server.network.Message;
import server.network.server.Server;

import java.util.ArrayList;

public class BankServer extends Server {
    ArrayList<BankAccount> bankAccounts;

    public BankServer() {
        super(9000);
        bankAccounts = new ArrayList<>();
    }

    @Override
    protected void setMethods() {
        methods.add("createAccount");
    }

    public Message createAccount(String username, String password) {
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
