package client.view.graphic.fxml.accountMenus.seller;

import com.jfoenix.controls.JFXTextField;
import client.controller.MediaController;
import client.controller.account.user.SellerController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import client.model.account.Seller;
import client.model.product.Product;
import client.view.graphic.ProgramApplication;
import client.view.graphic.alert.AlertController;
import client.view.graphic.alert.AlertType;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateAdvertisementFxml implements Initializable {
    public JFXTextField text;
    public TableView<Product> table;
    public TableColumn<String, Product> productNames;
    public JFXTextField productName;
    private SellerController sellerController = SellerController.getInstance();
    private Seller seller = (Seller) sellerController.getAccountInfo();
    private Product product;

    MediaController mediaController = ProgramApplication.getMediaController();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SellerController sellerController = SellerController.getInstance();
        table.getItems().setAll(sellerController.getSellerProducts());
        productNames.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    public void selectRequest(MouseEvent event) {
        product = table.getSelectionModel().getSelectedItem();
        productName.setText(product.getName());
    }

    public void buy(ActionEvent actionEvent) {
        String txt = text.getText();
        if (product==null || txt.equals("")) {
                new AlertController().create(AlertType.ERROR, "Fill all of the fields");
                return;
        }

        try {
            sellerController.addAdvertisement(product, seller, txt);
            new AlertController().create(AlertType.CONFIRMATION, "It will be added soon!");
            product = null;
            productName.setText("");
            text.setText("");
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }
}
