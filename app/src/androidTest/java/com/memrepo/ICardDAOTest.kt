package com.memrepo

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.memrepo.dao.ICardDAO
import com.memrepo.dao.NoteCardDatabase
import org.junit.After
import org.junit.Before

class ICardDAOTest {
    private lateinit var noteCardDatabase: NoteCardDatabase
    private lateinit var iCardDAO: ICardDAO

    @Before
    fun setUp(){
        noteCardDatabase = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                NoteCardDatabase::class.java
        ).allowMainThreadQueries().build()
        iCardDAO = noteCardDatabase.noteCardDAO()
    }

    @After
    fun tearDown(){
        noteCardDatabase.close()
    }
}