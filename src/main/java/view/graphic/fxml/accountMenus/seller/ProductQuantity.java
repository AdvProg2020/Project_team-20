package view.graphic.fxml.accountMenus.seller;

public class ProductQuantity {
    private String product;
    private int count;

    public ProductQuantity(String product, int count) {
        this.product = product;
        this.count = count;
    }

    public String getProduct() {
        return product;
    }

    public int getCount() {
        return count;
    }
}
