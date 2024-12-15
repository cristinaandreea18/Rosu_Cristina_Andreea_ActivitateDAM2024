package com.example.app8;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Marker.class},version=1)
public abstract class MarkerDatabase extends RoomDatabase {
    public abstract DAOMarker daoMarker();
}
