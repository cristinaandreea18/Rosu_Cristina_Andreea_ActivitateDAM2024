package com.example.app8;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DAOMarker {
    @Insert
    public void insert(Marker marker);

    @Query("select * from Markere")
    public List<Marker> selectAll();

    @Update
    public void update(Marker marker);

    @Delete
    public void delete(Marker marker);

}
