package controller.product.filter;

import model.product.Product;

import java.time.LocalDateTime;
import java.util.Comparator;


class  CompareDates implements Comparator<Product> {
    @Override
    public int compare(Product product1, Product product2) {
        int compareAddingDates = product2.getAddingDate().compareTo(product1.getAddingDate());
        int compareIDS =  product2.getId().compareTo(product1.getId());
        if(compareAddingDates==0){
            return compareIDS;
        }
        else {
            return compareAddingDates;
        }
    }
}
