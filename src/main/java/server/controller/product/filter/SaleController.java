package server.controller.product.filter;

import server.controller.MainController;
import client.model.product.Sale;

import java.util.ArrayList;

public class SaleController extends Filterable {
    private String sortElement;
    private MainController mainController;

    public SaleController() {
        super(200);
        this.mainController = MainController.getInstance();
    }

    public ArrayList<Sale> getAllSales() {
        return Sale.getAllSales();
    }

}
