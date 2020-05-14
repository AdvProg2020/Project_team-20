package controller.product.filter;

import model.product.Product;

import java.util.Comparator;
import java.util.Comparator;

    class  CompareNumberOfViews implements Comparator<Product> {

        @Override
        public int compare(Product product, Product anotherProduct) {
            int numberOfViewsCompare = Long.compare(anotherProduct.getViews(),product.getViews()) ;
            return numberOfViewsCompare;
        }
    }
