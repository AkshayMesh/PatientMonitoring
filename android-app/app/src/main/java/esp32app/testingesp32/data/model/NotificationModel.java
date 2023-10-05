package esp32app.testingesp32.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class NotificationModel {
    public String title;
    public String message;

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String type;
    public long timestamp;
    public String deviceModel;
    public NotificationModel() {
    }
}
