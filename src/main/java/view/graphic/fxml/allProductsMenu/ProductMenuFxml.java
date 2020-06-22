package view.graphic.fxml.allProductsMenu;

import com.jfoenix.controls.JFXTextArea;
import controller.product.ProductController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import model.account.Seller;
import model.product.Field.Field;
import model.product.Field.NumericalField;
import model.product.Field.OptionalField;
import model.product.Product;
import view.graphic.ProgramApplication;
import view.graphic.score.Score;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductMenuFxml implements Initializable {
    public ImageView productImg;
    public Text productName;
    private static Product currentProduct;
    private static ProductController productController;
    public ImageView score;
    public Text description;
    public JFXTextArea fields;
    public JFXTextArea sellers;

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
        for (Seller seller:currentProduct.getSellers())
            sellers.appendText(seller.getUsername());
        for (Field field:currentProduct.getGeneralFields()) {
            if (field instanceof NumericalField) {
                fields.appendText(field.getName() + ": " + ((NumericalField)field).getNumericalField());
            } else {
                StringBuilder optionalFields = new StringBuilder();
                for (String str: ((OptionalField)field).getOptionalFiled())
                    optionalFields.append(str).append(", ");
                fields.appendText(field.getName() + ": " + optionalFields);
            }
        }
    }

    public static void setCurrentProduct(Product currentProduct) {
        ProductMenuFxml.currentProduct = currentProduct;
        productController = new ProductController(currentProduct);
    }
}
