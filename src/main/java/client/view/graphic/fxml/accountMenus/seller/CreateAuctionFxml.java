package client.view.graphic.fxml.accountMenus.seller;

import client.controller.account.user.seller.SellerController;
import client.model.account.Manager;
import client.view.graphic.alert.AlertController;
import client.view.graphic.alert.AlertType;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CreateAuctionFxml {
    public TextField productName;
    public TextField productCompany;
    public ImageView productImg;
    public JFXButton create;
    public JFXDatePicker endDatePicker;
    public String path;
    public JFXTimePicker endTimePicker;

    public void handleDragOver(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.ANY);
        }
    }

    public void handleDrop(DragEvent event) {
        List<File> files = event.getDragboard().getFiles();
        Image img = null;
        try {
            img = new Image(new FileInputStream((files.get(0))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        productImg.setImage(img);
        File outputFile = new File("src/main/resources/Images/product" + Manager.getNumberOfRequests() + ".png");
        path = "product" + Manager.getNumberOfRequests() + ".png";
        BufferedImage bImage = SwingFXUtils.fromFXImage(img, null);
        try {
            ImageIO.write(bImage, "png", outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createProductButton(ActionEvent actionEvent) {
        try {
            ArrayList<String> details = new ArrayList<>();
            if (path==null || productName.getText().equals("") || productCompany.getText().equals("")) {
                new AlertController().create(AlertType.ERROR, "Fill the blanks");
                return;
            }
            details.add(productName.getText());
            details.add(productCompany.getText());
            LocalDate endDate = endDatePicker.getValue();
            LocalTime endTime = endTimePicker.getValue();
            LocalDateTime endLocalDateTime = LocalDateTime.of(endDate, endTime);

            SellerController.getInstance().createAuction(details, path, endLocalDateTime);
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }

    private void resetMenu() {
        productName.setText("");
        productCompany.setText("");
        productImg.setImage(null);
        path = null;
        endDatePicker.setValue(null);
    }

}
