package activitytrackerdemo.menuitems;

import activitytrackerdemo.ActivityType;

import java.time.LocalDateTime;
import java.util.Scanner;

public class MenuHelpers {

    public static final String START_DAY_FORMAT = "yyyy-MM-dd";
    public static final String START_TIME_FORMAT = "hh:mm";

    public static LocalDateTime getStartTime(Scanner scanner) {
        System.out.println("Kezdés napja (" + MenuHelpers.START_DAY_FORMAT + ") :");
        String startDate = scanner.nextLine();
        String[] date = startDate.split("-");
        int year = Integer.parseInt(date[0].trim());
        int month = Integer.parseInt(date[1].trim());
        int day = Integer.parseInt(date[2].trim());

        System.out.println("Kezdés időpontja (" + MenuHelpers.START_TIME_FORMAT + ") :");
        String startTime = scanner.nextLine();
        String[] time = startTime.split(":");
        int hours = Integer.parseInt(time[0].trim());
        int minutes = Integer.parseInt(time[1].trim());

        return LocalDateTime.of(year, month, day, hours, minutes);

    }

    public static ActivityType getActivityType(Scanner scanner) {
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
