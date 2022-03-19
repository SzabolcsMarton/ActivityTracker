package activitytrackerdemo;

import java.time.LocalDateTime;

public class ActivityService {
    ActivityRepository activityRepository;

    public ActivityService() {
        this.activityRepository = new ActivityRepository();
    }

    public boolean saveActivity(Activity activity){
        if(activity.getStartTime().isBefore( LocalDateTime.now().minusYears(1))){
            return false;
        }
        activityRepository.insertActivity(activity);
        return true;
    }



}
