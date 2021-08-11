package cu.cujae.gilsoft.tykeprof.app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.entity.Clue_Type;
import cu.cujae.gilsoft.tykeprof.data.entity.Question;
import cu.cujae.gilsoft.tykeprof.data.entity.Question_Type;
import cu.cujae.gilsoft.tykeprof.repository.Clue_Type_Repository;
import cu.cujae.gilsoft.tykeprof.repository.Question_Repository;
import cu.cujae.gilsoft.tykeprof.repository.Question_Type_Repository;

public class QuestionViewModel extends AndroidViewModel {

    private Clue_Type_Repository clue_type_repository;
    private Question_Type_Repository question_type_repository;
    private Question_Repository question_repository;
    private List<Question_Type> question_typeList;
    private List<Clue_Type> clue_typeList;

    public QuestionViewModel(@NonNull Application application) {
        super(application);
        this.clue_type_repository = new Clue_Type_Repository(application);
        this.question_type_repository = new Question_Type_Repository(application);
        this.clue_typeList = clue_type_repository.getAllClueTypeList();
        this.question_typeList = question_type_repository.getAllQuestionTypeList();
        this.question_repository = new Question_Repository(application);
    }

    public void deleteQuestionByID(long id) {
        question_repository.deleteQuestionByID(id);
    }

    public LiveData<List<Question>> getAllQuestion() {
        return question_repository.getAllQuestionList();
    }

    public List<Question> getAllQuestionLocalList() {
        return question_repository.getAllQuestionLocalList();
    }

    public List<Question_Type> getQuestion_typeList() {
        return question_typeList;
    }

    public List<Clue_Type> getClue_typeList() {
        return clue_typeList;
    }
}