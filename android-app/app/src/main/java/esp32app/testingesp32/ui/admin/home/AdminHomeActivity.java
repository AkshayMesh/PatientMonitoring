package esp32app.testingesp32.ui.admin.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import esp32app.testingesp32.R;
import esp32app.testingesp32.data.model.MediaDescriptionModel;
import esp32app.testingesp32.data.preference.SharedMain;
import esp32app.testingesp32.databinding.ActivityAdminHomeBinding;
import esp32app.testingesp32.ui.admin.media.AdminMediaActivity;
import esp32app.testingesp32.ui.admin.upload.BottomMediaSelection;
import esp32app.testingesp32.ui.admin.upload.FileUploadActivity;
import esp32app.testingesp32.ui.login.MainActivity;
import esp32app.testingesp32.ui.media.MediaViewModel;

public class AdminHomeActivity extends AppCompatActivity {

    private ActivityAdminHomeBinding binding;
    private MediaViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(MediaViewModel.class);

        initView();
        initObservers();
    }

    private void initObservers() {
        viewModel.observeDbForMedia();
        viewModel.getAudioListLiveData().observe(this, list->
            binding.tvAudio.setText(String.format("%s %s", list.size(), "Audios")));
        viewModel.getVideoListLiveData().observe(this, list->
                binding.tvVideo.setText(String.format("%s %s", list.size(), "Videos")));
    }

    private void initView() {
        binding.mediaAudioCard.setOnClickListener(v-> {
            Intent intent = new Intent(AdminHomeActivity.this, AdminMediaActivity.class);
            intent.putExtra("type", MediaDescriptionModel.AUDIO);
            startActivity(intent);
        });
        binding.mediaVideoCard.setOnClickListener(v-> {
            Intent intent = new Intent(AdminHomeActivity.this, AdminMediaActivity.class);
            intent.putExtra("type", MediaDescriptionModel.VIDEO);
            startActivity(intent);
        });
        binding.btnAddMedia.setOnClickListener(v-> BottomMediaSelection.newInstance(type -> {
            Intent intent = new Intent(AdminHomeActivity.this, FileUploadActivity.class);
            intent.putExtra("type", type);
            startActivity(intent);
        }).show(getSupportFragmentManager(), "Media Selection"));
        binding.tvLogout.setOnClickListener(v-> askForLogout());
    }

    private void askForLogout() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Logout");
        builder.setIcon(R.drawable.ic_logout);
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Yes", (d, i)->{
            SharedMain.newInstance(this).setLoggedIn(false).setId(-1);
            startActivity(new Intent(AdminHomeActivity.this, MainActivity.class));
            finish();
        }).setNegativeButton("No", (d, i)-> d.dismiss());
        builder.show();
    }

}