package com.example.note.roomdb

import androidx.room.*

@Dao
interface MyNoteDao {

    @Insert
    fun saveData(nt:NoteEntity)

    @Query("select * from noteentity")
    fun getData():List<NoteEntity>

    @Delete
    fun delData(del:NoteEntity)

    @Update
    fun updateData(up:NoteEntity)
}