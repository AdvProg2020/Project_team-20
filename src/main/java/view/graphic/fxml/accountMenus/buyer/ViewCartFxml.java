package view.graphic.fxml.accountMenus.buyer;

import controller.account.user.BuyerController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import model.product.Cart;
import model.product.Product;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ViewCartFxml implements Initializable {

    public TableView<CartTmp> mainTable;
    public TableColumn<CartTmp, String> productColumn;
    public TableColumn<CartTmp, String> priceColumn;
    public TableColumn<CartTmp, String> quantityColumn;
    public TableColumn<CartTmp, String> totalColumn;
    public ImageView plusIcon;
    public ImageView recycleBinIcon;
    public ImageView minusIcon;
    public Button minusButton;
    public Button plusButton;
    public Button recycleBinButton;
    public Text totalPrice;
    private String id;
    private Cart cart;
    private Double totalPrice2;
    public static BorderPane borderPane;

    private ArrayList<CartTmp> convertToCartTmp() throws Exception {
        BuyerController buyerController = BuyerController.getInstance();
        cart = buyerController.viewCart();
        totalPrice2 = buyerController.getTotalPrice();
        HashMap<Product, Integer> products = cart.getAllProducts();
        ArrayList<CartTmp> cartTmpS = new ArrayList<>();
        for (Product product : products.keySet()) {
            String name = product.getName();
            double price = product.getPrice(cart.getSelectedProductByProductId(product.getId()).getSeller());
            int quantity = products.get(product);
            double total = quantity * price;
            CartTmp cartTmp = new CartTmp(name, price, quantity, total);
            cartTmpS.add(cartTmp);
        }
        return cartTmpS;
    }

    private ObservableList<CartTmp> getCartTmpS() throws Exception {
        ObservableList<CartTmp> cartTmpS = FXCollections.observableArrayList();
        ArrayList<CartTmp> cartTmpS2 = convertToCartTmp();
        cartTmpS.addAll(cartTmpS2);
        return cartTmpS;
    }

    public static void setBorderPane(BorderPane borderPane2) {
        borderPane = borderPane2;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        minusIcon.setOpacity(0);
        recycleBinIcon.setOpacity(0);
        plusIcon.setOpacity(0);
        productColumn.setCellValueFactory(new PropertyValueFactory<CartTmp, String>("productName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<CartTmp, String>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<CartTmp, String>("quantity"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<CartTmp, String>("total"));
        try {
            mainTable.setItems(getCartTmpS());
            totalPrice.setText("");
            totalPrice.setText(Double.toString(totalPrice2));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    public void selectProduct(MouseEvent mouseEvent) throws Exception {
        String name = mainTable.getSelectionModel().getSelectedItem().getProductName();
        id = Product.getProductWithItsName(name).getId();
        minusIcon.setOpacity(1);
        recycleBinIcon.setOpacity(1);
        plusIcon.setOpacity(1);
    }

    public void handlePlus(ActionEvent actionEvent) throws Exception {
        cart.increaseProduct(id , 1);
        minusIcon.setOpacity(0);
        recycleBinIcon.setOpacity(0);
        plusIcon.setOpacity(0);
        id = null;

        productColumn.setCellValueFactory(new PropertyValueFactory<CartTmp, String>("productName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<CartTmp, String>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<CartTmp, String>("quantity"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<CartTmp, String>("total"));
        try {
            mainTable.setItems(getCartTmpS());
            totalPrice.setText("");
            totalPrice.setText(Double.toString(totalPrice2));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void handleMinus(ActionEvent actionEvent) throws Exception {
        cart.decreaseProduct(id , 1);
        minusIcon.setOpacity(0);
        recycleBinIcon.setOpacity(0);
        plusIcon.setOpacity(0);
        id = null;

        productColumn.setCellValueFactory(new PropertyValueFactory<CartTmp, String>("productName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<CartTmp, String>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<CartTmp, String>("quantity"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<CartTmp, String>("total"));
        try {
            mainTable.setItems(getCartTmpS());
            totalPrice.setText("");
            totalPrice.setText(Double.toString(totalPrice2));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void handleDelete(ActionEvent actionEvent) throws Exception {
        HashMap<Product, Integer> products = cart.getAllProducts();
        int quantity = products.get(Product.getProductById(id));
        cart.decreaseProduct(id , quantity);
        minusIcon.setOpacity(0);
        recycleBinIcon.setOpacity(0);
        plusIcon.setOpacity(0);
        id = null;

        productColumn.setCellValueFactory(new PropertyValueFactory<CartTmp, String>("productName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<CartTmp, String>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<CartTmp, String>("quantity"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<CartTmp, String>("total"));
        try {
            mainTable.setItems(getCartTmpS());
            totalPrice.setText("");
            totalPrice.setText(Double.toString(totalPrice2));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void handlePay(ActionEvent actionEvent) {
        Parent root;
        try {
            PurchaseMenu.setBorderPane(borderPane);
            root = FXMLLoader.load(new File("src/main/java/view/graphic/fxml/accountMenus/buyer/" + "purchaseMenu" + "Fxml" + ".fxml").toURI().toURL());
            borderPane.setCenter(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
