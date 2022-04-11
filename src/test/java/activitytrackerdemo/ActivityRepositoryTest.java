package activitytrackerdemo;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mariadb.jdbc.MariaDbDataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;

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

    @AfterAll
    static void teardownAll() throws SQLException {
        MariaDbDataSource dataSource = new MariaDbDataSource();
        dataSource.setUrl("jdbc:mariadb://localhost:3306/activitytracker?useUnicode=true");
        dataSource.setUser("root");
        dataSource.setPassword("root");

        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.clean();
        flyway.migrate();
    }

    @Test
    void selectAllActivityTestShouldReturnActivitiesFromDatabase() {
        // Given
        // When
        List<Activity> test = repository.selectAllActivity();
        // Then
        assertEquals(3, test.size());
        assertEquals(ActivityType.RUNNING, test.get(0).getActivityType());
        assertEquals("egy kis futás", test.get(0).getDescription());
        assertEquals(LocalDateTime.of(2022, 4, 1, 10, 00), test.get(0).getStartTime());

        assertEquals(ActivityType.RUNNING, test.get(1).getActivityType());
        assertEquals("egy masik futas", test.get(1).getDescription());
        assertEquals(LocalDateTime.of(2022, 4, 2, 11, 00), test.get(1).getStartTime());

        assertEquals(ActivityType.BIKING, test.get(2).getActivityType());
        assertEquals("egy kis biciklizés", test.get(2).getDescription());
        assertEquals(LocalDateTime.of(2022, 4, 2, 11, 10), test.get(2).getStartTime());

    }

    @Test
    void insertActivityThanQueryTest() {
        // Given
        LocalDateTime time = LocalDateTime.of(2000, 1, 1, 10, 10);
        Activity activity = new Activity(time, "test_desc", ActivityType.BIKING);
        List<Activity> emptyList = repository.selectAllActivity();
        // When
        repository.insertActivity(activity);
        List<Activity> test = repository.selectAllActivity();
        // Then
        assertEquals(3, emptyList.size());
        assertEquals(4, test.size());

    }

    @Test
    void insertActivitiesTest() {
        //Given
        LocalDateTime time = LocalDateTime.of(2000, 1, 1, 10, 10);
        LocalDateTime otherTime = LocalDateTime.of(2000, 1, 2, 10, 10);

        Activity activity = new Activity(time, "test_desc", ActivityType.BIKING);
        Activity otherActivity = new Activity(otherTime, "test_desc_other", ActivityType.RUNNING);
        //When
        repository.insertActivity(activity);
        repository.insertActivity(otherActivity);

        //Then
        List<Activity> test = repository.selectAllActivity();
        assertEquals(5, test.size());
        assertEquals("test_desc_other", test.get(1).getDescription());
    }

    @Test
    void insertActivityWithExistingDateShouldThrowException(){
        //Given
        LocalDateTime time = LocalDateTime.of(2022,04,01,10,00);
        Activity activity = new Activity(time,"test_test",ActivityType.RUNNING);
        //When
        DuplicateKeyException exception = assertThrows(DuplicateKeyException.class,
                () -> repository.insertActivity(activity));
        //Then
        assertNotNull(exception);


    }

    @Test
    void findOneActivityByTypeAndDateTest() {
        //Given
        LocalDateTime time = LocalDateTime.of(2022, 4, 2, 11, 00);

        //When
        Activity foundActivity = repository.findOneActivityByTypeAndDate(time, ActivityType.RUNNING.toString());

        //Then
        assertEquals(2, foundActivity.getId());
        assertEquals(time, foundActivity.getStartTime());
        assertEquals(ActivityType.RUNNING, foundActivity.getActivityType());
        assertEquals("egy masik futas", foundActivity.getDescription());
    }

    @Test
    void findOneActivityByTypeAndDateWithNonExistingParametersShouldThrowExceptionTest() {
        //Given
        LocalDateTime time = LocalDateTime.of(1999, 4, 2, 11, 00);

        //When
        DataAccessException exception = assertThrows(DataAccessException.class,
                () -> repository.findOneActivityByTypeAndDate(time, ActivityType.RUNNING.toString()));
        //Then
        assertNotNull(exception);
        assertTrue(exception instanceof EmptyResultDataAccessException);
    }

    @Test
    void upDateActivityStartTimeByIdShouldUpdateStartTimeToNewstartTimeAndReturnOneWithExistingIdTest(){
        //Given
        LocalDateTime newTime = LocalDateTime.of(2021,4,1,10,00);
        //When
        int test = repository.upDateActivityStartTimeById(1,newTime);
        //Then
        assertEquals(newTime,repository.selectAllActivity().get(0).getStartTime());
        assertEquals(1,test);
    }

    @Test
    void upDateActivityStartTimeByIdShouldReturnZeroWithNonExistingIdTest(){
        //Given
        LocalDateTime newTime = LocalDateTime.of(2021,4,1,10,00);
        //When
        int test = repository.upDateActivityStartTimeById(4,newTime);
        //Then
        assertEquals(0,test);
    }

    @Test
    void upDateActivityTypeByIdShouldChangeTypeAndReturnOneWithExisingIdTest(){
        //Given
        ActivityType newType = ActivityType.HIKING;
        //When
        int test = repository.upDateActivityTypeById(1,newType.toString());
        //Then
        assertEquals(1,test);
        assertEquals(newType,repository.selectAllActivity().get(0).getActivityType());
    }

    @Test
    void upDateActivityTypeByIdShouldReturnZeroWithNonExistingIdTest(){
        //Given
        ActivityType newType = ActivityType.HIKING;
        //When
        int test = repository.upDateActivityTypeById(5,newType.toString());
        //Then
        assertEquals(0,test);

    }

    @Test
    void upDateActivityDescriptionByIdShouldChangeDescriptionAndReturnOneWithExistingIdTest(){
        //Given
       String testDesc = "testing";
        //When
        int test = repository.upDateActivityDescriptionById(1,testDesc);
        //Then
        assertEquals(testDesc,repository.selectAllActivity().get(0).getDescription());
        assertEquals(1,test);
    }

    @Test
    void upDateActivityDescriptionByIdShoulddReturnZeroWithExistingIdTest(){
        //Given
        String testDesc = "testing";
        //When
        int test = repository.upDateActivityDescriptionById(5,testDesc);
        //Then
        assertEquals(0,test);
    }

    @Test
    void deleteActivityByIdTestListSizeShouldBeTwoTest(){
        //Given
        List<Activity> test = repository.selectAllActivity();
        //When
        repository.deleteActivityById(2);
        List<Activity> testAfterDelete = repository.selectAllActivity();
        //Then
        assertEquals(3,test.size());
        assertEquals(2, testAfterDelete.size());
    }

    @Test
    void deleteActivityByIdShouldReturnOneWithExistingIdTest() {
        //Given
        //When
        int numberOfRowsAffected = repository.deleteActivityById(2);
        //Then
        assertEquals(1,numberOfRowsAffected);
    }

    @Test
    void deleteActivityByIdShouldReturnZeroWithNonExistingIdTest() {
        //Given
        //When
        int numberOfRowsAffected = repository.deleteActivityById(10);
        //Then
        assertEquals(0, numberOfRowsAffected);
    }
}