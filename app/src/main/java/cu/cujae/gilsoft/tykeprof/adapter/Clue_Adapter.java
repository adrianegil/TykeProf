package cu.cujae.gilsoft.tykeprof.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.data.model.Clue_Model;
import cu.cujae.gilsoft.tykeprof.databinding.ClueItemListBinding;

public class Clue_Adapter extends RecyclerView.Adapter<Clue_Adapter.ViewHolder> {

    public ArrayList<Clue_Model> clueModels;
    private Context context;

    public Clue_Adapter(ArrayList<Clue_Model> clue_models, Context context) {
        this.clueModels = clue_models;
        this.context = context;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ClueItemListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.clue_item_list,
                parent, false);
        return new Clue_Adapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Clue_Model clue_model = clueModels.get(position);
        holder.binding.textViewClueName.setText(context.getString(R.string.clue) + ": " + clue_model.getClue_name());
        holder.binding.textViewClueTypeOfClue.setText(context.getString(R.string.clue_type) + ": " + clue_model.getClue_type_selected());
        holder.binding.viewLookDeleteClue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clueModels.remove(clue_model);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return clueModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final ClueItemListBinding binding;

        public ViewHolder(@NonNull ClueItemListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
