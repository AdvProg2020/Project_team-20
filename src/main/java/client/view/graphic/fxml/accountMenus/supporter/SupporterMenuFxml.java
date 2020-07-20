package client.view.graphic.fxml.accountMenus.supporter;

import client.controller.MediaController;
import client.controller.account.user.SupporterController;
import client.controller.chat.ChatController;
import client.model.account.AccountType;
import client.network.chat.ChatMessage;
import client.network.chat.SupporterChatRoom;
import client.view.graphic.ProgramApplication;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.sun.javaws.util.JfxHelper;
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

public class SupporterMenuFxml implements Initializable {
    private static SupporterMenuFxml supporterMenuFxml = null;

    public static SupporterMenuFxml getInstance() {
        return supporterMenuFxml;
    }

    private static Stage window;
    public BorderPane borderPane;
    public ImageView profileImg;
    public VBox chats;
    public VBox messages;
    public JFXTextField newComment;
    public JFXButton addCommentBtn;
    private SupporterController supporterController = SupporterController.getInstance();
    private ArrayList<JFXButton> buttons;
    private ArrayList<Separator> separators;
    private ChatController chatController = ChatController.getInstance();
    private String chatId;
    private boolean threadStop = false;
    private boolean updateThreadStarted = false;

    MediaController mediaController = ProgramApplication.getMediaController();

    {
        mediaController.stop();
        mediaController.buyerMenu();
        buttons = new ArrayList<>();
        separators = new ArrayList<>();
        supporterMenuFxml = this;
    }

    public static void start(Stage stage) throws Exception {
        window = stage;
        Parent root = FXMLLoader.load(new File("src/main/java/client/view/graphic/fxml/accountMenus/supporter/SupporterMenuFxml.fxml").toURI().toURL());
        stage.setScene(new Scene(root, 994, 666));
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        supporterController.setSupporterChatRooms(supporterController.getAllChatRooms());
        System.out.println(supporterController.getSupporterChatRooms());
        //new Thread(supporterController::updateChatRooms);
        updateChats();
    }

    public void updateChats() {
        chats.getChildren().removeAll(buttons);
        chats.getChildren().removeAll(separators);
        buttons = new ArrayList<>();
        separators = new ArrayList<>();
        ArrayList<SupporterChatRoom> supporterChatRooms = supporterController.getSupporterChatRooms();
        System.out.println("in update chats in menu");
        System.out.println(supporterChatRooms);
        for (SupporterChatRoom supporterChatRoom : supporterChatRooms) {
            System.out.println(supporterChatRoom.getBuyer());
            if (supporterChatRoom.getBuyer()==null) {
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
                chats.getChildren().add(button);
                chats.getChildren().add(separator);
                buttons.add(button);
                separators.add(separator);
            }
        }
    }

    public void goToChat(ActionEvent actionEvent) {
        JFXButton chat = (JFXButton) actionEvent.getSource();
        chatId = chat.getId();
        try {
            if (chat.getText().equals("No buyer"))
                return;
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Node node : chats.getChildren())
            if (node instanceof JFXButton)
                ((JFXButton) node).setStyle("-fx-background-color: transparent;");
        chat.setStyle("-fx-background-color: #525d5d;");
        messages.setOpacity(1);
        newComment.setOpacity(1);
        addCommentBtn.setOpacity(1);
        updateChats();
        updateMessages();
        updateThreadStarted = true;
    }


    private void updateMessages() {
        try {
            ArrayList<ChatMessage> chatMessages = chatController.getChatMessages();
            for (ChatMessage chatMessage:chatMessages) {
                JFXButton button = new JFXButton();
                button.setTextFill(new Color(0.3632, 0.4118, 0.41406, 1));
                button.setPrefWidth(278);
                button.setPrefHeight(30);
                if (chatMessage.getType().equals(AccountType.BUYER))
                    button.setStyle("-fx-alignment: LEFT;");
                else {
                    button.setStyle("-fx-alignment: RIGHT;");
                    button.setTextFill(new Color(0.42578, 0.69140625, 0.65625, 1));
                }
                button.setText(chatMessage.getContest());
                messages.getChildren().add(button);
                updateChats();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleLogout(ActionEvent actionEvent) {
        threadStop = true;
    }

    public void handleExit(ActionEvent actionEvent) {
        threadStop = true;
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

    public void addComment(ActionEvent actionEvent) {
        String context = newComment.getText();
        try {
            if (chatId==null||context.equals(""))
                return;
            chatController.writeNewMessage(chatId, supporterController.getAccountInfo().getUsername(), context, AccountType.SUPPORTER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
