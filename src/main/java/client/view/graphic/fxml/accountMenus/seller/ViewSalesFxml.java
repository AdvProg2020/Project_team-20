package client.view.graphic.fxml.accountMenus.seller;

import client.controller.MediaController;
import client.controller.account.user.SellerController;
import client.controller.product.ReceiptController;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import client.model.product.Product;
import client.model.receipt.SellerReceipt;
import client.view.graphic.ProgramApplication;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ViewSalesFxml implements Initializable {

    public TableView<SellerReceipt> table;
    public TableColumn<String, SellerReceipt> orderId;
    public Text receivedMoneyTxt;
    public Text discountPercentageTxt;
    public Text timeTxt;
    public Text idTxt;
    public Text offTxt;
    public Text buyerTxt;
    public TableView<ProductQuantity> productsTable;
    public TableColumn<String, Product> productsColumn;
    public TableColumn<String, Integer> quantityColumn;
    private SellerController sellerController = SellerController.getInstance();
    MediaController mediaController = ProgramApplication.getMediaController();

    public void selectOrder(MouseEvent event) {
        SellerReceipt sellerReceipt = table.getSelectionModel().getSelectedItem();
        receivedMoneyTxt.setText(Double.toString(sellerReceipt.getReceivedMoney()));
        discountPercentageTxt.setText(sellerReceipt.getDiscountPercentage() * 100 + "%");
        timeTxt.setText(sellerReceipt.getDateAndTime().getDayOfMonth() + "/" + sellerReceipt.getDateAndTime().getMonth() + "/" + sellerReceipt.getDateAndTime().getYear());
        offTxt.setText(Double.toString(sellerReceipt.getDiscountAmount()));
        idTxt.setText(sellerReceipt.getId());
        buyerTxt.setText(sellerReceipt.getBuyer().getUsername());
        showProducts(sellerReceipt);
    }

    public void showProducts(SellerReceipt sellerReceipt) {
        ReceiptController receiptController = new ReceiptController();
        HashMap<Product, Integer> productsCount = receiptController.getProducts(sellerReceipt);
        ArrayList<ProductQuantity> productQuantities = new ArrayList<>();
        for (Product product : productsCount.keySet()) {
            productQuantities.add(new ProductQuantity(product.getName(), productsCount.get(product)));
        }
        productsTable.getItems().setAll(productQuantities);
        productsColumn.setCellValueFactory(new PropertyValueFactory<>("product"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        table.getItems().setAll(sellerController.viewSalesHistory());
        orderId.setCellValueFactory(new PropertyValueFactory<>("id"));
    }
}
