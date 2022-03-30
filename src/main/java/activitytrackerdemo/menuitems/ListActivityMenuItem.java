package activitytrackerdemo.menuitems;

import activitytrackerdemo.Activity;
import activitytrackerdemo.ActivityService;

import java.util.List;
import java.util.Scanner;

public class ListActivityMenuItem implements MenuItem {

    private final ActivityService activityService;

    public ListActivityMenuItem() {
        this.activityService = new ActivityService();
    }

    @Override
    public void process(Scanner scanner) {
        System.out.println("Összes tevékenység:");
        System.out.println();
        List<Activity> allActivities = activityService.getAllActivities();
        if(allActivities == null || allActivities.isEmpty()){
            System.out.println("Nincsenek tárolt tevékenységek");
            return;
        }
        printActivities(allActivities);
    }

    private void printActivities(List<Activity> activities){
        for (Activity actual : activities){
            System.out.println('\t' + actual.toString());
        }
    }
}
