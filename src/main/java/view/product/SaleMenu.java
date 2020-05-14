package view.product;

import controller.product.filter.SaleController;
import model.account.Seller;
import model.product.Product;
import model.product.Sale;
import view.Menu;
import view.account.BuyerMenu;

import java.util.ArrayList;

public class SaleMenu extends Menu {
    SaleController saleController;

    private static SaleMenu saleMenu = null;

    public SaleMenu() {
        super("SaleMenu");
        setRegex();
        setMethods();
        saleController = new SaleController();
    }

    public void showProduct(String id) {
        try {
            Product product = Product.getProductById(id);
            ProductMenu productMenu = new ProductMenu(product);
            enter(productMenu);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void showOffs() {
        ArrayList<Sale> sales = saleController.getAllSales();
        System.out.println("Name                Price                Off                 Id\n");
        for (Sale sale:sales) {
            for (Product product:sale.getProducts()) {
                System.out.format("%s%20s%40f%60s", product.getName(), product.getPrice(),
                        sale.getSalePercentage(), product.getId());
            }
        }
    }

    private void setRegex() {
        regex.add("show product (\\w+)");
    }

    private void setMethods() {
        methods.add("showProduct");
    }

    public static SaleMenu getInstance() {
        if (saleMenu == null)
            saleMenu = new SaleMenu();
        return saleMenu;
    }
}
