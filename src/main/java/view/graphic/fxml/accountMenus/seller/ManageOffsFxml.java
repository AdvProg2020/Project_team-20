package view.graphic.fxml.accountMenus.seller;

import controller.account.user.SellerController;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import model.account.Seller;
import model.product.Product;
import model.product.Sale;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManageOffsFxml implements Initializable {
    public TableView<Sale> table;
    public Text title;
    public TextArea products;
    public TextField saleId;
    public TextField salePercentage;
    public TableColumn<String, Sale> saleIds;
    private SellerController sellerController = SellerController.getInstance();
    private Seller seller = (Seller) sellerController.getAccountInfo();

    private Sale sale;

    public void selectOff(MouseEvent event) {
        sale = table.getSelectionModel().getSelectedItem();
        saleId.setText(sale.getId());
        salePercentage.setText(Double.toString(sale.getSalePercentage()*100));
        ArrayList<Product> productsSale = sale.getProducts();
        for (Product product:productsSale) {
            products.appendText(product.getName()+"\n");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        table.getItems().setAll(seller.getSales());
        saleIds.setCellValueFactory(new PropertyValueFactory<>("id"));
    }
}
