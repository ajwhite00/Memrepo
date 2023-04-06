package com.memrepo

import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.memrepo.dto.NoteCard
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class NoteCardService {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    lateinit var mvm : MainViewModel

    @MockK
    lateinit var mockNoteCardService : com.memrepo.service.NoteCardService

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("Main Thread")

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun initMocksAndMainThread() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `given a view model with live data when populated with NoteCards then results show planet`(){
        givenViewModelIsInitializedWithMockData()
        whenNoteCardServiceFetchNoteCardsIsInvoked()
        theResultsShouldContainPlanets()
    }

    private fun givenViewModelIsInitializedWithMockData() {
        val noteCards = ArrayList<NoteCard>()
        val jupiter = NoteCard(1, "Planet", "Jupiter")
        noteCards.add(jupiter)
        val neptune = NoteCard(2,"Planet", "Neptune")
        noteCards.add(neptune)
        coEvery {mockNoteCardService.getNoteCardDAO().getAllNoteCards().getOrAwaitValue()} returns noteCards

        mvm = MainViewModel(noteCardService = mockNoteCardService)
    }

    private fun whenNoteCardServiceFetchNoteCardsIsInvoked() {
        mvm.noteCards
    }


    private fun theResultsShouldContainPlanets() {
        var allNoteCards : List<NoteCard>? = ArrayList<NoteCard>()
        val latch = CountDownLatch(1)
        val observer = object : Observer<List<NoteCard>>{
            override fun onChanged(receivedNoteCard: List<NoteCard>?) {
                    allNoteCards = receivedNoteCard
                    latch.countDown()
                    mvm.noteCards.removeObserver(this)
            }
        }

        mvm.noteCards.observeForever(observer)
        latch.await(10, TimeUnit.SECONDS)
        assertNotNull(allNoteCards)
        assertTrue(allNoteCards!!.isNotEmpty())
        var containsTwoPlanets = false
        allNoteCards!!.forEach{
            if (it.title.equals("Planet")){
                containsTwoPlanets = true
            }
        }
        assertTrue(containsTwoPlanets)
    }

}