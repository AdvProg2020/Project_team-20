package client.view.graphic.fxml.bank;

import client.controller.bank.BankController;
import client.view.graphic.alert.AlertController;
import client.view.graphic.alert.AlertType;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;

public class PayFxml {
    public JFXTextField receiptID;
    private BankController bankController = BankController.getInstance();

    public void handlePay(ActionEvent actionEvent) {
        try {
            bankController.handlePay(receiptID.getText());
            new AlertController().create(AlertType.CONFIRMATION, "");
            receiptID.setText("");
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }
}
