package view;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Menu {
    private static ArrayList<Menu> allMenus;
    protected static Scanner scanner;
    protected String name;
    protected Menu parent;
    protected ArrayList<Menu> subMenus;
    protected ArrayList<Pattern> patterns;
    protected ArrayList<String> regex;
    protected ArrayList<String> methods;


    public Menu(String name) {
        this.name = name;
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

    public abstract void show();

    public void help() {
        System.out.println("Help command:");
        System.out.println("these are the commands you can use in " + this.getName());
    }

    public void execute() {
    }

    public Menu back() {
        if (this.getParent() != null)
            return this.getParent();
        return this;
    }

    public Menu enter(Menu subMenu) {
        subMenu.setParent(this);
        return subMenu;
    }

    public static Matcher getMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }


    private Method getMethodByName(String name) {
        Class clazz = this.getClass();
        for (Method declaredMethod : clazz.getDeclaredMethods())
            if (declaredMethod.getName().equals(name)) return declaredMethod;
        return null;
    }

    public void callCommand(String command) throws InvocationTargetException, IllegalAccessException, Errors {
        for (int i = 0; i < patterns.size(); i++) {
            Matcher matcher = patterns.get(i).matcher(command);
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
            }
        }
        System.out.println("invalidCommand");
    }

    public void getCommand() {
        String command;
        while (!(command = scanner.nextLine()).equals("end")) {
            try {
                this.callCommand(command);
            } catch (InvocationTargetException | IllegalAccessException | Errors e) {
                e.printStackTrace();
            }
        }
    }

}
