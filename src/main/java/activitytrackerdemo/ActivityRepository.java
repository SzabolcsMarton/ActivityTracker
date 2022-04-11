package activitytrackerdemo;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

public class ActivityRepository {

    private final JdbcTemplate jdbcTemplate;

    public ActivityRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int insertActivity(Activity activity) {
       return jdbcTemplate.update("insert into activities(start_time,activity_desc,activity_type) values(?,?,?)",
                activity.getStartTime(), activity.getDescription(), activity.getActivityType().toString());
    }

    public List<Activity> selectAllActivity() {
        return jdbcTemplate.query("select * from activities order by start_time asc",
                (rs, rowNum) ->
                        new Activity(rs.getTimestamp("start_time").toLocalDateTime(),
                                rs.getString("activity_desc"),
                                ActivityType.valueOf(rs.getString("activity_type"))));
    }

    public Activity findOneActivityByTypeAndDate(LocalDateTime time, String type) {
        return jdbcTemplate.queryForObject("select * from activities where activity_type = ? and start_time = ?",
                (rs, i) -> new Activity(rs.getLong("id"), rs.getTimestamp("start_time").toLocalDateTime(),
                        rs.getString("activity_desc"),
                        ActivityType.valueOf(rs.getString("activity_type"))),
                type, time);
    }

    public int upDateActivityStartTimeById(long id, LocalDateTime time){
        return jdbcTemplate.update("update activities set start_time = ? where id = ?",time,id);
    }

    public int upDateActivityTypeById(long id, String activityTypeString){
        return jdbcTemplate.update("update activities set activity_type = ? where id = ?",activityTypeString,id);
    }

    public int upDateActivityDescriptionById(long id, String description){
        return jdbcTemplate.update("update activities set activity_desc = ? where id = ?",description,id);
    }



    public int deleteActivityById(long id) {
         return jdbcTemplate.update("delete from activities where id = ?", id);
    }


}
