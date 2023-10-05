package esp32app.testingesp32.data.room.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import esp32app.testingesp32.data.model.AccountDetails;
import esp32app.testingesp32.data.model.NotificationModel;
import esp32app.testingesp32.data.room.dao.AccountDAO;
import esp32app.testingesp32.data.room.dao.NotificationDAO;

@Database(entities = {AccountDetails.class, NotificationModel.class}, version = 2, exportSchema = false)
public abstract class RoomDatabaseMain extends RoomDatabase {
    public abstract AccountDAO accountsDao();
    public abstract NotificationDAO notificationDAO();
}
