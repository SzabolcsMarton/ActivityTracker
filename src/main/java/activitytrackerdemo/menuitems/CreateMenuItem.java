package activitytrackerdemo.menuitems;

import activitytrackerdemo.Activity;
import activitytrackerdemo.ActivityService;
import activitytrackerdemo.ActivityType;


import java.time.LocalDateTime;
import java.util.Scanner;

public class CreateMenuItem implements MenuItem {

    ActivityService activityService;
    private static final String STARTTIME_FORMAT = "yyyy MM dd";

    public CreateMenuItem() {
        this.activityService = new ActivityService();
    }

    @Override
    public void process(Scanner scanner) {

        System.out.println("Létrehozás menü.");
        System.out.println("Kérlek add meg a következő adatokat!");
        System.out.println();

        System.out.println("Aktivity típusa:");
        for (ActivityType actual : ActivityType.values()) {
            System.out.println("\t" + actual.getDescription());
        }
        System.out.print("\t");
        int ordinal = scanner.nextInt();
        scanner.nextLine();
        ActivityType type = ActivityType.byOrdinal(ordinal);

        LocalDateTime time = getStartTime(scanner);

        System.out.println("Leírás :");
        String description = scanner.nextLine();

        Activity activity = new Activity(time,description,type);
        activityService.saveActivity(activity);
    }

    private LocalDateTime getStartTime(Scanner scanner) {

        System.out.println("Kezdés időpont (" + STARTTIME_FORMAT+ ") :");
        String timeString = scanner.nextLine();
        return LocalDateTime.now();
        //TODO
//        SimpleDateFormat formatter = new SimpleDateFormat(STARTTIME_FORMAT);
//        LocalDateTime time = null;
//
//        try {
//            time = formatter.parse(timeString);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return time;
    }
}
