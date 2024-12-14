package com.example.app7;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Lumanare.class},version=1)
public abstract class LumanareDatabase extends RoomDatabase {
    public abstract DAOLumanare daoLumanare();
}
