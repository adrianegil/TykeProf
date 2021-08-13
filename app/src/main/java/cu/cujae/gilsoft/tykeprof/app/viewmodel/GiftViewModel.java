package cu.cujae.gilsoft.tykeprof.app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.entity.Gift;
import cu.cujae.gilsoft.tykeprof.data.model.Gift_Model;
import cu.cujae.gilsoft.tykeprof.repository.Gift_Repository;

public class GiftViewModel extends AndroidViewModel {

    private Gift_Repository gift_repository;
    private final LiveData<List<Gift>> allGift;

    public GiftViewModel(@NonNull @NotNull Application application) {
        super(application);

        this.gift_repository = new Gift_Repository(application);
        this.allGift = gift_repository.getAllGiftWeb();
    }

    public void saveGift(Gift_Model gift_model) {
        gift_repository.saveGift(gift_model);
    }

    public void updateGift(Gift_Model gift_model) {
        gift_repository.updateGiftWeb(gift_model);
    }

    public void deleteGift(long id) {
        gift_repository.deleteGift(id);
    }

    public LiveData<List<Gift>> getAllGift() {
        return allGift;
    }
}