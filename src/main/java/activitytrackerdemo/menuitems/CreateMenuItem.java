package activitytrackerdemo.menuitems;

import activitytrackerdemo.Activity;
import activitytrackerdemo.ActivityService;
import activitytrackerdemo.ActivityType;


import java.time.LocalDateTime;
import java.util.Scanner;

public class CreateMenuItem implements MenuItem {

    ActivityService activityService;
    private static final String START_TIME_FORMAT = "yyyy MM dd";

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
            success = activityService.saveActivity(activity);

            if(!success){
                System.out.println("Hibás adatok,kérlek probáld újra!");
            }else {
                System.out.println("\n\t\t--------Activity sikeresen elmentve!--------");
            }
        }while (!success);

    }

    private LocalDateTime getStartTime(Scanner scanner) {

        System.out.println("Kezdés időpont (" + START_TIME_FORMAT+ ") :");
        String timeString = scanner.nextLine();
        return LocalDateTime.now(); //.minusYears(2);
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
