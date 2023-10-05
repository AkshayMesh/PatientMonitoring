package esp32app.testingesp32.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class AccountDetails {

    public AccountDetails() {}

    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "user_name")
    public String firstName;

    @ColumnInfo(name = "password")
    public String pass;

}
