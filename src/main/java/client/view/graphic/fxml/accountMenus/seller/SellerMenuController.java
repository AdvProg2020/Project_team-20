package client.view.graphic.fxml.accountMenus.seller;

import client.controller.MediaController;
import client.controller.account.user.seller.SellerController;
import client.model.account.Seller;
import client.view.graphic.MenuNames;
import client.view.graphic.ProgramApplication;
import client.view.graphic.alert.AlertController;
import client.view.graphic.alert.AlertType;
import client.view.graphic.fxml.allProductsMenu.FxmlAllProductsMenu;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SellerMenuController implements Initializable {
    private static Stage window;
    public BorderPane borderPane;
    public ImageView profileImg;
    public ScrollPane scroll;
    private SellerController sellerController = SellerController.getInstance();
    private Seller seller = (Seller) sellerController.getAccountInfo();

    MediaController mediaController = ProgramApplication.getMediaController();

    {
        mediaController.stop();
        mediaController.sellerMenu();
    }

    public static void start(Stage stage) throws Exception {
        window = stage;
        Parent root = FXMLLoader.load(new File("src/main/java/client/view/graphic/fxml/accountMenus/seller/SellerMenuFxml.fxml").toURI().toURL());
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
    }

    public void handleShowCategories(ActionEvent actionEvent) {
        loadUI("showCategories");
    }

    public void handleManageOffs(ActionEvent actionEvent) {
        loadUI("manageOffs");
    }

    public void handleViewWallet(ActionEvent actionEvent){loadUI("yourWallet");}

    public void handleLogout(ActionEvent actionEvent) throws Exception {
        sellerController.logout();
        ProgramApplication.setMenu(MenuNames.MAINMENU);
        new AlertController().create(AlertType.CONFIRMATION, "log out was successful");
    }

    private void loadUI(String ui) {
        Parent root;
        try {
            root = FXMLLoader.load(new File("src/main/java/client/view/graphic/fxml/accountMenus/seller/" + ui + "Fxml" + ".fxml").toURI().toURL());
            borderPane.setCenter(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            File outputFile = new File("src/main/resources/Images/profile" + SellerController.getSeller().getUsername() + ".png");
            String path = "profile" + SellerController.getSeller().getUsername() + ".png";
            sellerController.setProfileImage(path);
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
        window.close();
    }

    public void handleCreateProduct(ActionEvent actionEvent) {
        loadUI("createProduct");
    }

    public void handleCreateOff(ActionEvent actionEvent) {
        loadUI("createOff");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Seller seller = SellerController.getSeller();
        if (seller.getImagePath() != null) {
            Image img1 = new Image(new File("src/main/resources/Images/" + seller.getImagePath()).toURI().toString());
            profileImg.setImage(img1);
        }
    }

    public void handleAdvertisement(ActionEvent actionEvent) {
        loadUI("createAdvertisement");
    }

    public void handleCreateFileProduct(ActionEvent actionEvent) {
        loadUI("createFileProduct");
    }

    public void handleCreateActions(ActionEvent actionEvent) {
        loadUI("createAuction");
    }
}
