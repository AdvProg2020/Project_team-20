package view.graphic.fxml.accountMenus.seller;

import controller.Main;
import controller.account.user.SellerController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.product.Product;
import view.graphic.MenuNames;
import view.graphic.ProgramApplication;
import view.graphic.alert.AlertController;
import view.graphic.alert.AlertType;
import java.io.File;
import java.util.ArrayList;

public class SellerMenuController {
    private static Stage window;
    public BorderPane borderPane;
    private SellerController sellerController = SellerController.getInstance();

    public static void start(Stage stage) throws Exception {
        window = stage;
        Parent root = FXMLLoader.load(new File("src/main/java/view/graphic/fxml/accountMenus/seller/SellerMenuFxml.fxml").toURI().toURL());
        stage.setTitle("Seller menu");
        stage.setScene(new Scene(root, 994, 666));
        stage.show();
    }

    public void handlePersonalInfo(ActionEvent actionEvent) {
        loadUI("personalInfo");
    }

    public void handleViewSales(ActionEvent actionEvent) {
        loadUI("viewSales");
    }

    public void handleManageProducts(ActionEvent actionEvent) {
        loadUI("manageProducts");
        ArrayList<Product> products = SellerController.getInstance().getAllProducts();
        for (Product product:products){
            ImageView imageView = new ImageView();
            Image image = new Image(product.getImagePath());
            imageView.setImage(image);
        }
    }


    public void handleShowCategories(ActionEvent actionEvent) {
        loadUI("showCategories");
    }

    public void handleManageOffs(ActionEvent actionEvent) {
        loadUI("manageOffs");
    }

    public void handleLogout(ActionEvent actionEvent) throws Exception{
        sellerController.logout();
        ProgramApplication.setMenu(MenuNames.MAINMENU);
        new AlertController().create(AlertType.CONFIRMATION, "log out was successful");
    }

    private void loadUI(String ui){
        Parent root;
        try {
            root = FXMLLoader.load(new File("src/main/java/view/graphic/fxml/accountMenus/seller/" + ui + "Fxml" + ".fxml").toURI().toURL());
            borderPane.setCenter(root);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void handleAllProducts(ActionEvent actionEvent) {
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
