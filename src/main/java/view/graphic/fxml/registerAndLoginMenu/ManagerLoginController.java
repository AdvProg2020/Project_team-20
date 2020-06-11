package view.graphic.fxml.registerAndLoginMenu;

import controller.account.LoginController;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import view.graphic.MenuNames;
import view.graphic.ProgramApplication;

import java.util.ArrayList;

public class ManagerLoginController {
    public TextField newUsername;
    public TextField newName;
    public TextField newPassword;
    public TextField newLastName;
    public TextField newEmail;
    public TextField newCredit;
    public TextField newPhoneNumber;

    public void handleSignUp() {
        LoginController loginController = LoginController.getInstance();
        String username = newUsername.getText(), name = newName.getText(), password = newPassword.getText(),
                lastName = newLastName.getText(), email = newEmail.getText(), creditString = newCredit.getText(),
                phoneNumber = newPhoneNumber.getText();
        if (username.isEmpty() || name.isEmpty() || password.isEmpty() || lastName.isEmpty() ||
                email.isEmpty() || creditString.isEmpty() || phoneNumber.isEmpty()) {
          //  new AlertController().create(window, AlertType.ERROR);
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
            loginController.createAccount(username, "manager", details, "");
        } catch (Exception e) {
           // new AlertController().create(window, AlertType.ERROR);
        }
        ProgramApplication.setMenu(MenuNames.MAINMENU);
    }

    public void enterMouse(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: #14b5a8; -fx-background-radius: 10");
    }

    public void exitMouse(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: #02f5e1; -fx-background-radius: 10");
        ;
    }

}
