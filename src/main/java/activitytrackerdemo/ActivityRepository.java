package activitytrackerdemo;

import org.mariadb.jdbc.MariaDbDataSource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;

public class ActivityRepository {

    private MariaDbDataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public ActivityRepository() {
        initDB();
    }

    private void initDB() {
        dataSource = new MariaDbDataSource();

        try {
            dataSource.setUrl("jdbc:mariadb://localhost:3306/activitytrackertemplate?useUnicode=true");
            dataSource.setUser("root");
            dataSource.setPassword("root");
        } catch (SQLException sqle) {
            throw new IllegalStateException("Cannot reach DataBase!", sqle);
        }

        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public void insertActivity(Activity activity) {
        jdbcTemplate.update("insert into activities(start_time,activity_desc,activity_type) values(?,?,?)",
                activity.getStartTime(), activity.getDescription(), activity.getActivityType().toString());

    }
}
