package view.graphic.fxml.accountMenus.buyer;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class BuyerMenuController {
    private static Stage window;

    public static void start(Stage stage) throws Exception{
        window = stage;
        Parent root = FXMLLoader.load(new File("src/main/java/view/graphic/fxml/accountMenus/BuyerMenuFxml.fxml").toURI().toURL());
        stage.setTitle("Sign up menu");
        stage.setScene(new Scene(root, 994, 666));
        stage.show();
    }
}
