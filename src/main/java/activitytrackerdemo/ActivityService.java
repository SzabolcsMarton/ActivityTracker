package activitytrackerdemo;

import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDateTime;
import java.util.List;

public class ActivityService {
    public static final int MIN_LENGTH = 5;
    public static final int NUMBER_OF_ROWS_IF_SUCCESS = 1;

    ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public boolean saveActivity(Activity activity) {
        if (isActivityInvalid(activity)) {
            return false;
        }
        int rowsAffected = activityRepository.insertActivity(activity);
        return rowsAffected == NUMBER_OF_ROWS_IF_SUCCESS;

    }

    public List<Activity> getAllActivities() {
        return activityRepository.selectAllActivity();
    }

    public Activity findOneActivityByTypeAndDate(LocalDateTime time, ActivityType type) {
        String activityString = type.toString();
        try {
            return activityRepository.findOneActivityByTypeAndDate(time, activityString);
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    public boolean modifyActivityStartTimeById(long id, LocalDateTime newTime) {
        if (isActivityStartTimeInvalid(newTime)) {
            return false;
        }
        int rowsAffected = activityRepository.upDateActivityStartTimeById(id, newTime);
        return rowsAffected == NUMBER_OF_ROWS_IF_SUCCESS;
    }

    public boolean modifyActivityTypeById(long id, ActivityType type) {
        String activityTypeString = type.toString();
        int rowsAffected = activityRepository.upDateActivityTypeById(id, activityTypeString);
        return rowsAffected == NUMBER_OF_ROWS_IF_SUCCESS;
    }

    public boolean modifyActivityDescriptionById(long id, String description) {
        if (isActivityDescriptionInValid(description)) {
            return false;
        }
        int rowsAffected = activityRepository.upDateActivityDescriptionById(id, description);
        return rowsAffected == NUMBER_OF_ROWS_IF_SUCCESS;
    }

    public boolean deleteActivityById(long id) {
        int rowsAffected = activityRepository.deleteActivityById(id);
        return rowsAffected == NUMBER_OF_ROWS_IF_SUCCESS;
    }

    private boolean isActivityInvalid(Activity activity) {
        return activity == null ||
                activity.getDescription().length() < MIN_LENGTH ||
                activity.getStartTime().isAfter(LocalDateTime.now());
    }

    private boolean isActivityDescriptionInValid(String description) {
        return description.length() < MIN_LENGTH;
    }

    private boolean isActivityStartTimeInvalid(LocalDateTime startTime) {
        return startTime.isAfter(LocalDateTime.now());
    }
}
