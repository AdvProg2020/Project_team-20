package client.view.graphic.fxml.mainMenu;

import client.controller.Main;
import client.controller.MediaController;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import client.view.graphic.MenuNames;
import client.view.graphic.ProgramApplication;
import client.view.graphic.fxml.allProductsMenu.FxmlAllProductsMenu;

public class FxmlMainMenu {
    public static Stage window;
    MediaController mediaController = ProgramApplication.getMediaController();
    public void enterMouse(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: #DCDF87; -fx-background-radius: 20; -fx-border-color: #27304f; -fx-border-radius: 20; -fx-border-width: 5");

    }

    public void exitMouse(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: #F0F2BD; -fx-background-radius: 20; -fx-border-color: #27304f; -fx-border-radius: 20; -fx-border-width: 5");
    }

    public void handleRegistrationLogin() {
        ProgramApplication.setMenu(MenuNames.REGISTERANDLOGINMENU);
    }

    public void handleAllProducts() {
        ProgramApplication.setMenu(MenuNames.ALLPRODUCTSMENU);
    }

    public void handleExit() {
        FxmlAllProductsMenu.key = false;
        Main.storeData();
        window.close();
    }

    public void enterMouseExit(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: #d94141;");
    }

    public void exitMouseExit(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: #ff4c4c;");
    }

    public static void setWindow(Stage window) {
        FxmlMainMenu.window = window;
    }

    public void handleErpBank(ActionEvent actionEvent) {
        ProgramApplication.setMenu(MenuNames.ERP_BANK);
    }
}
