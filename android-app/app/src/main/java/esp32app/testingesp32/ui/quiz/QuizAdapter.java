package esp32app.testingesp32.ui.quiz;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import esp32app.testingesp32.data.model.QuizModel;
import esp32app.testingesp32.databinding.ItemQuizBinding;
import esp32app.testingesp32.ui.custom.OptionsRadioGroup;

public class QuizAdapter extends ListAdapter<QuizModel, QuizAdapter.ItemHolder> {

    protected QuizAdapter() {
        super(new QuizDiff());
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemQuizBinding binding = ItemQuizBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ItemHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        QuizModel model = getItem(position);
        holder.bind(model);
    }

    public static class ItemHolder extends RecyclerView.ViewHolder {
        private final ItemQuizBinding binding;
        public ItemHolder(@NonNull ItemQuizBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(QuizModel model) {
            binding.setViewModel(model);

            OptionsRadioGroup optionsRadioGroup = binding.tvOptions;
            optionsRadioGroup.setOptions(model.options);
            optionsRadioGroup.setSelectedOption(model.answer);

            int correctOptionIndex = model.correctIndex-1;
            optionsRadioGroup.setCorrectOptionIndex(correctOptionIndex);
            optionsRadioGroup.setAnswerView(binding.tvAnswer);  // Set the answer view for the OptionsRadioGroup

            optionsRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                RadioButton selectedRadioButton = group.findViewById(checkedId);
                if (selectedRadioButton != null) {
                    model.tempAnswer = selectedRadioButton.getText().toString();
                    int selectedIndex = model.options.indexOf(model.tempAnswer);

                    if (selectedIndex == correctOptionIndex) {
                        // Show the answer when the correct option is selected
                        optionsRadioGroup.showAnswer();
                        binding.tvAnswer.setText(model.answer);
                    } else {
                        // Show "Incorrect" when the wrong option is selected
                        optionsRadioGroup.showInvalidAnswer();
                    }
                }
            });
        }
    }

    public static class QuizDiff extends DiffUtil.ItemCallback<QuizModel>{

        @Override
        public boolean areItemsTheSame(@NonNull QuizModel oldItem, @NonNull QuizModel newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull QuizModel oldItem, @NonNull QuizModel newItem) {
            return Objects.equals(oldItem.question, newItem.question);
        }
    }
}
