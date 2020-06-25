package view.graphic.fxml.accountMenus.seller;

import controller.account.user.ManagerController;
import controller.account.user.SellerController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import model.Requestable;
import model.account.Buyer;
import model.account.Seller;
import model.product.Field.Field;
import model.product.Product;
import view.graphic.alert.AlertController;
import view.graphic.alert.AlertType;
import view.graphic.fxml.accountMenus.manager.RequestTable;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ManageProductsFxml implements Initializable {

    public TableView<ProductTmp> mainTable;
    public TableColumn<ProductTmp, String> idColumn;
    public TableColumn<ProductTmp, String> nameColumn;
    public TextArea buyersField;
    public Text buyersText;
    public Separator separator1;
    public Text editText;
    public Separator separator2;
    public TextField description;
    public TextField price;
    public TextField count;
    public Button editButton;
    public TextArea optionalAddArea;
    public TextArea numericalAddArea;
    public TextArea numericalRemoveArea;
    public TextArea optionalRemoveArea;
    public Text fieldsText;
    public Separator separator3;
    public TextArea fieldsArea;
    public Separator separator;
    public Text priceText;
    public Text countText;

    private String productId;
    private Product product;

    //numericalFields
    private HashMap<String, Double> hashMapAddNF = new HashMap<>();
    private ArrayList<String> removeNF = new ArrayList<>();
    //optionalFields
    private HashMap<String, ArrayList<String>> hashMapAddOF = new HashMap<>();
    private ArrayList<String> removeOF = new ArrayList<>();

    private ArrayList<ProductTmp> convertToProductTmp() throws Exception {
        SellerController sellerController = SellerController.getInstance();
        ArrayList<Product> products = sellerController.getSellerProducts();
        ArrayList<ProductTmp> productTmpS = new ArrayList<>();
        for (Product product : products) {
            ProductTmp productTmp = new ProductTmp(product.getName(), Integer.parseInt(product.getId()));
            productTmpS.add(productTmp);
        }
        return productTmpS;
    }

    private ObservableList<ProductTmp> getProducts() throws Exception {
        ObservableList<ProductTmp> productTmpS = FXCollections.observableArrayList();
        ArrayList<ProductTmp> products = convertToProductTmp();
        for (ProductTmp productTmp : products) {
            productTmpS.add(productTmp);
        }
        return productTmpS;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setOpacity(0);
        idColumn.setCellValueFactory(new PropertyValueFactory<ProductTmp, String>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<ProductTmp, String>("productName"));
        try {
            mainTable.setItems(getProducts());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleEdit(ActionEvent actionEvent) {
        editField();
        String finalDescription = description.getText();
        String finalPrice = price.getText();
        String finalCount = count.getText();
        SellerController sellerController = SellerController.getInstance();
        ArrayList<String> details = new ArrayList<>();
        details.add(finalDescription);
        details.add(finalCount);
        details.add(finalPrice);
        System.out.println(finalPrice);
        System.out.println(finalDescription);
        System.out.println(finalCount);
        System.out.println(product.getName());
        try {
            sellerController.editProduct(productId, details, removeNF, hashMapAddNF, removeOF, hashMapAddOF);
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
        setOpacity(0);
    }

    private void editField() {
        //add numerical fields
        String numericalF = numericalAddArea.getText();
        String[] nf = numericalF.split("\\n");
        if (!nf[0].equals("")) {
            for (int i = 0; i < nf.length; i++) {
                String[] nFields3 = nf[i].split("\\s+");
                hashMapAddNF.put(nFields3[0], Double.parseDouble(nFields3[2]));
            }
        }
        //add optional fields
        String optionalF = optionalAddArea.getText();
        String[] of = optionalF.split("\\n");
        if (!of[0].equals("")) {
            for (int i = 0; i < of.length; i++) {
                String[] oFields = of[i].split("\\s+");
                ArrayList<String> options = new ArrayList<>();
                for (int j = 2; j < oFields.length; j++) {
                    options.add(oFields[j]);
                }
                System.out.println(oFields[0]);
                for(String string : options)
                    System.out.println(string);
                hashMapAddOF.put(oFields[0], options);
            }
        }
        //remove numerical fields
        String numericalF1 = numericalRemoveArea.getText();
        String[] nf1 = numericalF1.split("\\n");
        if (!nf1[0].equals("")) {
            for (int i = 0; i < nf1.length; i++) {
                removeNF.add(nf1[i]);
            }
        }
        //remove optional fields
        String optionalF1 = optionalRemoveArea.getText();
        String[] of1 = optionalF1.split("\\n");
        if (!of1[0].equals("")) {
            for (int i = 0; i < of1.length; i++) {
                removeOF.add(of1[i]);
            }
        }
    }

    private void setOpacity(int x) {
        separator1.setOpacity(x);
        separator2.setOpacity(x);
        separator3.setOpacity(x);
        separator.setOpacity(x);

        priceText.setOpacity(x);
        countText.setOpacity(x);

        buyersField.setOpacity(x);
        buyersText.setOpacity(x);

        editButton.setOpacity(x);
        editText.setOpacity(x);

        description.setOpacity(x);
        price.setOpacity(x);
        count.setOpacity(x);

        numericalAddArea.setOpacity(x);
        numericalRemoveArea.setOpacity(x);
        optionalAddArea.setOpacity(x);
        optionalRemoveArea.setOpacity(x);

        fieldsArea.setOpacity(x);
        fieldsText.setOpacity(x);
    }

    private void clearTextFields() {
        buyersField.clear();
        fieldsArea.clear();
        price.clear();
        count.clear();
        numericalRemoveArea.clear();
        optionalRemoveArea.clear();
        numericalAddArea.clear();
        optionalAddArea.clear();
        description.clear();
    }

    public void handleClicked(MouseEvent mouseEvent) throws Exception {
        productId = mainTable.getSelectionModel().getSelectedItem().getId();
        product = Product.getProductById(productId);

        setOpacity(1);
        clearTextFields();

        SellerController sellerController = SellerController.getInstance();
        ArrayList<Product> products = sellerController.getAllProducts();
        ArrayList<Field> fields = product.getGeneralFields();

        ArrayList<Buyer> buyers = product.getBuyers();
        for(Buyer buyer : buyers){
            buyersField.appendText(buyer.getUsername()+"\n");
        }

        for (Field field : fields) {
            fieldsArea.appendText(field.getType() + ":" + "\n" + field.getName() + "\n" + "\n");
        }

        price.appendText(Double.toString(product.getPrice(SellerController.getSeller())));
        count.appendText(Integer.toString(product.getCount(SellerController.getSeller())));
        description.appendText(product.getDescription());
    }
}
