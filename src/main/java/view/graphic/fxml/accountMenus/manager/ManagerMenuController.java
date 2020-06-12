package view.graphic.fxml.accountMenus.manager;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;

public class ManagerMenuController {
    private static Stage window;
    public TextArea text;
    public BorderPane borderPane;
    HBox h;


    public static void start(Stage stage) throws Exception {
        window = stage;
        Parent root = FXMLLoader.load(new File("src/main/java/view/graphic/fxml/accountMenus/manager/managerMenuFxml.fxml").toURI().toURL());
        stage.setTitle("Sign up menu");
        stage.setScene(new Scene(root, 994, 666));
        stage.show();
    }

    public void handlePersonalInfo(ActionEvent actionEvent) {
        loadUI("personalInfo");
    }

    public void handleRequests(ActionEvent actionEvent) {
        loadUI("requests");
    }

    public void handleManageUsers(ActionEvent actionEvent) {
        loadUI("manageUsers");
    }

    public void handleProducts(ActionEvent actionEvent) {
        loadUI("products");
    }

    public void handleDiscounts(ActionEvent actionEvent) {
        loadUI("discounts");
    }

    public void handleCreateDiscounts(ActionEvent actionEvent) {
        loadUI("createDiscounts");
    }

    public void handleManageCategories(ActionEvent actionEvent) {
        loadUI("manageCategories");
    }

    private void loadUI(String ui){
        Parent root;
        try {
            root = FXMLLoader.load(new File("src/main/java/view/graphic/fxml/accountMenus/manager/" + ui + "Fxml" + ".fxml").toURI().toURL());
            borderPane.setCenter(root);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
