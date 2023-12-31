package com.example.demo.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.demo.Model.Post;

@Database(entities = Post.class, exportSchema = false, version = 1)
 public abstract class DatabaseHelper extends RoomDatabase {

    public static final String DB_NAME = "notpaddb";
    public static DatabaseHelper instance;

    public abstract NoteDao noteDao();

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, DatabaseHelper.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}