package activitytrackerdemo;


import java.time.LocalDateTime;

public class ActivityService {
    public static final int MIN_LENGTH = 5;

    ActivityRepository activityRepository;

    public ActivityService() {
        this.activityRepository = new ActivityRepository();
    }

    public boolean saveActivity(Activity activity) {
        if (isActivityValid(activity))
            return false;

        try {
            activityRepository.insertActivity(activity);
            return true;
        }catch (Exception exception){
            return false;
        }

    }

    private boolean isActivityValid(Activity activity) {
        if (activity == null ||
                activity.getDescription().length() < MIN_LENGTH ||
                activity.getStartTime().isAfter(LocalDateTime.now())) {
            return true;
        }
        return false;
    }


}
