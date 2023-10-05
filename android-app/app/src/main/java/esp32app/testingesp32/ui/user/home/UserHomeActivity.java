package esp32app.testingesp32.ui.user.home;

import static esp32app.testingesp32.util.BaseUtil.getFirstName;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.room.Room;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import esp32app.testingesp32.R;
import esp32app.testingesp32.data.model.AccountDetails;
import esp32app.testingesp32.data.model.ESP32Model;
import esp32app.testingesp32.data.model.MediaDescriptionModel;
import esp32app.testingesp32.data.model.NotificationModel;
import esp32app.testingesp32.data.preference.SharedMain;
import esp32app.testingesp32.data.room.dao.AccountDAO;
import esp32app.testingesp32.data.room.database.RoomDatabaseMain;
import esp32app.testingesp32.databinding.ActivityUserHomeBinding;
import esp32app.testingesp32.service.FirebaseService;
import esp32app.testingesp32.ui.admin.upload.BottomMediaSelection;
import esp32app.testingesp32.ui.faq.FAQActivity;
import esp32app.testingesp32.ui.login.MainActivity;
import esp32app.testingesp32.ui.media.MediaViewModel;
import esp32app.testingesp32.ui.media.RecommendedAdapter;
import esp32app.testingesp32.ui.notification.NotificationActivity;
import esp32app.testingesp32.ui.quiz.QuizActivity;
import esp32app.testingesp32.ui.search.SearchActivity;
import esp32app.testingesp32.util.NotificationUtil;
import esp32app.testingesp32.util.ObjectUtils;
import esp32app.testingesp32.util.UiUtil;

public class UserHomeActivity extends AppCompatActivity {

    private ActivityUserHomeBinding binding;
    private ESP32Model mainDeviceModel;
    private AccountDetails accountModel;
    private MediaViewModel viewModel;
    private ArrayList<MediaDescriptionModel> mediaVideoModels;
    private ArrayList<MediaDescriptionModel> mediaAudioModels;

    private RecommendedAdapter mediaAdapter;
    private String mediaType, category;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(MediaViewModel.class);

        initViews();
        initTabs();
        initObservers();
        clickEvents();
        loadFromRoomDb();
        addEventListeners();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(new Intent(this, FirebaseService.class));
        }else {
            startService(new Intent(this, FirebaseService.class));
        }
