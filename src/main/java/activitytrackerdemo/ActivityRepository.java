package activitytrackerdemo;

import org.mariadb.jdbc.MariaDbDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;

public class ActivityRepository {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public ActivityRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insertActivity(Activity activity) {
        jdbcTemplate.update("insert into activities(start_time,activity_desc,activity_type) values(?,?,?)",
                activity.getStartTime(), activity.getDescription(), activity.getActivityType().toString());

    }
}
