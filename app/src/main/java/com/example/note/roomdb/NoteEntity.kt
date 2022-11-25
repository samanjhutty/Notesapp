package com.example.note.roomdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class NoteEntity {
    @PrimaryKey
        (autoGenerate = true)
    var id1: Int = 0

    @ColumnInfo(name = "title")
     var title:String?=null

    @ColumnInfo(name = "content")
     var content:String?=null

    @ColumnInfo(name = "date")
     var date:String?=null

    @ColumnInfo(name = "color")
    var color:String?=null

    @ColumnInfo(name = "laycolor")
    var laycolor:String?=null

    @ColumnInfo(name = "url")
    var url:String?=null

    @ColumnInfo(name = "textcolor")
    var textcolor:String?=null

}
