package model.account;

import javafx.scene.image.Image;
import model.GraphicPackage;

import java.io.File;

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
