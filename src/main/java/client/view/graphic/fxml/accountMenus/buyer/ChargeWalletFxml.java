package client.view.graphic.fxml.accountMenus.buyer;

import client.controller.account.user.BuyerController;
import client.view.graphic.alert.AlertController;
import client.view.graphic.alert.AlertType;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ChargeWalletFxml implements Initializable {


    public TextField amountOfMoney;
    public TextField bankPassword;
    public TextField bankUsername;
    public TextField accountId;
    public Text moneyInWallet;

    private BuyerController buyerController = BuyerController.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        double money = buyerController.getCurrentBuyer().getCredit();//we should change credit to wallet
        moneyInWallet.setText(Double.toString(money));
    }

    public void chargeWallet(ActionEvent actionEvent) {
        String username = bankUsername.getText();
        String password = bankPassword.getText();
        String money = amountOfMoney.getText();
        String sourceID = accountId.getText();
        if(username!=null && password!=null && money!=null && sourceID!=null){
            try {
                buyerController.ChargeWallet(Double.parseDouble(money),username,password,sourceID);
            }
            catch (Exception e){
                new AlertController().create(AlertType.ERROR, e.getMessage());
            }
        }
        else {
            new AlertController().create(AlertType.ERROR, "please fill all of the boxes");
        }
        double money2 = buyerController.getCurrentBuyer().getCredit();
        moneyInWallet.setText(Double.toString(money2));
        bankPassword.clear();
        bankUsername.clear();
        amountOfMoney.clear();
        accountId.clear();
    }
}
