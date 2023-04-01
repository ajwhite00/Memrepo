package com.memrepo.service

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.room.Room
import com.memrepo.dao.INoteCardDAO
import com.memrepo.dao.NoteCardDatabase
import com.memrepo.dto.NoteCard

interface INoteCardService {
    fun getNoteCardDAO() : INoteCardDAO
}

class NoteCardService(val application: Application) : INoteCardService {

    lateinit var db: NoteCardDatabase

    override fun getNoteCardDAO() : INoteCardDAO {
        if (!this::db.isInitialized) {
            db = Room.databaseBuilder(application, NoteCardDatabase::class.java, "Memrepo").build()
        }
        return db.noteCardDAO()
    }

    suspend fun saveNoteCard(noteCard: NoteCard) {
        try {
            noteCard.let {
                val noteCardDao = getNoteCardDAO()
                noteCardDao.saveNoteCard(noteCard = noteCard)
            }
            Log.d(TAG, "Successfully saved note card!")
        } catch (e: Exception) {
            Log.e(TAG, "error saving note card ${(e.message)}")
        }
    }

    suspend fun updateNoteCard(noteCard: NoteCard) {
        try {
            noteCard.let {
                val noteCardDao = getNoteCardDAO()
                noteCardDao.updateNoteCard(noteCard = noteCard)
            }
            Log.d(TAG, "Successfully updated note card!")
        } catch (e: Exception) {
            Log.e(TAG, "error saving note card ${(e.message)}")
        }
    }

    suspend fun deleteNoteCard(noteCard: NoteCard) {
        try {
            noteCard.let {
                val noteCardDao = getNoteCardDAO()
                noteCardDao.deleteNoteCard(noteCard = noteCard)
            }
            Log.d(TAG, "Successfully deleted note card!")
        } catch (e: Exception) {
            Log.e(TAG, "error saving note card ${(e.message)}")
        }
    }
}