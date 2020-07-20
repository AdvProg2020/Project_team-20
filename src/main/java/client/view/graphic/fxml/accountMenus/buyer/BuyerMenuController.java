package client.view.graphic.fxml.accountMenus.buyer;

import client.controller.Main;
import client.controller.MediaController;
import client.controller.account.user.BuyerController;
import client.view.graphic.chat.ChatFxml;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import client.model.account.Buyer;
import client.view.graphic.MenuNames;
import client.view.graphic.ProgramApplication;
import client.view.graphic.alert.AlertController;
import client.view.graphic.alert.AlertType;
import client.view.graphic.fxml.allProductsMenu.FxmlAllProductsMenu;
import client.view.graphic.popUp.PopUpControllerFxml;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

public class BuyerMenuController implements Initializable {
    private static Stage window;
    public BorderPane borderPane;
    public ImageView profileImg;
    private BuyerController buyerController = BuyerController.getInstance();
    private static boolean loadFromViewCart = false;
    private static boolean isChatting = false;

    MediaController mediaController = ProgramApplication.getMediaController();

    {
        mediaController.stop();
        mediaController.buyerMenu();
    }

    public static void start(Stage stage) throws Exception {
        window = stage;
        Parent root = FXMLLoader.load(new File("src/main/java/client/view/graphic/fxml/accountMenus/buyer/BuyerMenuFxml.fxml").toURI().toURL());
        stage.setScene(new Scene(root, 994, 666));
        stage.show();
        int randInt = ThreadLocalRandom.current().nextInt(0, 10);
        if (randInt % 2 == 0) {
            new PopUpControllerFxml().create();
        }
    }

    public void handleLogout(ActionEvent actionEvent) {
        buyerController.logout();
        ProgramApplication.setMenu(MenuNames.MAINMENU);
        new AlertController().create(AlertType.CONFIRMATION, "log out was successful");
    }

    public void loadUI(String ui) {
        Parent root;
        try {
            root = FXMLLoader.load(new File("src/main/java/client/view/graphic/fxml/accountMenus/buyer/" + ui + "Fxml" + ".fxml").toURI().toURL());
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
            File outputFile = new File("src/main/resources/Images/profile" + buyerController.getCurrentBuyer().getUsername() + ".png");
            String path = "profile" + buyerController.getCurrentBuyer().getUsername() + ".png";
            buyerController.setProfileImage(path);
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

    public void handleViewWallet(ActionEvent actionEvent) {
        ViewCartFxml.setBorderPane(borderPane);
        loadUI("chargeWallet");
    }

    public void handlePersonalInfo(ActionEvent actionEvent) {
        loadUI("personalInfo");
    }

    public void handleViewSales(ActionEvent actionEvent) {
        ViewCartFxml.setBorderPane(borderPane);
        loadUI("viewCart");
    }

    public void handlePurchase(ActionEvent actionEvent) {
        PurchaseMenu.setBorderPane(borderPane);
        loadUI("purchaseMenu");
    }

    public void handleViewOrders(ActionEvent actionEvent) {
        loadUI("viewOrder");
    }

    public void handleViewDiscountCodes(ActionEvent actionEvent) {
        loadUI("discountCodes");
    }

    public void handleExit() {
        FxmlAllProductsMenu.key = false;
        window.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Buyer buyer = buyerController.getCurrentBuyer();
        if (buyer.getImagePath() != null) {
            Image img1 = new Image(new File("src/main/resources/Images/" + buyer.getImagePath()).toURI().toString());
            profileImg.setImage(img1);
        }
        if (loadFromViewCart) {
            loadFromViewCart = false;
            loadUI("viewCart");
        }
    }

    public static void setLoadFromViewCart(boolean newLoadFromViewCart) {
        loadFromViewCart = newLoadFromViewCart;
    }

    public void handleViewErpBank(ActionEvent actionEvent) {
        ProgramApplication.setMenu(MenuNames.ERP_BANK);
    }

    public void handleSupport(ActionEvent actionEvent) {
    if (!isChatting) {
        Stage stage = new Stage();
        try {
            isChatting = true;
            ChatFxml.setBuyer(buyerController.getCurrentBuyer());
            Parent root = FXMLLoader.load(new File("src/main/java/client/view/graphic/chat/chatFxml.fxml").toURI().toURL());
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }

    public static void setIsChatting(boolean isChatting) {
        BuyerMenuController.isChatting = isChatting;
    }
}
