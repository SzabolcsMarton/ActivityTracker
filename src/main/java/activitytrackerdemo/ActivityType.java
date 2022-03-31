package activitytrackerdemo;

public enum ActivityType {
    BIKING(1, "Kerékpározás"),
    HIKING(2, "Túrázás"),
    RUNNING(3, "Futás"),
    BASKETBALL(4, "Kosárlabdázás");

    private int ordinal;
    private String description;


    ActivityType(int ordinal, String description) {
        this.ordinal = ordinal;
        this.description = description;
    }

    public static ActivityType byOrdinal(int ord) {
        for (ActivityType actual : ActivityType.values()) {
            if (actual.ordinal == ord) {
                return actual;
            }
        }

        return null;
    }

    public String getDescription() {
        return ordinal + ". " + description;
    }
}
