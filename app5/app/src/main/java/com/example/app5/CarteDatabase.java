package com.example.app5;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities={Carte.class},version=1)
public abstract class CarteDatabase extends RoomDatabase {
    public abstract DAOCarte daoCarte();
}
