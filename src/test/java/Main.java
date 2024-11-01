import com.mladin.database.DatabaseBridge;
import com.mladin.database.DatabaseManager;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class Main {
    public static Logger logger = LoggerFactory.getLogger("Main");

    public static void main(String[] args) {
        DatabaseBridge databaseBridge = new DatabaseBridge("<your database>", 3306, "<your username>", "<your password>", logger);

        DatabaseManager databaseManager = new DatabaseManager(databaseBridge);
        databaseManager.addScheme("forum");

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        HashMap<Object, LinkedList<Object>> result = databaseBridge.GET_ROWS_BY_CONDITION.runSync(databaseManager.getScheme("forum").getTable("users"), 2, "id = ? AND 1 = ?", "9a16831f-8d6f-438d-9fc1-140bde5deadc", 1).getResult();

        stopWatch.stop();

        logger.info("Took " + stopWatch.getTime(TimeUnit.MILLISECONDS) + "ms.");

        result.keySet().forEach(key -> {
            result.get(key).forEach(value -> {
                logger.info(value.toString());
            });

            logger.info("---------------------------------------");
        });
    }
}
