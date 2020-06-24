package view.graphic.fxml.accountMenus.buyer;

import controller.account.user.BuyerController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.product.Cart;
import view.graphic.alert.AlertController;
import view.graphic.alert.AlertType;

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

    private Boolean submit;
    private String address2;
    private String phone2;

    public void handlePay(ActionEvent actionEvent) {
        if(submit){

        }
        else{
            new AlertController().create(AlertType.ERROR, "please fill all of the boxes");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        phoneText.setOpacity(0);
        phone.setOpacity(0);
        submitPhone.setOpacity(0);
        submit = false;

        BuyerController buyerController = BuyerController.getInstance();
        Cart cart = buyerController.viewCart();
        discount.appendText(Double.toString(buyerController.getDiscount()));
        totalPrice.appendText(Double.toString(buyerController.getTotalPrice()));
    }


    public void handlePhone(ActionEvent actionEvent) {
        phone2 = phone.getText();
        submit = true;
    }

    public void handleAddress(ActionEvent actionEvent) {
        address2 = address.getText();
        phoneText.setOpacity(1);
        phone.setOpacity(1);
        submitPhone.setOpacity(1);
    }
}
