package cu.cujae.gilsoft.tykeprof.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.data.entity.Punctuation;
import cu.cujae.gilsoft.tykeprof.databinding.RankingItemlistBinding;

public class Ranking_Adapter extends ListAdapter<Punctuation, Ranking_Adapter.ViewHolder> {

    public Ranking_Adapter(@NonNull DiffUtil.ItemCallback<Punctuation> diffCallback) {
        super(diffCallback);
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RankingItemlistBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.ranking_itemlist,
                parent, false);
        return new Ranking_Adapter.ViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Punctuation punctuation = getItem(position);
        holder.binding.textViewFullName.setText(punctuation.getPlayer().getFullName());
        holder.binding.textViewUserName.setText(punctuation.getPlayer().getUserName());
        holder.binding.textViewPoints.setText(punctuation.getPunctuation() + " " + "pts");
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final RankingItemlistBinding binding;

        public ViewHolder(@NonNull RankingItemlistBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public static class PunctuationDiff extends DiffUtil.ItemCallback<Punctuation> {

        @Override
        public boolean areItemsTheSame(@NonNull Punctuation oldItem, @NonNull Punctuation newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Punctuation oldItem, @NonNull Punctuation newItem) {
            return oldItem.getPlayer().getFullName().equals(newItem.getPlayer().getFullName());
        }
    }
}
