package activitytrackerdemo;


import java.time.LocalDateTime;
import java.util.List;

public class ActivityService {
    public static final int MIN_LENGTH = 5;

    ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
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

    public List<Activity> getAllActivities() {
        return activityRepository.selectAllActivity();
    }

    public Activity findOneActivityByTypeAndDate(LocalDateTime time, ActivityType type){
        String activityString = type.toString();
        try {
            return activityRepository.findOneActivityByTypeAndDate(time,activityString);
        }catch (Exception exception){

            return null;
        }
    }

    public boolean deleteActivityById(long id){
        try {
            activityRepository.deleteActivityById(id);
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
