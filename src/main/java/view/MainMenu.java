package view;

public class MainMenu extends Menu {

    public MainMenu() {
        super("MainMenu");
    }

    @Override
    public void help() {
        super.help();
        System.out.println("1) enter RegistrationMenu  " +
                "  2) enter AllProductsMenu" +
                "  3) back" +
                "  4) help");
    }


}
