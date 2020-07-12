package client.view.graphic.fxml.bank;

import client.controller.bank.BankController;
import client.view.graphic.alert.AlertController;
import client.view.graphic.alert.AlertType;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;

public class BankControllerFxml {

    public TextField usernameTxt;
    public PasswordField newPasswordTxt;
    public JFXButton loginButton;
    public JFXButton newUserButton;
    public ImageView newUserPanel;
    public TextField passwordRepeatTxt;
    public TextField nameTxt;
    public TextField lastNameTxt;
    private BankController bankController = BankController.getInstance();
    private boolean loginBtnMode = true;

    public void handleLogin(ActionEvent actionEvent) {
        newUserPanel.setOpacity(0);
        passwordRepeatTxt.setOpacity(0);
        nameTxt.setOpacity(0);
        lastNameTxt.setOpacity(0);
        loginButton.setStyle("-fx-background-color: #ddf1ee;");
        loginButton.setText("Login");
        loginBtnMode = true;
        newUserButton.setStyle("-fx-background-color: #b3c9c9;");
    }

    public void handleNewUser(ActionEvent actionEvent) {
        newUserPanel.setOpacity(1);
        passwordRepeatTxt.setOpacity(1);
        nameTxt.setOpacity(1);
        lastNameTxt.setOpacity(1);
        loginButton.setStyle("-fx-background-color: #b3c9c9;");
        loginButton.setText("Create");
        loginBtnMode = false;
        newUserButton.setStyle("-fx-background-color: #ddf1ee;");
    }

    public void handleLoginBtn(ActionEvent actionEvent) {
        if (loginBtnMode)
            handleLogin();
        else
            handleCreateUser();
        usernameTxt.setText("");
        passwordRepeatTxt.setText("");
        newPasswordTxt.setText("");
        nameTxt.setText("");
        lastNameTxt.setText("");
    }

    private void handleLogin() {
        String username = usernameTxt.getText();
        String password = newPasswordTxt.getText();
        try {
            bankController.login(username, password);
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }

    private void handleCreateUser() {
        String username = usernameTxt.getText();
        String password = newPasswordTxt.getText();
        String name = nameTxt.getText();
        String repeatPass = passwordRepeatTxt.getText();
        String lastName = lastNameTxt.getText();
        try {
            String accountNumber = bankController.createUser(username, password, name, lastName, repeatPass);
            new AlertController().create(AlertType.INFO, "Thanks for creating account in our bank. Your account number is: " + accountNumber);
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }


}
