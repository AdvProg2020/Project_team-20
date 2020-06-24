package view.graphic.fxml.accountMenus.buyer;

import controller.account.user.BuyerController;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import model.account.Seller;
import model.product.Product;
import model.receipt.BuyerReceipt;
import view.graphic.fxml.accountMenus.seller.ProductQuantity;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ViewOrderFxml implements Initializable {
    public TableView<BuyerReceipt> table;
    public TextArea message;
    public Text title;
    public TableColumn<String, BuyerReceipt>  orderId;
    public Text paidMoneyTxt;
    public Text discountTxt;
    public Text timeTxt;
    public Text idTxt;
    public TableView<ProductQuantity> productsTable;
    public TableColumn<String, Product> productsColumn;
    public TableView<Seller> sellersTable;
    public TableColumn<String, Seller> sellersColumn;
    public TableColumn<String, Integer> quantityColumn;
    private BuyerController buyerController = BuyerController.getInstance();


    public void selectOrder(MouseEvent event) {
        BuyerReceipt buyerReceipt = table.getSelectionModel().getSelectedItem();
        paidMoneyTxt.setText(Double.toString(buyerReceipt.getPaidMoney()));
        discountTxt.setText(buyerReceipt.getDiscountPercentage() * 100 + "%");
        timeTxt.setText(buyerReceipt.getDateAndTime().getDayOfMonth() + "/" + buyerReceipt.getDateAndTime().getMonth() + "/" + buyerReceipt.getDateAndTime().getYear());
        idTxt.setText(buyerReceipt.getId());
        showProducts(buyerReceipt);
    }

    public void showProducts(BuyerReceipt buyerReceipt) {
        HashMap<Product, Integer> productsCount = buyerReceipt.getProducts();
        ArrayList<ProductQuantity> productQuantities = new ArrayList<>();
        for (Product product:productsCount.keySet()) {
            productQuantities.add(new ProductQuantity(product.getName(), productsCount.get(product)));
        }
        productsTable.getItems().setAll(productQuantities);
        productsColumn.setCellValueFactory(new PropertyValueFactory<>("product"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (BuyerReceipt buyerReceipt:buyerController.viewOrders())
            System.out.println(buyerReceipt.getId());
        table.getItems().setAll(buyerController.viewOrders());
        orderId.setCellValueFactory(new PropertyValueFactory<>("id"));
    }
}
