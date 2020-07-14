package client.view.graphic.fxml.bank;

import client.controller.bank.BankController;
import client.view.graphic.alert.AlertController;
import client.view.graphic.alert.AlertType;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;

public class DepositFxml {
    public JFXTextArea descriptionTxt;
    public JFXTextField moneyTxt;
    public JFXTextField destTxt;
    private BankController bankController = BankController.getInstance();

    public void handleCreate(ActionEvent actionEvent) {
        try {
            bankController.handleCreateDeposit(moneyTxt.getText(), descriptionTxt.getText(), destTxt.getText());
            new AlertController().create(AlertType.CONFIRMATION, "");
            descriptionTxt.setText("");
            moneyTxt.setText("");
            destTxt.setText("");
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }
}
