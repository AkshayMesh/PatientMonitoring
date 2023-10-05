package esp32app.testingesp32.app;

import android.app.Application;
import android.os.AsyncTask;

import androidx.room.Room;

import esp32app.testingesp32.data.model.AccountDetails;
import esp32app.testingesp32.data.room.database.RoomDatabaseMain;

public class MainApplicationClass extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RoomDatabaseMain db = Room.databaseBuilder(getApplicationContext(),
                RoomDatabaseMain.class, "Account-Video").build();
        AsyncTask.execute(()->db.accountsDao().nukeTable());
        AccountDetails accountAdmin = new AccountDetails();
        accountAdmin.name = "Admin";
        accountAdmin.uid = -55;
        accountAdmin.firstName = "admin_patient@gmail.com";
        accountAdmin.email = "admin_patient@gmail.com";
        accountAdmin.pass = "admin@12345";

        AccountDetails accountFirstUser = new AccountDetails();
        accountFirstUser.name = "Dinesh Tundalwar";
        accountFirstUser.uid = 1;
        accountFirstUser.firstName = "dinesh.tundalwar77@gmail.com";
        accountFirstUser.email = "dinesh.tundalwar77@gmail.com";
        accountFirstUser.pass = "Dinesh@123";

        AsyncTask.execute(()->db.accountsDao().insertAll(accountAdmin, accountFirstUser));

//        startService(new Intent(getApplicationContext(), FirebaseService.class));
    }
}
