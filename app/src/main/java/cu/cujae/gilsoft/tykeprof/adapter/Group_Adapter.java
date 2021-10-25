package cu.cujae.gilsoft.tykeprof.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.app.fragment.HomeNewStrategyFragment;
import cu.cujae.gilsoft.tykeprof.data.entity.Group;
import cu.cujae.gilsoft.tykeprof.databinding.GroupItemlistBinding;

public class Group_Adapter extends RecyclerView.Adapter<Group_Adapter.ViewHolder> {

    public ArrayList<Group> groups;
    private Context context;

    public Group_Adapter(ArrayList<Group> groupArrayList, Context context) {
        this.groups = groupArrayList;
        this.context = context;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GroupItemlistBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.group_itemlist,
                parent, false);
        return new Group_Adapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Group group = groups.get(position);

        if (HomeNewStrategyFragment.strategy_model.getGroupsList().contains(group))
            holder.binding.checkboxGroupSelected.setChecked(true);

        holder.binding.materialTextViewGroupName.setText(group.getGroup_name());
        holder.binding.checkboxGroupSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                group.setSelected(isChecked);
                if (isChecked) {
                    HomeNewStrategyFragment.strategy_model.getGroupsList().add(group);
                } else {
                    HomeNewStrategyFragment.strategy_model.getGroupsList().remove(group);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final GroupItemlistBinding binding;

        public ViewHolder(@NonNull GroupItemlistBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
