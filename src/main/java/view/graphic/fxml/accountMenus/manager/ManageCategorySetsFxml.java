package view.graphic.fxml.accountMenus.manager;

import controller.account.user.ManagerController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import model.product.category.CategorySet;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManageCategorySetsFxml implements Initializable {
    static boolean leave = false;

    public Text title;
    public TextArea message;
    public TableView<CategorySet> table;
    public TableColumn<String, CategorySet> categoryName;
    public Text title1;
    public Button removeButton;
    public Button editButton;
    public Button createCategory;
    public TextField newCategory;
    public Text title2;
    public Button addCategoryOk;
    public Button nameOk;
    public TextField addCategoryTxt;
    public TextField removeCategoryTxt;
    public TextField nameEditTxt;
    public Button removeCategoryOk;
    private ManagerController managerController = ManagerController.getInstance();
    private CategorySet categorySet;

    public void handleRemove(ActionEvent actionEvent) {
    }

    public void handleEnterRemove(MouseEvent event) {
    }

    public void handleExitRemove(MouseEvent event) {
    }

    public void handleEdit(ActionEvent actionEvent) {
    }

    public void handleEnterAccept(MouseEvent event) {
    }

    public void handleExitAccept(MouseEvent event) {
    }

    public void createCategory(ActionEvent actionEvent) {
    }

    public void selectCategory(MouseEvent mouseEvent) throws Exception {
        categorySet = table.getSelectionModel().getSelectedItem();
        message.setOpacity(0.7);
        title.setOpacity(1);
        removeButton.setOpacity(1);
        editButton.setOpacity(1);
        message.setText(categorySet.toString());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        message.setOpacity(0);
        title.setOpacity(0);
        removeButton.setOpacity(0);
        editButton.setOpacity(0);
        ArrayList<CategorySet> categories = managerController.manageCategorySets();
        table.getItems().setAll(categories);
        categoryName.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    public void handleAddCategory(ActionEvent actionEvent) {
    }

    public void handleEnterOk(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: #4caf50;");
    }

    public void handleExitOk(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: #60d520;");
    }

    public void handleRemoveCategory(ActionEvent actionEvent) {
    }

    public void handleOk(ActionEvent actionEvent) {
    }
}
