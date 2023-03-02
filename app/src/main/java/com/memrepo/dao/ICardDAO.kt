package com.memrepo.dao

import androidx.room.*
import com.memrepo.dto.NoteCard

@Dao
interface ICardDAO {

    @Query("SELECT * FROM NoteCard")
    fun getAllNoteCards() : List<NoteCard>

    @Insert
    fun saveNoteCard(vararg noteCards: NoteCard)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateNoteCard(vararg noteCards: NoteCard)

    @Delete
    fun delete(noteCards: NoteCard)
}