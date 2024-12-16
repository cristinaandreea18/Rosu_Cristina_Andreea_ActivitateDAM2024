package com.example.app9;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DAOBook {
    @Insert
    public void insert(Book book);

    @Query("select * from Books")
    public List<Book>selectAll();

}
