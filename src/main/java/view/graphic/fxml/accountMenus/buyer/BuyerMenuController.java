package view.graphic.fxml.accountMenus.buyer;

import controller.Main;
import controller.account.user.BuyerController;
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
import model.account.Buyer;
import view.graphic.MenuNames;
import view.graphic.ProgramApplication;
import view.graphic.alert.AlertController;
import view.graphic.alert.AlertType;
import view.graphic.fxml.allProductsMenu.FxmlAllProductsMenu;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BuyerMenuController implements Initializable {
    private static Stage window;
    public BorderPane borderPane;
    public ImageView profileImg;
    private BuyerController buyerController = BuyerController.getInstance();
    private static boolean loadFromViewCart = false;

    public static void start(Stage stage) throws Exception{
        window = stage;
        Parent root = FXMLLoader.load(new File("src/main/java/view/graphic/fxml/accountMenus/buyer/BuyerMenuFxml.fxml").toURI().toURL());
        stage.setTitle("Sign up menu");
        stage.setScene(new Scene(root, 994, 666));
        stage.show();
    }

    public void handleLogout(ActionEvent actionEvent) {
        buyerController.logout();
        ProgramApplication.setMenu(MenuNames.MAINMENU);
        new AlertController().create(AlertType.CONFIRMATION, "log out was successful");
    }

    public void loadUI(String ui){
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
        Main.storeData();
        window.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Buyer buyer = buyerController.getCurrentBuyer();
        if (buyer.getImagePath()!=null) {
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
}
