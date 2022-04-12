package activitytrackerdemo.menuitems;

import activitytrackerdemo.ActivityType;

import java.time.LocalDateTime;
import java.util.Scanner;

public class MenuHelpers {

    public static final String START_DAY_FORMAT = "yyyy-MM-dd";
    public static final String START_TIME_FORMAT = "hh:mm";
    private static String[] date;
    private static String[] time;

    public static LocalDateTime getStartTime(Scanner scanner) {
        System.out.println("Kezdés napja (" + MenuHelpers.START_DAY_FORMAT + "):");
        String startDate = scanner.nextLine();
        System.out.println("Kezdés időpontja (" + MenuHelpers.START_TIME_FORMAT + "):");
        String startTime = scanner.nextLine();
        date = startDate.split("-");
        time = startTime.split(":");
        ensureGetAllDataForLocalDateTime(date, time, scanner);
        return convertToLocalDateTime(date, time, scanner);

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

    private static void ensureGetAllDataForLocalDateTime(String[] date, String[] time, Scanner scanner) {
        if (date.length != 3 || time.length != 2) {
            System.out.println("Adjon meg minden dátum és időpont adatot \n a megadott formában");
            getStartTime(scanner);
        }
    }

    private static LocalDateTime convertToLocalDateTime(String[] date, String[] time, Scanner scanner) {
        LocalDateTime resultLocalDateTime = null;
        try {
            int year = Integer.parseInt(date[0].trim());
            int month = Integer.parseInt(date[1].trim());
            int day = Integer.parseInt(date[2].trim());
            int hours = Integer.parseInt(time[0].trim());
            int minutes = Integer.parseInt(time[1].trim());
            resultLocalDateTime = LocalDateTime.of(year, month, day, hours, minutes);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException exception) {
            System.out.println("Hibásan adta meg a dátum adatokat");
            getStartTime(scanner);
        }
        return resultLocalDateTime;
    }

}
