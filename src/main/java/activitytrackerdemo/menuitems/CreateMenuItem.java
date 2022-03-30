package activitytrackerdemo.menuitems;

import activitytrackerdemo.Activity;
import activitytrackerdemo.ActivityService;
import activitytrackerdemo.ActivityType;


import java.time.LocalDateTime;
import java.util.Scanner;

public class CreateMenuItem implements MenuItem {

    ActivityService activityService;

    public CreateMenuItem() {
        this.activityService = new ActivityService();
    }

    @Override
    public void process(Scanner scanner) {

        System.out.println("Létrehozás menü.");
        System.out.println("Kérlek add meg a következő adatokat!");
        System.out.println();

        boolean success;

        do {
            ActivityType type = MenuHelpers.getActivityType(scanner);
            LocalDateTime time = MenuHelpers.getStartTime(scanner);

            System.out.println("Leírás :");
            String description = scanner.nextLine();

            Activity activity = new Activity(time,description,type);
            success = activityService.saveActivity(activity);

            if(!success){
                System.out.println("Hibás adatok,kérlek probáld újra!");
            }else {
                System.out.println("\n\t\t--------Activity sikeresen elmentve!--------");
            }
        }while (!success);
    }
}
