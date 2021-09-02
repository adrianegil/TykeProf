package cu.cujae.gilsoft.tykeprof.app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.entity.Punctuation;
import cu.cujae.gilsoft.tykeprof.repository.Punctuation_Repository;

public class RankingViewModel extends AndroidViewModel {
    private Punctuation_Repository punctuation_repository;
    private LiveData<List<Punctuation>> allPunctuations;

    public RankingViewModel(@NonNull Application application) {
        super(application);
        this.punctuation_repository = new Punctuation_Repository(application);
        this.allPunctuations = punctuation_repository.getAllPunctuations();
    }

    public List<Punctuation> getAllPunctuationsLocalAsc() {
        return punctuation_repository.getPunctuationLocalListAsc();
    }

    public List<Punctuation> getAllPunctuationsLocalDesc() {
        return punctuation_repository.getPunctuationLocalListDesc();
    }

    public LiveData<List<Punctuation>> getAllPunctuations() {
        return allPunctuations;
    }
}