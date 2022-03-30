package activitytrackerdemo;

public enum MenuItemType {
    CREATE(1,"Új aktivity létrehozása"),
    LIST(2,"Összes aktivity kilistázása"),
    DELETE(3,"Aktivity törlése"),
    FINDONE(4,"Aktivity keresése dátum és tipus alapján "),
    EXIT(0,"Kilépés");

    private int ordinal;
    private String description;


    MenuItemType(int ordinal, String description ) {
        this.ordinal = ordinal;
        this.description = description;
    }

    public static MenuItemType byOrdinal(int ord) {
        for (MenuItemType m : MenuItemType.values()) {
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
