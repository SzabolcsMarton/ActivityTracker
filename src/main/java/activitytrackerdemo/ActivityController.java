package activitytrackerdemo;

import java.util.Arrays;
import java.util.Scanner;

public class ActivityController {

    Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        ActivityController controller = new ActivityController();
        controller.runMenu();
    }

    private void runMenu() {
        MenuItem menuItem = null;
        printTitle();

        do {
            printMenu();
            try {
                int option= Integer.parseInt(scanner.nextLine());
                menuItem = MenuItem.byOrdinal(option);
                assert menuItem != null;
                controll(menuItem);
            }catch (Exception exception){
                System.out.println("Helytelen menüpont! ");
            }
        } while (menuItem != MenuItem.EXIT);

        System.out.println("\n Viszontlátásra!");
    }

    private void printTitle(){
        System.out.println("Activity Tracker");
        System.out.println("Ezzel az alkalmazással nyilvántarthatjuk sportolási tevékenységeinket, és lekérdezhetjük eredményeinket.");
    }

    private void printMenu() {
        System.out.println("\nKérem válasszon a felsorolt menüpontokból, \nmajd nyomja meg az enter-t:");
        System.out.println();
        for(MenuItem actual : MenuItem.values()){
            System.out.println(actual.toString());
        }
    }

    private void controll(MenuItem option) {

        switch (option) {
            case CREATE:
                break;
        }
    }
}
