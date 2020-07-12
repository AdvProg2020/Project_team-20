package client.model.bank;

import java.util.ArrayList;

public class BankAccount {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String accountNumber;
    private ArrayList<BankReceipt> bankReceipts;
    private double money;

    public BankAccount(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        bankReceipts = new ArrayList<>();
        money = 0;
    }

    public boolean hasBankReceipt(String receiptId) {
        for (BankReceipt bankReceipt1:bankReceipts) {
            if (bankReceipt1.getID().equals(receiptId))
                return true;
        }
        return false;
    }

    public BankReceipt getBankReceiptByID(String receiptId) {
        for (BankReceipt bankReceipt1:bankReceipts) {
            if (bankReceipt1.getID().equals(receiptId))
                return bankReceipt1;
        }
        return null;
    }

    public ArrayList<BankReceipt> getDeposits() {
        ArrayList<BankReceipt> deposits = new ArrayList<>();
        for (BankReceipt bankReceipt:bankReceipts) {
            if (bankReceipt.getBankReceiptType().equals(BankReceiptType.DEPOSIT))
                deposits.add(bankReceipt);
        }
        return deposits;
    }

    public ArrayList<BankReceipt> getWithdraws() {
        ArrayList<BankReceipt> withdraws = new ArrayList<>();
        for (BankReceipt bankReceipt:bankReceipts) {
            if (bankReceipt.getBankReceiptType().equals(BankReceiptType.WITHDRAW))
                withdraws.add(bankReceipt);
        }
        return withdraws;
    }

    public ArrayList<BankReceipt> getMoves() {
        ArrayList<BankReceipt> moves = new ArrayList<>();
        for (BankReceipt bankReceipt:bankReceipts) {
            if (bankReceipt.getBankReceiptType().equals(BankReceiptType.MOVE))
                moves.add(bankReceipt);
        }
        return moves;
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

    public double getMoney() {
        return money;
    }

    public void increaseMoney(double moneyIncrease) {
        money+=moneyIncrease;
    }

    public void decreaseMoney(double moneyDecrease) {
        money-=moneyDecrease;
    }
}
