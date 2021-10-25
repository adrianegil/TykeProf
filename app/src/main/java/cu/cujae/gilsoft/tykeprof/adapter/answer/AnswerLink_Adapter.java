package cu.cujae.gilsoft.tykeprof.adapter.answer;

import android.annotation.SuppressLint;
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
import cu.cujae.gilsoft.tykeprof.data.model.Answer_Model;
import cu.cujae.gilsoft.tykeprof.databinding.AnswerLinkItemlistBinding;

public class AnswerLink_Adapter extends RecyclerView.Adapter<AnswerLink_Adapter.ViewHolder> {

    public ArrayList<Answer_Model> answerModels;
    private Context context;

    public AnswerLink_Adapter(ArrayList<Answer_Model> answer_models, Context context) {
        this.answerModels = answer_models;
        this.context = context;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AnswerLinkItemlistBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.answer_link_itemlist,
                parent, false);
        return new AnswerLink_Adapter.ViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Answer_Model answer_model = answerModels.get(position);
        holder.binding.textViewQuestionTextOfAnswer.setText(context.getString(R.string.question)+": "+answer_model.getQuestion());
        holder.binding.textViewAnswerText.setText(context.getString(R.string.link)+": "+ answer_model.getAnswer());
        holder.binding.viewDeleteLinkAnswer.setOnClickListener(new View.OnClickListener() {
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

        final AnswerLinkItemlistBinding binding;

        public ViewHolder(@NonNull AnswerLinkItemlistBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
