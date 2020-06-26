package view.graphic.fxml.allProductsMenu;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import controller.Main;
import controller.MainController;
import controller.product.filter.AllProductsController;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.account.*;
import model.product.Advertisement;
import model.product.Category;
import model.product.Product;
import model.product.Sale;
import view.graphic.MenuNames;
import view.graphic.ProgramApplication;
import view.graphic.alert.AlertController;
import view.graphic.alert.AlertType;
import view.graphic.fxml.accountMenus.buyer.BuyerMenuController;
import view.graphic.fxml.accountMenus.manager.ManagerMenuController;
import view.graphic.fxml.accountMenus.seller.SellerMenuController;
import view.graphic.fxml.mainMenu.FxmlMainMenu;
import view.graphic.score.Score;

import java.io.File;
import java.io.IOException;
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
    public Pane filters1;
    public TextField min;
    public TextField max;
    public TextField numericalFieldName;
    public TextField optionalField;
    public CheckBox optionalFilterBox;
    public CheckBox numericalFilterBox;
    public CheckBox filterByNameBox;
    public ChoiceBox choiceBox;
    public CheckBox categoryBox;
    public CheckBox sortByDate;
    public CheckBox sortByScores;
    public CheckBox sortByNumberOfViews;
    public JFXButton showProduct1;
    public JFXButton showProduct2;
    public JFXButton showProduct3;
    public JFXButton showProduct4;
    public JFXButton showProduct5;
    public JFXButton showProduct6;
    public JFXButton showProduct7;
    public JFXButton showProduct8;
    public JFXButton showProduct9;
    public CheckBox showOffs;
    public VBox productBox1;
    public VBox productBox2;
    public VBox productBox3;
    public VBox productBox4;
    public VBox productBox5;
    public VBox productBox6;
    public VBox productBox7;
    public VBox productBox8;
    public VBox productBox9;
    public ImageView productAddImg;
    public Text addTxt;
    public Text missTxt;
    public ImageView productAddImg2;
    public Text addTxt2;
    public Text missTxt2;
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

    private String categoryName;
    private int fromForBack;
    private static Stage mainWindow;
    private boolean offMode = false;
    private int currentAdd = 0;
    private ArrayList<Advertisement> adds;
    public static boolean key = true;

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
        adds = allProductsController.getAdvertisement();
        if (products.size() != 0)
            initializeProducts(0);
        showAdds();
        new changeAdd().start();
    }

    private void showAdds() {
        if (adds.size() > 1) {
            Product product = adds.get(1).getProduct();
            Image img1 = new Image(new File("src/main/resources/Images/" + product.getImagePath()).toURI().toString());
            productAddImg.setImage(img1);
            addTxt.setText(adds.get(1).getText());
            missTxt.setOpacity(1);
        }
        if (adds.size() > 0) {
            Advertisement add2 = adds.get(0);
            Product product = add2.getProduct();
            Image img1 = new Image(new File("src/main/resources/Images/" + product.getImagePath()).toURI().toString());
            productAddImg2.setImage(img1);
            productAddImg2.setImage(img1);
            addTxt2.setText(add2.getText());
            missTxt2.setOpacity(1);
            adds.remove(0);
        }
    }

    private void initializeCategories() {
        ArrayList<Category> allCategories = allProductsController.getAllCategories();
        ObservableList<String> categories = FXCollections.observableArrayList();
        for (Category category : allCategories) {
            categories.add(category.getName());
        }
        choiceBox.setItems(categories);
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
        fromForBack = from;
        Image img1 = new Image(new File("src/main/resources/Images/" + products.get(from).getImagePath()).toURI().toString());
        productImg1.setImage(img1);
        product1.setText(products.get(from).getName());
        product1Price.setText(Double.toString(products.get(from).getFirstPrice()));
        product1Score.setImage(new Score(products.get(from).getAverage()).getScoreImg());
        showProduct1.setOpacity(1);
        if (offMode)
            getOffHBox(productBox1, products.get(from));
        if (products.size() > (1 + from)) {
            Image img2 = new Image(new File("src/main/resources/Images/" + products.get(1 + from).getImagePath()).toURI().toString());
            productImg2.setImage(img2);
            product2.setText(products.get(1 + from).getName());
            product2Price.setText(Double.toString(products.get(1 + from).getFirstPrice()));
            product2Score.setImage(new Score(products.get(1 + from).getAverage()).getScoreImg());
            showProduct2.setOpacity(1);
            if (offMode)
                getOffHBox(productBox2, products.get(from + 1));
        }
        if (products.size() > (2 + from)) {
            Image img3 = new Image(new File("src/main/resources/Images/" + products.get(2 + from).getImagePath()).toURI().toString());
            productImg3.setImage(img3);
            product3.setText(products.get(2 + from).getName());
            product3Price.setText(Double.toString(products.get(2 + from).getFirstPrice()));
            product3Score.setImage(new Score(products.get(2 + from).getAverage()).getScoreImg());
            showProduct3.setOpacity(1);
            if (offMode)
                getOffHBox(productBox3, products.get(from + 2));
        }
        if (products.size() > (3 + from)) {
            Image img4 = new Image(new File("src/main/resources/Images/" + products.get(3 + from).getImagePath()).toURI().toString());
            productImg4.setImage(img4);
            product4.setText(products.get(3 + from).getName());
            product4Price.setText(Double.toString(products.get(3 + from).getFirstPrice()));
            product4Score.setImage(new Score(products.get(3 + from).getAverage()).getScoreImg());
            showProduct4.setOpacity(1);
            if (offMode)
                getOffHBox(productBox4, products.get(from + 3));
        }
        if (products.size() > (4 + from)) {
            Image img5 = new Image(new File("src/main/resources/Images/" + products.get(4 + from).getImagePath()).toURI().toString());
            productImg5.setImage(img5);
            product5.setText(products.get(4 + from).getName());
            product5Price.setText(Double.toString(products.get(4 + from).getFirstPrice()));
            product5Score.setImage(new Score(products.get(4 + from).getAverage()).getScoreImg());
            showProduct5.setOpacity(1);
            if (offMode)
                getOffHBox(productBox5, products.get(from + 4));
        }
        if (products.size() > (5 + from)) {
            Image img6 = new Image(new File("src/main/resources/Images/" + products.get(5 + from).getImagePath()).toURI().toString());
            productImg6.setImage(img6);
            product6.setText(products.get(5 + from).getName());
            product6Price.setText(Double.toString(products.get(5 + from).getFirstPrice()));
            product6Score.setImage(new Score(products.get(5 + from).getAverage()).getScoreImg());
            showProduct6.setOpacity(1);
            if (offMode)
                getOffHBox(productBox6, products.get(from + 5));
        }
        if (products.size() > (6 + from)) {
            Image img7 = new Image(new File("src/main/resources/Images/" + products.get(6 + from).getImagePath()).toURI().toString());
            productImg7.setImage(img7);
            product7.setText(products.get(6 + from).getName());
            product7Price.setText(Double.toString(products.get(6 + from).getFirstPrice()));
            product7Score.setImage(new Score(products.get(6 + from).getAverage()).getScoreImg());
            showProduct7.setOpacity(1);
            if (offMode)
                getOffHBox(productBox7, products.get(from + 6));
        }
        if (products.size() > (7 + from)) {
            Image img8 = new Image(new File("src/main/resources/Images/" + products.get(7 + from).getImagePath()).toURI().toString());
            productImg8.setImage(img8);
            product8.setText(products.get(7 + from).getName());
            product8Price.setText(Double.toString(products.get(7 + from).getFirstPrice()));
            product8Score.setImage(new Score(products.get(7 + from).getAverage()).getScoreImg());
            showProduct8.setOpacity(1);
            if (offMode)
                getOffHBox(productBox8, products.get(from + 7));
        }
        if (products.size() > (8 + from)) {
            Image img9 = new Image(new File("src/main/resources/Images/" + products.get(8 + from).getImagePath()).toURI().toString());
            productImg9.setImage(img9);
            product9.setText(products.get(8 + from).getName());
            product9Price.setText(Double.toString(products.get(8 + from).getFirstPrice()));
            product9Score.setImage(new Score(products.get(8 + from).getAverage()).getScoreImg());
            showProduct9.setOpacity(1);
            if (offMode)
                getOffHBox(productBox9, products.get(from + 8));
        }
    }

    public void getOffHBox(VBox offHBox, Product product) {
        HBox startDate = new HBox();
        HBox endDate = new HBox();
        HBox percentage = new HBox();
        Sale sale = Sale.getProductSale(product);
        TextField startDateTxt = new TextField();
        startDateTxt.setStyle("-fx-background-color: transparent; -fx-text-inner-color:#575957;");
        if (sale == null) {
            return;
        }
        startDateTxt.setText(" From: " + sale.getStartDate().getMonth() + "/" + sale.getStartDate().getDayOfMonth() + "/" + sale.getStartDate().getYear());
        startDate.getChildren().add(startDateTxt);

        offHBox.getChildren().add(startDate);
        TextField endDateTxt = new TextField();
        endDateTxt.setStyle("-fx-background-color: transparent; -fx-text-inner-color:#575957;");
        endDateTxt.setText(" To: " + sale.getEndDate().getMonth() + "/" + sale.getEndDate().getDayOfMonth() + "/" + sale.getEndDate().getYear());
        endDate.getChildren().add(endDateTxt);
        offHBox.getChildren().add(endDate);

        TextField percentageTxt = new TextField();
        percentageTxt.setStyle("-fx-background-color: transparent; -fx-text-inner-color:#575957;");
        percentageTxt.setText(" percentage: " + sale.getSalePercentage() * 100 + "%");
        percentage.getChildren().add(percentageTxt);
        offHBox.getChildren().add(percentage);
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
        GeneralAccount currentGeneralAccount = MainController.getInstance().getAccount();
        if (currentGeneralAccount.getGeneralAccountType().equals(GeneralAccountType.TEMP_ACCOUNT)) {
            new AlertController().create(AlertType.ERROR, "please first sign in");
            ProgramApplication.setMenu(MenuNames.REGISTERANDLOGINMENU);
        }
        Account currentAccount = (Account) currentGeneralAccount;
        if (currentAccount instanceof Seller || currentAccount instanceof Manager)
            new AlertController().create(AlertType.ERROR, "you are not a buyer!");
        else {
            try {
                BuyerMenuController.setLoadFromViewCart(true);
                BuyerMenuController.start(mainWindow);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void handleExit(ActionEvent actionEvent) {
        FxmlAllProductsMenu.key = false;
        Main.storeData();
        FxmlMainMenu.window.close();
    }

    public void handleMainMenu(ActionEvent actionEvent) {
        ProgramApplication.setMenu(MenuNames.MAINMENU);
    }

    public void handleNameFilter(ActionEvent actionEvent) throws Exception {
        String name = productName.getText();
        if (filterByNameBox.isSelected()) {
            ArrayList<String> details = new ArrayList<>();
            details.add(name);
            allProductsController.filterByProductName(details);
        } else {
            allProductsController.disAbleFilter(name);
            productName.clear();
        }
        products = allProductsController.getProducts();
        deleteProducts();
        initializeProducts(0);
    }

    public void handleNumericalFieldFilter(ActionEvent actionEvent) throws Exception {
        String name = numericalFieldName.getText();
        if (numericalFilterBox.isSelected()) {
            String MIN = min.getText();
            String MAX = max.getText();
            ArrayList<String> details = new ArrayList<>();
            details.add(name);
            details.add(MIN);
            details.add(MAX);
            allProductsController.filterByNumericalFilter(details);
        } else {
            allProductsController.disAbleFilter(name);
            numericalFieldName.clear();
            min.clear();
            max.clear();
        }
        products = allProductsController.getProducts();
        deleteProducts();
        initializeProducts(0);
    }

    public void handleOptionalFieldFilter(ActionEvent actionEvent) throws Exception {
        if (optionalFilterBox.isSelected()) {
            String all = optionalField.getText();
            String[] strings = all.split("\\s+");
            ArrayList<String> details = new ArrayList<>();
            details.add(strings[0]);
            for (int i = 2; i < strings.length; i++) {
                details.add(strings[i]);
            }
            allProductsController.filterByOptionalFilter(details);
        } else {
            String all = optionalField.getText();
            String[] strings = all.split("\\s+");
            allProductsController.disAbleFilter(strings[0]);
            optionalField.clear();
        }
        products = allProductsController.getProducts();
        deleteProducts();
        initializeProducts(0);
    }

    public void handleCategoryFilter(ActionEvent actionEvent) throws Exception {
        ArrayList<String> details = new ArrayList<>();
        if (categoryBox.isSelected()) {
            categoryName = choiceBox.getSelectionModel().getSelectedItem().toString();
            details.add(categoryName);
            allProductsController.filterByCategory(details);
        } else {
            Category category = Category.getCategoryByName(categoryName);
            disableCategoryFields(category);
        }
        products = allProductsController.getProducts();
        deleteProducts();
        initializeProducts(0);
    }

    private void deleteProducts() {
        productImg1.setImage(null);
        product1.setText("");
        product1Price.setText("");
        product1Score.setImage(null);
        showProduct1.setOpacity(0);
        productImg2.setImage(null);
        product2.setText("");
        product2Price.setText("");
        product2Score.setImage(null);
        showProduct2.setOpacity(0);
        productImg3.setImage(null);
        product3.setText("");
        product3Price.setText("");
        product3Score.setImage(null);
        showProduct3.setOpacity(0);
        productImg4.setImage(null);
        product4.setText("");
        product4Price.setText("");
        product4Score.setImage(null);
        showProduct4.setOpacity(0);
        productImg5.setImage(null);
        product5.setText("");
        product5Price.setText("");
        product5Score.setImage(null);
        showProduct5.setOpacity(0);
        productImg6.setImage(null);
        product6.setText("");
        product6Price.setText("");
        product6Score.setImage(null);
        showProduct6.setOpacity(0);
        productImg7.setImage(null);
        product7.setText("");
        product7Price.setText("");
        product7Score.setImage(null);
        showProduct7.setOpacity(0);
        productImg8.setImage(null);
        product8.setText("");
        product8Price.setText("");
        product8Score.setImage(null);
        showProduct8.setOpacity(0);
        productImg9.setImage(null);
        product9.setText("");
        product9Price.setText("");
        product9Score.setImage(null);
        showProduct9.setOpacity(0);
    }

    private void disableCategoryFields(Category category) throws Exception {
        allProductsController.disableCategoryFields(category);
    }

    public void handleDateSort(ActionEvent actionEvent) throws Exception {
        if (sortByDate.isSelected()) {
            allProductsController.changeSort("ByDates");
            products = allProductsController.showProducts();
        } else {
            allProductsController.disableSort();
            products = allProductsController.getProducts();
        }
        deleteProducts();
        initializeProducts(0);
    }

    public void handleScoresSort(ActionEvent actionEvent) throws Exception {
        if (sortByScores.isSelected()) {
            allProductsController.changeSort("ByScores");
            products = allProductsController.showProducts();
        } else {
            allProductsController.disableSort();
            products = allProductsController.getProducts();
        }
        deleteProducts();
        initializeProducts(0);
    }

    public void handleNumberOfViewsSort(ActionEvent actionEvent) throws Exception {
        if (sortByNumberOfViews.isSelected()) {
            allProductsController.changeSort("ByNumberOfViews");
            products = allProductsController.showProducts();
        } else {
            allProductsController.disableSort();
            products = allProductsController.getProducts();
        }
        deleteProducts();
        initializeProducts(0);
    }

    public void handleNextButton(ActionEvent actionEvent) {
        if (allProductsController.getProducts().size() > fromForBack + 9) {
            fromForBack = fromForBack + 9;
            deleteProducts();
            initializeProducts(fromForBack);
        }
    }

    public void handleBackButton(ActionEvent actionEvent) {
        fromForBack = fromForBack - 9;
        if (fromForBack >= 0) {
            deleteProducts();
            initializeProducts(fromForBack);
        }
    }

    public void showProduct1(ActionEvent actionEvent) {
        ProgramApplication.addToHistory(MenuNames.ALLPRODUCTSMENU);
        ProductMenuFxml.setCurrentProduct(products.get(fromForBack));
        Parent root = null;
        try {
            root = FXMLLoader.load(new File("src/main/java/view/graphic/fxml/allProductsMenu/productMenuFxml.fxml").toURI().toURL());
            mainWindow.setScene(new Scene(root, 994, 666));
            mainWindow.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showProduct2(ActionEvent actionEvent) {
        ProgramApplication.addToHistory(MenuNames.ALLPRODUCTSMENU);
        ProductMenuFxml.setCurrentProduct(products.get(fromForBack + 1));
        Parent root = null;
        try {
            root = FXMLLoader.load(new File("src/main/java/view/graphic/fxml/allProductsMenu/productMenuFxml.fxml").toURI().toURL());
            mainWindow.setScene(new Scene(root, 994, 666));
            mainWindow.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showProduct3(ActionEvent actionEvent) {
        ProgramApplication.addToHistory(MenuNames.ALLPRODUCTSMENU);
        ProductMenuFxml.setCurrentProduct(products.get(fromForBack + 2));
        Parent root = null;
        try {
            root = FXMLLoader.load(new File("src/main/java/view/graphic/fxml/allProductsMenu/productMenuFxml.fxml").toURI().toURL());
            mainWindow.setScene(new Scene(root, 994, 666));
            mainWindow.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showProduct4(ActionEvent actionEvent) {
        ProgramApplication.addToHistory(MenuNames.ALLPRODUCTSMENU);
        ProductMenuFxml.setCurrentProduct(products.get(fromForBack + 3));
        Parent root = null;
        try {
            root = FXMLLoader.load(new File("src/main/java/view/graphic/fxml/allProductsMenu/productMenuFxml.fxml").toURI().toURL());
            mainWindow.setScene(new Scene(root, 994, 666));
            mainWindow.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showProduct5(ActionEvent actionEvent) {
        ProgramApplication.addToHistory(MenuNames.ALLPRODUCTSMENU);
        ProductMenuFxml.setCurrentProduct(products.get(fromForBack + 4));
        Parent root = null;
        try {
            root = FXMLLoader.load(new File("src/main/java/view/graphic/fxml/allProductsMenu/productMenuFxml.fxml").toURI().toURL());
            mainWindow.setScene(new Scene(root, 994, 666));
            mainWindow.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showProduct6(ActionEvent actionEvent) {
        ProgramApplication.addToHistory(MenuNames.ALLPRODUCTSMENU);
        ProductMenuFxml.setCurrentProduct(products.get(fromForBack + 5));
        Parent root = null;
        try {
            root = FXMLLoader.load(new File("src/main/java/view/graphic/fxml/allProductsMenu/productMenuFxml.fxml").toURI().toURL());
            mainWindow.setScene(new Scene(root, 994, 666));
            mainWindow.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showProduct7(ActionEvent actionEvent) {
        ProgramApplication.addToHistory(MenuNames.ALLPRODUCTSMENU);
        ProductMenuFxml.setCurrentProduct(products.get(fromForBack + 6));
        Parent root = null;
        try {
            root = FXMLLoader.load(new File("src/main/java/view/graphic/fxml/allProductsMenu/productMenuFxml.fxml").toURI().toURL());
            mainWindow.setScene(new Scene(root, 994, 666));
            mainWindow.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showProduct8(ActionEvent actionEvent) {
        ProgramApplication.addToHistory(MenuNames.ALLPRODUCTSMENU);
        ProductMenuFxml.setCurrentProduct(products.get(fromForBack + 7));
        Parent root = null;
        try {
            root = FXMLLoader.load(new File("src/main/java/view/graphic/fxml/allProductsMenu/productMenuFxml.fxml").toURI().toURL());
            mainWindow.setScene(new Scene(root, 994, 666));
            mainWindow.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showProduct9(ActionEvent actionEvent) {
        ProgramApplication.addToHistory(MenuNames.ALLPRODUCTSMENU);
        ProductMenuFxml.setCurrentProduct(products.get(fromForBack + 8));
        Parent root = null;
        try {
            root = FXMLLoader.load(new File("src/main/java/view/graphic/fxml/allProductsMenu/productMenuFxml.fxml").toURI().toURL());
            mainWindow.setScene(new Scene(root, 994, 666));
            mainWindow.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setMainWindow(Stage window) {
        mainWindow = window;
    }

    public void handleAccountMenu(ActionEvent actionEvent) {
        GeneralAccount generalAccount = MainController.getInstance().getAccount();
        if (generalAccount instanceof TempAccount) {
            new AlertController().create(AlertType.ERROR, "please login first");
        } else if (generalAccount instanceof Seller) {
            try {
                SellerMenuController.start(mainWindow);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (generalAccount instanceof Buyer) {
            try {
                BuyerMenuController.start(mainWindow);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                ManagerMenuController.start(mainWindow);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void handleShowOffs(ActionEvent actionEvent) {
        if (!offMode) {
            offMode = true;
            ArrayList<Sale> sales = Sale.getAllSales();
            ArrayList<Product> productsSale = new ArrayList<>();
            for (Sale sale : sales)
                productsSale.addAll(sale.getProducts());
            products = productsSale;
            deleteProducts();
            if (products.size() != 0)
                initializeProducts(0);
        } else {
            offMode = false;
            try {
                products = allProductsController.showProducts();
                deleteProducts();
                deleteOffBoxes();
                if (products.size() != 0)
                    initializeProducts(0);
            } catch (Exception e) {
                new AlertController().create(AlertType.ERROR, e.getMessage());
            }
        }
    }

    public void deleteOffBoxes() {
        deleteOffBox(productBox1);
        deleteOffBox(productBox2);
        deleteOffBox(productBox3);
        deleteOffBox(productBox4);
        deleteOffBox(productBox5);
        deleteOffBox(productBox6);
        deleteOffBox(productBox7);
        deleteOffBox(productBox8);
        deleteOffBox(productBox9);
    }

    public void deleteOffBox(VBox vBox) {
        ArrayList<Node> deleteNodes = new ArrayList<>();
        for (Node node : vBox.getChildren()) {
            if (node instanceof HBox)
                deleteNodes.add(node);
        }
        vBox.getChildren().removeAll(deleteNodes);
    }

    public void handleNextAdd(ActionEvent actionEvent) {
        currentAdd++;
        if (adds.size() - 1 < currentAdd) {
            currentAdd = 0;
        }
        Product product = adds.get(currentAdd).getProduct();
        FadeTransition transition = new FadeTransition(Duration.seconds(1), productAddImg);
        transition.setToValue(0);
        transition.play();
        transition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Image img1 = new Image(new File("src/main/resources/Images/" + product.getImagePath()).toURI().toString());
                productAddImg.setOpacity(0);
                productAddImg.setImage(img1);
                addTxt.setText(adds.get(currentAdd).getText());
                FadeTransition transition = new FadeTransition(Duration.seconds(1), productAddImg);
                transition.setToValue(1);
                transition.play();
            }
        });
    }

    public class changeAdd extends Thread {
        @Override
        public void run() {
            while (key) {
                try {
                    sleep(5000);
                    try {
                        handleNextAdd(null);
                    } catch (Exception e) {
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
