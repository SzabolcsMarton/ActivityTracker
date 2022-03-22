package activitytrackerdemo;

import activitytrackerdemo.menuitems.*;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

public class ActivityController {



    public static void main(String[] args) {

        ActivityController controller = new ActivityController();
        controller.runMenu();
    }

    private void runMenu() {
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
        MenuItem menuItem;
        ActivityService activityService = getActivityService();
        switch (option) {
            case CREATE:
                menuItem = new CreateMenuItem(activityService);
                break;

            case LIST:
                menuItem = new ListActivityMenuItem();
                break;

            case DELETE:
                menuItem = new DeleteActivityMenuItem();
                break;

            case EXIT:
                menuItem = new ExitMenuItem();
                break;

            default:
                menuItem = null;
        }
        return Optional.of(menuItem);
    }

    private ActivityService getActivityService() {
        MariaDbDataSource dataSource = new MariaDbDataSource();

        try {
            dataSource.setUrl("jdbc:mariadb://localhost:3306/activitytrackertemplate?useUnicode=true");
            dataSource.setUser("root");
            dataSource.setPassword("root");
        } catch (SQLException sqle) {
            throw new IllegalStateException("Cannot reach DataBase!", sqle);
        }

        ActivityRepository activityRepository = new ActivityRepository(dataSource);
        ActivityService activityService = new ActivityService(activityRepository);

        return activityService;
    }
}
