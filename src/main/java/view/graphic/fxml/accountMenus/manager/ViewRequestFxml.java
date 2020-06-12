package view.graphic.fxml.accountMenus.manager;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

public class ViewRequestFxml {
    public JFXTextField requestId;
    public TextArea message;

    public void handleEnter(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: #00bfbc;");
    }

    public void handleExit(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: #009f9c;");
    }

    public void handleShow() {

    }


}
