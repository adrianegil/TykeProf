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

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.app.viewmodel.GiftViewModel;
import cu.cujae.gilsoft.tykeprof.app.viewmodel.QuestionViewModel;
import cu.cujae.gilsoft.tykeprof.data.AppDatabase;
import cu.cujae.gilsoft.tykeprof.data.dao.Answer_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Clue_Dao;
import cu.cujae.gilsoft.tykeprof.data.entity.Answer;
import cu.cujae.gilsoft.tykeprof.data.entity.Clue;
import cu.cujae.gilsoft.tykeprof.data.entity.Professional_Rol;
import cu.cujae.gilsoft.tykeprof.data.entity.Question;
import cu.cujae.gilsoft.tykeprof.data.entity.Question_Type;
import cu.cujae.gilsoft.tykeprof.databinding.QuestionItemlistBinding;
import cu.cujae.gilsoft.tykeprof.repository.Answer_Repository;
import cu.cujae.gilsoft.tykeprof.repository.Clue_Repository;
import cu.cujae.gilsoft.tykeprof.repository.Clue_Type_Repository;

public class Question_Adapter extends ListAdapter<Question, Question_Adapter.ViewHolder> {

    private Clue_Repository clue_repository;
    private Answer_Repository answer_repository;
    private QuestionViewModel questionViewModel;
    private Activity activity;

    public Question_Adapter(@NonNull DiffUtil.ItemCallback<Question> diffCallback, QuestionViewModel questionViewModel, Activity activity, Application application) {
        super(diffCallback);
        this.questionViewModel = questionViewModel;
        this.activity = activity;
        clue_repository = new Clue_Repository(application);
        answer_repository = new Answer_Repository(application);
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        QuestionItemlistBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.question_itemlist,
                parent, false);
        return new Question_Adapter.ViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Question question = getItem(position);
        holder.binding.setQuestion(question);
        holder.binding.layoutExpandedQuestionContent.setVisibility(question.isContenExpandable() ? View.VISIBLE : View.GONE);

        holder.binding.viewDeleteQuestion.setOnClickListener(v -> {
            AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
            dialog.setTitle(R.string.delete_question);
            dialog.setMessage(R.string.confirm_delete_question);
            dialog.setPositiveButton(R.string.yes, (dialog1, which) -> {
                questionViewModel.deleteQuestionByID(question.getId_question());
            });
            dialog.setNegativeButton("No", (dialog13, which) -> dialog13.dismiss());
            dialog.setNeutralButton(R.string.cancel, (dialog12, which) -> dialog12.dismiss());
            dialog.show();
        });

        List<Clue> clueList = clue_repository.getClueListByQuestionID(question.getId_question());
        List<Answer> answerList = answer_repository.getAnswerListByQuestionID(question.getId_question());
        holder.binding.textViewClueCant.setText(activity.getString(R.string.clue_cant) + " " + clueList.size());
        holder.binding.textViewAnswerCant.setText(activity.getString(R.string.answer_cant) + " " + answerList.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final QuestionItemlistBinding binding;

        public ViewHolder(@NonNull QuestionItemlistBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.viewLookQuestionContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Question question = Question_Adapter.this.getCurrentList().get(getAdapterPosition());
                    question.setContenExpandable(!question.isContenExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }


    public static class QuestionDiff extends DiffUtil.ItemCallback<Question> {

        @Override
        public boolean areItemsTheSame(@NonNull Question oldItem, @NonNull Question newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Question oldItem, @NonNull Question newItem) {
            return oldItem.getQuestion_title().equals(newItem.getQuestion_title());
        }
    }

}
