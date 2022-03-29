package activitytrackerdemo.menuitems;

import java.time.LocalDateTime;
import java.util.Scanner;

public class MenuHelpers {

    public static final String START_DAY_FORMAT = "yyyy,MM,dd,hh,mm";

    public static LocalDateTime getStartTime(Scanner scanner) {

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
