package view;

import controller.Main;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Menu {
    private static ArrayList<Menu> allMenus = new ArrayList<>();
    protected static Scanner scanner;
    protected String name;
    protected String command;
    protected Menu parent;
    protected ArrayList<Menu> subMenus;
    protected ArrayList<Pattern> patterns;
    protected ArrayList<String> regex;
    protected ArrayList<String> methods;

    public Menu(String name) {
        this.name = name;
        this.patterns = new ArrayList<>();
        this.regex = new ArrayList<>();
        this.methods = new ArrayList<>();
        scanner = new Scanner(System.in);
        this.methods.add("enterWithName");
        this.methods.add("back");
        this.methods.add("help");
        this.regex.add("enter (RegisterAndLoginMenu|AllProductsMenu)");
        this.regex.add("back");
        this.regex.add("help");
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

    public ArrayList<Pattern> getPatterns() {
        return patterns;
    }

    public ArrayList<String> getRegex() {
        return regex;
    }

    public void setPatterns(ArrayList<Pattern> patterns) {
        this.patterns = patterns;
    }

    public void setRegex(ArrayList<String> regex) {
        this.regex = regex;
    }

    public static ArrayList<Menu> getAllMenus() {
        return allMenus;
    }

    public ArrayList<String> getMethods() {
        return methods;
    }

    public void setMethods(ArrayList<String> methods) {
        this.methods = methods;
    }

    public void show() {
        System.out.println("Welcome To " + this.getName());
    }

    public void help() {
        System.out.println("Help command:");
        System.out.println("these are the commands you can use in " + this.getName());
        for (int i = 0; i < methods.size(); i++) {
            System.out.println(i + 1 + ") " + regex.get(i));
        }
    }

    public static Menu getMenuFromName(String name) throws Exception {
        for (Menu menu : allMenus) {
            if (menu.getName().equals(name))
                return menu;
        }
        throw new MenuHasNotExistException();
    }

    public static class MenuHasNotExistException extends Exception {
        public MenuHasNotExistException() {
            super("Menu has not exist");
        }
    }

    public void back() {
        if (this.getParent() != null)
            Main.setCurrentMenu(this.getParent());
        Main.setCurrentMenu(this);
    }

    public void enterWithName(String subMenuName) {
        try {
            Menu subMenu = getMenuFromName(subMenuName);
            subMenu.setParent(this);
            Main.setCurrentMenu(subMenu);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void enter(Menu subMenu) {
        subMenu.setParent(this);
        Main.setCurrentMenu(subMenu);
    }

    public void preProcess() {
        for (int i = 0; i < regex.size(); i++) {
            patterns.add(i, Pattern.compile(regex.get(i)));
        }
    }

    private Method getMethodByName(String name) {
        Class clazz = this.getClass();
        for (Method declaredMethod : clazz.getDeclaredMethods())
            if (declaredMethod.getName().equals(name)) return declaredMethod;
        return null;
    }

    public void callCommand(String command) throws InvocationTargetException, IllegalAccessException {
        Matcher matcher;
        for (int i = 0; i < patterns.size(); i++) {
            matcher = patterns.get(i).matcher(command);
            if (matcher.matches()) {
                ArrayList<Object> inputs = new ArrayList<>();
                for (int j = 1; j <= matcher.groupCount(); j++) {
                    Object input = null;
                    try {
                        input = Double.parseDouble(matcher.group(j));
                    } catch (Exception e) {
                        if (!matcher.group(j).isEmpty())
                            input = matcher.group(j);
                    }
                    if (input != null) inputs.add(input);
                }
                Objects.requireNonNull(getMethodByName(methods.get(i))).invoke(this, inputs.toArray());
                return;
            }
        }
        System.out.println("invalidCommand");
    }

    public void getCommand() {
        this.show();
        if (!(command = scanner.nextLine().trim()).equals("end")) {
            try {
                this.callCommand(command);
            } catch (InvocationTargetException | IllegalAccessException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        } else
            System.exit(0);
    }


}
