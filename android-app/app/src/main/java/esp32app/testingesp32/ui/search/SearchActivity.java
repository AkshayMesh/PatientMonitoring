package esp32app.testingesp32.ui.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import esp32app.testingesp32.data.model.SearchModel;
import esp32app.testingesp32.databinding.ActivitySearchBinding;

public class SearchActivity extends AppCompatActivity {

    private ActivitySearchBinding binding;
    private SearchViewModel viewModel;
    private SearchAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        initObservers();
        initViews();
    }

    private void initObservers() {
        viewModel.getLiveSearchData().observe(this, this::onListUpdated);
    }

    private void onListUpdated(List<SearchModel> models) {
        if (models!=null){
            adapter.submitList(models);
        }
    }

    private void initViews() {
        binding.backImg.setOnClickListener(v-> onBackPressed());
        binding.rvSearch.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchAdapter();
        binding.rvSearch.setAdapter(adapter);
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Update the list when the user input changes
                if (adapter != null) {
                    adapter.filter(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}