package view.graphic.fxml.accountMenus.buyer;

import controller.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.graphic.MenuNames;
import view.graphic.ProgramApplication;

import java.io.File;

public class BuyerMenuController {
    private static Stage window;
    public BorderPane borderPane;

    public static void start(Stage stage) throws Exception{
        window = stage;
        Parent root = FXMLLoader.load(new File("src/main/java/view/graphic/fxml/accountMenus/buyer/BuyerMenuFxml.fxml").toURI().toURL());
        stage.setTitle("Sign up menu");
        stage.setScene(new Scene(root, 994, 666));
        stage.show();
    }

    public void handleLogout(ActionEvent actionEvent) {
    }

    private void loadUI(String ui){
        Parent root;
        try {
            root = FXMLLoader.load(new File("src/main/java/view/graphic/fxml/accountMenus/buyer/" + ui + "Fxml" + ".fxml").toURI().toURL());
            borderPane.setCenter(root);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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

    public void handlePersonalInfo(ActionEvent actionEvent) {
        loadUI("personalInfo");
    }

    public void handleViewSales(ActionEvent actionEvent) {
    }

    public void handlePurchase(ActionEvent actionEvent) {
    }

    public void handleViewOrders(ActionEvent actionEvent) {
    }

    public void handleViewDiscountCodes(ActionEvent actionEvent) {
        loadUI("discountCodes");
    }

    public void handleExit() {
        Main.storeData();
        window.close();
    }
}
