package view.graphic.fxml.accountMenus.manager;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class ManageCategoriesController {

    public Text title;
    public TextArea message;
    public TableView table;
    public TableColumn categoryName;
    public TextField passwordEdit;
    public TextField creditEdit;
    public TextField lastNameEdit;
    public TextField nameEdit;
    public TextField phoneNumberEdit;
    public TextField gmailEdit;
    public TextField phoneNumberEdit1;
    public TextField gmailEdit1;
    public Button createCategory;
    public Button gmailOk;
    public Button phoneOk1;
    public Button removeButton;
    public Button editButton;
    public Button passwordOk;
    public Button creditOk;
    public Button lastNameOk;
    public Button nameOk;
    public Button phoneOk;

    public void createCategory(ActionEvent actionEvent) {
    }

    public void handleEnterAccept(MouseEvent mouseEvent) {
    }

    public void handleExitAccept(MouseEvent mouseEvent) {
    }

    public void handleOk(ActionEvent actionEvent) {
    }

    public void handleRemove(ActionEvent actionEvent) {
    }

    public void handleEnterRemove(MouseEvent mouseEvent) {
    }

    public void selectCategory(MouseEvent mouseEvent) {
    }

    public void handleExitRemove(MouseEvent mouseEvent) {
    }


    /*public void handleEdit(ActionEvent actionEvent) {
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
    }*/

    private void clear() {
        passwordEdit.setText("");
        nameEdit.setText("");
        lastNameEdit.setText("");
        phoneNumberEdit.setText("");
        gmailEdit.setText("");
        creditEdit.setText("");
    }

    public void removeEditPanel() {
        gmailEdit.setOpacity(0);
        phoneNumberEdit.setOpacity(0);
        nameEdit.setOpacity(0);
        lastNameEdit.setOpacity(0);
        creditEdit.setOpacity(0);
        passwordEdit.setOpacity(0);
        gmailOk.setOpacity(0);
        lastNameOk.setOpacity(0);
        nameOk.setOpacity(0);
        passwordOk.setOpacity(0);
        phoneOk.setOpacity(0);
        creditOk.setOpacity(0);
    }

    public void showEditPanel() {
        gmailEdit.setStyle("-fx-text-inner-color: white; -fx-background-color: #45546e;");
        phoneNumberEdit.setStyle("-fx-text-inner-color: white; -fx-background-color: #45546e;");
        nameEdit.setStyle("-fx-text-inner-color: white; -fx-background-color: #45546e;");
        lastNameEdit.setStyle("-fx-text-inner-color: white; -fx-background-color: #45546e;");
        creditEdit.setStyle("-fx-text-inner-color: white; -fx-background-color: #45546e;");
        passwordEdit.setStyle("-fx-text-inner-color: white; -fx-background-color: #45546e;");
        gmailEdit.setOpacity(0.71);
        phoneNumberEdit.setOpacity(0.71);
        nameEdit.setOpacity(0.71);
        lastNameEdit.setOpacity(0.71);
        creditEdit.setOpacity(0.71);
        passwordEdit.setOpacity(0.71);
        gmailOk.setOpacity(0.71);
        lastNameOk.setOpacity(0.71);
        nameOk.setOpacity(0.71);
        passwordOk.setOpacity(0.71);
        phoneOk.setOpacity(0.71);
        creditOk.setOpacity(0.71);
    }

    /*
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
    }*/

    public void handleEnterOk(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: #4caf50;");
    }

    public void handleExitOk(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: #60d520;");
    }

    public void handleEdit(ActionEvent actionEvent) {
    }
}
