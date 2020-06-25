package view.graphic.fxml.registerAndLoginMenu;

import controller.Main;
import controller.account.LoginController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.account.AccountType;
import view.graphic.ProgramApplication;
import view.graphic.alert.AlertController;
import view.graphic.alert.AlertType;
import view.graphic.fxml.accountMenus.buyer.BuyerMenuController;
import view.graphic.fxml.accountMenus.manager.ManagerMenuController;
import view.graphic.fxml.accountMenus.seller.SellerMenuController;
import view.graphic.fxml.allProductsMenu.FxmlAllProductsMenu;

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
    public TextField newCredit;
    public TextField newCompanyInfo;
    public TextField newPhoneNumber;

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
        Parent root = FXMLLoader.load(new File("src/main/java/view/graphic/fxml/registerAndLoginMenu/SignUpFxml.fxml").toURI().toURL());
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
        Parent root = FXMLLoader.load(new File("src/main/java/view/graphic/fxml/registerAndLoginMenu/SignUpSeller.fxml").toURI().toURL());
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
                lastName = newLastName.getText(), email = newEmail.getText(), creditString = newCredit.getText(),
                phoneNumber = newPhoneNumber.getText();
        if (phoneNumber.isEmpty() || username.isEmpty() || name.isEmpty() || password.isEmpty() || lastName.isEmpty() ||
                email.isEmpty() || creditString.isEmpty()) {
            new AlertController().create(AlertType.ERROR, "please fill all of the boxes");
            return;
        }
        ArrayList<String> details = new ArrayList<>();
        details.add(name);
        details.add(lastName);
        details.add(email);
        details.add(phoneNumber);
        details.add(password);
        details.add(creditString);
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
                lastName = newLastName.getText(), email = newEmail.getText(), creditString = newCredit.getText(),
                phoneNumber = newPhoneNumber.getText(), detail = newCompanyInfo.getText();
        if (phoneNumber.isEmpty() || username.isEmpty() || name.isEmpty() || password.isEmpty() || lastName.isEmpty() ||
                email.isEmpty() || creditString.isEmpty() || detail.isEmpty()) {
            new AlertController().create(AlertType.ERROR, "please fill all of the boxes");
            return;
        }
        ArrayList<String> details = new ArrayList<>();
        details.add(name);
        details.add(lastName);
        details.add(email);
        details.add(phoneNumber);
        details.add(password);
        details.add(creditString);
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
        Main.storeData();
        window.close();
    }

    public void enterMouseExit(MouseEvent event) {
        ((Button)event.getSource()).setStyle("-fx-background-color: #d94141;");

    }

    public void exitMouseExit(MouseEvent event) {
        ((Button)event.getSource()).setStyle("-fx-background-color: #ff4c4c;");;
    }
}
