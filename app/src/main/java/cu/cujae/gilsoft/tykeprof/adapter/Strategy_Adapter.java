package cu.cujae.gilsoft.tykeprof.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.app.viewmodel.StrategyViewModel;
import cu.cujae.gilsoft.tykeprof.data.entity.Strategy;
import cu.cujae.gilsoft.tykeprof.data.entity.Teacher;
import cu.cujae.gilsoft.tykeprof.data.relation.StrategyWhitQuestions;
import cu.cujae.gilsoft.tykeprof.data.relation.StrategyWhitTopics;
import cu.cujae.gilsoft.tykeprof.databinding.StrategyItemlistBinding;
import cu.cujae.gilsoft.tykeprof.repository.Teacher_Repository;

public class Strategy_Adapter extends ListAdapter<Strategy, Strategy_Adapter.ViewHolder> {

    private Teacher_Repository teacher_repository;
    private StrategyViewModel strategyViewModel;
    private Activity activity;

    public Strategy_Adapter(@NonNull DiffUtil.ItemCallback<Strategy> diffCallback, StrategyViewModel questionViewModel, Activity activity, Application application) {
        super(diffCallback);
        this.strategyViewModel = questionViewModel;
        this.activity = activity;
        this.teacher_repository = new Teacher_Repository(application);
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        StrategyItemlistBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.strategy_itemlist,
                parent, false);
        return new Strategy_Adapter.ViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Strategy strategy = getItem(position);
        holder.binding.setStrategy(strategy);
        holder.binding.layoutExpandedStrategyContent.setVisibility(strategy.isContenExpandable() ? View.VISIBLE : View.GONE);

        holder.binding.viewDeleteStrategy.setOnClickListener(v -> {
            AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
            dialog.setTitle(R.string.delete_strategy);
            dialog.setMessage(R.string.confirm_delete_strategy);
            dialog.setPositiveButton(R.string.yes, (dialog1, which) -> {
                strategyViewModel.deleteStrategyByID(strategy.getId_strategy());
            });
            dialog.setNegativeButton("No", (dialog13, which) -> dialog13.dismiss());
            dialog.setNeutralButton(R.string.cancel, (dialog12, which) -> dialog12.dismiss());
            dialog.show();
        });

        StrategyWhitTopics strategyWhitTopics = strategyViewModel.getStrategyWhitTopicsByStrategyID(strategy.getId_strategy());
        holder.binding.textViewStrategyCantTopics.setText(activity.getString(R.string.topics_cant) + " "+strategyWhitTopics.topicList.size());

        StrategyWhitQuestions strategyWhitQuestions = strategyViewModel.getStrategyWhitQuestionsByStrategyID(strategy.getId_strategy());
        holder.binding.textViewStrategyCantQuestions.setText(activity.getString(R.string.question_cant) +" "+ strategyWhitQuestions.questionList.size());

        Teacher teacher = teacher_repository.getTeacherLocalById(strategy.getId_teacher());
        holder.binding.textViewStrategyCreator.setText(activity.getString(R.string.creator) + " "+teacher.getFullName());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final StrategyItemlistBinding binding;

        public ViewHolder(@NonNull StrategyItemlistBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.viewLookStrategyContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Strategy strategy = Strategy_Adapter.this.getCurrentList().get(getAdapterPosition());
                    strategy.setContenExpandable(!strategy.isContenExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }

    public static class StrategyDiff extends DiffUtil.ItemCallback<Strategy> {

        @Override
        public boolean areItemsTheSame(@NonNull Strategy oldItem, @NonNull Strategy newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Strategy oldItem, @NonNull Strategy newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    }

}
