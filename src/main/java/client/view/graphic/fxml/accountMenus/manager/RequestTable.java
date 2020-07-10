package client.view.graphic.fxml.accountMenus.manager;

import javafx.beans.property.SimpleStringProperty;

public class RequestTable {
    private SimpleStringProperty detail;
    private SimpleStringProperty id;

    public RequestTable(String detail, int id) {
        this.detail = new SimpleStringProperty(detail);
        this.id = new SimpleStringProperty(Integer.toString(id));
    }

    public String getDetail() {
        return detail.get();
    }

    public String getId() {
        return id.get();
    }
}
