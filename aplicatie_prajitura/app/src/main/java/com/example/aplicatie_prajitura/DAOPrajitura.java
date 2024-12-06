package com.example.aplicatie_prajitura;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DAOPrajitura {
    @Insert
    void insert(Prajitura prajitura);

    @Query("select * from Prajituri")
    List<Prajitura> getPrajituri();

    @Delete
    void deletePrajitura(Prajitura prajitura);

    @Update
    void updatePrajitura(Prajitura prajitura);
}
