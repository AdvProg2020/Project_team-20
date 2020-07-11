package server.model.bank;

public class BankReceipt {
    private BankReceiptType bankReceiptType;
    private double money;
    private String sourceID;
    private String destID;
    private String description;

    public BankReceipt(BankReceiptType bankReceiptType, double money, String sourceID, String destID, String description) {
        this.bankReceiptType = bankReceiptType;
        this.money = money;
        this.sourceID = sourceID;
        this.destID = destID;
        this.description = description;
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
}
