package client.view.graphic.alert;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AlertController implements Initializable {
    public Button message;
    static Stage mainStage;
    static AlertType alertType;
    static String text;

    public void create(AlertType alertType, String text) {
        mainStage = new Stage();
        AlertController.alertType = alertType;
        AlertController.text = text;
        Parent root;
        if (alertType.equals(AlertType.CONFIRMATION)) {
            try {
                root = FXMLLoader.load(new File("src/main/java/client/view/graphic/alert/alertSuccessFxml.fxml").toURI().toURL());
                Scene alertScene = new Scene(root, 219, 358);
                mainStage.setScene(alertScene);
                mainStage.initStyle(StageStyle.UNDECORATED);
                mainStage.show();
            } catch (IOException e) {e.printStackTrace();}
        }
        else {
            try {
                root = FXMLLoader.load(new File("src/main/java/client/view/graphic/alert/alertErrorFxml.fxml").toURI().toURL());
                Scene alertScene = new Scene(root, 219, 358);
                mainStage.setScene(alertScene);
                mainStage.initStyle(StageStyle.UNDECORATED);
                mainStage.show();
            } catch (IOException e) {e.printStackTrace();}
        }
    }

    public void enterMouse(MouseEvent event) {
        if (alertType.equals(AlertType.CONFIRMATION))
            ((Button)event.getSource()).setStyle("-fx-background-color: #51d756; -fx-background-radius: 20");
        else
            ((Button)event.getSource()).setStyle("-fx-background-color: #f44336; -fx-background-radius: 20");
    }

    public void exitMouse(MouseEvent event) {
        if (alertType.equals(AlertType.CONFIRMATION))
            ((Button)event.getSource()).setStyle("-fx-background-color: #4caf50; -fx-background-radius: 20");
        else
            ((Button)event.getSource()).setStyle("-fx-background-color: #b71c1c; -fx-background-radius: 20");
    }

    public void enterMouseWhy(MouseEvent event) {
        ((Button)event.getSource()).setStyle("-fx-background-color: transparent; -fx-text-fill: #b71c1c;");
    }

    public void exitMouseWhy(MouseEvent event) {
        ((Button)event.getSource()).setStyle("-fx-background-color: transparent; -fx-text-fill: #f44336;");;
    }

    public void handleWhy() {
        message.setText(text);
    }

    public void handleClose() {
        mainStage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        message.setText(text);
    }
}
