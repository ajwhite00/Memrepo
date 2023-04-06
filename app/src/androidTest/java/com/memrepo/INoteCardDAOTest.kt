package com.memrepo

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.memrepo.dao.INoteCardDAO
import com.memrepo.dao.NoteCardDatabase
import org.junit.After
import org.junit.Before

class INoteCardDAOTest {

    private lateinit var noteCardDatabase: NoteCardDatabase
    private lateinit var iNoteCardDAO: INoteCardDAO

    /**
     * Set up instance of room database to test DAO
     */
    @Before
    fun setUp(){
        noteCardDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NoteCardDatabase::class.java
        ).allowMainThreadQueries().build()
        iNoteCardDAO = noteCardDatabase.noteCardDAO()
    }

    @After
    fun teardown(){
        noteCardDatabase.close()
    }

    //TODO Test DAO crud operations
}