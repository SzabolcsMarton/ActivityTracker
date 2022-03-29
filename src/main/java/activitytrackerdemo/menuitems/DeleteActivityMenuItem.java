package activitytrackerdemo.menuitems;

import activitytrackerdemo.ActivityService;

import java.util.Scanner;

public class DeleteActivityMenuItem implements MenuItem {

    ActivityService activityService;

    public DeleteActivityMenuItem() {
        this.activityService = new ActivityService();
    }

    @Override
    public void process(Scanner scanner) {

    }
}
