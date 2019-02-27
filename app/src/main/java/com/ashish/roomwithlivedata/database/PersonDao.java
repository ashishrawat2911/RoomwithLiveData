package com.ashish.roomwithlivedata.database;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ashish.roomwithlivedata.model.Person;

import java.util.List;

@Dao
public interface PersonDao {

    @Query("SELECT * FROM PERSON ORDER BY ID")
    LiveData<List<Person>> loadAllPersons();

    @Insert
    void insertPerson(Person person);

    @Update
    void updatePerson(Person person);

    @Delete
    void delete(Person person);

    @Query("SELECT * FROM PERSON WHERE id = :id")
    Person loadPersonById(int id);
}
