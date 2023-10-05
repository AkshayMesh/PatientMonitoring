package esp32app.testingesp32.data.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import esp32app.testingesp32.data.model.NotificationModel;

@Dao
public interface NotificationDAO {
    @Query("SELECT * FROM NotificationModel ORDER BY timestamp DESC")
    List<NotificationModel> getAll();

    @Query("SELECT * FROM NotificationModel WHERE id == :id LIMIT 1")
    NotificationModel loadAccountById(int id);

    @Insert
    void insertAll(NotificationModel... NotificationModel);

    @Query("DELETE FROM NotificationModel")
    void nukeTable();
}
