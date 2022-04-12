package activitytrackerdemo;

public enum MenuItemType {
    CREATE(1, "Aktivity létrehozása"),
    LIST(2, "Aktivity-k listázása/szűrése"),
    UPDATE(3, "Activity módosítása"),
    DELETE(4, "Aktivity törlése"),
    FINDONE(5, "Aktivity keresése dátum és tipus alapján "),
    EXIT(0, "Kilépés");

    private final int ordinal;
    private final String description;


    MenuItemType(int ordinal, String description) {
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
