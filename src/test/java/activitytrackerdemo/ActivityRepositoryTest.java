//package activitytrackerdemo;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mariadb.jdbc.MariaDbDataSource;
//
//import javax.sql.DataSource;
//
//import java.time.LocalDateTime;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class ActivityRepositoryTest {
//    DataSource dataSource;
//
//    @BeforeEach
//    void BeforeEach()
//    {
//        // reset ds, flyway
//        dataSource = new MariaDbDataSource();
//
//    }
//    @Test
//    void insertActivityTest() {
//        // Given
//        String expectedDesc = "expectedDesc";
//        ActivityType expectedAT = ActivityType.BIKING;
//        Activity testActiity = new Activity(LocalDateTime.MAX, expectedDesc, expectedAT);
//
//        // When
//        ActivityRepository repo = new ActivityRepository(dataSource);
//        repo.insertActivity(testActiity);
//
//        // Then
//        // Get from DB   testActiity.getId()
//        assertEquals(expectedDesc, receivedItem.Desc);
//    }
//
//    @Test
//    void selectAllActivityTest() {
//    }
//
//    @Test
//    void findOneActivityByTypeAndDateTest() {
//    }
//
//    @Test
//    void deleteActivityByIdTest() {
//    }
//}