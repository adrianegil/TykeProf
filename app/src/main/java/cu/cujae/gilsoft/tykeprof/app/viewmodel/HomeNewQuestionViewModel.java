package cu.cujae.gilsoft.tykeprof.app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.entity.Clue_Type;
import cu.cujae.gilsoft.tykeprof.data.entity.Question_Type;
import cu.cujae.gilsoft.tykeprof.data.entity.Subject;
import cu.cujae.gilsoft.tykeprof.data.model.Question_Model;
import cu.cujae.gilsoft.tykeprof.repository.Clue_Type_Repository;
import cu.cujae.gilsoft.tykeprof.repository.Question_Repository;
import cu.cujae.gilsoft.tykeprof.repository.Question_Type_Repository;
import cu.cujae.gilsoft.tykeprof.repository.Subject_Repository;

public class HomeNewQuestionViewModel extends AndroidViewModel {

    private Question_Type_Repository question_type_repository;
    private Clue_Type_Repository clue_type_repository;
    private Subject_Repository subject_repository;
    private Question_Repository question_repository;
    private List<Subject> subjectList;

    public HomeNewQuestionViewModel(@NonNull Application application) {
        super(application);
        this.subject_repository = new Subject_Repository(application);
        this.subjectList = subject_repository.getAllSubjectList();
        this.question_type_repository = new Question_Type_Repository(application);
        this.clue_type_repository = new Clue_Type_Repository(application);
        this.question_repository = new Question_Repository(application);
    }

    public void saveQuestion(Question_Model question_model) {
        question_repository.saveQuestion(question_model);
    }

    public List<Question_Type> getQuestionTypeList() {
        return question_type_repository.getQuestionTypeLocalList();
    }

    public List<Clue_Type> getClue_typeList() {
        return clue_type_repository.getAllClueTypeLocalList();
    }

    public LiveData<List<Subject>> getLiveSubjectList() {
        return subject_repository.getLiveSubjectLocalList();
    }
}
