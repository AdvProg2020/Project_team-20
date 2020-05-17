package view.product;

import controller.product.filter.AllProductsController;
import model.filter.Filter;
import model.product.Category;
import model.product.Product;
import view.Menu;

import java.util.ArrayList;

public class AllProductsMenu extends Menu {
    AllProductsController allProductsController;

   private static AllProductsMenu allProductsMenu = null;

    private AllProductsMenu() {
        super("AllProductsMenu");
        setRegex();
        setMethods();
        allProductsController = AllProductsController.getInstance();
        //showProducts();
        preProcess();
    }

    public static AllProductsMenu getInstance() {
        if (allProductsMenu == null)
            allProductsMenu = new AllProductsMenu();
        return allProductsMenu;
    }

    //NOT SURE!!!!!!!!!!
    public void viewCategories() {
        ArrayList<Category> categories = allProductsController.getAllCategories();
        System.out.println("All categories:");
        for (Category category:categories)
            System.out.println(category.getName());
    }

    public void showProducts() {
        try {
            ArrayList<Product> products = allProductsController.showProducts();
            System.out.format("%-20s%-20s\n","Name","id");
            for (Product product:products) {
                System.out.format("%-20s%-20s\n", product.getName(), product.getId());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void showProduct(double idDouble) {
        try {
            Product product = allProductsController.getProduct(Integer.toString((int)idDouble));
            ProductMenu productMenu = new ProductMenu(product);
            enter(productMenu);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void showAvailableFilters() {
        System.out.println("You can filter products by their names, categories.");
        System.out.println("Also there is an option which you can filter products by their numerical or optional fields.");
    }

    public void filter(String filterName) {
        System.out.println("Please insert the type of your filter. ( category | product name | optional field | numerical field");
        ArrayList<String> details = new ArrayList<>();
        details.add(scanner.nextLine());
        if (details.get(0).equals("optional field")) {
            System.out.println("How many optional field do you want to insert?");
            int count = Integer.parseInt(scanner.nextLine());
            for (int i=0; i<count; i++)
                details.add(scanner.nextLine());
        }
        if (details.get(0).equals("numerical field")) {
            System.out.println("Please insert you min filed.");
            details.add(scanner.nextLine());
            System.out.println("Please insert you max filed.");
            details.add(scanner.nextLine());
        }
        try {
            allProductsController.filter(filterName, details);
            System.out.println("Filter was added successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void showCurrentFilters() {
        ArrayList<Filter> currentFilters = allProductsController.getFilters();
        System.out.println("Current filters are:");
        for (Filter filter:currentFilters) {
            System.out.println("Name               : " + filter.getName());
        }
    }

    public void disableFilter(String filterName) {
        try {
            allProductsController.disAbleFilter(filterName);
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
            allProductsController.changeSort(sortType);
            System.out.println("The sort was successful!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void showCurrentSort() {
        System.out.println("The current sort is: " + allProductsController.getCurrentSort());
    }

    public void disableSort() {
        allProductsController.disableSort();
        System.out.println("The sort was disabled. The default sort is viewsSort.");
    }

    public void purchase() {
        try {
            allProductsController.purchase();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void setRegex() {
        regex.add("view categories");
        regex.add("show products");
        regex.add("show product (\\d+)");
        regex.add("show available filters");
        regex.add("filter");
        regex.add("current filters");
        regex.add("disable filter (\\w+)");
        regex.add("show available sorts");
        regex.add("sort (ByScores|ByDates|ByNumberOfViews)");
        regex.add("current sort");
        regex.add("disable sort");
        regex.add("purchase");
        regex.add("showProducts");
    }

    private void setMethods() {
        methods.add("viewCategories");
        methods.add("showProducts");
        methods.add("showProduct");
        methods.add("showAvailableFilters");
        methods.add("filter");
        methods.add("showCurrentFilters");
        methods.add("disableFilter");
        methods.add("showAvailableSorts");
        methods.add("sort");
        methods.add("showCurrentSort");
        methods.add("disableSort");
        methods.add("purchase");
        methods.add("showProducts");
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
