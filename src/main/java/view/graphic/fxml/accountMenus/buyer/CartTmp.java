package view.graphic.fxml.accountMenus.buyer;

import javafx.beans.DefaultProperty;
import javafx.beans.property.SimpleStringProperty;

public class CartTmp {
    private SimpleStringProperty productName;
    private SimpleStringProperty price;
    private SimpleStringProperty quantity;
    private SimpleStringProperty total;

    public CartTmp(String productName, double price , int quantity , double total) {
        this.productName = new SimpleStringProperty(productName);
        this.price = new SimpleStringProperty(Double.toString(price));
        this.quantity = new SimpleStringProperty(Integer.toString(quantity));
        this.total = new SimpleStringProperty(Double.toString(total));
    }

    public String getProductName() {
        return productName.get();
    }

    public String getPrice() {
        return price.get();
    }

    public String getQuantity(){
        return quantity.get();
    }

    public String getTotal(){
        return total.get();
    }
}
