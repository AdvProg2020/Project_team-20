package client.model.account;

import client.model.GraphicPackage;

public abstract class GeneralAccount {
    private GeneralAccountType generalAccountType;
    protected GraphicPackage graphicPackage;

    public GeneralAccount(GeneralAccountType generalAccountType) {
        this.generalAccountType = generalAccountType;
        this.graphicPackage = new GraphicPackage();
    }

    public GeneralAccountType getGeneralAccountType() {
        return generalAccountType;
    }

    public GraphicPackage getGraphicPackage() {
        return graphicPackage;
    }
}
