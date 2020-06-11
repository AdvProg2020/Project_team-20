package view.graphic.fxml.registerAndLoginMenu;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class FxmlRegisterAndLoginMenu {

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
}
