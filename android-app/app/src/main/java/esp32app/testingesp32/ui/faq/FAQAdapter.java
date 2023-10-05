package esp32app.testingesp32.ui.faq;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import esp32app.testingesp32.R;
import esp32app.testingesp32.data.model.FaqModel;
import esp32app.testingesp32.databinding.ItemFaqBinding;
import esp32app.testingesp32.util.UiUtil;

public class FAQAdapter extends ListAdapter<FaqModel, FAQAdapter.ItemHolder> {

    protected FAQAdapter() {
        super(new FAQDiff());
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFaqBinding binding = ItemFaqBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ItemHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        FaqModel model = getItem(position);
        holder.bind(model);
    }

    public static class ItemHolder extends RecyclerView.ViewHolder {
        private final ItemFaqBinding binding;
        public ItemHolder(@NonNull ItemFaqBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(FaqModel model) {
            binding.setViewModel(model);
            binding.card.setOnClickListener(v-> openOrCloseQuestion(model));
        }

        private void openOrCloseQuestion(FaqModel model) {
            if (model.isOpen){
                UiUtil.removeViews(binding.tvSubHead);
                binding.imageView.setImageResource(R.drawable.ic_arrow_down);
                model.isOpen = false;
            } else {
                UiUtil.showViews(binding.tvSubHead);
                binding.imageView.setImageResource(R.drawable.arrow_up);
                model.isOpen = true;
            }
        }
    }

    public static class FAQDiff extends DiffUtil.ItemCallback<FaqModel>{

        @Override
        public boolean areItemsTheSame(@NonNull FaqModel oldItem, @NonNull FaqModel newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull FaqModel oldItem, @NonNull FaqModel newItem) {
            return Objects.equals(oldItem.question, newItem.question);
        }
    }
}
