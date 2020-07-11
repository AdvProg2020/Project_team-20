package server.model.bank;

import java.util.ArrayList;

public class BankAccount {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String accountNumber;
    private ArrayList<BankReceipt> bankReceipts;

    public BankAccount(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        bankReceipts = new ArrayList<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void addReceipt(BankReceipt bankReceipt) {
        bankReceipts.add(bankReceipt);
    }

    public ArrayList<BankReceipt> getBankReceipts() {
        return bankReceipts;
    }
}
