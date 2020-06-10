package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ProgramApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("first.fxml"));
        Scene first = new Scene(root, 400, 600);
        primaryStage.setTitle("test");
        primaryStage.setScene(first);
        primaryStage.show();
    }

    public static void startApp(String[] args) {
        launch(args);
    }
}
