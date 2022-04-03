package activitytrackerdemo.menuitems;

import activitytrackerdemo.Activity;
import activitytrackerdemo.ActivityService;

import java.util.List;
import java.util.Scanner;

public class ListActivityMenuItem implements MenuItem {

    private final ActivityService activityService;

    public ListActivityMenuItem(ActivityService activityService) {
        this.activityService = activityService;
    }

    @Override
    public void process(Scanner scanner) {
        System.out.println("\t\t -------- Összes tevékenység --------");
        System.out.println();
        List<Activity> allActivities = activityService.getAllActivities();
        if (allActivities == null || allActivities.isEmpty()) {
            System.out.println("Nincsenek tárolt tevékenységek");
            return;
        }
        printActivities(allActivities);
        System.out.println("\t\t -------------------------------------");
        System.out.println();
    }

    private void printActivities(List<Activity> activities) {
        for (Activity actual : activities) {
            System.out.println('\t' + actual.toString());
        }
    }
}
