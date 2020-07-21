package client.view.graphic.chat;

import client.controller.chat.ChatController;
import client.model.account.AccountType;
import client.model.account.Buyer;
import client.network.chat.ChatMessage;
import client.network.chat.SupporterChatRoom;
import client.view.graphic.fxml.accountMenus.buyer.BuyerMenuController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
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
    public JFXButton addCommentBtn;
    private String chatRoomId;
    private boolean threadStop = false;
    public static Buyer buyer;

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
                button.setText(supporterChatRoom.getSupporter().getUsername());
                button.setId(supporterChatRoom.getId());
                button.setTextFill(new Color(0.3632, 0.4118, 0.41406, 1));
                button.setOnAction(this::goToChat);
                button.setPrefWidth(278);
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
        messagesChats.getChildren().removeAll(buttons);
        messagesChats.getChildren().removeAll(separators);
        buttons = new ArrayList<>();
        newComment.setOpacity(1);
        addCommentBtn.setOpacity(1);
        JFXButton chat = (JFXButton) actionEvent.getSource();
        try {
            chatRoomId = chat.getId();
            chatController.addToChatRoom(chatRoomId);


            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    Runnable updater = new Runnable() {

                        @Override
                        public void run() {
                            updateMessages();
                        }
                    };

                    while (threadStop) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                        }
                        Platform.runLater(updater);
                    }
                }

            });
            thread.setDaemon(true);
            thread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateMessages() {
        try {
            ArrayList<ChatMessage> chatMessages = chatController.getAllMessages(chatRoomId);
            messagesChats.getChildren().removeAll(buttons);
            buttons = new ArrayList<>();
            for (ChatMessage chatMessage:chatMessages) {
                System.out.println(chatMessage.getContest());
                JFXButton button = new JFXButton();
                button.setTextFill(new Color(0.3632, 0.4118, 0.41406, 1));
                button.setPrefWidth(278);
                button.setPrefHeight(30);
                if (chatMessage.getType().equals(AccountType.SUPPORTER))
                    button.setStyle("-fx-alignment: center-left; -fx-font-size:20");
                else {
                    button.setStyle("-fx-alignment: center-right; -fx-font-size:20");
                    button.setTextFill(new Color(0.42578, 0.69140625, 0.65625, 1));
                }
                button.setText(chatMessage.getContest());
                messagesChats.getChildren().add(button);
                buttons.add(button);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleClose(ActionEvent actionEvent) {
        threadStop = true;
        BuyerMenuController.setIsChatting(false);
        chatController.disconnect();
    }

    public void addComment(ActionEvent actionEvent) {
        String context = newComment.getText();
        try {
            if (chatRoomId==null||context.equals(""))
                return;
            newComment.setText("");
            chatController.writeNewMessage(chatRoomId, buyer.getUsername(), context, AccountType.BUYER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setBuyer(Buyer buyer) {
        ChatFxml.buyer = buyer;
    }
}
