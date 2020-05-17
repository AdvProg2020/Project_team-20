package controller.product.filter;

import model.product.Product;

import java.time.LocalDateTime;
import java.util.Comparator;


class  CompareDates implements Comparator<LocalDateTime> {
    @Override
    public int compare(LocalDateTime date1, LocalDateTime date2) {
        return date2.compareTo(date1);
    }
}
