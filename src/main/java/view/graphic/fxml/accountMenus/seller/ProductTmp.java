package view.graphic.fxml.accountMenus.seller;

import javafx.beans.property.SimpleStringProperty;

public class ProductTmp {
    private SimpleStringProperty productName;
    private SimpleStringProperty id;

    public ProductTmp(String productName, int id) {
        this.productName = new SimpleStringProperty(productName);
        this.id = new SimpleStringProperty(Integer.toString(id));
    }

    public String getProductName() {
        return productName.get();
    }

    public String getId() {
        return id.get();
    }
}
