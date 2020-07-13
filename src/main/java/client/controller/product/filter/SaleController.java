package client.controller.product.filter;

import client.controller.MainController;
import client.model.product.Product;
import client.model.product.Sale;
import client.network.Client;
import client.network.Message;

import java.util.ArrayList;

public class SaleController extends Filterable {
    private String sortElement;
    private MainController mainController;

    public SaleController() {
        this.mainController = MainController.getInstance();
        client = new Client(2000);
        client.readMessage();
    }

    public ArrayList<Sale> getAllSales() {
        Message message = new Message("getAllSales");
        client.writeMessage(message);
        Message answer = client.readMessage();
        return (ArrayList<Sale>) answer.getObjects().get(0);
    }

    public ArrayList<Product> getSaleProducts(Sale sale) {
        Message message = new Message("getSaleProducts");
        message.addToObjects(sale);
        client.writeMessage(message);
        return (ArrayList<Product>) client.readMessage().getObjects().get(0);
    }

}
