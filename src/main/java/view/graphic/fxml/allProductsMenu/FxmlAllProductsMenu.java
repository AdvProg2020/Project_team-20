package view.graphic.fxml.allProductsMenu;

import controller.product.filter.AllProductsController;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import model.product.Product;
import view.graphic.alert.AlertController;
import view.graphic.alert.AlertType;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FxmlAllProductsMenu implements Initializable {
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
        } catch (Exception e) {
            try {
                new AlertController().create(AlertType.ERROR, e.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (products.size()!=0)
            initializeProducts(0);
    }

    private void initializeProducts(int from) {
        System.out.println(products.get(from).getImagePath());
        Image img1 = new Image(new File("src/main/resources/Images/" + products.get(from).getImagePath()).toURI().toString());
        productImg1.setImage(img1);
        product1.setText(products.get(from).getName());
        if (products.size()>(1+from)) {
            Image img2 = new Image(new File("src/main/resources/Images/" + products.get(1 + from).getImagePath()).toURI().toString());
            productImg2.setImage(img2);
            product2.setText(products.get(1 + from).getName());
        }
        if (products.size()>(2+from)) {
            Image img3 = new Image(new File("src/main/resources/Images/" + products.get(2 + from).getImagePath()).toURI().toString());
            productImg3.setImage(img3);
            product3.setText(products.get(2 + from).getName());
        }
        if (products.size()>(3+from)) {
            Image img4 = new Image(new File("src/main/resources/Images/" + products.get(3 + from).getImagePath()).toURI().toString());
            productImg4.setImage(img4);
            product4.setText(products.get(3 + from).getName());
        }
        if (products.size()>(4+from)) {
            Image img5 = new Image(new File("src/main/resources/Images/" + products.get(4 + from).getImagePath()).toURI().toString());
            productImg5.setImage(img5);
            product5.setText(products.get(4 + from).getName());
        }
        if (products.size()>(5+from)) {
            Image img6 = new Image(new File("src/main/resources/Images/" + products.get(5 + from).getImagePath()).toURI().toString());
            productImg6.setImage(img6);
            product6.setText(products.get(5 + from).getName());
        }
        if (products.size()>(6+from)) {
            Image img7 = new Image(new File("src/main/resources/Images/" + products.get(6 + from).getImagePath()).toURI().toString());
            productImg7.setImage(img7);
            product7.setText(products.get(6 + from).getName());
        }
        if (products.size()>(7+from)) {
            Image img8 = new Image(new File("src/main/resources/Images/" + products.get(7 + from).getImagePath()).toURI().toString());
            productImg8.setImage(img8);
            product8.setText(products.get(7 + from).getName());
        }
        if (products.size()>(8+from)) {
            Image img9 = new Image(new File("src/main/resources/Images/" + products.get(8 + from).getImagePath()).toURI().toString());
            productImg9.setImage(img9);
            product9.setText(products.get(8 + from).getName());
        }
    }

/*
    public void show(ActionEvent actionEvent) throws Exception{
        Image img = new Image(new File("src/main/resources/Images/" + name).toURI().toString());
        show.setImage(img);
    }

    public void handleDragOver(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.ANY);
        }
    }

    public void handleDrop(DragEvent event) throws Exception{
        List<File> files = event.getDragboard().getFiles();
        Image img = new Image(new FileInputStream((files.get(0))));
        get.setImage(img);
        File outputFile = new File("src/main/resources/Images/hi.png");
        name = "hi.png";
        BufferedImage bImage = SwingFXUtils.fromFXImage(img, null);
        try {
            ImageIO.write(bImage, "png", outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
     */

}
