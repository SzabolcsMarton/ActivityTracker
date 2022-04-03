package activitytrackerdemo;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

public class ActivityRepository {

    private DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    public ActivityRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insertActivity(Activity activity) {
        jdbcTemplate.update("insert into activities(start_time,activity_desc,activity_type) values(?,?,?)",
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

    public int deleteActivityById(long id) {
         return jdbcTemplate.update("delete from activities where id = ?", id);
    }
}
