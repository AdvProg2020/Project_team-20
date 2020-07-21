package client.view.graphic.fxml.accountMenus.seller;

import client.controller.account.user.BuyerController;
import client.controller.account.user.seller.SellerController;
import client.model.account.Seller;
import client.view.graphic.alert.AlertController;
import client.view.graphic.alert.AlertType;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class YourWalletFxml implements Initializable {
    public TextField moneyInWallet;
    public TextField amountOfMoney;
    public TextField bankPassword;
    public TextField bankUsername;
    public TextField bankUsername1;
    public TextField bankPassword1;
    public TextField amountOfMoney1;

    private SellerController sellerController = SellerController.getInstance();
    client.model.account.Seller seller = (Seller) sellerController.getAccountInfo();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        double money = seller.getCredit();//we should change credit to wallet
        moneyInWallet.appendText(Double.toString(money));
    }

    public void chargeWallet(ActionEvent actionEvent) {
        String username = bankUsername.getText();
        String password = bankPassword.getText();
        String money = amountOfMoney.getText();
        if (username != null && password != null && money != null) {
            sellerController.chargeWallet(Double.parseDouble(money), username, password);
        } else {
            new AlertController().create(AlertType.ERROR, "please fill all of the boxes");
        }
        bankPassword.clear();
        bankUsername.clear();
        amountOfMoney.clear();
    }

    public void withdrawMoney(ActionEvent actionEvent) {
        String username = bankUsername1.getText();
        String password = bankPassword1.getText();
        String money = amountOfMoney1.getText();
        if (username != null && password != null && money != null) {
            sellerController.withdrawMoneyFromWallet(Double.parseDouble(money), username, password);
        } else {
            new AlertController().create(AlertType.ERROR, "please fill all of the boxes");
        }
        bankPassword1.clear();
        bankUsername1.clear();
        amountOfMoney1.clear();
    }
}
