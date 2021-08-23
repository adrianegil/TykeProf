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
import cu.cujae.gilsoft.tykeprof.data.model.Bonus_Model;
import cu.cujae.gilsoft.tykeprof.databinding.BonusItemListBinding;

public class Bonus_Adapter extends RecyclerView.Adapter<Bonus_Adapter.ViewHolder>{

    public ArrayList<Bonus_Model> bonusModels;
    private Context context;

    public Bonus_Adapter(ArrayList<Bonus_Model> bonus_models, Context context) {
        this.bonusModels = bonus_models;
        this.context = context;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BonusItemListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.bonus_item_list,
                parent, false);
        return new Bonus_Adapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {

        Bonus_Model bonusModel = bonusModels.get(position);
        holder.binding.textViewBonus.setText(context.getString(R.string.bonus) + " " + bonusModel.getBonus());
        holder.binding.textViewTimeLess.setText(context.getString(R.string.time_less) + ": " + bonusModel.getTime_less());
        holder.binding.viewDeleteBonus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bonusModels.remove(bonusModel);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return bonusModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final BonusItemListBinding binding;

        public ViewHolder(@NonNull BonusItemListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
