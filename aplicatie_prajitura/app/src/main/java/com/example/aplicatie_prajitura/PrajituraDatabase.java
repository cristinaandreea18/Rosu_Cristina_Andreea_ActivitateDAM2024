package com.example.aplicatie_prajitura;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Prajitura.class},version=1)
public abstract class PrajituraDatabase extends RoomDatabase {
    public abstract DAOPrajitura daoPrajitura();

}
