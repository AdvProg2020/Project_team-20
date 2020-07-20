package client.view.graphic.chat;

import client.controller.chat.ChatController;
import client.network.chat.SupporterChatRoom;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChatFxml implements Initializable {
    public JFXTextField newComment;
    public VBox messagesChats;

    private ChatController chatController = ChatController.getInstance();
    private ArrayList<JFXButton> buttons;
    private ArrayList<Separator> separators;

    {
        buttons = new ArrayList<>();
        separators = new ArrayList<>();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            ArrayList<SupporterChatRoom> supporterChatRooms = chatController.getAllChatRooms();
            System.out.println(messagesChats);
            messagesChats.getChildren().removeAll(buttons);
            messagesChats.getChildren().removeAll(separators);
            buttons = new ArrayList<>();
            separators = new ArrayList<>();
            for (SupporterChatRoom supporterChatRoom : supporterChatRooms) {
                JFXButton button = new JFXButton();
                if (supporterChatRoom.getBuyer() != null)
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
                messagesChats.getChildren().add(button);
                messagesChats.getChildren().add(separator);
                buttons.add(button);
                separators.add(separator);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goToChat(ActionEvent actionEvent) {

    }

    public void handleClose(ActionEvent actionEvent) {
    }

    public void addComment(ActionEvent actionEvent) {
    }
}
