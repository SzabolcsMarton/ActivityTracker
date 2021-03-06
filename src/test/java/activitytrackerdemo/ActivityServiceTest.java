package activitytrackerdemo;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterAll;
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
    void saveActivityShouldReturnTrueTest() {
        //Given
        LocalDateTime time = LocalDateTime.of(2000, 1, 1, 10, 10);
        Activity activity = new Activity(time, "test_desc", ActivityType.BIKING);
        //When
        boolean testesult = service.saveActivity(activity);
        //Then
        assertTrue(testesult);
    }

    @Test
    void saveActivityShouldReturnFalseIfDescLengthIs4CharsTest() {
        //Given
        LocalDateTime time = LocalDateTime.of(2000, 1, 1, 10, 10);
        Activity activity = new Activity(time, "test", ActivityType.BIKING);
        //When
        boolean testResult = service.saveActivity(activity);
        //Then
        assertFalse(testResult);
    }

    @Test
    void saveActivityShouldReturnFalseWithTimeLaterTheanNowTest() {
        //Given
        LocalDateTime time = LocalDateTime.of(2023, 1, 1, 10, 10);
        Activity activity = new Activity(time, "test", ActivityType.BIKING);
        //When
        boolean testResult = service.saveActivity(activity);
        //Then
        assertFalse(testResult);
    }

    @Test
    void saveActivityShouldReturnFalseWithNullActivityTest() {
        //Given
        Activity activity = null;
        //When
        boolean testResult = service.saveActivity(activity);
        //Then
        assertFalse(testResult);
    }

    @Test
    void findOneActivityByTypeAndDateShouldReturnActivityTest() {
        //Given
        LocalDateTime time = LocalDateTime.of(2022, 04, 01, 10, 00);
        //When
        Activity activity = service.findOneActivityByTypeAndDate(time, ActivityType.RUNNING);
        //Then
        assertEquals(1, activity.getId());
        assertEquals("egy kis fut??s", activity.getDescription());

    }

    @Test
    void findOneActivityByTypeAndDateShouldReturnNullWithNonExistingDateTest() {
        //Given
        LocalDateTime time = LocalDateTime.of(1999, 04, 01, 10, 00);
        //When
        Activity activity = service.findOneActivityByTypeAndDate(time, ActivityType.RUNNING);
        //Then
        assertNull(activity);
    }

    @Test
    void findOneActivityByTypeAndDateShouldReturnNullWithWrongTypeTest() {
        //Given
        LocalDateTime time = LocalDateTime.of(2022, 04, 01, 10, 00);
        //When
        Activity activity = service.findOneActivityByTypeAndDate(time, ActivityType.BIKING);
        //Then
        assertNull(activity);
    }

    @Test
    void getAllActivitiesReturnedListSizeShouldBeThree() {
        //Given
        List<Activity> testResult;
        //When
        testResult = service.getAllActivities();
        //Then
        assertEquals(3, testResult.size());
    }

    @Test
    void modifyActivityStartTimeByIdShouldReturnTrueTest(){
        //Given
        LocalDateTime testTime = LocalDateTime.of(1980,10,11,10,10);
        long testId = 1;
        boolean testResult;
        //Given
        testResult = service.modifyActivityStartTimeById(testId,testTime);
        //Then
        assertTrue(testResult);
    }

    @Test
    void modifyActivityStartTimeByIdShouldReturnFalseWithTimeInFutureTest(){
        //Given
        LocalDateTime testTime = LocalDateTime.of(2023,10,11,10,10);
        long testId = 1;
        boolean testResult;
        //Given
        testResult = service.modifyActivityStartTimeById(testId,testTime);
        //Then
        assertFalse(testResult);
    }

    @Test
    void modifyActivityStartTimeByIdShouldReturnFalseWithNonExistingIdTest(){
        //Given
        LocalDateTime testTime = LocalDateTime.of(1980,10,11,10,10);
        long testId = 5;
        boolean testResult;
        //Given
        testResult = service.modifyActivityStartTimeById(testId,testTime);
        //Then
        assertFalse(testResult);
    }

    @Test
    void modifyActivityTypeByIdShouldReturnTrueTest(){
        //Given
        ActivityType newType = ActivityType.HIKING;
        long testId = 1;
        boolean testResult;
        //When
        testResult = service.modifyActivityTypeById(testId,newType);
        //Then
        assertTrue(testResult);
    }

    @Test
    void modifyActivityTypeByIdShouldReturnFalseWithNonExistingIdTest(){
        //Given
        ActivityType newType = ActivityType.HIKING;
        long testId = 5;
        boolean testResult;
        //When
        testResult = service.modifyActivityTypeById(testId,newType);
        //Then
        assertFalse(testResult);
    }

    @Test
    void modifyActivityDescriptionByIdShouldReturnTrueTest(){
        //Given
        String newDescriptionValid = "brand new description get updated";
        long testId = 1;
        boolean testResult;
        //When
        testResult = service.modifyActivityDescriptionById(testId,newDescriptionValid);
        //Then
        assertTrue(testResult);
    }

    @Test
    void modifyActivityDescriptionByIdShouldReturnFalseWithDescriptionLenghtNotLongerThanFiveTest(){
        //Given
        String newDescriptionValid = "1234";
        long testId = 1;
        boolean testResult;
        //When
        testResult = service.modifyActivityDescriptionById(testId,newDescriptionValid);
        //Then
        assertFalse(testResult);
    }

    @Test
    void modifyActivityDescriptionByIdShouldReturnFalseWithDNonExistingIdTest(){
        //Given
        String newDescriptionValid = "brand new description get updated";
        long testId = 5;
        boolean testResult;
        //When
        testResult = service.modifyActivityDescriptionById(testId,newDescriptionValid);
        //Then
        assertFalse(testResult);
    }

    @Test
    void deleteActivityByIdShouldReturnTrueWithExistingIdTest(){
        //Given
        long testId = 2;
        boolean testResult;
        //When
        testResult = service.deleteActivityById(testId);
        //Then
        assertTrue(testResult);
    }

    @Test
    void deleteActivityByIdShouldReturnFalseWithNonExistingIdTest(){
        //Given
        long testId = 5;
        boolean testResult;
        //When
        testResult = service.deleteActivityById(testId);
        //Then
        assertFalse(testResult);
    }

}