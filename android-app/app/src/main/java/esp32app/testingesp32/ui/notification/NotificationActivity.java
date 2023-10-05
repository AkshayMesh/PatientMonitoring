package esp32app.testingesp32.ui.notification;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import java.util.ArrayList;

import esp32app.testingesp32.data.model.NotificationModel;
import esp32app.testingesp32.data.room.database.RoomDatabaseMain;
import esp32app.testingesp32.databinding.ActivityNotificationBinding;
import esp32app.testingesp32.util.UiUtil;

public class NotificationActivity extends AppCompatActivity {

    private ActivityNotificationBinding binding;
    private ArrayList<NotificationModel> notifications;
    private NotificationAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
        onClickEvents();
    }

    private void onClickEvents() {
        binding.backImg.setOnClickListener(v-> onBackPressed());
    }

    private void initViews() {
        adapter = new NotificationAdapter();
        binding.rvNotification.setLayoutManager(new LinearLayoutManager(this));
        binding.rvNotification.setAdapter(adapter);
        RoomDatabaseMain db = Room.databaseBuilder(getApplicationContext(),
                RoomDatabaseMain.class, "Account-Video").build();
        AsyncTask.execute(()->{
            notifications = new ArrayList<>(db.notificationDAO().getAll());
            runOnUiThread(this::onListUpdated);
        });
    }

    private void onListUpdated() {
        if (notifications.size()>0){
            UiUtil.hideViews(binding.ltNoNotification);
            UiUtil.showViews(binding.rvNotification);
            adapter.submitList(notifications);
        } else {
            UiUtil.showViews(binding.ltNoNotification);
            UiUtil.hideViews(binding.rvNotification);
        }
    }
}