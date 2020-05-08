package model.account;

public abstract class GeneralAccount {
    private AccountType accountType;

    public GeneralAccount(AccountType accountType) {
        this.accountType = accountType;
    }

    public AccountType getAccountType() {
        return accountType;
    }
}
