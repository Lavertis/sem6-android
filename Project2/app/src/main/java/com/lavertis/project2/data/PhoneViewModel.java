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
        allPhones = phoneRepository.getAllPhones();
    }

    public void addAllPhones(List<Phone> phoneList) {
        phoneRepository.addAllPhones(phoneList);
    }

    public LiveData<List<Phone>> getAllPhones() {
        return allPhones;
    }

    public void deleteAll() {
        phoneRepository.deleteAll();
    }
}
