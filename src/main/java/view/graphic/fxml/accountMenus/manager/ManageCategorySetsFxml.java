package view.graphic.fxml.accountMenus.manager;

import controller.account.user.ManagerController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import model.product.category.CategorySet;
import view.graphic.alert.AlertController;
import view.graphic.alert.AlertType;

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
    public TextField addSubCategoryTxt;
    public Button addSubCatOk;
    public TextField removeSubCategoryTxt;
    public Button removeSubCatOk;
    private ManagerController managerController = ManagerController.getInstance();
    private CategorySet categorySet;

    public void handleRemove(ActionEvent actionEvent) {
        if (categorySet!=null) {
            try {
                managerController.managerRemoveCategorySet(categorySet.getName());
            } catch (CategorySet.CategoryDoesNotFoundException e) {
                new AlertController().create(AlertType.ERROR, e.getMessage());
            }
        }
    }

    public void handleEnterRemove(MouseEvent event) {
    }

    public void handleExitRemove(MouseEvent event) {
    }

    public void handleEdit(ActionEvent actionEvent) {
        Button button = ((Button) actionEvent.getSource());
        if (!leave) {
            button.setStyle("-fx-background-color: #ff826f;");
            button.setText("Leave");
            leave = true;
            showEditPanel();
        } else {
            button.setStyle("-fx-background-color: #009f9c;");
            button.setText("Edit");
            leave = false;
            clear();
            removeEditPanel();
        }
    }

    public void showEditPanel() {
        nameEditTxt.setStyle("-fx-text-inner-color: white; -fx-background-color: #45546e;");
        addCategoryTxt.setStyle("-fx-text-inner-color: white; -fx-background-color: #45546e;");
        removeCategoryTxt.setStyle("-fx-text-inner-color: white; -fx-background-color: #45546e;");
        addSubCategoryTxt.setStyle("-fx-text-inner-color: white; -fx-background-color: #45546e;");
        addSubCategoryTxt.setOpacity(0.71);
        removeSubCategoryTxt.setStyle("-fx-text-inner-color: white; -fx-background-color: #45546e;");
        addSubCatOk.setOpacity(0.71);
        removeSubCatOk.setOpacity(0.71);
        removeSubCategoryTxt.setOpacity(0.71);
        nameEditTxt.setOpacity(0.71);
        addCategoryTxt.setOpacity(0.71);
        removeCategoryTxt.setOpacity(0.71);
        nameOk.setOpacity(0.71);
        addCategoryOk.setOpacity(0.71);
        removeCategoryOk.setOpacity(0.71);
    }

    public void removeEditPanel() {
        nameEditTxt.setOpacity(0);
        addCategoryTxt.setOpacity(0);
        removeCategoryTxt.setOpacity(0);
        nameOk.setOpacity(0);
        addCategoryOk.setOpacity(0);
        removeCategoryOk.setOpacity(0);
    }

    private void clear() {
        nameEditTxt.setText("");
        addCategoryTxt.setText("");
        removeCategoryTxt.setText("");
    }

    public void handleEnterAccept(MouseEvent event) {
    }

    public void handleExitAccept(MouseEvent event) {
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

    public void handleEnterOk(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: #4caf50;");
    }

    public void handleExitOk(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: #60d520;");
    }

    public void createCategory(ActionEvent actionEvent) {
        String newCategoryStr = newCategory.getText();
        if (newCategoryStr.equals("")) {
            new AlertController().create(AlertType.ERROR, "Fill the fields");
            return;
        }
        try {
            managerController.addCategorySet(newCategoryStr);
            ArrayList<CategorySet> categories = managerController.manageCategorySets();
            table.getItems().setAll(categories);
            categoryName.setCellValueFactory(new PropertyValueFactory<>("name"));
            new AlertController().create(AlertType.CONFIRMATION, "successful");
            newCategory.setText("");
        } catch (CategorySet.CategoryNameException e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }

    public void handleAddCategory(ActionEvent actionEvent) {
        String toAddCategory = addCategoryTxt.getText();
        if (toAddCategory.equals("")) {
            new AlertController().create(AlertType.ERROR, "Fill the field");
            return;
        }
        try {
            managerController.addCategorySetToCategorySet(categorySet.getName(), toAddCategory);
            new AlertController().create(AlertType.CONFIRMATION, "successful");
            message.setText(categorySet.toString());
        } catch (CategorySet.CategoryDoesNotFoundException e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }

    public void handleRemoveCategory(ActionEvent actionEvent) {
        String toRemoveCategory = removeCategoryTxt.getText();
        if (toRemoveCategory.equals("")) {
            new AlertController().create(AlertType.ERROR, "Fill the field");
            return;
        }
        try {
            managerController.removeCategorySetFromCategorySet(categorySet.getName(), toRemoveCategory);
            new AlertController().create(AlertType.CONFIRMATION, "successful");
            message.setText(categorySet.toString());
        } catch (CategorySet.CategoryDoesNotFoundException e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }

    public void handleOk(ActionEvent actionEvent) {
        String newName = nameEditTxt.getText();
        if (newName.equals("")) {
            new AlertController().create(AlertType.ERROR, "Fill the field");
            return;
        }
        try {
            managerController.editCategoryName(categorySet.getName(), newName);
            new AlertController().create(AlertType.CONFIRMATION, "successful");
            message.setText(categorySet.toString());
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }

    public void handleAddSubCat(ActionEvent actionEvent) {
        String subCatToAdd = addSubCategoryTxt.getText();
        if (subCatToAdd.equals("")) {
            new AlertController().create(AlertType.ERROR, "Fill the field");
            return;
        }
        try {
            managerController.addSubCategoryToCategorySet(categorySet.getName(), subCatToAdd);
            new AlertController().create(AlertType.CONFIRMATION, "successful");
            message.setText(categorySet.toString());
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }

    public void handleRemoveSubCat(ActionEvent actionEvent) {
    }
}
