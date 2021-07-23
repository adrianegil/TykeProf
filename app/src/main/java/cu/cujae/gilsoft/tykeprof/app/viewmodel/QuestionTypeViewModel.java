package cu.cujae.gilsoft.tykeprof.app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.entity.Question_Type;
import cu.cujae.gilsoft.tykeprof.repository.Question_Type_Repository;

public class QuestionTypeViewModel extends AndroidViewModel {

    private Question_Type_Repository question_type_repository;
    private final LiveData<List<Question_Type>> allQuestionType;

    public QuestionTypeViewModel(@NonNull Application application) {
        super(application);
        this.question_type_repository = new Question_Type_Repository(application);
        this.allQuestionType = question_type_repository.getAllQuestionType();
    }

    public void saveQuestionType(Question_Type question_type) {
        question_type_repository.saveQuestionType(question_type);
    }

    public  void deleteQuestionType(Question_Type question_type){
        question_type_repository.deleteQuestionType(question_type);
    }

    public LiveData<List<Question_Type>> getAllQuestionType() {
        return allQuestionType;
    }
}