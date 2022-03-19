package activitytrackerdemo.submenus;

public class ExitMenuItem implements MenuItem {
    @Override
    public void process() {
        System.out.println("\nViszontlátásra!");
    }
}
