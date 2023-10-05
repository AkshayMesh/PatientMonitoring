package esp32app.testingesp32.ui.admin.media;

import static esp32app.testingesp32.ui.admin.media.MediaOptions.OPTION_DELETE;
import static esp32app.testingesp32.ui.admin.media.MediaOptions.OPTION_VIEW;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import esp32app.testingesp32.R;
import esp32app.testingesp32.data.model.MediaDescriptionModel;
import esp32app.testingesp32.databinding.ActivityAdminMediaBinding;
import esp32app.testingesp32.ui.admin.upload.BottomMediaSelection;
import esp32app.testingesp32.ui.media.MediaViewModel;
import esp32app.testingesp32.ui.player.PlayerActivity;

public class AdminMediaActivity extends AppCompatActivity {

    private ActivityAdminMediaBinding binding;
    private AdminMediaAdapter mediaAdapter;
    private String mediaType, category;
    private MediaViewModel viewModel;
    private ArrayList<MediaDescriptionModel> mediaVideoModels;
    private ArrayList<MediaDescriptionModel> mediaAudioModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminMediaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(MediaViewModel.class);

        initViews();
        getIntentData();
        initTabs();
        initObservers();
        clickEvents();
    }

    private void clickEvents() {
        binding.tvMediaType.setOnClickListener(v-> BottomMediaSelection.newInstance(this::onMediaChanged).show(getSupportFragmentManager(), "Media Selection"));
    }

    private void getIntentData() {
        Intent prevIntent = getIntent();
        if (prevIntent != null) {
            mediaType = prevIntent.getStringExtra("type");
        }
    }

    private void onMediaChanged(String s) {
        binding.tvMediaType.setText(s);
        mediaType = s;
        topRecycler();
    }

    private void initViews() {
        category = "All";
        mediaType = "video";
        mediaAudioModels = new ArrayList<>();
        mediaVideoModels = new ArrayList<>();
        binding.topList.setLayoutManager(new GridLayoutManager(this, 2));
        mediaAdapter = new AdminMediaAdapter(this::onMediaClicked);
        binding.topList.setAdapter(mediaAdapter);
        viewModel.observeDbForMedia();
    }

    private void onMediaClicked(MediaDescriptionModel model) {
        MediaOptions.newInstance(type -> handleOptions(type, model)).show(getSupportFragmentManager(), "Media Selection Options");
    }

    private void initTabs() {
        int count = 0;
        TabLayout.Tab allTab = binding.tabs.newTab();
        allTab.setText("All");
        allTab.setId(count);
        binding.tabs.addTab(allTab);
        ArrayList<String> categories = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.categories)));
        for (String cat : categories) {
            count++;
            if (count!=1) {
                TabLayout.Tab tab = binding.tabs.newTab();
                tab.setText(cat);
                tab.setId(count);
                binding.tabs.addTab(tab);
            }
        }

        binding.tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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

    private void initObservers() {
        viewModel.getAudioListLiveData().observe(this, list-> {
            mediaAudioModels.clear();
            mediaAudioModels.addAll(list);
            topRecycler();
        });
        viewModel.getVideoListLiveData().observe(this, list-> {
            mediaVideoModels.clear();
            mediaVideoModels.addAll(list);
            topRecycler();
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

    private void handleOptions(int type, MediaDescriptionModel model) {
        switch (type){
            case OPTION_DELETE:
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
                builder.setTitle("Delete Media");
                builder.setMessage("Are you sure you want to delete selected media?");
                builder.setPositiveButton("Yes", (d, i)-> attemptToDeleteMedia(model)).
                        setNegativeButton("No", (d, i)-> d.dismiss());
                builder.show();
                break;
            default:
            case OPTION_VIEW:
                Intent intent = new Intent(this, PlayerActivity.class);
                intent.putExtra("url", model.url);
                startActivity(intent);
                break;
        }
    }

    private void attemptToDeleteMedia(MediaDescriptionModel model) {
        switch (mediaType){
            case MediaDescriptionModel.AUDIO:
                MediaDescriptionModel.getAudioDb().child(""+model.id).removeValue();
                break;
            case MediaDescriptionModel.VIDEO:
                MediaDescriptionModel.getVideoDb().child(""+model.id).removeValue();
                break;
        }
    }

}