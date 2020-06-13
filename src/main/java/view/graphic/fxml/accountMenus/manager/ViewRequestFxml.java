package view.graphic.fxml.accountMenus.manager;

import com.jfoenix.controls.JFXTextField;
import controller.account.user.ManagerController;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import model.Requestable;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ViewRequestFxml{
    public JFXTextField requestId;
    public TextArea message;
    static String ourMessage;

    public void handleEnter(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: #00bfbc;");
    }

    public void handleExit(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: #009f9c;");
    }
}
