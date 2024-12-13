package com.example.app5;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DAOCarte {

    @Insert
    void insert(Carte carte);
    @Query("select * from Carti")
    List<Carte> getCarti();

    @Delete
    void deleteCarte(Carte carte);

    @Update
    void updateCarte(Carte carte);

}
