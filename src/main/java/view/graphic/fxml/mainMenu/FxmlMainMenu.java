package view.graphic.fxml.mainMenu;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;

public class FxmlMainMenu {
    private static Stage window;

    public void enterMouse(MouseEvent event) {
        ((Button)event.getSource()).setStyle("-fx-background-color: #DCDF87; -fx-background-radius: 20");

    }

    public void exitMouse(MouseEvent event) {
        ((Button)event.getSource()).setStyle("-fx-background-color: #F0F2BD; -fx-background-radius: 20");;
    }

    public void handleRegistrationLogin() {

    }

    public void handleAllProducts() {

    }

    public void handleSales() {

    }

    public void handleExit() {
        window.close();
    }

    public static void setWindow(Stage window) {
        FxmlMainMenu.window = window;
    }
}
