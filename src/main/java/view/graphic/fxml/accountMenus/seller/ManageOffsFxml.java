package view.graphic.fxml.accountMenus.seller;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import controller.MediaController;
import controller.account.user.SellerController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import model.account.Seller;
import model.product.Product;
import model.product.Sale;
import view.graphic.ProgramApplication;
import view.graphic.alert.AlertController;
import view.graphic.alert.AlertType;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManageOffsFxml implements Initializable {
    public TableView<Sale> table;
    public Text title;
    public TextField saleId;
    public TextField salePercentage;
    public TableColumn<String, Sale> saleIds;
    public TextField percentageEdit;
    public Button percentageOk;
    public TextField startDate;
    public TextField endDate;
    public JFXDatePicker startDatePicker;
    public JFXTimePicker startTimePicker;
    public JFXDatePicker endDatePicker;
    public JFXTimePicker endTimePicker;
    public TableView<Product> table1;
    public TableColumn<String, Product> productNames;
    public TableView<Product> chosenTable;
    public TableColumn<String, Product> chosenUsers;
    public TableView<Product> saleProductNamesTable;
    public TableColumn<String, Product> saleProductNames;
    public TableView<Product> productsToRemoveTable;
    public TableColumn<String, Product> removeProducts;
    public Button editButton;
    private SellerController sellerController = SellerController.getInstance();
    private Seller seller = (Seller) sellerController.getAccountInfo();
    static boolean leave = false;
    private ArrayList<Product> productsToAdd;
    private ArrayList<Product> productsToRemove;
    private Sale sale;
    MediaController mediaController = ProgramApplication.getMediaController();

    public void selectOff(MouseEvent event) {
        sale = table.getSelectionModel().getSelectedItem();
        saleId.setText(sale.getId());
        salePercentage.setText(Double.toString(sale.getSalePercentage()*100));
        startDate.setText(sale.getStartDate().toString());
        endDate.setText(sale.getEndDate().toString());
        productsToAdd = new ArrayList<>();
        productsToRemove = new ArrayList<>();
        saleProductNamesTable.getItems().setAll(sale.getProducts());
        saleProductNames.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        table.getItems().setAll(seller.getSales());
        saleIds.setCellValueFactory(new PropertyValueFactory<>("id"));
        table1.getItems().setAll(seller.getProducts());
        productNames.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    public void handleEdit(ActionEvent actionEvent) {
        if (sale!=null) {
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

    public void showEditPanel() {
        percentageEdit.setStyle("-fx-text-inner-color: white; -fx-background-color: #45546e;");
        percentageEdit.setOpacity(0.71);
        percentageOk.setOpacity(0.71);
        startDatePicker.setOpacity(0.9);
        startTimePicker.setOpacity(0.9);
        endDatePicker.setOpacity(0.9);
        endTimePicker.setOpacity(0.9);
        table1.setOpacity(0.7);
        chosenTable.setOpacity(0.7);
        productsToRemoveTable.setOpacity(0.7);
    }

    private void clear() {
        percentageEdit.setText("");
        productsToRemoveTable.getItems().clear();
        chosenTable.getItems().clear();
        startTimePicker.setAccessibleText(null);
        startDatePicker.setValue(null);
    }

    public void removeEditPanel() {
        percentageEdit.setOpacity(0);
        percentageOk.setOpacity(0);
        startDatePicker.setOpacity(0);
        startTimePicker.setOpacity(0);
        endDatePicker.setOpacity(0);
        endTimePicker.setOpacity(0);
        table1.setOpacity(0);
        chosenTable.setOpacity(0);
        productsToRemoveTable.setOpacity(0);
    }

    public void handleOk(ActionEvent actionEvent) {
        if (sale!=null) {
            LocalTime startTime = startTimePicker.getValue();
            LocalDate startDate = startDatePicker.getValue();
            LocalTime endTime = endTimePicker.getValue();
            LocalDate endDate = endDatePicker.getValue();
            LocalDateTime startLocalDateTime;
            if ((startDate!=null && startTime==null) || (startDate==null && startTime!=null) || (endDate!=null && endTime==null) || (endDate==null && endTime!=null)) {
                new AlertController().create(AlertType.ERROR, "Fill date&time");
                return;
            }
            if (startTime!=null)
                startLocalDateTime = LocalDateTime.of(startDate, startTime);
            else
                startLocalDateTime = null;
            LocalDateTime endLocalDateTime;
            if (endTime!=null)
                endLocalDateTime = LocalDateTime.of(endDate, endTime);
            else
                endLocalDateTime = null;
            ArrayList<String> productsToRemoveStr = new ArrayList<>();
            for (Product product:productsToRemove)
                productsToRemoveStr.add(product.getId());
            ArrayList<String> productsToAddStr = new ArrayList<>();
            for (Product product:productsToAdd)
                productsToAddStr.add(product.getId());

            try {
                String percentage = percentageEdit.getText();
                sellerController.editOff(sale.getId(), startLocalDateTime, endLocalDateTime, percentage, productsToRemoveStr, productsToAddStr);
            } catch (Exception e) {
                new AlertController().create(AlertType.ERROR, e.getMessage());
            }
            new AlertController().create(AlertType.CONFIRMATION, "Edit was sent to managers");
            editButton.setStyle("-fx-background-color: #009f9c;");
            editButton.setText("Edit");
            leave = false;
            clear();
            removeEditPanel();
        }
    }

    public void handleEnterOk(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: #4caf50;");
    }

    public void handleExitOk(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: #60d520;");
    }

    public void selectRequest(MouseEvent event) {
        Product product = table1.getSelectionModel().getSelectedItem();
        if (!productsToAdd.contains(product) && !sale.getProducts().contains(product))
            productsToAdd.add(product);
        chosenTable.getItems().removeAll();
        chosenTable.getItems().setAll(productsToAdd);
        chosenUsers.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    public void selectRequestProducts(MouseEvent event) {
        Product product = saleProductNamesTable.getSelectionModel().getSelectedItem();
        if (!productsToRemove.contains(product))
            productsToRemove.add(product);
        productsToRemoveTable.getItems().removeAll();
        productsToRemoveTable.getItems().setAll(productsToRemove);
        removeProducts.setCellValueFactory(new PropertyValueFactory<>("name"));
    }
}
