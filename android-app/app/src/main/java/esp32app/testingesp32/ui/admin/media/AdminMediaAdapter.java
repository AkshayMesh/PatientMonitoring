package esp32app.testingesp32.ui.admin.media;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import esp32app.testingesp32.data.model.MediaDescriptionModel;
import esp32app.testingesp32.databinding.ItemRecommendedVideoBinding;

public class AdminMediaAdapter extends ListAdapter<MediaDescriptionModel, AdminMediaAdapter.ItemHolder> {

    private final OnClick listener;

    public AdminMediaAdapter(OnClick listener) {
        super(new MediaDiff());
        this.listener = listener;
    }

    public interface OnClick{
        void onClicked(MediaDescriptionModel model);
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRecommendedVideoBinding binding = ItemRecommendedVideoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ItemHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        MediaDescriptionModel model = getItem(position);
        holder.bind(model);
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        private final ItemRecommendedVideoBinding binding;
        public ItemHolder(@NonNull ItemRecommendedVideoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(MediaDescriptionModel model) {
            binding.setViewModel(model);
            binding.cardThumb.setOnClickListener(v-> listener.onClicked(model));
            try {
                Glide.with(binding.ivThumb.getContext()).load(model.url).into(binding.ivThumb);
            }catch (Exception e){
                Log.e("GLIDE", "Unable to load thumbnail");
            }
        }
    }

    public static class MediaDiff extends DiffUtil.ItemCallback<MediaDescriptionModel>{

        @Override
        public boolean areItemsTheSame(@NonNull MediaDescriptionModel oldItem, @NonNull MediaDescriptionModel newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull MediaDescriptionModel oldItem, @NonNull MediaDescriptionModel newItem) {
            return oldItem.id == newItem.id;
        }
    }
}
