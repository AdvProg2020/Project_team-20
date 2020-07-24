package client.view.graphic.fxml.accountMenus.buyer;

import client.controller.auction.AuctionController;
import client.model.auction.Auction;
import client.model.receipt.BuyerReceipt;
import client.view.graphic.alert.AlertController;
import client.view.graphic.alert.AlertType;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewAuctionsFxml implements Initializable {
    public TableView<Auction> auctionsTable;
    public TableColumn<String, Auction> auctionsColumn;
    private static Stage window;
    public TextField moneyTxt;
    private AuctionController auctionController = AuctionController.getInstance();

    public void handleJoin(ActionEvent actionEvent) {
        Auction auction = auctionsTable.getSelectionModel().getSelectedItem();
        double price =  Double.parseDouble(moneyTxt.getText());
        if (auction==null)
            return;
        Parent root;
        try {
            AuctionFxml.setAuctionID(auction.getId());
            AuctionFxml.setWindow(window);
            auctionController.addBuyer(auction.getId(), price);
            root = FXMLLoader.load(new File("src/main/java/client/view/graphic/fxml/accountMenus/buyer/auctionFxml.fxml").toURI().toURL());
            window.setScene(new Scene(root, 994, 666));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            auctionsTable.getItems().setAll(auctionController.getAllAuctions());
            auctionsColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }

    public static void setWindow(Stage window) {
        ViewAuctionsFxml.window = window;
    }
}
