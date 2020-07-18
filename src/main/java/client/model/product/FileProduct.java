package client.model.product;

import client.model.account.Account;
import client.model.account.Seller;
import client.model.product.Field.Field;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;

public class FileProduct extends Product {
    private File file;
    private String fileType;

    public FileProduct(ArrayList<Field> generalFields, String description, double price, Seller seller, File file, String fileType) {
        super(generalFields, description, price, seller);
        this.file = file;
        this.fileType = fileType;
        this.productType = ProductType.FILE;
    }

    @Override
    public void changeStateAccepted() {
        super.changeStateAccepted();
    }

    @Override
    public void changeStateRejected() {
        super.changeStateRejected();
    }

    @Override
    public void changeStateEdited(ArrayList<Field> generalFields, String description, double price, Seller seller,
                                  File file, String fileType) throws Exception {
        if (editedProduct == null) {
            editedProduct = new FileProduct(generalFields, description, price, seller, file, fileType);
            editedProduct.state = RequestableState.EDITED;
        } else
            throw new ProductIsUnderEditingException();
    }

    @Override
    public void edit() {
        Set<Seller> sellerSet = editedProduct.getCount().keySet();
        ArrayList<Seller> sellers = new ArrayList<>(sellerSet);
        Seller seller = sellers.get(0);
        generalFields = editedProduct.getGeneralFields();
        priceWithName.replace(seller.getUsername(), editedProduct.getPrice(seller));
        description = editedProduct.getDescription();
        file = ((FileProduct) editedProduct).getFile();
        fileType = ((FileProduct) editedProduct).getFileType();
        editedProduct = null;
        state = RequestableState.ACCEPTED;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    @Override
    public String toString() {
        try {
            Seller seller = (Seller) Account.getAccountWithUsername(sellersUsername.get(0));
            String productString = "Name                : " + name + "\n" + "\n" +
                    "RequestType         : Product" + "\n" + "\n" +
                    "Seller              : " + seller.getName() + "\n" + "\n" +
                    "Price               : " + priceWithName.get(seller.getUsername()) + "\n" + "\n" +
                    "file type               : " + fileType;
            if (state.equals(RequestableState.EDITED))
                productString = "<Edited>\n" + productString;
            return productString;
        } catch (Exception e) {
            return null;
        }
    }
}
