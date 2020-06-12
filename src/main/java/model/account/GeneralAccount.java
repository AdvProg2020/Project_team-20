package model.account;

import javafx.scene.image.Image;
import model.GraphicPackage;

public abstract class GeneralAccount {
    private GeneralAccountType generalAccountType;
    protected GraphicPackage graphicPackage;

    public GeneralAccount(GeneralAccountType generalAccountType) {
        this.generalAccountType = generalAccountType;
        this.graphicPackage = new GraphicPackage();
        // check this
        graphicPackage.setMainImage(new Image("src/main/resources/Images/ProfileImg.png"));
    }

    public GeneralAccountType getGeneralAccountType() {
        return generalAccountType;
    }

    public GraphicPackage getGraphicPackage() {
        return graphicPackage;
    }
}