//        startService(new Intent(this, FirebaseService.class));
    }

    private void initObservers() {
        viewModel.getAudioListLiveData().observe(this, list -> {
            mediaAudioModels.clear();
            mediaAudioModels.addAll(list);
            topRecycler();
        });
        viewModel.getVideoListLiveData().observe(this, list -> {
            mediaVideoModels.clear();
            mediaVideoModels.addAll(list);
            topRecycler();
        });
    }

    private void clickEvents() {
        binding.ivHome.setOnClickListener(v -> setUiForHome());
        binding.ivMedia.setOnClickListener(v -> setUiForMedia());
        binding.ivNotification.setOnClickListener(v ->
                startActivity(new Intent(UserHomeActivity.this, NotificationActivity.class)));
        binding.ivSearch.setOnClickListener(v ->
                startActivity(new Intent(UserHomeActivity.this, SearchActivity.class)));
        binding.cardQuiz.setOnClickListener(v ->
                startActivity(new Intent(UserHomeActivity.this, QuizActivity.class)));
        binding.cardFaq.setOnClickListener(v ->
                startActivity(new Intent(UserHomeActivity.this, FAQActivity.class)));
        binding.layoutMedia.tvMediaType.setOnClickListener(v -> BottomMediaSelection.newInstance(this::onMediaChanged).show(getSupportFragmentManager(), "Media Selection"));
        binding.tvLogout.setOnClickListener(v -> askForLogout());
    }

    private void askForLogout() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Logout");
        builder.setIcon(R.drawable.ic_logout);
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Yes", (d, i) -> {
            SharedMain.newInstance(this).setLoggedIn(false).setId(-1);
            startActivity(new Intent(UserHomeActivity.this, MainActivity.class));
            finish();
        }).setNegativeButton("No", (d, i) -> d.dismiss());
        builder.show();
    }

    private void onMediaChanged(String s) {
        binding.layoutMedia.tvMediaType.setText(s);
        mediaType = s;
        topRecycler();
    }

    private void setUiForMedia() {
        UiUtil.removeViews(binding.layoutHome);
        UiUtil.showViews(binding.layoutMedia.parent);
        ImageViewCompat.setImageTintList(binding.ivHome, ColorStateList.valueOf(ContextCompat.getColor(this, R.color.icon_grey)));
        ImageViewCompat.setImageTintList(binding.ivMedia, ColorStateList.valueOf(ContextCompat.getColor(this, R.color.green)));
    }

    private void setUiForHome() {
        UiUtil.showViews(binding.layoutHome);
        UiUtil.removeViews(binding.layoutMedia.parent);
        ImageViewCompat.setImageTintList(binding.ivMedia, ColorStateList.valueOf(ContextCompat.getColor(this, R.color.icon_grey)));
        ImageViewCompat.setImageTintList(binding.ivHome, ColorStateList.valueOf(ContextCompat.getColor(this, R.color.green)));
    }

    private void initTabs() {
        int count = 0;
        TabLayout.Tab allTab = binding.layoutMedia.tabs.newTab();
        allTab.setText("All");
        allTab.setId(count);
        binding.layoutMedia.tabs.addTab(allTab);
        ArrayList<String> categories = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.categories)));
        for (String cat : categories) {
            count++;
            if (count != 1) {
                TabLayout.Tab tab = binding.layoutMedia.tabs.newTab();
                tab.setText(cat);
                tab.setId(count);
                binding.layoutMedia.tabs.addTab(tab);
            }
        }

        binding.layoutMedia.tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                category = Objects.requireNonNull(tab.getText()).toString();
                topRecycler();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void topRecycler() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            switch (mediaType) {
                case MediaDescriptionModel.AUDIO:
                    mediaAdapter.submitList(mediaAudioModels.stream().filter(media -> {
                        if (category.equals("All"))
                            return true;
                        return media.category.equals(category);
                    }).distinct().collect(Collectors.toList()));
                    break;
                default:
                case MediaDescriptionModel.VIDEO:
                    mediaAdapter.submitList(mediaVideoModels.stream().filter(media -> {
                        if (category.equals("All"))
                            return true;
                        return media.category.equals(category);
                    }).distinct().collect(Collectors.toList()));
                    break;
            }
        }
    }

    private void loadFromRoomDb() {
        int id = SharedMain.newInstance(this).getId();
        RoomDatabaseMain db = Room.databaseBuilder(getApplicationContext(),
                RoomDatabaseMain.class, "Account-Video").build();
        AccountDAO dao = db.accountsDao();

        AsyncTask.execute(() -> {
            accountModel = dao.loadAccountById(id);
            runOnUiThread(() -> binding.tvGreetings.setText(String.format("Hi, %s", getFirstName(accountModel.name))));
        });
    }

    private void addEventListeners() {
        ESP32Model.getEsp32Db().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    if (snapshot.getValue() != null) {
                        String rawValue = snapshot.getValue().toString();
                        String jsonCompatibleData = ObjectUtils.convertToValidJSON(rawValue);

                        mainDeviceModel = (ESP32Model) ObjectUtils.stringToObject(jsonCompatibleData, ESP32Model.class);
                        if (mainDeviceModel != null) {
                            mainDeviceModel.Spo2 = mainDeviceModel.Spo2.substring(0, mainDeviceModel.Spo2.length() - 1);
                            loadDataInsideViews();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserHomeActivity.this, "Unable to connect to server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViews() {
        category = "All";
        mediaType = "video";
        mediaAudioModels = new ArrayList<>();
        mediaVideoModels = new ArrayList<>();
        binding.layoutMedia.topList.setLayoutManager(new GridLayoutManager(this, 2));
        mediaAdapter = new RecommendedAdapter();
        binding.layoutMedia.topList.setAdapter(mediaAdapter);
        viewModel.observeDbForMedia();
    }

    private void loadDataInsideViews() {
        binding.setViewModel(mainDeviceModel);
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer.start();
        }
        countDownTimer = new CountDownTimer(5000, 1000) {

            public void onTick(long l) {
            }

            public void onFinish() {//here mnext is the button from which we can get next question.
                //checkForAlerts();
            }
        }.start();
    }

    private void checkForAlerts() {
//        notification.type = null;
        if (mainDeviceModel.position.equalsIgnoreCase("falling")) {
            NotificationModel notification = new NotificationModel();
            notification.deviceModel = ObjectUtils.objectToString(mainDeviceModel);
            notification.title = "Emergency Alert";
            notification.message = "Patient's is falling";
            notification.type = "Position";
            NotificationUtil.setNotificationMessage(this, false, notification, true, null);
            NotificationUtil.addNotificationToDatabase(this, notification);
        }
        if (mainDeviceModel.Avg >= 150) {
            NotificationModel notification = new NotificationModel();
            notification.deviceModel = ObjectUtils.objectToString(mainDeviceModel);
            notification.title = "Pulse rate Alert";
            notification.message = "Patient's pulse rate is very high";
            notification.type = "Pulse";
            NotificationUtil.setNotificationMessage(this, false, notification, true, null);
            NotificationUtil.addNotificationToDatabase(this, notification);
        }
        if (mainDeviceModel.temperature >= 100) {
            NotificationModel notification = new NotificationModel();
            notification.deviceModel = ObjectUtils.objectToString(mainDeviceModel);
            notification.title = "Fever Alert";
            notification.message = "Patient's body temperature is very high";
            notification.type = "Temperature";
            NotificationUtil.setNotificationMessage(this, false, notification, true, null);
            NotificationUtil.addNotificationToDatabase(this, notification);
        }
        try {
            double spo = Double.parseDouble(mainDeviceModel.Spo2);
            if (spo < 90) {
                NotificationModel notification = new NotificationModel();
                notification.deviceModel = ObjectUtils.objectToString(mainDeviceModel);
                notification.title = "Oxygen Alert";
                notification.message = "Patient's oxygen level is very low";
                notification.type = "Spo2";
                NotificationUtil.setNotificationMessage(this, false, notification, true, null);
                NotificationUtil.addNotificationToDatabase(this, notification);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}