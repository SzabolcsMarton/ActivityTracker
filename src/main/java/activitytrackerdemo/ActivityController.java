package activitytrackerdemo;

import activitytrackerdemo.menuitems.*;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {

        this.activityService = activityService;
    }


    public void runMenu() {
        MenuItemType menuItemType = null;
        printTitle();
        Scanner scanner = new Scanner(System.in);
        do {
            printMenu();

            try {
                int option = scanner.nextInt();
                scanner.nextLine();
                menuItemType = MenuItemType.byOrdinal(option);
                assert menuItemType != null;

                Optional<MenuItem> menuItem = getMenuItem(menuItemType);

                menuItem.orElseThrow(() -> new IllegalArgumentException("Helytelen submenu"))
                        .process(scanner);

            } catch (Exception exception) {
                System.out.println("Helytelen menüpont! ");
            }
        } while (menuItemType != MenuItemType.EXIT);
        scanner.close();
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

    private Optional<MenuItem> getMenuItem(MenuItemType option) {
        MenuItem menuItem = switch (option) {
            case CREATE -> new CreateMenuItem(activityService);
            case LIST -> new ListActivityMenuItem(activityService);
            case DELETE -> new DeleteActivityMenuItem(activityService);
            case FINDONE -> new FindOneActivityMenuItem(activityService);
            case EXIT -> new ExitMenuItem();
        };

        return Optional.of(menuItem);
    }
}
