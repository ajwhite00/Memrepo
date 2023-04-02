package com.memrepo.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @constructor creates a dto for what values should be stored in a NoteCard.
 *
 * cardId is a primary key that autogenerates for every newly created NoteCard.
 * title and snippet are both components of a NoteCard that is a stored string for the User.
 */
@Entity(tableName = "NoteCards")
data class NoteCard(
        @PrimaryKey(autoGenerate = true) val cardId: Int,
        @ColumnInfo(name = "title") val title: String,
        @ColumnInfo(name = "snippet") val snippet: String
) {
        fun createSnippetDisplayList(): MutableList<String> {
                return this.snippet
                        .replace("-", " ") //replace hyphens with space because of two cases: split words with hyphen in between and remove hyphen in the middle of a sentence
                        .replace(Regex(" {2,}"), " ") //replace any double spaces caused by hyphen replacement with a single space
                        .replace(Regex("[^a-zA-Z ]"), "") //remove punctuation and other non-letter chars
                        .lowercase() //change everything to lowercase to be used later for mic input comparison
                        .split(" ") //separates words as space
                        .toMutableList() //creates a list individual words to be used later for mic input comparison
        }
}