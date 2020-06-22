package view.graphic.fxml.allProductsMenu;

import com.jfoenix.controls.JFXCheckBox;
import controller.Main;
import controller.product.filter.AllProductsController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.product.Category;
import model.product.Product;
import view.graphic.MenuNames;
import view.graphic.ProgramApplication;
import view.graphic.alert.AlertController;
import view.graphic.alert.AlertType;
import view.graphic.fxml.mainMenu.FxmlMainMenu;
import view.graphic.score.Score;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FxmlAllProductsMenu implements Initializable {
    public Pane categories;
    public Text product1Price;
    public ImageView product1Score;
    public Text product2Price;
    public ImageView product2Score;
    public Text product3Price;
    public ImageView product3Score;
    public Text product4Price;
    public ImageView product4Score;
    public Text product5Price;
    public ImageView product5Score;
    public Text product6Price;
    public ImageView product6Score;
    public Text product7Price;
    public ImageView product7Score;
    public Text product8Price;
    public ImageView product8Score;
    public Text product9Price;
    public ImageView product9Score;
    public Pane filters;
    public Pane sortingPart;
    public TextField productName;
    public Button filterByNameButton;
    public Pane filters1;
    public Button numericalFilterButton;
    public TextField min;
    public TextField max;
    public TextField numericalFieldName;
    public TextField optionalField;
    public Button optionalFilterButton;
    private AllProductsController allProductsController = AllProductsController.getInstance();
    private static ArrayList<Product> products;

    public ImageView productImg1;
    public Text product1;
    public ImageView productImg2;
    public Text product2;
    public ImageView productImg3;
    public Text product3;
    public ImageView productImg4;
    public Text product4;
    public ImageView productImg5;
    public Text product5;
    public ImageView productImg6;
    public Text product6;
    public ImageView productImg7;
    public Text product7;
    public ImageView productImg8;
    public Text product8;
    public ImageView productImg9;
    public Text product9;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            products = allProductsController.showProducts();
            initializeCategories();
        } catch (Exception e) {
            try {
                new AlertController().create(AlertType.ERROR, e.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (products.size() != 0)
            initializeProducts(0);
    }

    private void initializeCategories() {
        ArrayList<Category> allCategories = allProductsController.getAllCategories();
        for (Category category : allCategories) {
            JFXCheckBox jfxCheckBox = new JFXCheckBox();
            jfxCheckBox.setText(category.getName());
            jfxCheckBox.setOnMouseClicked(this::handleAddCategory);
            categories.getChildren().add(jfxCheckBox);
        }
    }

    private void handleAddCategory(MouseEvent event) {
        String name = ((JFXCheckBox) event.getSource()).getText();
        ArrayList<String> details = new ArrayList<>();
        details.add(name);
        try {
            allProductsController.filter("category", details);
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }


    private void initializeProducts(int from) {
        Image img1 = new Image(new File("src/main/resources/Images/" + products.get(from).getImagePath()).toURI().toString());
        productImg1.setImage(img1);
        product1.setText(products.get(from).getName());
        product1Price.setText(Double.toString(products.get(from).getFirstPrice()));
        product1Score.setImage(new Score(products.get(from).getAverage()).getScoreImg());

        if (products.size() > (1 + from)) {
            Image img2 = new Image(new File("src/main/resources/Images/" + products.get(1 + from).getImagePath()).toURI().toString());
            productImg2.setImage(img2);
            product2.setText(products.get(1 + from).getName());
            product2Price.setText(Double.toString(products.get(1 + from).getFirstPrice()));
            product2Score.setImage(new Score(products.get(1 + from).getAverage()).getScoreImg());
        }
        if (products.size() > (2 + from)) {
            Image img3 = new Image(new File("src/main/resources/Images/" + products.get(2 + from).getImagePath()).toURI().toString());
            productImg3.setImage(img3);
            product3.setText(products.get(2 + from).getName());
            product3Price.setText(Double.toString(products.get(2 + from).getFirstPrice()));
            product3Score.setImage(new Score(products.get(2 + from).getAverage()).getScoreImg());
        }
        if (products.size() > (3 + from)) {
            Image img4 = new Image(new File("src/main/resources/Images/" + products.get(3 + from).getImagePath()).toURI().toString());
            productImg4.setImage(img4);
            product4.setText(products.get(3 + from).getName());
            product4Price.setText(Double.toString(products.get(3 + from).getFirstPrice()));
            product4Score.setImage(new Score(products.get(3 + from).getAverage()).getScoreImg());
        }
        if (products.size() > (4 + from)) {
            Image img5 = new Image(new File("src/main/resources/Images/" + products.get(4 + from).getImagePath()).toURI().toString());
            productImg5.setImage(img5);
            product5.setText(products.get(4 + from).getName());
            product5Price.setText(Double.toString(products.get(4 + from).getFirstPrice()));
            product5Score.setImage(new Score(products.get(4 + from).getAverage()).getScoreImg());
        }
        if (products.size() > (5 + from)) {
            Image img6 = new Image(new File("src/main/resources/Images/" + products.get(5 + from).getImagePath()).toURI().toString());
            productImg6.setImage(img6);
            product6.setText(products.get(5 + from).getName());
            product6Price.setText(Double.toString(products.get(5 + from).getFirstPrice()));
            product6Score.setImage(new Score(products.get(5 + from).getAverage()).getScoreImg());
        }
        if (products.size() > (6 + from)) {
            Image img7 = new Image(new File("src/main/resources/Images/" + products.get(6 + from).getImagePath()).toURI().toString());
            productImg7.setImage(img7);
            product7.setText(products.get(6 + from).getName());
            product7Price.setText(Double.toString(products.get(6 + from).getFirstPrice()));
            product7Score.setImage(new Score(products.get(6 + from).getAverage()).getScoreImg());
        }
        if (products.size() > (7 + from)) {
            Image img8 = new Image(new File("src/main/resources/Images/" + products.get(7 + from).getImagePath()).toURI().toString());
            productImg8.setImage(img8);
            product8.setText(products.get(7 + from).getName());
            product8Price.setText(Double.toString(products.get(7 + from).getFirstPrice()));
            product8Score.setImage(new Score(products.get(7 + from).getAverage()).getScoreImg());
        }
        if (products.size() > (8 + from)) {
            Image img9 = new Image(new File("src/main/resources/Images/" + products.get(8 + from).getImagePath()).toURI().toString());
            productImg9.setImage(img9);
            product9.setText(products.get(8 + from).getName());
            product9Price.setText(Double.toString(products.get(8 + from).getFirstPrice()));
            product9Score.setImage(new Score(products.get(8 + from).getAverage()).getScoreImg());
        }
    }

    public void mainTabBtnEnter(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: transparent; -fx-border-color: white; -fx-border-radius: 3;");
    }

    public void mainTabBtnExit(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: transparent;");
    }

    public void handleLogin(ActionEvent actionEvent) {
        ProgramApplication.setMenu(MenuNames.REGISTERANDLOGINMENU);
    }

    public void handleViewCart(ActionEvent actionEvent) {
    }

    public void handleExit(ActionEvent actionEvent) {
        Main.storeData();
        FxmlMainMenu.window.close();
    }

    public void handleMainMenu(ActionEvent actionEvent) {
        ProgramApplication.setMenu(MenuNames.MAINMENU);
    }

    public void handleFilterByName(ActionEvent actionEvent) {
        String name = productName.getText();
        ArrayList<String> details = new ArrayList<>();
        details.add(name);
        allProductsController.filterByProductName(details);
        //not sure
        initializeProducts(0);
    }

    public void handleNumericalFilter(ActionEvent actionEvent) {
        String name = numericalFieldName.getText();
        String MIN = min.getText();
        String MAX = max.getText();
        ArrayList<String> details = new ArrayList<>();
        details.add(name);
        details.add(MIN);
        details.add(MAX);
        allProductsController.filterByNumericalFilter(details);
        //not sure
        initializeProducts(0);
    }

    public void handleOptionalFilter(ActionEvent actionEvent) {
        String all = optionalField.getText();
        String[] strings = all.split("\\s+");
        ArrayList<String> details = new ArrayList<>();
        details.add(strings[0]);
        for(int i = 1 ; i<strings.length ; i++){
            details.add(strings[i]);
        }
        allProductsController.filterByOptionalFilter(details);
        //not sure
        initializeProducts(0);
    }
}
