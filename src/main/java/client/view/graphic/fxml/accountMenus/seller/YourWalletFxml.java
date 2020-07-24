package client.view.graphic.fxml.accountMenus.seller;

import client.controller.account.user.BuyerController;
import client.controller.account.user.seller.SellerController;
import client.model.account.Seller;
import client.view.graphic.alert.AlertController;
import client.view.graphic.alert.AlertType;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class YourWalletFxml implements Initializable {

    public TextField amountOfMoney;
    public TextField bankPassword;
    public TextField bankUsername;
    public TextField bankUsername1;
    public TextField bankPassword1;
    public TextField amountOfMoney1;
    public TextField accountId;
    public TextField accountId1;
    public Text moneyInWallet;

    private SellerController sellerController = SellerController.getInstance();
    client.model.account.Seller seller = (Seller) sellerController.getAccountInfo();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        double money = seller.getCredit();//we should change credit to wallet
        moneyInWallet.setText(Double.toString(money));
    }

    public void chargeWallet(ActionEvent actionEvent) {
        String username = bankUsername.getText();
        String password = bankPassword.getText();
        String money = amountOfMoney.getText();
        String sourceId = accountId.getText();
        if (username != null && password != null && money != null && sourceId !=null) {
            sellerController.chargeWallet(Double.parseDouble(money), username, password , sourceId);
        } else {
            new AlertController().create(AlertType.ERROR, "please fill all of the boxes");
        }
        bankPassword.clear();
        bankUsername.clear();
        amountOfMoney.clear();
        accountId.clear();
    }

    public void withdrawMoney(ActionEvent actionEvent) {
        String username = bankUsername1.getText();
        String password = bankPassword1.getText();
        String money = amountOfMoney1.getText();
        String sourceId = accountId1.getText();
        if (username != null && password != null && money != null) {
            try {
                sellerController.withdrawMoneyFromWallet(Double.parseDouble(money), username, password, sourceId);
            }
            catch (Exception e){
                new AlertController().create(AlertType.ERROR, e.getMessage());
            }
        } else {
            new AlertController().create(AlertType.ERROR, "please fill all of the boxes");
        }
        bankPassword1.clear();
        bankUsername1.clear();
        amountOfMoney1.clear();
        accountId1.clear();
    }
}
