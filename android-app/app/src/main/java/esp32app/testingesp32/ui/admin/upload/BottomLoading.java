package esp32app.testingesp32.ui.admin.upload;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import esp32app.testingesp32.R;
import esp32app.testingesp32.databinding.BottomLoadingBinding;

public class BottomLoading extends BottomSheetDialogFragment {

    BottomLoadingBinding binding;
    public BottomLoading() {}

    public static BottomLoading newInstance() {
        Bundle args = new Bundle();
        BottomLoading fragment = new BottomLoading();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BottomLoadingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void setError(){
        binding.progressView.setVisibility(View.GONE);
        binding.imageGraphic.setVisibility(View.VISIBLE);
        binding.imageGraphic.setImageResource(R.drawable.error_bro);
    }

    public void setCompleted(){
        binding.progressView.setVisibility(View.GONE);
        binding.imageGraphic.setVisibility(View.VISIBLE);
        binding.imageGraphic.setImageResource(R.drawable.completed_bro);
    }

}
