package cu.cujae.gilsoft.tykeprof.app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.entity.Gift_Type;
import cu.cujae.gilsoft.tykeprof.repository.Gift_Type_Repository;

public class GiftTypeViewModel extends AndroidViewModel {

    private Gift_Type_Repository gift_type_repository;
    private final LiveData<List<Gift_Type>> allGiftType;

    public GiftTypeViewModel(@NonNull Application application) {
        super(application);
        this.gift_type_repository = new Gift_Type_Repository(application);
        this.allGiftType = gift_type_repository.getAllGiftTypeWeb();
    }

    public void saveGiftType(Gift_Type gift_type) {
        gift_type_repository.saveGiftType(gift_type);
    }

    public  void deleteGiftType(Gift_Type gift_type){
        gift_type_repository.deleteGiftType(gift_type);
    }

    public LiveData<List<Gift_Type>> getAllGiftType() {
        return allGiftType;
    }
}