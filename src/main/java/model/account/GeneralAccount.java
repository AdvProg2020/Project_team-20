package model.account;

public abstract class GeneralAccount {
    private GeneralAccountType generalAccountType;

    public GeneralAccount(GeneralAccountType generalAccountType) {
        this.generalAccountType = generalAccountType;
    }

    public GeneralAccountType getGeneralAccountType() {
        return generalAccountType;
    }
}
