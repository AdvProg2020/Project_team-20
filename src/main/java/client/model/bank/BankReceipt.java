package client.model.bank;

public class BankReceipt {
    private static int bankReceiptCount = 0;
    private BankReceiptType bankReceiptType;
    private double money;
    private String sourceID;
    private String destID;
    private String description;
    private String ID;
    private boolean receiptState;

    public BankReceipt(BankReceiptType bankReceiptType, double money, String sourceID, String destID, String description) {
        this.bankReceiptType = bankReceiptType;
        this.money = money;
        this.sourceID = sourceID;
        this.destID = destID;
        this.description = description;
        this.ID = Integer.toString(bankReceiptCount);
        bankReceiptCount++;
        receiptState = false;
    }

    public BankReceiptType getBankReceiptType() {
        return bankReceiptType;
    }

    public double getMoney() {
        return money;
    }

    public String getSourceID() {
        return sourceID;
    }

    public String getDestID() {
        return destID;
    }

    public String getDescription() {
        return description;
    }

    public boolean wasPaid() {
        return receiptState;
    }

    public void setReceiptState(boolean receiptState) {
        this.receiptState = receiptState;
    }

    public String getID() {
        return ID;
    }
}
