package activitytrackerdemo.menuitems;

import activitytrackerdemo.Activity;
import activitytrackerdemo.ActivityService;
import activitytrackerdemo.ActivityType;

import java.util.List;
import java.util.Scanner;

public class ListActivityMenuItem implements MenuItem {

    private final ActivityService activityService;
    private List<Activity> allActivities;
    private List<Activity> filteredActivities;

    public ListActivityMenuItem(ActivityService activityService) {
        this.activityService = activityService;
    }

    @Override
    public void process(Scanner scanner) {
        allActivities = activityService.getAllActivities();

        System.out.println("Tevékenységek listázása menüpont");
        ensureActivitiesNotNullOrEmpty();

        System.out.println("Szeretne szűrni a találatokat?");
        System.out.println("y- Igen, n- Nem");
        String answer = scanner.nextLine();
        processFilter(answer, scanner);

    }

    private void processFilter(String answer, Scanner scanner) {
        if (answer.equalsIgnoreCase("n")) {
            printActivities(allActivities);
        } else if (answer.equalsIgnoreCase("y")) {
            processFilterOptions(scanner);
        } else {
            process(scanner);
        }
    }

    private void processFilterOptions(Scanner scanner) {
        printFilterSubMenu();
        int filterOption = scanner.nextInt();
        scanner.nextLine();
        if (filterOption == 1) {
            ActivityType type = MenuHelpers.getActivityType(scanner);
            filterByType(type);
            printActivities(filteredActivities);
        }
    }

    private void filterByType(ActivityType type) {
        filteredActivities = allActivities
                .stream()
                .filter(activity -> activity.getActivityType().equals(type)).toList();
    }

    private void printActivities(List<Activity> activities) {
        System.out.println("\t\t -------- Összes tevékenység --------");
        for (Activity actual : activities) {
            System.out.println('\t' + actual.toString());
        }
        System.out.println("\t\t -------------------------------------");
    }

    private void ensureActivitiesNotNullOrEmpty() {
        if (allActivities == null || allActivities.isEmpty()) {
            System.out.println("--- Nincsenek tárolt tevékenységek ---");
            return;
        }
    }

    private void printFilterSubMenu() {
        System.out.println("1 - Szűrés tipus alapján");
        System.out.println("2 - Szűrés idősáv alapján");
        System.out.println("3 - Szűrés leírás részlet alapján");
    }


}
