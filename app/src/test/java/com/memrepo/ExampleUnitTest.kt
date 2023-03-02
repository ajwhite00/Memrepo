package com.memrepo

import com.memrepo.dto.NoteCard
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun `Given a NoteCard about the order of the planets, When I toString the object, the ID, title, Then snippet attributes should be returned`(){
        val planets = NoteCard(0, "Planets", "Mercury, Venus, Earth, Mars, Jupiter, Saturn, Uranus, Neptune")
        assertEquals("NoteCard(cardID=0, title=Planets, snippet=Mercury, Venus, Earth, Mars, Jupiter, Saturn, Uranus, Neptune)", planets.toString())
    }

    @Test
    fun `Given an object with cardId, 0, title, Planet, and snippet equal to the order of the planets, When I use createSnippetDisplayList() to create a list of the snippet words, Then the list should populate each index with its own word, no punctuation, in all lowercase`(){
        val planets = NoteCard(0, "Planets", "Mercury, Venus, Earth, Mars, Jupiter, Saturn, Uranus, Neptune")
        val planetList = planets.createSnippetDisplayList()

        assertEquals("[mercury, venus, earth, mars, jupiter, saturn, uranus, neptune]", planetList.toString())
    }

    @Test
    fun `Given a NoteCard with cardID, 0, the title, I Have A Dream Speech, and a snippet, 2 sentences from MLK's speech, When When I use createSnippetDisplayList() to create a list of the snippet words, Then the list should populate each index with its own word, no punctuation, in all lowercase`(){
        val speech = NoteCard(0, "I Have A Dream Speech",
            "I have a dream that one day this nation will rise up and live out the true meaning of its creed. " +
                    "We hold these truths to be self evident that all men are created equal")
        val speechWordList = speech.createSnippetDisplayList()

        assertEquals("[i, have, a, dream, that, one, day, this, nation, will, rise, up, and, live, out, the, true, meaning, of, its, creed, " +
                "we, hold, these, truths, to, be, self, evident, that, all, men, are, created, equal]", speechWordList.toString())
    }
}
