package com.memrepo.dao

import androidx.room.*
import com.memrepo.dto.NoteCard

/**
 * Interface that implements CRUD functions from the DAO.
 */
@Dao
interface ICardDAO {

    /**
     * getAllNoteCards makes a basic SQLLite statement that grabs all NoteCards from the database.
     */
    @Query("SELECT * FROM NoteCard")
    fun getAllNoteCards() : List<NoteCard>

    /**
     * saveNoteCard inserts a new instance of a NoteCard into the database.
     */
    @Insert
    fun saveNoteCard(vararg noteCards: NoteCard)

    /**
     * updateNoteCard updates the NoteCard by replacing the information into the database.
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateNoteCard(vararg notecards: NoteCard)

    /**
     * delete removes selected NoteCard from the database.
     */
    @Delete
    fun delete(notecards: NoteCard)
}