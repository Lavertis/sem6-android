package com.lavertis.project2.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class PhoneViewModel extends AndroidViewModel {
    private final PhoneRepository phoneRepository;
    private final LiveData<List<Phone>> allPhones;

    public PhoneViewModel(@NonNull Application application) {
        super(application);
        phoneRepository = new PhoneRepository(application);
        allPhones = phoneRepository.selectAllPhones();
    }

    public LiveData<List<Phone>> getAllPhones() {
        return allPhones;
    }

    public void insertPhone(Phone phone) {
        phoneRepository.insertPhone(phone);
    }

    public void insertAllPhones(List<Phone> phoneList) {
        phoneRepository.insertAllPhones(phoneList);
    }

    public void updatePhone(Phone phone) {
        phoneRepository.updatePhone(phone);
    }

    public void deletePhone(Phone phone) {
        phoneRepository.deletePhone(phone);
    }

    public void deleteAllPhones() {
        phoneRepository.deleteAllPhones();
    }
}
