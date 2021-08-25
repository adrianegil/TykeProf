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
import cu.cujae.gilsoft.tykeprof.data.entity.Question;
import cu.cujae.gilsoft.tykeprof.databinding.QuestionStrategyItemlistBinding;

public class QuestionOfStrategy_Adapter extends RecyclerView.Adapter<QuestionOfStrategy_Adapter.ViewHolder> {

    public ArrayList<Question> questions;
    private Context context;

    public QuestionOfStrategy_Adapter(ArrayList<Question> questionArrayList, Context context) {
        this.questions = questionArrayList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        QuestionStrategyItemlistBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.question_strategy_itemlist,
                parent, false);
        return new QuestionOfStrategy_Adapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {

        Question question = questions.get(position);

        if (HomeNewStrategyFragment.strategy_model.getQuestionsList().contains(question))
            holder.binding.checkboxQuestionSelected.setChecked(true);

        holder.binding.textViewQuestionTitle.setText(question.getQuestion_title());
        holder.binding.textViewQuestionType.setText(question.getQuestion_type().getType());

        holder.binding.checkboxQuestionSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                question.setSelected(isChecked);
                if (isChecked) {
                    HomeNewStrategyFragment.strategy_model.getQuestionsList().add(question);
                } else {
                    HomeNewStrategyFragment.strategy_model.getQuestionsList().remove(question);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final QuestionStrategyItemlistBinding binding;
        public ViewHolder(@NonNull QuestionStrategyItemlistBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
