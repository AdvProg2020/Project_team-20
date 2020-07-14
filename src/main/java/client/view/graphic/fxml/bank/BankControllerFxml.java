package client.view.graphic.fxml.bank;

import client.controller.Main;
import client.controller.bank.BankController;
import client.view.graphic.MenuNames;
import client.view.graphic.ProgramApplication;
import client.view.graphic.alert.AlertController;
import client.view.graphic.alert.AlertType;
import client.view.graphic.fxml.allProductsMenu.FxmlAllProductsMenu;
import com.jfoenix.controls.JFXButton;
import com.sun.tools.hat.internal.model.Root;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;

public class BankControllerFxml {

    public TextField usernameTxt;
    public PasswordField newPasswordTxt;
    public JFXButton loginButton;
    public JFXButton newUserButton;
    public ImageView newUserPanel;
    public TextField passwordRepeatTxt;
    public TextField nameTxt;
    public TextField lastNameTxt;
    public BorderPane borderPane;
    private BankController bankController = BankController.getInstance();
    private boolean loginBtnMode = true;
    private static Stage window;

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
            handleLoginMenu();
        else
            handleCreateUser();
        usernameTxt.setText("");
        passwordRepeatTxt.setText("");
        newPasswordTxt.setText("");
        nameTxt.setText("");
        lastNameTxt.setText("");
    }

    private void handleLoginMenu() {
        String username = usernameTxt.getText();
        String password = newPasswordTxt.getText();
        try {
            bankController.login(username, password);
            Parent root = FXMLLoader.load(new File("src/main/java/client/view/graphic/fxml/bank/BankAccountFxml.fxml").toURI().toURL());
            window.setScene(new Scene(root, 994, 666));
        } catch (Exception e) {
            e.printStackTrace();
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


    public void handleExit(ActionEvent actionEvent) {
        FxmlAllProductsMenu.key = false;
        Main.storeData();
        window.close();
    }

    public void loadUI(String ui) {
        Parent root;
        try {
            root = FXMLLoader.load(new File("src/main/java/client/view/graphic/fxml/bank/" + ui + "Fxml" + ".fxml").toURI().toURL());
            borderPane.setCenter(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleErpShop(ActionEvent actionEvent) {
        ProgramApplication.setMenu(MenuNames.MAINMENU);
    }

    public static void setWindow(Stage window) {
        BankControllerFxml.window = window;
    }

    public void handleTransitionBalance(ActionEvent actionEvent) {
        loadUI("Transitions");
    }

    public void handleDeposit(ActionEvent actionEvent) {
        loadUI("Deposit");
    }

    public void handleWithdraw(ActionEvent actionEvent) {
        loadUI("Withdraw");
    }

    public void handleMove(ActionEvent actionEvent) {
        loadUI("Move");
    }

    public void handlePay(ActionEvent actionEvent) {
        loadUI("Pay");
    }

    public void handleLogout(ActionEvent actionEvent) {
        bankController.logout();
        ProgramApplication.setMenu(MenuNames.ERP_BANK);
    }
}
