package esp32app.testingesp32.ui.admin.upload;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import esp32app.testingesp32.data.model.MediaDescriptionModel;
import esp32app.testingesp32.databinding.BottomMediaSelectionBinding;

public class BottomMediaSelection extends BottomSheetDialogFragment {

    private BottomMediaSelectionBinding binding;
    private final OnSelection selection;

    public BottomMediaSelection(OnSelection selection) {
        this.selection = selection;
    }

    public static BottomMediaSelection newInstance(OnSelection selection) {
        Bundle args = new Bundle();
        BottomMediaSelection fragment = new BottomMediaSelection(selection);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = BottomMediaSelectionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onClickEvents();
    }

    public interface OnSelection{
        void onTypeSelected(String type);
    }

    private void onClickEvents() {
        binding.mediaAudioCard.setOnClickListener( v-> {
            selection.onTypeSelected(MediaDescriptionModel.AUDIO);
            dismiss();
        });
        binding.mediaVideoCard.setOnClickListener( v-> {
            selection.onTypeSelected(MediaDescriptionModel.VIDEO);
            dismiss();
        });
    }
}
