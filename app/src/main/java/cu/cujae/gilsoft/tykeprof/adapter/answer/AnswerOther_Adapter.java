package cu.cujae.gilsoft.tykeprof.adapter.answer;

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
import cu.cujae.gilsoft.tykeprof.adapter.Bonus_Adapter;
import cu.cujae.gilsoft.tykeprof.adapter.Clue_Adapter;
import cu.cujae.gilsoft.tykeprof.data.model.Answer_Model;
import cu.cujae.gilsoft.tykeprof.data.model.Clue_Model;
import cu.cujae.gilsoft.tykeprof.databinding.AnswerOtherItemlistBinding;
import cu.cujae.gilsoft.tykeprof.databinding.ClueItemListBinding;

public class AnswerOther_Adapter extends RecyclerView.Adapter<AnswerOther_Adapter.ViewHolder> {

    public ArrayList<Answer_Model> answerModels;
    private Context context;

    public AnswerOther_Adapter(ArrayList<Answer_Model> answer_models, Context context) {
        this.answerModels = answer_models;
        this.context = context;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AnswerOtherItemlistBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.answer_other_itemlist,
                parent, false);
        return new AnswerOther_Adapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Answer_Model answer_model = answerModels.get(position);

        if (answer_model.isCorrect())
            holder.binding.imageViewIconAnswer.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_answer_correct));
        else
            holder.binding.imageViewIconAnswer.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_error_red));

        holder.binding.textViewAnswerText.setText(answer_model.getAnswer());
        holder.binding.viewDeleteOtherAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerModels.remove(answer_model);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return answerModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final AnswerOtherItemlistBinding binding;

        public ViewHolder(@NonNull AnswerOtherItemlistBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
