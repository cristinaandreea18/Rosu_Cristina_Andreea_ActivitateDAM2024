package com.example.app9;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Book.class},version=1)
public abstract class BookDatabase extends RoomDatabase {
    public abstract DAOBook daoBook();
}
