package com.example.app7;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DAOLumanare {
    @Insert
    public void insert(Lumanare l);

    @Query("select * from Lumanari")
    List<Lumanare>selectLumanari();

    @Delete
    public void delete(Lumanare l);

    @Update
    public void update(Lumanare l);
}
