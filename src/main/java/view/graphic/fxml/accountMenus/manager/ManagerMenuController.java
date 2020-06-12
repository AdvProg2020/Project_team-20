package view.graphic.fxml.accountMenus.manager;

import controller.account.user.ManagerController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Requestable;
import model.account.Manager;
import view.graphic.MenuNames;
import view.graphic.ProgramApplication;
import view.graphic.alert.AlertController;
import view.graphic.alert.AlertType;

import java.io.File;
import java.util.ArrayList;

public class ManagerMenuController {
    private static Stage window;
    public TextArea text;
    public BorderPane borderPane;
    HBox h;
    private ManagerController managerController = ManagerController.getInstance();
    private Manager manager = (Manager) managerController.getAccountInfo();


    public static void start(Stage stage) throws Exception {
        window = stage;
        Parent root = FXMLLoader.load(new File("src/main/java/view/graphic/fxml/accountMenus/manager/managerMenuFxml.fxml").toURI().toURL());
        stage.setTitle("Manager menu");
        stage.setScene(new Scene(root, 994, 666));
        stage.show();
    }

    public void handlePersonalInfo(ActionEvent actionEvent) {

        loadUI("personalInfo");
    }

    public void handleRequests(ActionEvent actionEvent) {
        ArrayList<Requestable> requests = Manager.getRequests();

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

    public void handleLogout(ActionEvent actionEvent) throws Exception {
        System.out.println("click on logout");
        managerController.logout();
        ProgramApplication.setMenu(MenuNames.MAINMENU);
        new AlertController().create(AlertType.CONFIRMATION, "log out was successful");
    }

    public void handleAllProducts(ActionEvent actionEvent) {
    }

    public void handleOffs(ActionEvent actionEvent) {
    }
}
