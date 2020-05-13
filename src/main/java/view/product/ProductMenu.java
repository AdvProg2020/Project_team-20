package view.product;

import controller.product.ProductController;
import model.account.Seller;
import model.product.Category;
import model.product.Product;
import model.product.Sale;
import view.Menu;

public class ProductMenu extends Menu {
    ProductController productController;
    Product product;
    String sellerId;

    public ProductMenu(Product product) {
        super("ProductMenu");
        productController = new ProductController(product);
        this.methods.add("digest");
        this.methods.add("addProductToCart");
        this.methods.add("selectSeller");
        this.methods.add("attributes");
        this.regex.add("digest");
        this.regex.add("add to cart");
        this.regex.add("select seller (\\S+)");
        this.regex.add("attributes");

    }

    public void digest() {
        System.out.println("name : " + product.getName());
        System.out.println("description : " + product.getDescription());
        for (Seller seller : product.getSellers()) {
            Sale sale = seller.getSaleWithProduct(product);
            System.out.print("seller : " + seller.getName() + " seller username : " + seller.getUsername()
                    + " price : " + product.getPrice(seller) + " count : " + product.getCount(seller));
            if (sale != null)
                System.out.println("sale start date : " + sale.getStartDate() + " sale end date"
                        + sale.getEndDate() + " sale percentage " + sale.getSalePercentage());
            else System.out.println();
        }
        System.out.println("average score : " + product.getAverage());
    }

    public void addProductToCart() {
        try {
            productController.addProductToCart(getSellerId());
            System.out.println("addition was successful");
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
    }

    public void attributes() {
        for (Category category : product.getCategories()) {

        }
    }

    public void selectSeller(String sellerId) {
        setSellerId(sellerId);
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerId() {
        return sellerId;
    }
}
