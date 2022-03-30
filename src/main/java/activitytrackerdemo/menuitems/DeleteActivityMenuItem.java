package activitytrackerdemo.menuitems;

import activitytrackerdemo.Activity;
import activitytrackerdemo.ActivityService;
import activitytrackerdemo.ActivityType;

import java.time.LocalDateTime;
import java.util.Scanner;

public class DeleteActivityMenuItem implements MenuItem {

    ActivityService activityService;

    public DeleteActivityMenuItem() {
        this.activityService = new ActivityService();
    }

    @Override
    public void process(Scanner scanner) {
        System.out.println("\nTörlés menü.");
        System.out.println("\nA tevékenység megkereséséhez add meg a következő adatokat!");

        ActivityType type = MenuHelpers.getActivityType(scanner);
        LocalDateTime time = MenuHelpers.getStartTime(scanner);

        Activity activity = activityService.findOneActivityByTypeAndDate(time, type);
        if (activity == null) {
            System.out.println("Nem található tevékenység a megadott adatokkal");
            return;
        }
        System.out.println("Ezt a tevékenységet szeretnéd törölni? :");
        System.out.println();
        System.out.println("\t" + activity.toString());
        System.out.println();
        System.out.println("y- Igen, n- Nem");
        String answer = scanner.nextLine();
        processAnswer(activity, answer);
    }

    private void processAnswer(Activity activity, String answer) {
        if (answer.equalsIgnoreCase("y")) {
            boolean success = activityService.deleteActivityById(activity.getId());
            printResult(success);
        }
    }

    private void printResult(boolean success) {
        if (!success) {
            System.out.println("Hibás adatok,kérlek probáld újra!");
        } else {
            System.out.println("\n\t\t--------Activity sikeresen töröve!--------");
        }
    }
}
