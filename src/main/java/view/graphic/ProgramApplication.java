package view.graphic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class ProgramApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(new File("src/main/java/view/graphic/fxml/first.fxml").toURI().toURL());
        Scene first = new Scene(root, 400, 600);
        primaryStage.setTitle("test");
        primaryStage.setScene(first);
        primaryStage.show();
    }

    public static void startApp(String[] args) {
        launch(args);
    }
}
