package activitytrackerdemo.menuitems;

import activitytrackerdemo.Activity;
import activitytrackerdemo.ActivityService;
import activitytrackerdemo.ActivityType;


import java.time.LocalDateTime;
import java.util.Scanner;

public class CreateMenuItem implements MenuItem {

    ActivityService activityService;
    private static final String START_DAY_FORMAT = "yyyy,MM,dd,hh,mm";

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

        System.out.println("Kezdés napja (" + START_DAY_FORMAT + ") :");

        String startDate = scanner.nextLine();

        String[]startDateAndTime = startDate.split(",");
        int year = Integer.parseInt(startDateAndTime[0].trim());
        int month = Integer.parseInt(startDateAndTime[1].trim());
        int day = Integer.parseInt(startDateAndTime[2].trim());
        int hours = Integer.parseInt(startDateAndTime[3].trim());
        int minutes = Integer.parseInt(startDateAndTime[4].trim());

        return LocalDateTime.of(year,month,day,hours,minutes);

    }


}
