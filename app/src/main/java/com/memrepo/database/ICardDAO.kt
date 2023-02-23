package com.memrepo.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ICardDAO {

    @Query("SELECT * FROM NoteCard")
    fun getAllNoteCards() : List<NoteCard>

    @Insert
    fun insertAll(vararg notecards: NoteCard)

    @Delete
    fun delete(notecards: NoteCard)
}