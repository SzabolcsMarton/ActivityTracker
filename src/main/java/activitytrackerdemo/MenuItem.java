package activitytrackerdemo;

public enum MenuItem {
    CREATE(1,"Új aktivity létrehozása"),

    EXIT(2,"Kilépés");

    private int ordinal;
    private String description;


    MenuItem(int ordinal, String description ) {
        this.ordinal = ordinal;
        this.description = description;
    }

    public static MenuItem byOrdinal(int ord) {
        for (MenuItem m : MenuItem.values()) {
            if (m.ordinal == ord) {
                return m;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return ordinal + ". " + description;
    }
}
