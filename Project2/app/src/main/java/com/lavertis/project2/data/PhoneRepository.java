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

    public LiveData<List<Phone>> selectAllPhones() {
        return allPhones;
    }

    public void insertPhone(Phone phone) {
        PhoneDatabase.databaseWriteExecutor.execute(() -> phoneDao.insert(phone));
    }

    public void insertAllPhones(List<Phone> phoneList) {
        PhoneDatabase.databaseWriteExecutor.execute(() -> phoneDao.insertAll(phoneList));
    }

    public void updatePhone(Phone phone) {
        PhoneDatabase.databaseWriteExecutor.execute(() -> phoneDao.update(phone));
    }

    public void deletePhone(Phone phone) {
        PhoneDatabase.databaseWriteExecutor.execute(() -> phoneDao.delete(phone));
    }

    public void deleteAllPhones() {
        PhoneDatabase.databaseWriteExecutor.execute(phoneDao::truncate);
    }
}
