package cu.cujae.gilsoft.tykeprof.app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.entity.Question;
import cu.cujae.gilsoft.tykeprof.data.entity.Strategy;
import cu.cujae.gilsoft.tykeprof.data.relation.StrategyWhitQuestions;
import cu.cujae.gilsoft.tykeprof.data.relation.StrategyWhitTopics;
import cu.cujae.gilsoft.tykeprof.repository.Strategy_Repository;

public class StrategyViewModel extends AndroidViewModel {

    private Strategy_Repository strategy_repository;
    private QuestionViewModel questionViewModel;
    private LiveData<List<Question>> arrayQuestionsList;

    public StrategyViewModel(@NonNull Application application) {
        super(application);
        this.questionViewModel = new QuestionViewModel(application);
        this.arrayQuestionsList = questionViewModel.getAllQuestion();
        this.strategy_repository = new Strategy_Repository(application);
    }

    public void deleteStrategyByID(long id) {
        strategy_repository.deleteStrategyByID(id);
    }

    public LiveData<List<Strategy>> getAllStrategies() {
        return strategy_repository.getAllStrategies();
    }

    public StrategyWhitQuestions getStrategyWhitQuestionsByStrategyID(long id){
        return strategy_repository.getStrategyWhitQuestionsByStrategyID(id);
    }

    public StrategyWhitTopics getStrategyWhitTopicsByStrategyID(long id){
        return strategy_repository.getStrategyWhitTopicsByStrategyID(id);
    }

    public long getStrategiesListSize() {
       /* long size;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Call<List<Strategy>> listCallStrategies = strategy_service.getAllStrategiesByWeb("Bearer " + UserHelper.getToken(context));
        try {
            size = listCallStrategies.execute().body().size();
        } catch (IOException e) {
            size = getAllStrategiesLocalList().size();
        }
        return size;*/

        return strategy_repository.getAllStrategiesLocalList().size();
    }

}