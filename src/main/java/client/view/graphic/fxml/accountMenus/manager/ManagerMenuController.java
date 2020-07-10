package client.view.graphic.fxml.accountMenus.manager;

import client.controller.Main;
import client.controller.MediaController;
import client.controller.account.user.ManagerController;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import client.model.account.Manager;
import client.view.graphic.MenuNames;
import client.view.graphic.ProgramApplication;
import client.view.graphic.alert.AlertController;
import client.view.graphic.alert.AlertType;
import client.view.graphic.fxml.allProductsMenu.FxmlAllProductsMenu;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ManagerMenuController implements Initializable {
    private static Stage window;
    public TextArea text;
    public BorderPane borderPane;
    public ImageView imageView;
    public ImageView profileImage;
    public ImageView profileImg;
    HBox h;
    private ManagerController managerController = ManagerController.getInstance();
    private Manager manager = (Manager) managerController.getAccountInfo();

    MediaController mediaController = ProgramApplication.getMediaController();

    {
        mediaController.stop();
        mediaController.managerMenu();
    }

    public static void start(Stage stage) throws Exception {
        window = stage;
        Parent root = FXMLLoader.load(new File("src/main/java/client.view/graphic/fxml/accountMenus/manager/managerMenuFxml.fxml").toURI().toURL());
        stage.setTitle("Manager menu");
        stage.setScene(new Scene(root, 994, 666));
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (manager.getImagePath() != null) {
            Image img1 = new Image(new File("src/main/resources/Images/" + manager.getImagePath()).toURI().toString());
            profileImg.setImage(img1);
        }
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
            root = FXMLLoader.load(new File("src/main/java/client.view/graphic/fxml/accountMenus/manager/" + ui + "Fxml" + ".fxml").toURI().toURL());
            borderPane.setCenter(root);
        } catch (Exception e) {
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

    public void handleDragDropped(DragEvent event) {
        List<File> files = event.getDragboard().getFiles();
        Image img;
        try {
            img = new Image(new FileInputStream((files.get(0))));
            profileImg.setImage(img);
            File outputFile = new File("src/main/resources/Images/profile" + ManagerController.getCurrentManager().getUsername() + ".png");
            String path = "profile" + ManagerController.getCurrentManager().getUsername() + ".png";
            managerController.setProfileImage(path);
            BufferedImage bImage = SwingFXUtils.fromFXImage(img, null);
            try {
                ImageIO.write(bImage, "png", outputFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void handleDragOver(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.ANY);
        }
    }

    public void handleExit() {
        FxmlAllProductsMenu.key = false;
        Main.storeData();
        window.close();
    }

    public void handleManageCategorySets(ActionEvent actionEvent) {
        loadUI("manageCategorySets");
    }
}
