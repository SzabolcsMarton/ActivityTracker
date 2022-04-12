package activitytrackerdemo.menuitems;

import activitytrackerdemo.Activity;
import activitytrackerdemo.ActivityService;
import activitytrackerdemo.ActivityType;

import java.time.LocalDateTime;
import java.util.Scanner;

public class UpdateMenuItem implements MenuItem{

    private final ActivityService activityService;
    private Activity activityToModify;

    public UpdateMenuItem(ActivityService activityService) {
        this.activityService = activityService;
    }

    @Override
    public void process(Scanner scanner) {
        System.out.println("\nMódosítás menüpont.");
        System.out.println("\nA módosítani kívánt tevékenység megkereséséhez add meg a következő adatokat!");

        ActivityType type = MenuHelpers.getActivityType(scanner);
        LocalDateTime time = MenuHelpers.getStartTime(scanner);

        activityToModify = activityService.findOneActivityByTypeAndDate(time, type);
        if (activityToModify == null) {
            System.out.println("Nem található tevékenység a megadott adatokkal");
            return;
        }
        System.out.println("Ezt a tevékenységet szeretnédmódosítani?");
        System.out.println();
        System.out.println("\t" +activityToModify);
        System.out.println();
        System.out.println("y- Igen, n- Nem");
        String answer = scanner.nextLine();
        if (answer.equalsIgnoreCase("y")) {
            processModify(getDetailOptionToModify(scanner),scanner);
        }
        if (answer.equalsIgnoreCase("n")){
            process(scanner);
        }
    }

    private int getDetailOptionToModify(Scanner scanner){
        printSubmenuOptions();
        String detailOption = scanner.nextLine();
        int detailOptionNumber = 0;
        try {
            detailOptionNumber = Integer.parseInt(detailOption);
        }catch (NumberFormatException exception){
            System.out.println("Kérem az adott menüpontok közül válasszon");
            getDetailOptionToModify(scanner);
        }
        return detailOptionNumber;
    }

    private void processModify(int option,Scanner scanner){
        switch (option) {
            case 1:
                LocalDateTime newTime = MenuHelpers.getStartTime(scanner);
                boolean resultTimeModify = activityService.modifyActivityStartTimeById(activityToModify.getId(),newTime);
                printResult(resultTimeModify);
                break;
            case 2:
                String newDescription = getNewDescription(scanner);
                boolean resultDescModify = activityService.modifyActivityDescriptionById(activityToModify.getId(),newDescription);
                printResult(resultDescModify);
                break;
            case 3:
                ActivityType newType =MenuHelpers.getActivityType(scanner);
                boolean resultTypeModify = activityService.modifyActivityTypeById(activityToModify.getId(),newType);
                printResult(resultTypeModify);
                break;
        }

    }

    private String getNewDescription(Scanner scanner){
        System.out.println("Adja meg az új leírást");
        return scanner.nextLine();
    }

    private void printResult(boolean result){
        if(result){
            System.out.println("Activity sikeresen módosítva");
        }else {
            System.out.println("Hibás adatok kérlek próbáld újra");
        }
    }
    private void printSubmenuOptions(){
        System.out.println("Melyik adatot szeretné módosítani?");
        System.out.println("\t 1 - Időpont");
        System.out.println("\t 2 - Leírás");
        System.out.println("\t 3 - Típus");
    }

}
