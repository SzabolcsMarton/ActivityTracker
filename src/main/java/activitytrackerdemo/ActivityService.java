package activitytrackerdemo;


import java.time.LocalDateTime;

public class ActivityService {
    public static final int MIN_LENGTH = 5;

    ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {


        this.activityRepository = activityRepository;
    }

    public boolean saveActivity(Activity activity) {
        if (isActivityValid(activity))
            return false;

        activityRepository.insertActivity(activity);
        return true;
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
