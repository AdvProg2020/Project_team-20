package view.graphic.fxml.accountMenus;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class ManagerMenuController {
    private static Stage window;

    public void start(Stage mainStage) throws Exception{
        window = mainStage;
        Parent root = FXMLLoader.load(new File("src/main/java/view/graphic/fxml/accountMenus/ManagerMenuFxml.fxml").toURI().toURL());
        window.setTitle("Manager account");
        window.setScene(new Scene(root, 994, 666));
        window.show();
    }


}
