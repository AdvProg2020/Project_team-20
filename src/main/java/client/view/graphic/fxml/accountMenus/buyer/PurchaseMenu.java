package client.view.graphic.fxml.accountMenus.buyer;

import client.controller.MediaController;
import client.controller.account.user.BuyerController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import client.model.product.Cart;
import client.view.graphic.ProgramApplication;
import client.view.graphic.alert.AlertController;
import client.view.graphic.alert.AlertType;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class PurchaseMenu implements Initializable {
    public Text addressText;
    public TextField address;
    public Text phoneText;
    public TextField phone;
    public Button submitPhone;
    public Button submitAddress;
    public Text totalPriceText;
    public Text discountText;
    public TextField totalPrice;
    public TextField discount;
    public Button payButton;
    public Button submitDiscount;
    public TextField discountCode;
    public Text discountCodeText;

    public static BorderPane borderPane;

    private Boolean submit;
    private String address2;
    private String phone2;
    private String discountCode2;
    MediaController mediaController = ProgramApplication.getMediaController();

    public void handlePay(ActionEvent actionEvent) {
        if (submit) {
            BuyerController buyerController = BuyerController.getInstance();
            try {
                buyerController.purchase(address2, phone2, discountCode2);
                new AlertController().create(AlertType.CONFIRMATION, "Thanks for buying");
                phoneText.setOpacity(0);
                phone.setOpacity(0);
                submitPhone.setOpacity(0);
                discountCodeText.setOpacity(0);
                discountCode.setOpacity(0);
                submitDiscount.setOpacity(0);
                totalPrice.setText("");
                discount.setText("");
                address.setText("");
                submit = false;
            } catch (Exception e) {
                new AlertController().create(AlertType.ERROR, e.getMessage());
            }
        } else {
            new AlertController().create(AlertType.ERROR, "please fill all of the boxes");
        }
    }

    public static void setBorderPane(BorderPane borderPane) {
        PurchaseMenu.borderPane = borderPane;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        phoneText.setOpacity(0);
        phone.setOpacity(0);
        submitPhone.setOpacity(0);
        discountCodeText.setOpacity(0);
        discountCode.setOpacity(0);
        submitDiscount.setOpacity(0);
        submit = false;
        BuyerController buyerController = BuyerController.getInstance();
        Cart cart = buyerController.viewCart();
        discount.appendText(Double.toString(buyerController.getDiscount()));
        totalPrice.appendText(Double.toString(buyerController.getTotalPrice()));
    }


    public void handlePhone(ActionEvent actionEvent) {
        phone2 = phone.getText();
        discountCodeText.setOpacity(1);
        discountCode.setOpacity(1);
        submitDiscount.setOpacity(1);
    }

    public void handleAddress(ActionEvent actionEvent) {
        address2 = address.getText();
        phoneText.setOpacity(1);
        phone.setOpacity(1);
        submitPhone.setOpacity(1);
    }

    public void handleDiscount(ActionEvent actionEvent) {
        discountCode2 = discountCode.getText();
        submit = true;
    }

    public void handleReject(ActionEvent actionEvent) {
        BuyerController buyerController = BuyerController.getInstance();
        buyerController.viewCart().resetCart();
        Parent root;
        try {
            root = FXMLLoader.load(new File("src/main/java/client/view/graphic/fxml/accountMenus/buyer/" + "purchaseMenu" + "Fxml" + ".fxml").toURI().toURL());
            borderPane.setCenter(root);
        } catch (Exception e) {
            e.printStackTrace();
            ;
        }
    }
}
