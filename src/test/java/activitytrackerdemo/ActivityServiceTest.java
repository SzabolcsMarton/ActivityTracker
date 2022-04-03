package activitytrackerdemo;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ActivityServiceTest {


    ActivityRepository repository;
    ActivityService service;

    @BeforeEach
    void setUp() throws SQLException {
        MariaDbDataSource dataSource = new MariaDbDataSource();
        dataSource.setUrl("jdbc:mariadb://localhost:3306/activitytracker?useUnicode=true");
        dataSource.setUser("root");
        dataSource.setPassword("root");

        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.clean();
        flyway.migrate();

        repository = new ActivityRepository(dataSource);
        service = new ActivityService(repository);
    }

    @Test
    void saveActivityShouldReturnTrueTest() {
        LocalDateTime time = LocalDateTime.of(2000,1,1,10,10);
        Activity activity = new Activity(time,"test_desc",ActivityType.BIKING);

        assertTrue(service.saveActivity(activity));
    }

    @Test
    void saveActivityShouldReturnFalseIfDescLengthIs4CharsTest() {
        LocalDateTime time = LocalDateTime.of(2000,1,1,10,10);
        Activity activity = new Activity(time,"test",ActivityType.BIKING);

        assertFalse(service.saveActivity(activity));
    }

    @Test
    void saveActivityShouldReturnFalseWithTimeLaterTheanNowTest(){
        LocalDateTime time = LocalDateTime.of(2023,1,1,10,10);
        Activity activity = new Activity(time,"test",ActivityType.BIKING);

        assertFalse(service.saveActivity(activity));
    }

    @Test
    void saveActivityShouldReturnFalseWithNullTest(){
        Activity activity = null;

        assertFalse(service.saveActivity(activity));
    }
}