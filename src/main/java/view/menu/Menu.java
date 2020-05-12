package view.menu;

import java.util.ArrayList;

public abstract class Menu {
    protected String name;
    protected Menu parent;
    protected ArrayList<Menu> subMenus;
    private static ArrayList<Menu> allMenus;

    public Menu(String name, Menu parent) {
        this.name = name;
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Menu getParent() {
        return parent;
    }

    public void setParent(Menu parent) {
        this.parent = parent;
    }

    public ArrayList<Menu> getSubMenus() {
        return subMenus;
    }

    public void addSubMenu(Menu subMenus) {
        this.subMenus.add(subMenus);
    }

    public static void addToAllMenus(Menu menu) {
        allMenus.add(menu);
    }

    public static ArrayList<Menu> getAllMenus() {
        return allMenus;
    }

    public void show() {
    }

    public void execute() {
    }

}
