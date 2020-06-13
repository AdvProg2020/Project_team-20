package view.graphic.fxml.accountMenus.manager;

import controller.account.user.ManagerController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Requestable;
import model.account.Manager;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class RequestsController implements Initializable {

    public TableView<RequestTable> table;
    public TableColumn<RequestTable,String> requestId;
    public TableColumn<RequestTable,String> details;

    ManagerController managerController = ManagerController.getInstance();
    Manager manager = (Manager) managerController.getAccountInfo();


    private ArrayList<RequestTable> convertToRequestTable() throws Exception {
        ManagerController managerController = ManagerController.getInstance();
        HashMap<Integer, Requestable> requestArrayList = managerController.manageRequests();
        ArrayList<RequestTable> requestTables = new ArrayList<>();
        for(int number : requestArrayList.keySet()){
            String strings = managerController.requestDetails(number);
            String[] strings1 = strings.split("\n");
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
        requestId.setCellValueFactory(new PropertyValueFactory<RequestTable,String>("id"));
        details.setCellValueFactory(new PropertyValueFactory<RequestTable,String>("detail"));
        try {
            table.setItems(getRequests());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectRequest(MouseEvent mouseEvent) {
        String id = table.getSelectionModel().getSelectedItem().getId();
        Requestable requestable = Manager.getRequests().get(Integer.parseInt(id) - 1);
    }
}





