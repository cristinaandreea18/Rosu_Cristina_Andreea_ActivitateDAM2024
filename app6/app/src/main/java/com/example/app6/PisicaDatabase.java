package com.example.app6;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Pisica.class}, version=1)
public abstract class PisicaDatabase extends RoomDatabase {
    public abstract DAOPisica daoPisica();
}
