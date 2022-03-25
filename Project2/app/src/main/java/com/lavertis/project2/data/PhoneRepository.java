package com.lavertis.project2.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class PhoneRepository {
    private final PhoneDao phoneDao;
    private final LiveData<List<Phone>> allPhones;

    public PhoneRepository(Application application) {
        PhoneDatabase phoneDatabase = PhoneDatabase.getInstance(application);
        phoneDao = phoneDatabase.phoneDao();
        allPhones = phoneDao.getAll();
    }

    public void addPhone(Phone phone) {
        PhoneDatabase.databaseWriteExecutor.execute(() -> phoneDao.insert(phone));
    }

    public void addAllPhones(List<Phone> phoneList) {
        PhoneDatabase.databaseWriteExecutor.execute(() -> phoneDao.insertAll(phoneList));
    }

    public LiveData<List<Phone>> getAllPhones() {
        return allPhones;
    }

    public void deleteAll() {
        PhoneDatabase.databaseWriteExecutor.execute(phoneDao::truncate);
    }
}
