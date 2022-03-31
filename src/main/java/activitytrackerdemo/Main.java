package activitytrackerdemo;

import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        ActivityController controller = new ActivityController(getActivityService());
        controller.runMenu();
    }

    private static ActivityService getActivityService() {
        MariaDbDataSource dataSource = new MariaDbDataSource();

        try {
            dataSource.setUrl("jdbc:mariadb://localhost:3306/activitytrackertemplate?useUnicode=true");
            dataSource.setUser("root");
            dataSource.setPassword("root");
        } catch (SQLException sqle) {
            throw new IllegalStateException("Cannot reach DataBase!", sqle);
        }

        ActivityRepository activityRepository = new ActivityRepository(dataSource);
        return  new ActivityService(activityRepository);


    }
}
