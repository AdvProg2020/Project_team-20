package view.graphic.fxml.accountMenus.seller;

import controller.account.user.SellerController;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import model.product.category.Category;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ShowCategoriesFxml implements Initializable {
    public Text title;
    public TextArea message;
    public TableView<Category> table;
    public TableColumn<Object, Object> categoryName;
    public Text title1;
    private SellerController sellerController = SellerController.getInstance();
    Category category;

    public void selectCategory(MouseEvent event) {
        category = table.getSelectionModel().getSelectedItem();
        message.setOpacity(0.7);
        title.setOpacity(1);
        message.setText(category.toString());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<Category> categories = sellerController.showCategories();
        table.getItems().setAll(categories);
        categoryName.setCellValueFactory(new PropertyValueFactory<>("name"));
    }
}
