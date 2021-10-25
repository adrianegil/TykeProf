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
import cu.cujae.gilsoft.tykeprof.data.entity.Topic;
import cu.cujae.gilsoft.tykeprof.databinding.TopicItemlistBinding;

public class Topic_Adapter extends RecyclerView.Adapter<Topic_Adapter.ViewHolder> {

    public ArrayList<Topic> topics;
    private Context context;

    public Topic_Adapter(ArrayList<Topic> topicArrayList, Context context) {
        this.topics = topicArrayList;
        this.context = context;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TopicItemlistBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.topic_itemlist,
                parent, false);
        return new Topic_Adapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Topic topic = topics.get(position);

        if (HomeNewStrategyFragment.strategy_model.getTopicsList().contains(topic)){
            holder.binding.checkboxTopicSelected.setChecked(true);
        }

        holder.binding.checkboxTopicSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                topic.setSelected(isChecked);
                if(isChecked){
                    HomeNewStrategyFragment.strategy_model.getTopicsList().add(topic);
                }
                else {
                    HomeNewStrategyFragment.strategy_model.getTopicsList().remove(topic);

                }
            }
        });

        holder.binding.materialTextViewTopicName.setText(topic.getName());
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final TopicItemlistBinding binding;

        public ViewHolder(@NonNull TopicItemlistBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
