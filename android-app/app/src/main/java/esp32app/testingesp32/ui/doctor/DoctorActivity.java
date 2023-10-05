package esp32app.testingesp32.ui.doctor;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import esp32app.testingesp32.data.model.SearchModel;
import esp32app.testingesp32.databinding.ActivityDoctorBinding;

public class DoctorActivity extends AppCompatActivity {

    private ActivityDoctorBinding binding;
    public static SearchModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
    }

    private void initViews() {
        binding.backImg.setOnClickListener(v-> onBackPressed());
        binding.setViewModel(model);
        binding.btnCall.setOnClickListener(v->{
            Uri dialUri = Uri.parse("tel:"+model.number);
            Intent callIntent = new Intent(Intent.ACTION_DIAL, dialUri);
            startActivity(callIntent);
        });
    }
}