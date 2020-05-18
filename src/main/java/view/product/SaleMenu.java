package view.product;

import controller.product.filter.AllProductsController;
import controller.product.filter.SaleController;
import model.filter.Filter;
import model.product.Product;
import model.product.Sale;
import view.Menu;

import java.util.ArrayList;

public class SaleMenu extends Menu {
    SaleController saleController;

    private static SaleMenu saleMenu = null;

    public SaleMenu() {
        super("SaleMenu");
        setRegex();
        setMethods();
        saleController = new SaleController();
        preProcess();
    }

    public void showProduct(String id) {
        try {
            Product product = AllProductsController.getInstance().getProduct(id);
            ProductMenu productMenu = new ProductMenu(product);
            enter(productMenu);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void showAvailableFilters() {
        System.out.println("You can filter products by their names, categories.");
        System.out.println("Also there is an option which you can filter products by their numerical or " +
                "optional fields.");
    }

    public void filter(String filterName) {
        System.out.println("Please insert the type of your filter. ( category | product name | optional field | " +
                "numerical field");
        ArrayList<String> details = new ArrayList<>();
        details.add(scanner.nextLine());
        if (details.get(0).equals("optional field")) {
            System.out.println("How many optional field do you want to insert?");
            int count = Integer.parseInt(scanner.nextLine());
            for (int i = 0; i < count; i++)
                details.add(scanner.nextLine());
        }
        if (details.get(0).equals("numerical field")) {
            System.out.println("Please insert you min filed.");
            details.add(scanner.nextLine());
            System.out.println("Please insert you max filed.");
            details.add(scanner.nextLine());
        }
        try {
            saleController.filter(filterName, details);
            System.out.println("Filter was added successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void showCurrentFilters() {
        ArrayList<Filter> currentFilters = saleController.getFilters();
        System.out.println("Current filters are:");
        for (Filter filter : currentFilters) {
            System.out.println("Name               : " + filter.getName());
        }
    }

    public void disableFilter(String filterName) {
        try {
            saleController.disAbleFilter(filterName);
            System.out.println("The filter was disabled successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void showAvailableSorts() {
        System.out.println("You can sort product by time/score/views. The default sort is by views.");
    }

    public void sort(String sortType) {
        try {
            saleController.changeSort(sortType);
            System.out.println("The sort was successful!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void showCurrentSort() {
        System.out.println("The current sort is: " + saleController.getCurrentSort());
    }

    public void disableSort() {
        saleController.disableSort();
        System.out.println("The sort was disabled. The default sort is viewsSort.");
    }

    public void showOffs() {
        ArrayList<Sale> sales = saleController.getAllSales();
        System.out.format("%-20s%-20s%-20s%-20s\n", "Name", "Price", "Off", "Id");
        for (Sale sale : sales) {
            for (Product product : sale.getProducts()) {
                System.out.format("%-20s%-20s%-20f%-20s", product.getName(), product.getPrice(sale.getSeller()),
                        sale.getSalePercentage() * 100, product.getId());
            }
        }
    }

    private void setRegex() {
        regex.add("show product (\\w+)");
        regex.add("show available filters");
        regex.add("filter");
        regex.add("current filters");
        regex.add("disable filter (\\w+)");
        regex.add("show available sorts");
        regex.add("sort (ByScores|ByDates|ByNumberOfViews)");
        regex.add("current sort");
        regex.add("disable sort");
        regex.add("showOffs");
    }

    private void setMethods() {
        methods.add("showProduct");
        methods.add("showAvailableFilters");
        methods.add("filter");
        methods.add("showCurrentFilters");
        methods.add("disableFilter");
        methods.add("showAvailableSorts");
        methods.add("sort");
        methods.add("showCurrentSort");
        methods.add("disableSort");
        methods.add("showOffs");
    }

    public static SaleMenu getInstance() {
        if (saleMenu == null)
            saleMenu = new SaleMenu();
        return saleMenu;
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void help() {
        super.help();
    }

    @Override
    public void enterWithName(String subMenuName) {
        super.enterWithName(subMenuName);
    }

    @Override
    public void back() {
        super.back();
    }
}
