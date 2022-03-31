package activitytrackerdemo;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mariadb.jdbc.MariaDbDataSource;

import javax.sql.DataSource;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ActivityRepositoryTest {

    ActivityRepository repository;

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

    }

    @Test
    void insertActivityThanQTest() {
        // Given
        LocalDateTime time = LocalDateTime.of(2000, 1, 1, 10, 10);
        Activity activity = new Activity(time, "test_desc", ActivityType.BIKING);
        List<Activity> emptyList = repository.selectAllActivity();
        // When
        repository.insertActivity(activity);
        List<Activity> test = repository.selectAllActivity();
        // Then
        assertEquals(0, emptyList.size());
        assertEquals(1, test.size());

    }

    @Test
    void selectAllActivityTest() {
        //Given
        LocalDateTime time = LocalDateTime.of(2000, 1, 1, 10, 10);
        LocalDateTime otherTime = LocalDateTime.of(2000, 1, 2, 10, 10);

        Activity activity = new Activity(time, "test_desc", ActivityType.BIKING);
        Activity otherActivity = new Activity(time, "test_desc_other", ActivityType.RUNNING);
        repository.insertActivity(activity);
        repository.insertActivity(otherActivity);
        //When
        List<Activity> test = repository.selectAllActivity();
        //Then
        assertEquals(2, test.size());
        assertEquals("test_desc_other", test.get(1).getDescription());
    }

    @Test
    void findOneActivityByTypeAndDateTest() {
        //Given
        LocalDateTime time = LocalDateTime.of(2000, 1, 1, 10, 10);
        Activity activity = new Activity(time, "test_desc", ActivityType.BIKING);
        repository.insertActivity(activity);
        //When
        Activity testActivity = repository.findOneActivityByTypeAndDate(time, ActivityType.BIKING.toString());
        //Then
        assertEquals(time, testActivity.getStartTime());
        assertEquals(ActivityType.BIKING, testActivity.getActivityType());
    }

    @Test
    void deleteActivityByIdTest() {
        //Given
        LocalDateTime time = LocalDateTime.of(2000, 1, 1, 10, 10);
        LocalDateTime otherTime = LocalDateTime.of(2000, 1, 2, 10, 10);

        Activity activity = new Activity(time, "test_desc", ActivityType.BIKING);
        Activity otherActivity = new Activity(otherTime, "test_desc_other", ActivityType.RUNNING);
        repository.insertActivity(activity);
        repository.insertActivity(otherActivity);
        List<Activity> test = repository.selectAllActivity();
        //When
        Activity activityToDelete = repository.findOneActivityByTypeAndDate(otherTime,ActivityType.RUNNING.toString());
        repository.deleteActivityById(activityToDelete.getId());
        List<Activity> otherTest = repository.selectAllActivity();
        //Then
        assertEquals(2,test.size());
        assertEquals(1,otherTest.size());

    }
}