package controller.product.filter;

import controller.MainController;
import model.product.Sale;

import java.util.ArrayList;

public class SaleController extends Filterable {
    private String sortElement;
    private MainController mainController;

    public SaleController() {
        this.mainController = MainController.getInstance();
    }

    public ArrayList<Sale> getAllSales() {
        return Sale.getAllSales();
    }
}
