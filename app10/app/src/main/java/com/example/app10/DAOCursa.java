package com.example.app10;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DAOCursa{
    @Insert
    public void insert(Cursa cursa);

    @Query("select * from Curse")
    public List<Cursa>getAll();

    @Delete
    public void delete(Cursa cursa);
}
