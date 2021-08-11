package cu.cujae.gilsoft.tykeprof.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import org.jetbrains.annotations.NotNull;

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.app.viewmodel.QuestionTypeViewModel;
import cu.cujae.gilsoft.tykeprof.data.entity.Question_Type;


public class QuestionType_Adapter extends ListAdapter<Question_Type, QuestionType_Adapter.ViewHolder> {

    private QuestionTypeViewModel questionTypeViewModel;
    private Context context;

    public QuestionType_Adapter(@NonNull DiffUtil.ItemCallback<Question_Type> diffCallback, QuestionTypeViewModel questionTypeViewModel, Context context) {
        super(diffCallback);
        this.questionTypeViewModel = questionTypeViewModel;
        this.context = context;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_type_itemlist, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Question_Type question_type = getItem(position);
        holder.materialTextViewType.setText(question_type.getType());
        holder.materialButtonDeleteQuestionType.setOnClickListener(v -> {
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setTitle(R.string.delete_question_type);
            dialog.setMessage(R.string.confirm_delete_cuestion_type);

            dialog.setPositiveButton(R.string.yes, (dialog1, which) -> {
                questionTypeViewModel.deleteQuestionType(question_type);
            });
            dialog.setNegativeButton("No", (dialog13, which) -> dialog13.dismiss());
            dialog.setNeutralButton(R.string.cancel, (dialog12, which) -> dialog12.dismiss());
            dialog.show();
        });
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final MaterialTextView materialTextViewType;
        private final MaterialButton materialButtonDeleteQuestionType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            materialTextViewType = itemView.findViewById(R.id.materialTextViewTypeQuestion);
            materialButtonDeleteQuestionType = itemView.findViewById(R.id.viewDeleteQuestionType);
        }
    }


    public static class QuestionTypeDiff extends DiffUtil.ItemCallback<Question_Type> {

        @Override
        public boolean areItemsTheSame(@NonNull Question_Type oldItem, @NonNull Question_Type newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Question_Type oldItem, @NonNull Question_Type newItem) {
            return oldItem.getType().equals(newItem.getType());
        }
    }
}
