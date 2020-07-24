package client.view.graphic.fxml.accountMenus.buyer;

import client.controller.MainController;
import client.controller.account.LoginController;
import client.controller.auction.AuctionController;
import client.model.account.AccountType;
import client.model.account.Buyer;
import client.model.product.Product;
import client.network.Message;
import client.network.chat.ChatMessage;
import client.view.graphic.alert.AlertController;
import client.view.graphic.alert.AlertType;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AuctionFxml implements Initializable {
    private static String auctionID;
    private static String buyerUsername;
    public VBox messagesChats;
    public JFXTextField newComment;
    public JFXButton addCommentBtn;
    public JFXTextField moneyIncTxt;
    public ImageView productImg;
    public Text productNameTxt;
    public Text productDetailsTxt;
    public Text highestPrice;
    private ArrayList<JFXButton> buttons;
    private AuctionController auctionController;
    private boolean threadStop = true;
    private static Stage window;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        auctionController = AuctionController.getInstance();
        buyerUsername = ((Buyer)MainController.getInstance().getAccount()).getUsername();
        updateMessages();
        try {
            Product product = auctionController.getProduct(auctionID);
            Image img = new Image(new File("src/main/resources/Images/" + product.getImagePath()).toURI().toString());
            productImg.setImage(img);
            productDetailsTxt.setText(product.getDescription());
            productNameTxt.setText(product.getName());
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    Runnable updater = new Runnable() {

                        @Override
                        public void run() {
                            updateMessages();
                            try {
                                Message message = auctionController.isEnded(auctionID);
                                processAuctionEnd(message);
                                highestPrice.setText(Double.toString(auctionController.getHighestPrice(auctionID)));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
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

    private void processAuctionEnd(Message message) {
        if (message.getText().equals("No"))
            return;
        if (message.getText().equals("Won"))
            new AlertController().create(AlertType.INFO, "You won! Thanks for buying.");
        if (message.getText().equals("Lost"))
            new AlertController().create(AlertType.INFO, "You Lost!");
        try {
            BuyerMenuController.start(window);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateMessages() {
        try {
            ArrayList<ChatMessage> chatMessages = auctionController.getAllMessages(auctionID);
            messagesChats.getChildren().removeAll(buttons);
            buttons = new ArrayList<>();
            for (ChatMessage chatMessage:chatMessages) {
                JFXButton button = new JFXButton();
                button.setTextFill(new Color(0.3632, 0.4118, 0.41406, 1));
                button.setPrefWidth(278);
                button.setPrefHeight(30);
                if (chatMessage.getSenderName().equals(buyerUsername))
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

    public void addComment(ActionEvent actionEvent) {
        String context = newComment.getText();
        try {
            if (context.equals(""))
                return;
            newComment.setText("");
            auctionController.addMessage(auctionID, context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mainTabBtnEnter(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: transparent; -fx-border-color: white; -fx-border-radius: 3;");
    }

    public void mainTabBtnExit(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: transparent;");
    }

    public void handleExit(ActionEvent actionEvent) {
        try {
            auctionController.removeBuyer(auctionID);
            new AlertController().create(AlertType.INFO, "If you leave the auction you will be removed from list of buyers!");
            BuyerMenuController.start(window);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setAuctionID(String auctionID) {
        AuctionFxml.auctionID = auctionID;
    }

    public static void setBuyerUsername(String buyerUsername) {
        AuctionFxml.buyerUsername = buyerUsername;
    }

    public void handleIncrease(ActionEvent actionEvent) {
        if (moneyIncTxt.getText().equals(""))
            return;
        double moneyInc = Double.parseDouble(moneyIncTxt.getText());
        try {
            auctionController.increaseBuyerPrice(auctionID, moneyInc);
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }

    public static void setWindow(Stage window) {
        AuctionFxml.window = window;
    }
}
