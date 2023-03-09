package com.memrepo.database

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.memrepo.dao.ICardDAO
import com.memrepo.dto.NoteCard
import kotlinx.coroutines.flow.Flow

class NoteCardRepository(private val noteCardDao: ICardDAO) {

    val allNoteCards: LiveData<List<NoteCard>> = noteCardDao.getAllNoteCards()

    @WorkerThread
    suspend fun insertNoteCard(noteCard: NoteCard)
    {
        noteCardDao.insertNoteCard(noteCard)
    }

    @WorkerThread
    suspend fun updateNoteCard(noteCard: NoteCard)
    {
        noteCardDao.updateNoteCard(noteCard)
    }

    @WorkerThread
    suspend fun deleteNoteCard(noteCard: NoteCard)
    {
        noteCardDao.deleteNoteCard(noteCard)
    }

}