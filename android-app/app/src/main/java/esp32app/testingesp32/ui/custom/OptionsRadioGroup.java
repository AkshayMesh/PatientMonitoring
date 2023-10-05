package esp32app.testingesp32.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class OptionsRadioGroup extends RadioGroup {

    private List<String> options;
    private int correctOptionIndex = -1;
    private TextView answerView; // Reference to the view displaying the answer

    public OptionsRadioGroup(@NonNull Context context) {
        super(context);
        init();
    }

    public OptionsRadioGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        removeAllViews();
        if (options != null) {
            for (int i = 0; i < options.size(); i++) {
                String option = options.get(i);

                RadioButton radioButton = new RadioButton(getContext());
                radioButton.setText(option);
                radioButton.setLayoutParams(new RadioGroup.LayoutParams(
                        RadioGroup.LayoutParams.MATCH_PARENT,
                        RadioGroup.LayoutParams.WRAP_CONTENT
                ));
                radioButton.setTag(i);

                radioButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    int selectedOptionIndex = (int) buttonView.getTag();
                    boolean isCorrect = selectedOptionIndex == correctOptionIndex;

                    if (isChecked) {
                        if (isCorrect) {
                            showAnswer();
                        } else {
                            showInvalidAnswer();
                        }
                    } else {
                        hideAnswer();
                    }
                });

                addView(radioButton);
            }
        }
    }

    public void setOptions(List<String> options) {
        this.options = options;
        init();
    }

    public void setSelectedOption(String selectedOption) {
        int selectedIndex = options.indexOf(selectedOption);
        if (selectedIndex != -1) {
            clearCheck();
            RadioButton selectedRadioButton = (RadioButton) getChildAt(selectedIndex);
            if (selectedRadioButton != null) {
                selectedRadioButton.setChecked(true);
            }
        }
    }

    public void setCorrectOptionIndex(int correctOptionIndex) {
        this.correctOptionIndex = correctOptionIndex;
    }

    public void setAnswerView(TextView answerView) {
        this.answerView = answerView;
    }

    public void showAnswer() {
        if (answerView != null) {
            answerView.setVisibility(View.VISIBLE);
        }
    }

    public void showInvalidAnswer() {
        if (answerView != null) {
            answerView.setVisibility(View.VISIBLE);
            answerView.setText("Incorrect");
        }
    }

    private void hideAnswer() {
        if (answerView != null) {
            answerView.setVisibility(View.GONE);
        }
    }
}