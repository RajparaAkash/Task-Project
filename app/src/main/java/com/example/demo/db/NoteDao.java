package com.example.demo.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.demo.Model.Post;

import java.util.List;

@Dao
@SuppressWarnings("ALL")
public interface NoteDao {
    @Query("SELECT * FROM notepad order by note_id asc")
    List<Post> getAll();

    @Query("DELETE FROM notepad")
    public void nukeTable();

    @Insert
    void insert(Post task);

    @Query("DELETE FROM notepad WHERE note_id = :id")
    void delete(int id);

    @Update
    void update(Post task);

    @Query("UPDATE notepad SET title=:text WHERE note_id = :id")
    void update(String text, int id);

    @Query("SELECT * FROM notepad ORDER BY note_id DESC LIMIT 1")
    Post getLastRecord();

}