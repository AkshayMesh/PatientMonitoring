package esp32app.testingesp32.ui.search;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import esp32app.testingesp32.data.model.SearchModel;
import esp32app.testingesp32.databinding.ItemSearchBinding;
import esp32app.testingesp32.ui.doctor.DoctorActivity;

public class SearchAdapter extends ListAdapter<SearchModel, SearchAdapter.ItemHolder> {
    private List<SearchModel> searchListFull;
    protected SearchAdapter() {
        super(new SearchDiff());
        searchListFull = new ArrayList<>();
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSearchBinding binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ItemHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        SearchModel model = getItem(position);
        holder.bind(model);
    }

    public static class ItemHolder extends RecyclerView.ViewHolder {
        private final ItemSearchBinding binding;
        public ItemHolder(@NonNull ItemSearchBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(SearchModel model) {
            binding.setViewModel(model);
            String availability = "Unavailable";
            if (model.available!=null && !model.available.isEmpty())
                availability = model.available.equalsIgnoreCase("Yes")?"Available":"Unavailable";
            binding.tvAvailability.setText(availability);
            binding.executePendingBindings();
            itemView.setOnClickListener(v->{
                DoctorActivity.model = model;
                Context context = v.getContext();
                context.startActivity(new Intent(context, DoctorActivity.class));
            });
        }
    }

    public void filter(String text) {
        if (searchListFull.isEmpty()) {
            searchListFull = getCurrentList();
        }
        List<SearchModel> filteredList = new ArrayList<>();
        for (SearchModel searchModel : searchListFull) {
            if (searchModel.name.toLowerCase().contains(text.toLowerCase()) ||
                    searchModel.profession.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(searchModel);
            }
        }
        submitList(filteredList);
    }

    public static class SearchDiff extends DiffUtil.ItemCallback<SearchModel>{

        @Override
        public boolean areItemsTheSame(@NonNull SearchModel oldItem, @NonNull SearchModel newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull SearchModel oldItem, @NonNull SearchModel newItem) {
            return Objects.equals(oldItem.id, newItem.id);
        }
    }
}
