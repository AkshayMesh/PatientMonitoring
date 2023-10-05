package esp32app.testingesp32.ui.admin.upload;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;

import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import esp32app.testingesp32.R;
import esp32app.testingesp32.data.model.MediaDescriptionModel;
import esp32app.testingesp32.databinding.ActivityFileUploadBinding;
import esp32app.testingesp32.util.BaseUtil;
import esp32app.testingesp32.util.FileUtil;

public class FileUploadActivity extends AppCompatActivity {

    private ActivityFileUploadBinding binding;
    private ActivityResultLauncher<Intent> videoLauncher;
    private ActivityResultLauncher<Intent> audioLauncher;
    private String mediaType;
    private Uri selectedMediaUri;
    private ExoPlayer player;
    private ArrayList<String> category;
    private String title, description, selectedCategory;
    private BottomLoading loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFileUploadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        registerObserver();
        getIntentData();
        initView();
        clickEvent();
    }

    private void registerObserver() {
        videoLauncher = new BaseUtil.LaunchActivityForResult() {
            @Override
            public void onResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    selectedMediaUri = result.getData() != null ? result.getData().getData() : null;
                    loadVideoInPLayer();
                }
            }
        }.setLauncher(this);
        audioLauncher = new BaseUtil.LaunchActivityForResult() {
            @Override
            public void onResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    selectedMediaUri = result.getData() != null ? result.getData().getData() : null;
                    loadVideoInPLayer();
                }
            }
        }.setLauncher(this);
    }

    private void loadVideoInPLayer() {
        initializePlayer();
    }

    private void initializePlayer() {
        if (player == null) {
            player = new ExoPlayer.Builder(this).build();
        }
        binding.exoPlayerView.setVisibility(View.VISIBLE);
        binding.exoPlayerView.setPlayer(player);
        MediaItem mediaItem = MediaItem.fromUri(selectedMediaUri);
// Set the media item to be played.
        player.setMediaItem(mediaItem);
// Prepare the player.
        player.prepare();
// Start the playback.
        player.play();
    }


    private void getIntentData() {
        Intent prevIntent = getIntent();
        if (prevIntent != null) {
            mediaType = prevIntent.getStringExtra("type");
        }
    }

    private void initView() {
        if (mediaType != null && !mediaType.isEmpty()) {
            String subHeading = "Upload " + mediaType + " files";
            binding.tvUploadSubheading.setText(subHeading);
        }
        loading = BottomLoading.newInstance();
        category = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.categories)));
    }

    private void clickEvent() {
        binding.btnAddMedia.setOnClickListener(v -> createChooserForMedia());
        binding.backImg.setOnClickListener(v -> onBackPressed());
        binding.catSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedCategory = category.get(position);
                // your code here
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        binding.btnSave.setOnClickListener(v -> {
            if (checkAllRequiredFields()) {
                loading.show(getSupportFragmentManager(), "Loading");
                uploadFile();
            }
        });
    }

    private void uploadFile() {
        switch (mediaType) {
            case MediaDescriptionModel.AUDIO:
                AsyncTask.execute(() -> MediaDescriptionModel.getAudioStorageDir().child(System.currentTimeMillis() + "." + FileUtil.getFileExtension(selectedMediaUri, FileUploadActivity.this)).putFile(selectedMediaUri).addOnSuccessListener(taskSnapshot -> {
                    if (taskSnapshot.getMetadata() != null && taskSnapshot.getMetadata().getReference() != null) {
                        afterUploading(taskSnapshot.getMetadata().getReference());
                    }
                }).addOnFailureListener(e -> Toast.makeText(FileUploadActivity.this, "Please try again after some time", Toast.LENGTH_SHORT).show()));
                break;
            case MediaDescriptionModel.VIDEO:
                AsyncTask.execute(() -> MediaDescriptionModel.getVideoStorageDir().child(System.currentTimeMillis() + "." + FileUtil.getFileExtension(selectedMediaUri, FileUploadActivity.this)).putFile(selectedMediaUri).addOnSuccessListener(taskSnapshot -> {
                    if (taskSnapshot.getMetadata() != null && taskSnapshot.getMetadata().getReference() != null) {
                        afterUploading(taskSnapshot.getMetadata().getReference());
                    }
                }).addOnFailureListener(e -> Toast.makeText(FileUploadActivity.this, "Please try again after some time", Toast.LENGTH_SHORT).show()));
                break;
        }
    }

    private void afterUploading(StorageReference ref) {
        Task<Uri> result = ref.getDownloadUrl();
        result.addOnSuccessListener(uri ->{
            selectedMediaUri = uri;

            if (checkAllRequiredFields()){
                MediaDescriptionModel model = new MediaDescriptionModel();
                model.id = (int) System.currentTimeMillis();
                model.desc = description;
                model.title = title;
                model.category = selectedCategory;
                model.url = selectedMediaUri.toString();

                switch (mediaType){
                    case MediaDescriptionModel.AUDIO:
                        MediaDescriptionModel.getAudioDb().child(String.valueOf(model.id)).setValue(model).addOnFailureListener(e -> {
                            if (loading!=null && loading.isVisible())
                                loading.setError();
                            runOnUiThread(()-> Toast.makeText(this, "Error : "+e.getMessage(), Toast.LENGTH_SHORT).show());
                            e.printStackTrace();
                        }).addOnSuccessListener(unused -> {
                            if (loading!=null && loading.isVisible()) {
                                loading.setCompleted();
                                new Handler().postDelayed(()-> {
                                    loading.dismiss();
                                    finish();
                                }, 1000);
                            }
                        });
                        break;
                    case MediaDescriptionModel.VIDEO:
                        MediaDescriptionModel.getVideoDb().child(String.valueOf(model.id)).setValue(model).addOnFailureListener(e -> {
                            if (loading!=null && loading.isVisible())
                                loading.setError();
                            runOnUiThread(()-> Toast.makeText(this, "Error : "+e.getMessage(), Toast.LENGTH_SHORT).show());
                            e.printStackTrace();
                        }).addOnSuccessListener(unused -> {
                            if (loading!=null && loading.isVisible()) {
                                loading.setCompleted();
                                new Handler().postDelayed(()-> {
                                    loading.dismiss();
                                    finish();
                                }, 1000);
                            }
                        });
                        break;
                }
            }
        });
    }

    private boolean checkAllRequiredFields() {
        title = Objects.requireNonNull(binding.etTitle.getText()).toString().trim();
        description = Objects.requireNonNull(binding.etDescription.getText()).toString().trim();
        if (selectedMediaUri == null) {
            Toast.makeText(this, "Please select a file to upload", Toast.LENGTH_SHORT).show();
            return false;
        } else if (selectedCategory == null || selectedCategory.isEmpty()) {
            Toast.makeText(this, "Please select a category", Toast.LENGTH_SHORT).show();
            return false;
        } else if (title == null || title.isEmpty()) {
            binding.tilTitle.setErrorEnabled(true);
            binding.tilTitle.setError("*Required");
            return false;
        } else if (description.isEmpty()) {
            binding.tilTitle.setErrorEnabled(false);
            binding.tilTitle.setError("");
            binding.tilDescription.setErrorEnabled(true);
            binding.tilDescription.setError("*Required");
            return false;
        } else {
            binding.tilTitle.setErrorEnabled(false);
            binding.tilTitle.setError("");
            binding.tilDescription.setErrorEnabled(false);
            binding.tilDescription.setError("");
            return true;
        }
    }

    private void createChooserForMedia() {
        Intent intent = new Intent();
        switch (mediaType) {
            case MediaDescriptionModel.AUDIO:
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("audio/*");
                audioLauncher.launch(Intent.createChooser(intent, "Select audio"));
                break;
            case MediaDescriptionModel.VIDEO:
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                videoLauncher.launch(Intent.createChooser(intent, "Select Video"));
                break;
            default:

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    private void releasePlayer() {
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }

}