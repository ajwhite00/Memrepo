package com.memrepo

import com.memrepo.database.NoteCard
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun toString_NoteCard(){
        val planets = NoteCard(0, "Planets", "Mercury, Venus, Earth, Mars, Jupiter, Saturn, Uranus, Neptune")
        assertEquals("NoteCard(cardID=0, title=Planets, snippet=Mercury, Venus, Earth, Mars, Jupiter, Saturn, Uranus, Neptune)", planets.toString())
    }

    @Test
    fun stringListFormat_NoteCardSnippetDisplay(){
        val planets = NoteCard(0, "Planets", "Mercury, Venus, Earth, Mars, Jupiter, Saturn, Uranus, Neptune")
        assertEquals("Mercury Venus Earth Mars Jupiter Saturn Uranus Neptune", planets.snippetDisplay)
        assertEquals("[Mercury, Venus, Earth, Mars, Jupiter, Saturn, Uranus, Neptune]", planets.snippetDisplayList.toString())
        assertEquals("Earth", planets.snippetDisplayList[2])
        assertEquals("Saturn", planets.snippetDisplayList[5])
        assertEquals("Neptune", planets.snippetDisplayList[7])
    }

    //may need to move everything to lowercase, string and user input from mic to compare
    @Test
    fun stringSentenceFormat_NoteCardSnippetDisplay(){
        val iHaveADream = NoteCard(0, "I Have A Dream Speech",
            "I have a dream that one day this nation will rise up and live out the true meaning of its creed: " +
                    "We hold these truths to be self-evident, that all men are created equal.")
        assertEquals("I have a dream that one day this nation will rise up and live out the true meaning of its creed " +
                "We hold these truths to be self evident that all men are created equal", iHaveADream.snippetDisplay)
        assertEquals("nation", iHaveADream.snippetDisplayList[8])
        assertEquals("creed", iHaveADream.snippetDisplayList[20])
        assertEquals("truths", iHaveADream.snippetDisplayList[24])
    }

    @Test
    fun threePlusThree_EqualsSix()
    {
        assertEquals(6, 3 + 3)
    }

    @Test
    fun threePlusFour_EqualsSeven()
    {
        assertEquals(7, 3 + 4)
    }
}