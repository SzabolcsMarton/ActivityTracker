package activitytrackerdemo.menuitems;

import activitytrackerdemo.Activity;
import activitytrackerdemo.ActivityService;
import activitytrackerdemo.ActivityType;

import java.time.LocalDateTime;
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
        System.out.println("\tTevékenységek listázása menüpont");
        ensureActivitiesNotNullOrEmpty();
        System.out.println("\tSzeretne szűrni a találatokat?");
        System.out.println("\ty- Igen, n- Nem\t");
        String answer = scanner.nextLine();
        processAnswer(answer, scanner);

    }

    private void processAnswer(String answer, Scanner scanner) {
        if (answer.equalsIgnoreCase("n")) {
            printActivities(allActivities);
        } else if (answer.equalsIgnoreCase("y")) {
            filterOptionsHandler(scanner);
        } else {
            process(scanner);
        }
    }

    private void filterOptionsHandler(Scanner scanner) {
        printFilterSubMenu();
        System.out.println("\t\t");
        int filterOption = scanner.nextInt();
        scanner.nextLine();
        switch (filterOption) {
            case 1 -> processFilterByType(scanner);
            case 2 -> processFilterByTimeInRange(scanner);
            case 3 -> processFilterByPartOfDescription(scanner);
            default -> filterOptionsHandler(scanner);
        }
    }

    private void processFilterByPartOfDescription(Scanner scanner) {
        System.out.println("Adja mega leírás részletet");
        String partOfDescription = scanner.nextLine();
        filterByPartOfDescription(partOfDescription);
        printActivities(filteredActivities);
    }

    private void processFilterByTimeInRange(Scanner scanner) {
        System.out.println("Adja meg az időszak kezdő időpontját: ");
        LocalDateTime timeFrom = MenuHelpers.getStartTime(scanner);
        System.out.println("Adja meg az időszak záró időpontját: ");
        LocalDateTime timeTo = MenuHelpers.getStartTime(scanner);
        filterByTimeInRange(timeFrom, timeTo);
        printActivities(filteredActivities);
    }

    private void processFilterByType(Scanner scanner) {
        ActivityType type = MenuHelpers.getActivityType(scanner);
        filterByType(type);
        printActivities(filteredActivities);
    }

    private void filterByType(ActivityType type) {
        filteredActivities = allActivities
                .stream()
                .filter(activity -> activity.getActivityType().equals(type)).toList();
    }

    private void filterByTimeInRange(LocalDateTime fromTime, LocalDateTime toTime) {
        filteredActivities = allActivities
                .stream()
                .filter(activity -> activity.getStartTime().isAfter(fromTime) && activity.getStartTime().isBefore(toTime))
                .toList();
    }

    private void filterByPartOfDescription(String partOfDescription) {
        filteredActivities = allActivities
                .stream()
                .filter(activity -> activity.getDescription().contains(partOfDescription))
                .toList();
    }

    private void printActivities(List<Activity> activities) {
        System.out.println("\t\t ------------ Tevékenységek ------------");
        System.out.println("\t\t ---------------- " + activities.size() + "-db ----------------\n");
        for (Activity actual : activities) {
            System.out.println("\t\t" + actual.toString() + "\n");
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
        System.out.println("\t\t1 - Szűrés tipus alapján");
        System.out.println("\t\t2 - Szűrés időszak alapján");
        System.out.println("\t\t3 - Szűrés leírás részlet alapján");
    }


}
