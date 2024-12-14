package com.example.app6;

import android.app.UiAutomation;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DAOPisica {
    @Insert
    public void insert(Pisica pisica);

    @Query("select* from Pisici")
    List<Pisica> getPisici();

    @Delete
    public void deletePisica(Pisica pisica);

    @Update
    public void updatePisica(Pisica pisica);
}
