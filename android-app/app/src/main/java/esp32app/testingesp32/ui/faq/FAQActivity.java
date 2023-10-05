package esp32app.testingesp32.ui.faq;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import esp32app.testingesp32.data.model.FaqModel;
import esp32app.testingesp32.databinding.ActivityFaqActivityBinding;

public class FAQActivity extends AppCompatActivity {

    private ActivityFaqActivityBinding binding;
    private FAQViewModel viewModel;
    private FAQAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFaqActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(FAQViewModel.class);

        initViews();
        initObserver();
    }

    private void initObserver() {
        viewModel.getLiveFaqData().observe(this, this::updateRecyclerView);
    }

    private void updateRecyclerView(ArrayList<FaqModel> models) {
        adapter.submitList(models);
    }

    private void initViews() {
        binding.rvFaq.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FAQAdapter();
        binding.rvFaq.setAdapter(adapter);
        binding.backImg.setOnClickListener(v-> onBackPressed());
    }
}