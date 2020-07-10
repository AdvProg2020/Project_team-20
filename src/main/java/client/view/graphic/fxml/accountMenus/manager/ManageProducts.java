package client.view.graphic.fxml.accountMenus.manager;

import com.jfoenix.controls.JFXButton;
import client.controller.MediaController;
import client.controller.account.user.ManagerController;
import client.controller.product.filter.AllProductsController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import client.model.product.Product;
import client.view.graphic.ProgramApplication;
import client.view.graphic.alert.AlertController;
import client.view.graphic.alert.AlertType;
import client.view.graphic.score.Score;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManageProducts implements Initializable {
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
    public TextField productName;
    public JFXButton removeProduct1;
    public JFXButton removeProduct2;
    public JFXButton removeProduct3;
    public JFXButton removeProduct4;
    public JFXButton removeProduct5;
    public JFXButton removeProduct6;
    public JFXButton removeProduct7;
    public JFXButton removeProduct8;
    public JFXButton removeProduct9;
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

    private int fromForBack;
    ManagerController managerController = ManagerController.getInstance();

    MediaController mediaController = ProgramApplication.getMediaController();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            products = allProductsController.showProducts();
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

    private void initializeProducts(int from) {
        fromForBack = from;
        Image img1 = new Image(new File("src/main/resources/Images/" + products.get(from).getImagePath()).toURI().toString());
        productImg1.setImage(img1);
        product1.setText(products.get(from).getName());
        product1Price.setText(Double.toString(products.get(from).getFirstPrice()));
        product1Score.setImage(new Score(products.get(from).getAverage()).getScoreImg());
        removeProduct1.setOpacity(1);
        setProduct2(from);
        setProduct3(from);
        setProduct4(from);
        setProduct5(from);
        setProduct6(from);
        setProduct7(from);
        setProduct8(from);
        setProduct9(from);
    }

    private void setProduct2(int from) {
        if (products.size() > (1 + from)) {
            Image img2 = new Image(new File("src/main/resources/Images/" + products.get(1 + from).getImagePath()).toURI().toString());
            productImg2.setImage(img2);
            product2.setText(products.get(1 + from).getName());
            product2Price.setText(Double.toString(products.get(1 + from).getFirstPrice()));
            product2Score.setImage(new Score(products.get(1 + from).getAverage()).getScoreImg());
            removeProduct2.setOpacity(1);
        } else {
            productImg2.setImage(null);
            product2.setText("");
            product2Price.setText("");
            product2Score.setImage(null);
            removeProduct2.setOpacity(0);
        }
    }

    private void setProduct3(int from) {
        if (products.size() > (2 + from)) {
            Image img3 = new Image(new File("src/main/resources/Images/" + products.get(2 + from).getImagePath()).toURI().toString());
            productImg3.setImage(img3);
            product3.setText(products.get(2 + from).getName());
            product3Price.setText(Double.toString(products.get(2 + from).getFirstPrice()));
            product3Score.setImage(new Score(products.get(2 + from).getAverage()).getScoreImg());
            removeProduct3.setOpacity(1);
        } else {
            productImg3.setImage(null);
            product3.setText("");
            product3Price.setText("");
            product3Score.setImage(null);
            removeProduct3.setOpacity(0);
        }
    }

    private void setProduct4(int from) {
        if (products.size() > (3 + from)) {
            Image img4 = new Image(new File("src/main/resources/Images/" + products.get(3 + from).getImagePath()).toURI().toString());
            productImg4.setImage(img4);
            product4.setText(products.get(3 + from).getName());
            product4Price.setText(Double.toString(products.get(3 + from).getFirstPrice()));
            product4Score.setImage(new Score(products.get(3 + from).getAverage()).getScoreImg());
            removeProduct4.setOpacity(1);
        } else {
            productImg4.setImage(null);
            product4.setText("");
            product4Price.setText("");
            product4Score.setImage(null);
            removeProduct4.setOpacity(0);
        }
    }

    private void setProduct5(int from) {
        if (products.size() > (4 + from)) {
            Image img5 = new Image(new File("src/main/resources/Images/" + products.get(4 + from).getImagePath()).toURI().toString());
            productImg5.setImage(img5);
            product5.setText(products.get(4 + from).getName());
            product5Price.setText(Double.toString(products.get(4 + from).getFirstPrice()));
            product5Score.setImage(new Score(products.get(4 + from).getAverage()).getScoreImg());
            removeProduct5.setOpacity(1);
        } else {
            productImg5.setImage(null);
            product5.setText("");
            product5Price.setText("");
            product5Score.setImage(null);
            removeProduct5.setOpacity(0);
        }
    }

    private void setProduct6(int from) {
        if (products.size() > (5 + from)) {
            Image img6 = new Image(new File("src/main/resources/Images/" + products.get(5 + from).getImagePath()).toURI().toString());
            productImg6.setImage(img6);
            product6.setText(products.get(5 + from).getName());
            product6Price.setText(Double.toString(products.get(5 + from).getFirstPrice()));
            product6Score.setImage(new Score(products.get(5 + from).getAverage()).getScoreImg());
            removeProduct6.setOpacity(1);
        } else {
            productImg6.setImage(null);
            product6.setText("");
            product6Price.setText("");
            product6Score.setImage(null);
            removeProduct6.setOpacity(0);
        }
    }

    private void setProduct7(int from) {
        if (products.size() > (6 + from)) {
            Image img7 = new Image(new File("src/main/resources/Images/" + products.get(6 + from).getImagePath()).toURI().toString());
            productImg7.setImage(img7);
            product7.setText(products.get(6 + from).getName());
            product7Price.setText(Double.toString(products.get(6 + from).getFirstPrice()));
            product7Score.setImage(new Score(products.get(6 + from).getAverage()).getScoreImg());
            removeProduct7.setOpacity(1);
        } else {
            productImg7.setImage(null);
            product7.setText("");
            product7Price.setText("");
            product7Score.setImage(null);
            removeProduct7.setOpacity(0);
        }
    }

    private void setProduct8(int from) {
        if (products.size() > (7 + from)) {
            Image img8 = new Image(new File("src/main/resources/Images/" + products.get(7 + from).getImagePath()).toURI().toString());
            productImg8.setImage(img8);
            product8.setText(products.get(7 + from).getName());
            product8Price.setText(Double.toString(products.get(7 + from).getFirstPrice()));
            product8Score.setImage(new Score(products.get(7 + from).getAverage()).getScoreImg());
            removeProduct8.setOpacity(1);
        } else {
            productImg8.setImage(null);
            product8.setText("");
            product8Price.setText("");
            product8Score.setImage(null);
            removeProduct8.setOpacity(0);
        }
    }

    private void setProduct9(int from) {
        if (products.size() > (8 + from)) {
            Image img9 = new Image(new File("src/main/resources/Images/" + products.get(8 + from).getImagePath()).toURI().toString());
            productImg9.setImage(img9);
            product9.setText(products.get(8 + from).getName());
            product9Price.setText(Double.toString(products.get(8 + from).getFirstPrice()));
            product9Score.setImage(new Score(products.get(8 + from).getAverage()).getScoreImg());
            removeProduct9.setOpacity(1);
        } else {
            productImg9.setImage(null);
            product9.setText("");
            product9Price.setText("");
            product9Score.setImage(null);
            removeProduct9.setOpacity(0);
        }
    }

    private void deleteProducts() {
        productImg1.setImage(null);
        product1.setText("");
        product1Price.setText("");
        product1Score.setImage(null);
        removeProduct1.setOpacity(0);
        productImg2.setImage(null);
        product2.setText("");
        product2Price.setText("");
        product2Score.setImage(null);
        removeProduct2.setOpacity(0);
        productImg3.setImage(null);
        product3.setText("");
        product3Price.setText("");
        product3Score.setImage(null);
        removeProduct3.setOpacity(0);
        productImg4.setImage(null);
        product4.setText("");
        product4Price.setText("");
        product4Score.setImage(null);
        removeProduct4.setOpacity(0);
        productImg5.setImage(null);
        product5.setText("");
        product5Price.setText("");
        product5Score.setImage(null);
        removeProduct5.setOpacity(0);
        productImg6.setImage(null);
        product6.setText("");
        product6Price.setText("");
        product6Score.setImage(null);
        removeProduct6.setOpacity(0);
        productImg7.setImage(null);
        product7.setText("");
        product7Price.setText("");
        product7Score.setImage(null);
        removeProduct7.setOpacity(0);
        productImg8.setImage(null);
        product8.setText("");
        product8Price.setText("");
        product8Score.setImage(null);
        removeProduct8.setOpacity(0);
        productImg9.setImage(null);
        product9.setText("");
        product9Price.setText("");
        product9Score.setImage(null);
        removeProduct9.setOpacity(0);
    }

    public void removeProduct1(ActionEvent actionEvent) {
        try {
            managerController.mangerRemoveProduct(products.get(fromForBack).getId());
            showProducts(fromForBack);
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }

    public void removeProduct2(ActionEvent actionEvent) {
        try {
            managerController.mangerRemoveProduct(products.get(fromForBack + 1).getId());
            showProducts(fromForBack);
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }

    public void removeProduct3(ActionEvent actionEvent) {
        try {
            managerController.mangerRemoveProduct(products.get(fromForBack + 2).getId());
            showProducts(fromForBack);
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }

    public void removeProduct4(ActionEvent actionEvent) {
        try {
            managerController.mangerRemoveProduct(products.get(fromForBack + 3).getId());
            showProducts(fromForBack);
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }

    public void removeProduct5(ActionEvent actionEvent) {
        try {
            managerController.mangerRemoveProduct(products.get(fromForBack + 4).getId());
            showProducts(fromForBack);
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }

    public void removeProduct6(ActionEvent actionEvent) {
        try {
            managerController.mangerRemoveProduct(products.get(fromForBack + 5).getId());
            showProducts(fromForBack);
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }

    public void removeProduct7(ActionEvent actionEvent) {
        try {
            managerController.mangerRemoveProduct(products.get(fromForBack + 6).getId());
            showProducts(fromForBack);
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }

    public void removeProduct8(ActionEvent actionEvent) {
        try {
            managerController.mangerRemoveProduct(products.get(fromForBack + 7).getId());
            showProducts(fromForBack);
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }

    public void removeProduct9(ActionEvent actionEvent) {
        try {
            managerController.mangerRemoveProduct(products.get(fromForBack + 8).getId());
            showProducts(fromForBack);
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
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

    private void showProducts(int from) {
        try {
            products = allProductsController.showProducts();
        } catch (Exception e) {
            try {
                new AlertController().create(AlertType.ERROR, e.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (products.size() != 0)
            initializeProducts(from);
        else {
            productImg1.setImage(null);
            product1.setText("");
            product1Price.setText("");
            product1Score.setImage(null);
            removeProduct1.setOpacity(0);
        }
    }
}
