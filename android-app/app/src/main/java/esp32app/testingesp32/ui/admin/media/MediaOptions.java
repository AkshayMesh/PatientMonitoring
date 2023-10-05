package esp32app.testingesp32.ui.admin.media;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import esp32app.testingesp32.databinding.MediaOptionsBinding;

public class MediaOptions extends BottomSheetDialogFragment {

    private MediaOptionsBinding binding;
    private final OnSelection selection;
    public static final int OPTION_DELETE = 0;
    public static final int OPTION_VIEW = 1;

    public MediaOptions(OnSelection selection) {
        this.selection = selection;
    }

    public static MediaOptions newInstance(OnSelection selection) {
        Bundle args = new Bundle();
        MediaOptions fragment = new MediaOptions(selection);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = MediaOptionsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onClickEvents();
    }

    public interface OnSelection{
        void onTypeSelected(int type);
    }

    private void onClickEvents() {
        binding.tvDelete.setOnClickListener( v-> {
            selection.onTypeSelected(OPTION_DELETE);
            dismiss();
        });
        binding.tvView.setOnClickListener( v-> {
            selection.onTypeSelected(OPTION_VIEW);
            dismiss();
        });
    }
}
