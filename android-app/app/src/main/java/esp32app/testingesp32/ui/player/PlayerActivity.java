package esp32app.testingesp32.ui.player;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;

import esp32app.testingesp32.databinding.ActivityPlayerBinding;

public class PlayerActivity extends AppCompatActivity {

    private ActivityPlayerBinding binding;
    private String url;
    private ExoPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getIntentData();
        initViews();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent!=null){
            url = intent.getStringExtra("url");
        }
    }

    private void initViews() {
        if (url!=null && !url.isEmpty())
            initializePlayer();
        else {
            Toast.makeText(this, "File not found", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void initializePlayer() {
        if (player == null) {
            player = new ExoPlayer.Builder(this).build();
        }
        binding.exoPlayerView.setVisibility(View.VISIBLE);
        binding.exoPlayerView.setPlayer(player);
        MediaItem mediaItem = MediaItem.fromUri(url);
// Set the media item to be played.
        player.setMediaItem(mediaItem);
// Prepare the player.
        player.prepare();
// Start the playback.
        player.play();
    }

    private void releasePlayer() {
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }
}