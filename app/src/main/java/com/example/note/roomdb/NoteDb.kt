package com.example.note.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NoteEntity::class], version = 32)
abstract class NoteDb :RoomDatabase(){
    abstract fun mynotedao():MyNoteDao

    companion object{
        @Volatile
         var instance:NoteDb?=null

        fun gtBase(context: Context):NoteDb {

            return instance?: synchronized(this){
                val i= Room.databaseBuilder(
                    context.applicationContext,
                    NoteDb::class.java,"noobmoster69").build()

                instance=i
                i
            }
        }
    }
}