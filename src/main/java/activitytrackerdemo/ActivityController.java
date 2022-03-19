package activitytrackerdemo;

import activitytrackerdemo.submenus.*;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.Scanner;

public class ActivityController {

    Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        ActivityController controller = new ActivityController();
        controller.runMenu();
    }

    private void runMenu() {
        MenuItemType menuItem = null;
        printTitle();

        do {
            printMenu();

            try {
                int option = Integer.parseInt(scanner.nextLine());
                menuItem = MenuItemType.byOrdinal(option);
                assert menuItem != null;

                Optional<SubMenu> subMenu = getSubMenu(menuItem);
                subMenu.orElseThrow(() -> new IllegalArgumentException("Helytelen submenu")).process();
            } catch (Exception exception) {
                System.out.println("Helytelen menüpont! ");
            }
        } while (menuItem != MenuItemType.EXIT);

    }

    private void printTitle() {
        System.out.println("Activity Tracker");
        System.out.println("Ezzel az alkalmazással nyilvántarthatjuk sportolási tevékenységeinket, és lekérdezhetjük eredményeinket.");
    }

    private void printMenu() {
        System.out.println("\nKérem válasszon a felsorolt menüpontokból, \nmajd nyomja meg az enter-t:");
        System.out.println();
        for (MenuItemType actual : MenuItemType.values()) {
            System.out.println(actual.toString());
        }
    }

    private Optional<SubMenu> getSubMenu(MenuItemType option) {
        SubMenu subMenu;
        switch (option) {
            case CREATE:
                subMenu = new CreateSubMenu();
                break;

            case LIST:
                subMenu = new ListActivitySubMenu();
                break;

            case DELETE:
                subMenu = new DeleteActivitySubMenu();
                break;

            case EXIT:
                subMenu = new ExitSubMenu();
                break;

            default:
                subMenu = null;
        }
        return Optional.of(subMenu);
    }
}
