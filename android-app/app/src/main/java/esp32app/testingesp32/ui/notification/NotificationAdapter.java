package esp32app.testingesp32.ui.notification;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import esp32app.testingesp32.data.model.NotificationModel;
import esp32app.testingesp32.databinding.ItemNotificationsBinding;
import esp32app.testingesp32.util.DateUtil;

public class NotificationAdapter extends ListAdapter<NotificationModel, NotificationAdapter.ItemHolder> {

    protected NotificationAdapter() {
        super(new NotificationDiff());
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNotificationsBinding binding = ItemNotificationsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ItemHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        NotificationModel model = getItem(position);
        holder.bind(model);
    }

    public static class ItemHolder extends RecyclerView.ViewHolder {
        private final ItemNotificationsBinding binding;
        public ItemHolder(@NonNull ItemNotificationsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(NotificationModel model) {
            binding.setViewModel(model);
            String strDate = DateUtil.convertTimestampToFormattedDate(model.timestamp);
            binding.tvDate.setText(strDate);
        }
    }

    public static class NotificationDiff extends DiffUtil.ItemCallback<NotificationModel>{

        @Override
        public boolean areItemsTheSame(@NonNull NotificationModel oldItem, @NonNull NotificationModel newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull NotificationModel oldItem, @NonNull NotificationModel newItem) {
            return oldItem.id == newItem.id;
        }
    }
}
