package view.graphic.fxml.allProductsMenu;

import com.jfoenix.controls.JFXTextArea;
import controller.account.user.ManagerController;
import controller.product.ProductController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import model.Requestable;
import model.account.Seller;
import model.product.Field.Field;
import model.product.Field.NumericalField;
import model.product.Field.OptionalField;
import model.product.Product;
import model.product.comment.Comment;
import view.graphic.ProgramApplication;
import view.graphic.fxml.accountMenus.manager.RequestTable;
import view.graphic.score.Score;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ProductMenuFxml implements Initializable {
    public ImageView productImg;
    public Text productName;
    private static Product currentProduct;
    private static ProductController productController;
    public ImageView score;
    public Text description;
    public JFXTextArea fields;
    public ListView<String> sellers;
    public ObservableList<String> sellerUserNames =
            FXCollections.observableArrayList();
    public Text price;
    public TableView commentTable;
    public TableColumn usernameColumn;
    public TableColumn commentColumn;
    public TextField comment;
    public TextField replyToComment;
    private Seller seller;

    public void handleLogin(ActionEvent actionEvent) {
    }

    public void mainTabBtnEnter(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: transparent; -fx-border-color: white; -fx-border-radius: 3;");
    }

    public void mainTabBtnExit(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: transparent;");
    }

    public void handleBack(ActionEvent actionEvent) {
        ProgramApplication.back();
    }

    public void handleExit(ActionEvent actionEvent) {
    }

    public void handleMainMenu(ActionEvent actionEvent) {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image img = new Image(new File("src/main/resources/Images/" + currentProduct.getImagePath()).toURI().toString());
        productImg.setImage(img);
        productName.setText(currentProduct.getName());
        description.setText(currentProduct.getDescription());
        score.setImage(new Score(currentProduct.getAverage()).getScoreImg());

        for (Field field:currentProduct.getGeneralFields()) {
            if (field instanceof NumericalField) {
                fields.appendText(field.getName() + ": " + ((NumericalField)field).getNumericalField() + "\n");
            } else {
                StringBuilder optionalFields = new StringBuilder();
                for (String str: ((OptionalField)field).getOptionalFiled())
                    optionalFields.append(str).append(", ");
                fields.appendText(field.getName() + ": " + optionalFields + "\n");
            }
        }
        fields.setEditable(false);

        for (Seller seller:currentProduct.getSellers())
            sellerUserNames.add(seller.getUsername());
        sellers.setItems(sellerUserNames);


    }

    public static void setCurrentProduct(Product currentProduct) {
        ProductMenuFxml.currentProduct = currentProduct;
        productController = new ProductController(currentProduct);
    }


    public void selectSeller(MouseEvent event) {
        String selected = sellers.getSelectionModel().getSelectedItem();
        seller = currentProduct.getSellerByUsername(selected);
        price.setText(Double.toString(currentProduct.getPrice(seller)));
    }

    public void handleAddToCart(ActionEvent actionEvent) {
    }

    public void handleComment(ActionEvent actionEvent) {
    }

    public void handleReply(ActionEvent actionEvent) {
    }
}
