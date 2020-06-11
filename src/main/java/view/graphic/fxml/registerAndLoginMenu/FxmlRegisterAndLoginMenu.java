package view.graphic.fxml.registerAndLoginMenu;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import view.graphic.ProgramApplication;

import java.io.File;

public class FxmlRegisterAndLoginMenu {

    public TextField usernameText;
    public TextField passwordText;
    private static Stage window;
    private static String signUpMode = "b";
    public TextField newUsername;
    public TextField newPassword;
    public TextField newEmail;
    public TextField newName;
    public TextField newLastName;
    public TextField newCredit;
    public TextField newCompanyInfo;

    public void enterMouse(MouseEvent event) {
        ((Button)event.getSource()).setStyle("-fx-background-color: #14b5a8; -fx-background-radius: 10");
    }

    public void exitMouse(MouseEvent event) {
        ((Button)event.getSource()).setStyle("-fx-background-color: #02f5e1; -fx-background-radius: 10");;
    }

    public void enterMouseSign(MouseEvent event) {
        ((Button)event.getSource()).setStyle("-fx-text-fill: #14b5a8; -fx-background-color: transparent;");

    }

    public void exitMouseSign(MouseEvent event) {
        ((Button)event.getSource()).setStyle("-fx-background-color: #02f5e1; -fx-background-color: transparent;");;
    }

    public void handleLogin() {

    }

    public void handleSignUp() throws Exception{
        Parent root = FXMLLoader.load(new File("src/main/java/view/graphic/fxml/registerAndLoginMenu/SignUpFxml.fxml").toURI().toURL());
        window.setTitle("Sign up menu");
        window.setScene(new Scene(root, 994, 666));
        window.show();
    }

    public void changeSignUpMode () throws Exception{
        if (signUpMode.equals("b")) {
            signUpMode = "s";
            showSignUpSeller();
        } else {
            signUpMode = "b";
            handleSignUp();
        }
    }

    public void showSignUpSeller() throws Exception{
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
        ((Button)event.getSource()).setStyle("-fx-background-color: #02dcca; -fx-background-radius: 0");
    }

    public void exitMouseChangeSign(MouseEvent event) {
        ((Button)event.getSource()).setStyle("-fx-background-color: #02f5e1; -fx-background-radius: 0");;
    }
    
    public void handleSignUpBuyer() {
        
    }
    
    public void handleSignUpSeller() {
        
    }

}
