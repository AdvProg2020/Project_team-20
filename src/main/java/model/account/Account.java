package model.account;

import java.util.ArrayList;

public abstract class Account extends GeneralAccount{
    private static ArrayList<Account> allAccounts = new ArrayList<>();
    private String name;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String username;
    private String password;
    private AccountType accountType;
    private double credit;

    public Account(String name, String lastName, String email, String phoneNumber, String username, String password, double credit, GeneralAccountType generalAccountType, AccountType accountType) {
        super(generalAccountType);
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
        this.credit = credit;
        this.accountType = accountType;
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

    public String getPassword() {
        return password;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    //TODO design for database
    public static void addAccount(Account account) {
        allAccounts.add(account);
    }

    public static void deleteAccount(Account account) {
        allAccounts.remove(account);
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

    public void setPassword(String password) {
        this.password = password;
    }

    public static Account getAccountWithUsername(String username) {
        for (Account account : allAccounts) {
            if (account.getUsername().equals(username))
                return account;
        }
        return null;
        //throw new AccountUnavailableException();
    }

    //ehtiaj darim ????
    public static boolean hasThisAccount(String username) {
        return getAccountWithUsername(username) != null;
    }
}
