package activitytrackerdemo.menuitems;

import activitytrackerdemo.Activity;
import activitytrackerdemo.ActivityService;
import activitytrackerdemo.ActivityType;

import java.time.LocalDateTime;
import java.util.Scanner;

public class FindOneActivityMenuItem implements MenuItem {
    ActivityService activityService;

    public FindOneActivityMenuItem(ActivityService activityService) {
        this.activityService = activityService;
    }

    @Override
    public void process(Scanner scanner) {
        System.out.println("Keresés menüpont");
        System.out.println("A tevékenység megkereséséhez add meg a következő adatokat!");

        ActivityType type = MenuHelpers.getActivityType(scanner);
        LocalDateTime time = MenuHelpers.getStartTime(scanner);
        Activity activity = activityService.findOneActivityByTypeAndDate(time, type);

        if (activity == null) {
            System.out.println("\nNem található tevékenység a megadott adatokkal");
        } else {
            System.out.println("\nA keresett tevékenység:");
            System.out.println("\t" + activity);
        }

    }
}
