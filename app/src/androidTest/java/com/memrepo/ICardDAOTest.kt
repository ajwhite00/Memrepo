package com.memrepo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.memrepo.dao.ICardDAO
import com.memrepo.dao.NoteCardDatabase
import com.memrepo.dto.NoteCard
import kotlinx.coroutines.*
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
@SmallTest
class ICardDAOTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

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

    @Test
    fun insertNoteCard() = runBlocking{

        val noteCard = NoteCard(1,"Planet", "Mercury, Venus, Earth, Mars, Jupiter, Saturn, Uranus, Neptune")
        iCardDAO.saveNoteCard(noteCard)

        val result = iCardDAO.getAllNoteCards().getOrAwaitValue()

        Assert.assertEquals(1, result.size)
        Assert.assertEquals("Mercury, Venus, Earth, Mars, Jupiter, Saturn, Uranus, Neptune", result[0].snippet)
    }

    @Test
    fun updateNoteCard() = runBlocking{
        val noteCard = NoteCard(1,"Planet", "Mercury, Venus, Earth, Mars, Jupiter, Saturn, Uranus, Neptune")
        iCardDAO.saveNoteCard(noteCard)

        val updatedNoteCard = NoteCard(1, "Test", "This is a test to see if the NoteCard updates")

        iCardDAO.updateNoteCard(updatedNoteCard)

        val result = iCardDAO.getAllNoteCards().getOrAwaitValue()

        Assert.assertEquals(1, result.size)
        Assert.assertNotEquals(result[0], updatedNoteCard)
    }

    @Test
    fun deleteNoteCard() = runBlocking{
        val noteCard = NoteCard(1,"Planet", "Mercury, Venus, Earth, Mars, Jupiter, Saturn, Uranus, Neptune")
        iCardDAO.delete(noteCard)

        val result = iCardDAO.getAllNoteCards().getOrAwaitValue()

        Assert.assertEquals(0, result.size)
    }

}