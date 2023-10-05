package esp32app.testingesp32.ui.quiz;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import esp32app.testingesp32.data.model.QuizModel;
import esp32app.testingesp32.databinding.ActivityQuizBinding;

public class QuizActivity extends AppCompatActivity {

    private ActivityQuizBinding binding;
    private QuizViewModel viewModel;
    private QuizAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(QuizViewModel.class);

        initView();
        initObservers();
    }

    private void initObservers() {
        viewModel.getLiveQuizData().observe(this, this::updateRecyclerView);
    }

    private void updateRecyclerView(ArrayList<QuizModel> models) {
        adapter.submitList(models);
    }

    private void initView() {
        binding.rvQuiz.setLayoutManager(new LinearLayoutManager(this));
        adapter = new QuizAdapter();
        binding.rvQuiz.setAdapter(adapter);
        binding.backImg.setOnClickListener(v-> onBackPressed());
    }
}