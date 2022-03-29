package activitytrackerdemo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Activity {

    private long id;
    private LocalDateTime startTime;
    private String description;
    private ActivityType activityType;

    public Activity(LocalDateTime startTime, String description, ActivityType activityType) {
        this.startTime = startTime;
        this.description = description;
        this.activityType = activityType;
    }

    public Activity(long id, LocalDateTime startTime, String description, ActivityType activityType) {
        this.id = id;
        this.startTime = startTime;
        this.description = description;
        this.activityType = activityType;
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public String getDescription() {
        return description;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    private String formatTime(LocalDateTime time){
        DateTimeFormatter FOMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd  hh:mm a");
        return FOMATTER.format(time);

    }

    @Override
    public String toString() {
        return formatTime(startTime) + " " + activityType + " - " + description;
    }
}
