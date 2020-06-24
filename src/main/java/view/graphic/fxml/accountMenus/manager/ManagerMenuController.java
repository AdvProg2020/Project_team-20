package view.graphic.fxml.accountMenus.manager;

import controller.Main;
import controller.account.user.ManagerController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
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
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManagerMenuController implements Initializable {
    private static Stage window;
    public TextArea text;
    public BorderPane borderPane;
    public ImageView imageView;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO do this
        //imageView.setImage(manager.getGraphicPackage().getMainImage());
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

        loadUI("discount/discounts");
    }

    public void handleCreateDiscounts(ActionEvent actionEvent) {
        loadUI("discount/createDiscounts");
    }

    public void handleManageCategories(ActionEvent actionEvent) {

        loadUI("manageCategories");
    }

    public void loadUI(String ui) {
        Parent root;
        try {
            root = FXMLLoader.load(new File("src/main/java/view/graphic/fxml/accountMenus/manager/" + ui + "Fxml" + ".fxml").toURI().toURL());
            borderPane.setCenter(root);
        } catch (Exception e) {
           // System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void handleLogout(ActionEvent actionEvent) throws Exception {
        managerController.logout();
        ProgramApplication.setMenu(MenuNames.MAINMENU);
        new AlertController().create(AlertType.CONFIRMATION, "log out was successful");
    }

    public void handleAllProducts(ActionEvent actionEvent) {
        ProgramApplication.setMenu(MenuNames.ALLPRODUCTSMENU);
    }

    public void handleOffs(ActionEvent actionEvent) {
    }

    public void handleDragDropped(DragEvent event) {
    }

    public void handleDragOver(DragEvent event) {
    }

    public void handleExit() {
        Main.storeData();
        window.close();
    }
}
