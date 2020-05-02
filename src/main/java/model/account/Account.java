package model.account;

import java.util.ArrayList;

public abstract class Account {
    private static ArrayList<Account> allAccounts = new ArrayList<>();
    private String name;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String username;
    private double credit;

    private enum type {
        MANAGER,
        SELLER,
        BUYER
    }

    public Account(String name, String lastName, String email, String phoneNumber, String username, double credit) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.credit = credit;
    }

    public static ArrayList<Account> getAllAccounts() {
        return allAccounts;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public double getCredit() {
        return credit;
    }

    //TODO design for database
    public static void addAccount(Account account) {
        allAccounts.add(account);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public static Account getAccountWithUsername(String username) throws Exception{
        for (Account account : allAccounts) {
            if (account.getUsername().equals(username))
                return account;
        }
        throw new AccountUnavailableException();
    }

    //ehtiaj darim ????
    public static boolean hasThisAccount(String username) throws Exception {
        return getAccountWithUsername(username) != null;
    }

    public static class AccountUnavailableException extends Exception {
        public AccountUnavailableException() { super("Account unavailable"); }
    }
}
