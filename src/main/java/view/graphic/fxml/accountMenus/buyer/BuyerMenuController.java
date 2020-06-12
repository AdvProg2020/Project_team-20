package view.graphic.fxml.accountMenus.buyer;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;

public class BuyerMenuController {
    private static Stage window;
    public BorderPane borderPane;

    public static void start(Stage stage) throws Exception{
        window = stage;
        Parent root = FXMLLoader.load(new File("src/main/java/view/graphic/fxml/accountMenus/BuyerMenuFxml.fxml").toURI().toURL());
        stage.setTitle("Sign up menu");
        stage.setScene(new Scene(root, 994, 666));
        stage.show();
    }

    public void handleLogout(ActionEvent actionEvent) {
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

    public void handleAllProducts(ActionEvent actionEvent) {
    }

    public void handleOffs(ActionEvent actionEvent) {
    }
}
