package view.graphic.fxml.accountMenus.manager;

import controller.account.user.ManagerController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import model.product.category.SubCategory;
import view.graphic.alert.AlertController;
import view.graphic.alert.AlertType;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManageCategoriesController implements Initializable {
    static boolean leave = false;

    public Text title;
    public TextArea message;
    public TableView<SubCategory> table;
    public TableColumn<Object, Object> categoryName;
    public TextField addFieldPartEdit;
    public TextField removeFieldEdit;
    public TextField addSubEdit;
    public TextField nameEdit;
    public TextField removeSubEdit;
    public TextField addProductEdit;
    public TextField removeProductEdit;
    public TextField newCategory;
    public Button createCategory;
    public Button addProductOk;
    public Button removeProductOk;
    public Button removeButton;
    public Button editButton;
    public Button addFieldPartOk;
    public Button removeFieldOk;
    public Button addSubOk;
    public Button nameOk;
    public Button removeSubOk;

    ManagerController managerController = ManagerController.getInstance();
    SubCategory subCategory;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        message.setOpacity(0);
        title.setOpacity(0);
        removeButton.setOpacity(0);
        editButton.setOpacity(0);
        ArrayList<SubCategory> categories = managerController.manageCategories();
        table.getItems().setAll(categories);
        categoryName.setCellValueFactory(new PropertyValueFactory<>("name"));
    }


    public void selectCategory(MouseEvent mouseEvent) throws Exception {
        subCategory = table.getSelectionModel().getSelectedItem();
        message.setOpacity(0.7);
        title.setOpacity(1);
        removeButton.setOpacity(1);
        editButton.setOpacity(1);
        message.setText(subCategory.toString());
    }

    public void createCategory(ActionEvent actionEvent) {
        String name = newCategory.getText();
        if (name.isEmpty()) {
            new AlertController().create(AlertType.ERROR, "field is empty");
            return;
        }
        try {
            managerController.addCategory(name);
            ArrayList<SubCategory> categories = managerController.manageCategories();
            table.getItems().setAll(categories);
            categoryName.setCellValueFactory(new PropertyValueFactory<>("name"));
            new AlertController().create(AlertType.CONFIRMATION, "successful");
            newCategory.setText("");
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }

    public void handleEnterAccept(MouseEvent mouseEvent) {
    }

    public void handleExitAccept(MouseEvent mouseEvent) {
    }

    public void handleOk(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (addProductOk.equals(source)) {
            addProduct();
        } else if (addSubOk.equals(source)) {
            addSub();
        } else if (nameOk.equals(source)) {
            editName();
        } else if (removeFieldOk.equals(source)) {
            removeField();
        } else if (addFieldPartOk.equals(source)) {
            addField();
        } else if (removeProductOk.equals(source)) {
            removeProduct();
        } else if (removeSubOk.equals(source)) {
            removeSub();
        }
    }

    private void removeSub() {
        String name = removeSubEdit.getText();
        if (name.isEmpty()) {
            new AlertController().create(AlertType.ERROR, "field is empty");
            return;
        }
        try {
            //TODO check this after all category set
            managerController.removeSubCategoryFromAllSubCategories(subCategory.getName(), name);
            new AlertController().create(AlertType.CONFIRMATION, "successful");
            message.setText(subCategory.toString());
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }

    private void removeProduct() {
        String name = removeProductEdit.getText();
        if (name.isEmpty()) {
            new AlertController().create(AlertType.ERROR, "field is empty");
            return;
        }
        try {
            managerController.removeProductFromCategory(subCategory.getName(), name);
            new AlertController().create(AlertType.CONFIRMATION, "successful");
            message.setText(subCategory.toString());
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }

    private void addField() {
        String name = addFieldPartEdit.getText();
        if (name.isEmpty()) {
            new AlertController().create(AlertType.ERROR, "field is empty");
            return;
        }
        try {
            managerController.addNewFieldToCategory(subCategory.getName(), name);
            new AlertController().create(AlertType.CONFIRMATION, "successful");
            message.setText(subCategory.toString());
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }

    private void removeField() {
        String name = removeFieldEdit.getText();
        if (name.isEmpty()) {
            new AlertController().create(AlertType.ERROR, "field is empty");
            return;
        }
        try {
            managerController.removeFieldFromCategory(subCategory.getName(), name);
            new AlertController().create(AlertType.CONFIRMATION, "successful");
            message.setText(subCategory.toString());
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }

    private void editName() {
        String name = nameEdit.getText();
        if (name.isEmpty()) {
            new AlertController().create(AlertType.ERROR, "field is empty");
            return;
        }
        try {
            managerController.editCategoryName(subCategory.getName(), name);
            new AlertController().create(AlertType.CONFIRMATION, "successful");
            message.setText(subCategory.toString());
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }

    private void addSub() {
        String name = addSubEdit.getText();
        if (name.isEmpty()) {
            new AlertController().create(AlertType.ERROR, "field is empty");
            return;
        }
        try {
            managerController.addSubCategoryToCategorySet(subCategory.getName(), name);
            new AlertController().create(AlertType.CONFIRMATION, "successful");
            message.setText(subCategory.toString());
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }

    private void addProduct() {
        String name = addProductEdit.getText();
        if (name.isEmpty()) {
            new AlertController().create(AlertType.ERROR, "field is empty");
            return;
        }
        try {
            managerController.addProductToCategory(subCategory.getName(), name);
            new AlertController().create(AlertType.CONFIRMATION, "successful");
            message.setText(subCategory.toString());
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }


    public void handleRemove(ActionEvent actionEvent) {
        try {
            managerController.managerRemoveCategory(subCategory.getName());
            ArrayList<SubCategory> categories = managerController.manageCategories();
            table.getItems().setAll(categories);
            categoryName.setCellValueFactory(new PropertyValueFactory<>("name"));
            new AlertController().create(AlertType.ERROR, "remove was successful");
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }

    public void handleEnterRemove(MouseEvent mouseEvent) {
    }

    public void handleExitRemove(MouseEvent mouseEvent) {
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

    private void clear() {
        addFieldPartEdit.setText("");
        nameEdit.setText("");
        addSubEdit.setText("");
        removeSubEdit.setText("");
        addProductEdit.setText("");
        removeFieldEdit.setText("");
        removeProductEdit.setText("");
    }

    public void removeEditPanel() {
        addProductEdit.setOpacity(0);
        removeSubEdit.setOpacity(0);
        nameEdit.setOpacity(0);
        addSubEdit.setOpacity(0);
        removeFieldEdit.setOpacity(0);
        addFieldPartEdit.setOpacity(0);
        removeProductEdit.setOpacity(0);
        addProductOk.setOpacity(0);
        addSubOk.setOpacity(0);
        nameOk.setOpacity(0);
        addFieldPartOk.setOpacity(0);
        removeSubOk.setOpacity(0);
        removeFieldOk.setOpacity(0);
        removeProductOk.setOpacity(0);
    }

    public void showEditPanel() {
        addProductEdit.setStyle("-fx-text-inner-color: white; -fx-background-color: #45546e;");
        removeSubEdit.setStyle("-fx-text-inner-color: white; -fx-background-color: #45546e;");
        nameEdit.setStyle("-fx-text-inner-color: white; -fx-background-color: #45546e;");
        addSubEdit.setStyle("-fx-text-inner-color: white; -fx-background-color: #45546e;");
        removeFieldEdit.setStyle("-fx-text-inner-color: white; -fx-background-color: #45546e;");
        addFieldPartEdit.setStyle("-fx-text-inner-color: white; -fx-background-color: #45546e;");
        removeProductEdit.setStyle("-fx-text-inner-color: white; -fx-background-color: #45546e;");
        removeProductEdit.setOpacity(0.71);
        addProductEdit.setOpacity(0.71);
        removeSubEdit.setOpacity(0.71);
        nameEdit.setOpacity(0.71);
        addSubEdit.setOpacity(0.71);
        removeFieldEdit.setOpacity(0.71);
        addFieldPartEdit.setOpacity(0.71);
        addProductOk.setOpacity(0.71);
        addSubOk.setOpacity(0.71);
        nameOk.setOpacity(0.71);
        addFieldPartOk.setOpacity(0.71);
        removeSubOk.setOpacity(0.71);
        removeFieldOk.setOpacity(0.71);
        removeProductOk.setOpacity(0.71);
    }


    public void handleEnter(MouseEvent event) {
        if (!leave)
            ((Button) event.getSource()).setStyle("-fx-background-color: #00bfbc;");
        else
            ((Button) event.getSource()).setStyle("-fx-background-color: #dd7160;");
    }

    public void handleExit(MouseEvent event) {
        if (!leave)
            ((Button) event.getSource()).setStyle("-fx-background-color: #009f9c;");
        else
            ((Button) event.getSource()).setStyle("-fx-background-color: #ff826f;");
    }

    public void handleEnterOk(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: #4caf50;");
    }

    public void handleExitOk(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: #60d520;");
    }


}
