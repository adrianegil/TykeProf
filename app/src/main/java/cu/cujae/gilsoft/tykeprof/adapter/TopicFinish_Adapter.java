package cu.cujae.gilsoft.tykeprof.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.data.entity.Topic;
import cu.cujae.gilsoft.tykeprof.databinding.TopicFinishItemlistBinding;

public class TopicFinish_Adapter extends RecyclerView.Adapter<TopicFinish_Adapter.ViewHolder> {

    public ArrayList<Topic> topics;
    private Context context;

    public TopicFinish_Adapter(ArrayList<Topic> topicArrayList, Context context) {
        this.topics = topicArrayList;
        this.context = context;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TopicFinishItemlistBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.topic_finish_itemlist,
                parent, false);
        return new TopicFinish_Adapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Topic topic = topics.get(position);
        holder.binding.materialTextViewTopicName.setText(topic.getName());
        holder.binding.textViewTopicDescrip.setText(topic.getDescrip());
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final TopicFinishItemlistBinding binding;

        public ViewHolder(@NonNull TopicFinishItemlistBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
