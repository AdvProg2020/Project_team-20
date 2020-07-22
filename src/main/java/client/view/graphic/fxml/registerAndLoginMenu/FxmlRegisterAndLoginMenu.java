package client.view.graphic.fxml.registerAndLoginMenu;

import client.controller.Main;
import client.controller.MediaController;
import client.controller.account.LoginController;
import client.controller.account.user.SupporterController;
import client.view.graphic.fxml.accountMenus.supporter.SupporterMenuFxml;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import client.model.account.AccountType;
import client.view.graphic.ProgramApplication;
import client.view.graphic.alert.AlertController;
import client.view.graphic.alert.AlertType;
import client.view.graphic.fxml.accountMenus.buyer.BuyerMenuController;
import client.view.graphic.fxml.accountMenus.manager.ManagerMenuController;
import client.view.graphic.fxml.accountMenus.seller.SellerMenuController;
import client.view.graphic.fxml.allProductsMenu.FxmlAllProductsMenu;

import java.io.File;
import java.util.ArrayList;

public class FxmlRegisterAndLoginMenu {

    public TextField usernameText;
    public TextField passwordText;
    private static Stage window;
    private static String signUpMode = "b";
    public TextField newUsername;
    public PasswordField newPassword;
    public TextField newEmail;
    public TextField newName;
    public TextField newLastName;
    public TextField newCompanyInfo;
    public TextField newPhoneNumber;
    MediaController mediaController = ProgramApplication.getMediaController();

    public void enterMouse(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: #14b5a8; -fx-background-radius: 10");
    }

    public void exitMouse(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: #02f5e1; -fx-background-radius: 10");
    }

    public void enterMouseSign(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-text-fill: #14b5a8; -fx-background-color: transparent;");
    }

    public void exitMouseSign(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: #02f5e1; -fx-background-color: transparent;");
    }

    public void handleLogin() throws Exception {
        LoginController loginController = LoginController.getInstance();
        String username = usernameText.getText(), password = passwordText.getText();
        if (username.isEmpty() || password.isEmpty()) {
            new AlertController().create(AlertType.ERROR, "please fill all of the boxes");
            return;
        }
        try {
            AccountType accountType = loginController.login(username, password);
            switch (accountType) {
                case BUYER:
                    BuyerMenuController.start(window);
                    break;
                case MANAGER:
                    ManagerMenuController.start(window);
                    break;
                case SELLER:
                    SellerMenuController.start(window);
                    break;
                case SUPPORTER:
                    SupporterMenuFxml.start(window);
                    break;
            }
            clear();
            new AlertController().create(AlertType.CONFIRMATION, "login was successful");
        } catch (Exception e) {
            e.printStackTrace();
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }

    private void clear() {
        usernameText.setText("");
        passwordText.setText("");
    }

    public void handleSignUp() throws Exception {
        Parent root = FXMLLoader.load(new File("src/main/java/client/view/graphic/fxml/registerAndLoginMenu/SignUpFxml.fxml").toURI().toURL());
        window.setTitle("Sign up menu");
        window.setScene(new Scene(root, 994, 666));
        window.show();
    }

    public void changeSignUpMode() throws Exception {
        if (signUpMode.equals("b")) {
            signUpMode = "s";
            showSignUpSeller();
        } else {
            signUpMode = "b";
            handleSignUp();
        }
    }

    public void showSignUpSeller() throws Exception {
        Parent root = FXMLLoader.load(new File("src/main/java/client/view/graphic/fxml/registerAndLoginMenu/SignUpSeller.fxml").toURI().toURL());
        window.setTitle("Sign up menu");
        window.setScene(new Scene(root, 994, 666));
        window.show();
    }

    public void handleBack() {
        ProgramApplication.back();
    }

    public static void setWindow(Stage window) {
        FxmlRegisterAndLoginMenu.window = window;
    }

    public void enterMouseChangeSign(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: #02dcca; -fx-background-radius: 0");
    }

    public void exitMouseChangeSign(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: #02f5e1; -fx-background-radius: 0");
        ;
    }

    public void handleSignUpBuyer() throws Exception {
        LoginController loginController = LoginController.getInstance();
        String name = newName.getText(), username = newUsername.getText(), password = newPassword.getText(),
                lastName = newLastName.getText(), email = newEmail.getText(),
                phoneNumber = newPhoneNumber.getText();
        if (phoneNumber.isEmpty() || username.isEmpty() || name.isEmpty() || password.isEmpty() || lastName.isEmpty() ||
                email.isEmpty() ) {
            new AlertController().create(AlertType.ERROR, "please fill all of the boxes");
            return;
        }
        ArrayList<String> details = new ArrayList<>();
        details.add(name);
        details.add(lastName);
        details.add(email);
        details.add(phoneNumber);
        details.add(password);
        try {
            loginController.createAccount(username, "buyer", details, "");
            ProgramApplication.back();
            new AlertController().create(AlertType.CONFIRMATION, "sign up was successful");
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }

    public void handleSignUpSeller() throws Exception {
        LoginController loginController = LoginController.getInstance();
        String name = newName.getText(), username = newUsername.getText(), password = newPassword.getText(),
                lastName = newLastName.getText(), email = newEmail.getText(),
                phoneNumber = newPhoneNumber.getText(), detail = newCompanyInfo.getText();
        if (phoneNumber.isEmpty() || username.isEmpty() || name.isEmpty() || password.isEmpty() || lastName.isEmpty() ||
                email.isEmpty() || detail.isEmpty()) {
            new AlertController().create(AlertType.ERROR, "please fill all of the boxes");
            return;
        }
        ArrayList<String> details = new ArrayList<>();
        details.add(name);
        details.add(lastName);
        details.add(email);
        details.add(phoneNumber);
        details.add(password);
        try {
            loginController.createAccount(username, "seller", details, detail);
            ProgramApplication.back();
            new AlertController().create(AlertType.CONFIRMATION, "sign up was successful");
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }

    public void handleExit() {
        FxmlAllProductsMenu.key = false;
        window.close();
    }

    public void enterMouseExit(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: #d94141;");
    }

    public void exitMouseExit(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: #ff4c4c;");
    }
}
