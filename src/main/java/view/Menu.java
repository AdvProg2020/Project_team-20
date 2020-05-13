package view;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Menu {
    private static ArrayList<Menu> allMenus;
    protected static Scanner scanner;
    protected String name;
    protected Menu parent;
    protected ArrayList<Menu> subMenus;
    protected ArrayList<String> patterns;
    protected ArrayList<String> regex;


    public Menu(String name, Menu parent) {
        this.name = name;
        this.parent = parent;
        this.patterns = new ArrayList<>();
        this.regex = new ArrayList<>();
        scanner = new Scanner(System.in);
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

    public Scanner getScanner() {
        return scanner;
    }

    public ArrayList<String> getPatterns() {
        return patterns;
    }

    public ArrayList<String> getRegex() {
        return regex;
    }

    public void setPatterns(ArrayList<String> patterns) {
        this.patterns = patterns;
    }

    public void setRegex(ArrayList<String> regex) {
        this.regex = regex;
    }

    public static ArrayList<Menu> getAllMenus() {
        return allMenus;
    }

    public abstract void show();

    public abstract void execute();

    public Menu back() {
        return this.getParent();
    }

    public Menu enter(Menu subMenu) {
        subMenu.setParent(this);
        return subMenu;
    }

    private static Matcher getMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }

}
