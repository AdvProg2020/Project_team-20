package client.view.graphic.fxml.accountMenus.supporter;

import client.controller.MediaController;
import client.controller.account.user.SupporterController;
import client.model.account.Supporter;
import client.network.chat.SupporterChatRoom;
import client.view.graphic.ProgramApplication;
import client.view.graphic.popUp.PopUpControllerFxml;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

public class SupporterMenuFxml implements Initializable {
    private static Stage window;
    public BorderPane borderPane;
    public ImageView profileImg;
    public VBox chats;
    private SupporterController supporterController = SupporterController.getInstance();

    MediaController mediaController = ProgramApplication.getMediaController();

    {
        mediaController.stop();
        mediaController.buyerMenu();
    }

    public static void start(Stage stage) throws Exception {
        window = stage;
        Parent root = FXMLLoader.load(new File("src/main/java/client/view/graphic/fxml/accountMenus/supporter/SupporterMenuFxml.fxml").toURI().toURL());
        stage.setScene(new Scene(root, 994, 666));
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateChats();
    }

    public void updateChats() {
        chats.getChildren().removeAll();
        ArrayList<SupporterChatRoom> supporterChatRooms = supporterController.getAllChatRooms();
        for (SupporterChatRoom supporterChatRoom:supporterChatRooms) {
            JFXButton button = new JFXButton();
            if (supporterChatRoom.getBuyer()!=null)
                button.setText(supporterChatRoom.getBuyer().getUsername());
            else
                button.setText("No buyer");
            button.setId(supporterChatRoom.getId());
            button.setTextFill(new Color(0.84375, 0.97265625, 0.99609375, 1));
            button.setOnAction(this::goToChat);
            button.setPrefWidth(367);
            button.setPrefHeight(40);
            Separator separator = new Separator();
            separator.setOpacity(0.05);
            separator.setStyle("-fx-padding: 5px; -fx-border-insets: 5px; -fx-background-insets: 5px;");
            chats.getChildren().add(button);
            chats.getChildren().add(separator);
        }
    }

    public void goToChat(ActionEvent actionEvent) {
        for (Node node:chats.getChildren())
            if (node instanceof JFXButton)
                ((JFXButton) node).setStyle("-fx-background-color: transparent;");
        JFXButton button = (JFXButton) actionEvent.getSource();
        button.setStyle("-fx-background-color: #525d5d;");
    }

    public void handleLogout(ActionEvent actionEvent) {
    }

    public void handleExit(ActionEvent actionEvent) {
    }

    public void handleDragDropped(DragEvent event) {
    }

    public void handleDragOver(DragEvent event) {
    }

    public void handleAddChatRoom(ActionEvent actionEvent) {
        try {
            supporterController.createChatRoom();
            updateChats();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
