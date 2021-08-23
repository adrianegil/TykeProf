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
import cu.cujae.gilsoft.tykeprof.databinding.AnswerOrderItemlistBinding;

public class AnswerOrder_Adapter extends RecyclerView.Adapter<AnswerOrder_Adapter.ViewHolder> {

    public ArrayList<Answer_Model> answerModels;
    private Context context;

    public AnswerOrder_Adapter(ArrayList<Answer_Model> answer_models, Context context) {
        this.answerModels = answer_models;
        this.context = context;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AnswerOrderItemlistBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.answer_order_itemlist,
                parent, false);
        return new AnswerOrder_Adapter.ViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Answer_Model answer_model = answerModels.get(position);
        holder.binding.textViewAnswerText.setText(context.getString(R.string.answer) + ": " + answer_model.getAnswer());
        holder.binding.textViewOrder.setText(context.getString(R.string.order) + ": " + answer_model.getOrder());
        holder.binding.viewDeleteOrderAnswer.setOnClickListener(new View.OnClickListener() {
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

        final AnswerOrderItemlistBinding binding;

        public ViewHolder(@NonNull AnswerOrderItemlistBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
