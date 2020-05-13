package view;

public class MainMenu extends Menu {

    public MainMenu() {
        super("MainMenu");
        this.methods.add("enterWithName");
        this.methods.add("back");
        this.methods.add("help");
        this.regex.add("enter [RegistrationMenu|AllProductsMenu]");
        this.regex.add("back");
        this.regex.add("help");
    }

    @Override
    public void help() {
        super.help();
        System.out.println("1) enter RegistrationMenu  " +
                "  2) enter AllProductsMenu" +
                "  3)back" +
                "  4)help");
    }


}
