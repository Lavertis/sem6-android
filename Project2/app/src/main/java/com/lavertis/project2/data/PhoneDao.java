package com.lavertis.project2.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.Collection;
import java.util.List;

@Dao
public interface PhoneDao {
    @Query("SELECT * FROM phones")
    LiveData<List<Phone>> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Phone phone);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(Collection<Phone> phones);

    @Query("DELETE FROM phones")
    void truncate();
}
