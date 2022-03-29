package activitytrackerdemo.menuitems;

import activitytrackerdemo.ActivityType;

import java.time.LocalDateTime;
import java.util.Scanner;

public class MenuHelpers {

    public static final String START_DAY_FORMAT = "yyyy,MM,dd,hh,mm";

    public static LocalDateTime getStartTime(Scanner scanner) {
        System.out.println("Kezdés napja (" + MenuHelpers.START_DAY_FORMAT + ") :");

        String startDate = scanner.nextLine();

        String[]startDateAndTime = startDate.split(",");
        int year = Integer.parseInt(startDateAndTime[0].trim());
        int month = Integer.parseInt(startDateAndTime[1].trim());
        int day = Integer.parseInt(startDateAndTime[2].trim());
        int hours = Integer.parseInt(startDateAndTime[3].trim());
        int minutes = Integer.parseInt(startDateAndTime[4].trim());

        return LocalDateTime.of(year,month,day,hours,minutes);

    }

    public static ActivityType getActivityType(Scanner scanner){
        System.out.println("Aktivity típusa:");
        for (ActivityType actual : ActivityType.values()) {
            System.out.println("\t" + actual.getDescription());
        }
        System.out.print("\t");
        int ordinal = scanner.nextInt();
        scanner.nextLine();
        return ActivityType.byOrdinal(ordinal);
    }
}
