package com.memrepo.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.memrepo.dto.NoteCard
import kotlinx.coroutines.flow.Flow

/**
 * Interface that implements CRUD functions from the DAO.
 */
@Dao
interface ICardDAO {

    /**
     * getAllNoteCards makes a basic SQLLite statement that grabs all NoteCards from the database.
     */
    @Query("SELECT * FROM note_card_table")
    fun getAllNoteCards() : LiveData<List<NoteCard>>

    /**
     * insertNoteCards inserts all note-card data into the database.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNoteCard(notecards: NoteCard)

    @Update
    fun updateNoteCard(notecards: NoteCard)

    /**
     * delete removes selected NoteCard from the database.
     */
    @Delete
    fun deleteNoteCard(notecards: NoteCard)
}