package activitytrackerdemo;

import java.time.LocalDateTime;

public class ActivityService {
    ActivityRepository activityRepository;

    public ActivityService() {
        this.activityRepository = new ActivityRepository();
    }

    public boolean saveActivity(Activity activity) {
        if (activity == null ||
                activity.getStartTime().isBefore(LocalDateTime.now().minusYears(1)) ||
                activity.getDescription().length() < 5
        ) {
            return false;
        }
        activityRepository.insertActivity(activity);
        return true;
    }


}
