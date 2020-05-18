package model.account;

import com.gilecode.yagson.YaGson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Account extends GeneralAccount{
    private static ArrayList<Account> allAccounts = new ArrayList<>();
    protected String name;
    protected String lastName;
    protected String email;
    protected String phoneNumber;
    protected String username;
    protected String password;
    protected AccountType accountType;
    protected double credit;

    public Account(String name, String lastName, String email, String phoneNumber, String username, String password, double credit, AccountType accountType) {
        super(GeneralAccountType.ACCOUNT);
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

    public void edit(Account account) {
        this.name = account.getName();
        this.lastName = account.getLastName();
        this.email = account.getEmail();
        this.phoneNumber = account.getPhoneNumber();
        this.username = account.getUsername();
        this.password = account.getPassword();
        this.credit = account.getCredit();
        this.accountType = account.getAccountType();
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

    public void increaseCredit(double money) {
        credit += money;
    }

    public void decreaseCredit(double money) throws Exception{
        if (credit<money)
            throw new notEnoughMoneyException();
        else
            credit -= money;
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

    public static Account getAccountWithUsername(String username) throws Exception {
        for (Account account : allAccounts) {
            if (account.getUsername().equals(username))
                return account;
        }
        throw new AccountUnavailableException();
    }

    public static Buyer getBuyerWithUsername(String username) throws Exception{
        Account account = getAccountWithUsername(username);
        if(account instanceof Buyer){
            return (Buyer)account;
        }
        throw new AccountIsNotBuyerException();
    }

    public static class notEnoughMoneyException extends Exception {
        public notEnoughMoneyException() {
            super("not Enough Money");
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public static class AccountUnavailableException extends Exception {
        public AccountUnavailableException() {
            super("Account doesn't exist");
        }
    }

    public static class AccountIsNotBuyerException extends Exception {
        public AccountIsNotBuyerException() {
            super("Account Is Not Buyer!");
        }
    }

    //ehtiaj darim ????
    public static boolean hasThisAccount(String username) {
        for (Account account : allAccounts) {
            if (account.getUsername().equals(username))
                return true;
        }
        return false;
    }

    public static void store() {
        YaGson yaGson = new YaGson();
        File file = new File("src/main/resources/Account.txt");
        try {
            FileWriter fileWriter = new FileWriter(file, false);
            for (Account account : allAccounts) {
                fileWriter.write(yaGson.toJson(account));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
