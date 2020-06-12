package view.graphic.fxml.accountMenus.manager;

import controller.account.user.ManagerController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Requestable;
import model.account.Manager;
import model.product.RequestType;
import model.product.RequestableState;
import oracle.jrockit.jfr.events.RequestableEventEnvironment;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class RequestsController implements Initializable {


    public TableView<RequestTable> table;

    public TableColumn<RequestTable,Integer> details;
    public TableColumn<RequestTable,String> requestId;

    public ArrayList<RequestTable> convertToRequestTable() throws Exception {
        ManagerController managerController = ManagerController.getInstance();
        HashMap<Integer, Requestable> requestArrayList = managerController.manageRequests();
        ArrayList<RequestTable> requestTables = new ArrayList<>();
        for(int number : requestArrayList.keySet()){
            RequestTable requestTable = new RequestTable(managerController.requestDetails(number), number);
            requestTables.add(requestTable);
        }
        return requestTables;
    }


    public ObservableList<RequestTable> getRequests() {
        try {
            ObservableList<RequestTable> requestTables1 = FXCollections.observableArrayList();
            ArrayList<RequestTable> requestTables = convertToRequestTable();
            for (RequestTable request : requestTables) {
                requestTables1.add(request);
            }
            return requestTables1;
        }
        catch (Exception e){
        }
        return null;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        requestId.setCellValueFactory(new PropertyValueFactory<RequestTable,String>("id"));
        details.setCellValueFactory(new PropertyValueFactory<RequestTable,Integer>("detail"));
        table.setItems(getRequests());
    }

}

class RequestTable {
    private SimpleStringProperty detail;
    private SimpleStringProperty id;

    public RequestTable(String detail , int id){
        this.detail = new SimpleStringProperty(detail);
        this.id = new SimpleStringProperty(Integer.toString(id));
    }

    public SimpleStringProperty detailProperty() {
        return detail;
    }

    public SimpleStringProperty idProperty() {
        return id;
    }
}
