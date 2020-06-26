package view.graphic.fxml.accountMenus.seller;

import controller.account.user.SellerController;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import model.product.category.SubCategory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ShowCategoriesFxml implements Initializable {
    public Text title;
    public TextArea message;
    public TableView<SubCategory> table;
    public TableColumn<Object, Object> categoryName;
    public Text title1;
    private SellerController sellerController = SellerController.getInstance();
    SubCategory subCategory;

    public void selectCategory(MouseEvent event) {
        subCategory = table.getSelectionModel().getSelectedItem();
        message.setOpacity(0.7);
        title.setOpacity(1);
        message.setText(subCategory.toString());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<SubCategory> categories = sellerController.showCategories();
        table.getItems().setAll(categories);
        categoryName.setCellValueFactory(new PropertyValueFactory<>("name"));
    }
}
