package activitytrackerdemo.menuitems;

import java.util.Scanner;

public class ExitMenuItem implements MenuItem {

    @Override
    public void process(Scanner scanner) {
        System.out.println("\n ****** Viszontlátásra! ******");
    }
}
