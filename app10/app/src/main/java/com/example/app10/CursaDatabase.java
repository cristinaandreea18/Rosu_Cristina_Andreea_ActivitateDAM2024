package com.example.app10;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Cursa.class},version = 1)
public abstract class CursaDatabase extends RoomDatabase {
    public abstract DAOCursa daoCursa();
}
