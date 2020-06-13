package view.graphic.fxml.accountMenus.manager;

import controller.account.user.ManagerController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import model.Requestable;
import model.account.Manager;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import controller.Main;
import controller.account.user.ManagerController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Requestable;
import model.account.Manager;
import view.graphic.MenuNames;
import view.graphic.ProgramApplication;
import view.graphic.alert.AlertController;
import view.graphic.alert.AlertType;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RequestsController implements Initializable {

    public TableView<RequestTable> table;
    public TableColumn<RequestTable, String> requestId;
    public TableColumn<RequestTable, String> details;
    public TextArea message;
    public Text title;
    public Button tickButton;
    public Button crossButton;
    private String id;


    ManagerController managerController = ManagerController.getInstance();
    Manager manager = (Manager) managerController.getAccountInfo();
    private TextArea message1;


    private ArrayList<RequestTable> convertToRequestTable() throws Exception {
        ManagerController managerController = ManagerController.getInstance();
        HashMap<Integer, Requestable> requestArrayList = managerController.manageRequests();
        ArrayList<RequestTable> requestTables = new ArrayList<>();
        for (int number : requestArrayList.keySet()) {
            String strings = managerController.requestDetails(number);
            String[] strings1 = strings.split("\n\n");
            String[] strings2 = strings1[1].split(":");
            RequestTable requestTable = new RequestTable(strings2[1], number);
            requestTables.add(requestTable);
        }
        return requestTables;
    }

    private ObservableList<RequestTable> getRequests() throws Exception {
        ObservableList<RequestTable> requestTables1 = FXCollections.observableArrayList();
        ArrayList<RequestTable> requestTables = convertToRequestTable();
        for (RequestTable request : requestTables) {
            requestTables1.add(request);
        }
        return requestTables1;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        message.setOpacity(0);
        title.setOpacity(0);
        tickButton.setOpacity(0);
        crossButton.setOpacity(0);
        requestId.setCellValueFactory(new PropertyValueFactory<RequestTable, String>("id"));
        details.setCellValueFactory(new PropertyValueFactory<RequestTable, String>("detail"));
        try {
            table.setItems(getRequests());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectRequest(MouseEvent mouseEvent) throws Exception {
        id = table.getSelectionModel().getSelectedItem().getId();
        //Requestable requestable = Manager.getRequests().get(Integer.parseInt(id) - 1);
        System.out.println(managerController.requestDetails(Integer.parseInt(id)));
        message.appendText(managerController.requestDetails(Integer.parseInt(id)));
        title.setOpacity(1);
        message.setOpacity(0.7);
        tickButton.setOpacity(1);
        crossButton.setOpacity(1);
    }

    public void handleCross() throws Exception {
        managerController.declineRequest(Integer.parseInt(id));
        title.setOpacity(0);
        message.setOpacity(0);
        tickButton.setOpacity(0);
        crossButton.setOpacity(0);
        requestId.setCellValueFactory(new PropertyValueFactory<RequestTable, String>("id"));
        details.setCellValueFactory(new PropertyValueFactory<RequestTable, String>("detail"));
        try {
            table.setItems(getRequests());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleTick() throws Exception {
        managerController.acceptRequest(Integer.parseInt(id));
        title.setOpacity(0);
        message.setOpacity(0);
        tickButton.setOpacity(0);
        crossButton.setOpacity(0);
        requestId.setCellValueFactory(new PropertyValueFactory<RequestTable, String>("id"));
        details.setCellValueFactory(new PropertyValueFactory<RequestTable, String>("detail"));
        try {
            table.setItems(getRequests());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleEnterAccept(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: #4caf50;");
    }

    public void handleExitAccept(MouseEvent event) {
       ((Button) event.getSource()).setStyle("-fx-background-color: #60d520;");
    }

    public void handleEnterRemove(MouseEvent event) {
      ((Button) event.getSource()).setStyle("-fx-background-color: #fb5f48;");
    }

    public void handleExitRemove(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: #ff826f;");
    }
}





