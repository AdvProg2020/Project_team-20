package client.view.graphic.fxml.bank;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;

public class BankControllerFxml {
    public ImageView newUserPanel;
    public PasswordField passwordRepeat;
    public PasswordField name;
    public PasswordField lastName;
    public TextField newName;
    public PasswordField newPassword;
    public JFXButton loginButton;
    public JFXButton newUserButton;

    public void handleLogin(ActionEvent actionEvent) {
        newUserPanel.setOpacity(0);
        passwordRepeat.setOpacity(0);
        name.setOpacity(0);
        lastName.setOpacity(0);
        loginButton.setStyle("-fx-background-color: #ddf1ee;");
        newUserButton.setStyle("-fx-background-color: #b3c9c9;");
    }

    public void handleNewUser(ActionEvent actionEvent) {
        newUserPanel.setOpacity(1);
        passwordRepeat.setOpacity(1);
        name.setOpacity(1);
        lastName.setOpacity(1);
        loginButton.setStyle("-fx-background-color: #b3c9c9;");
        newUserButton.setStyle("-fx-background-color: #ddf1ee;");
    }
}
