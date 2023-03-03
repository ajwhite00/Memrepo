package com.memrepo.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteCard(
        @PrimaryKey(autoGenerate = true) val cardID : Int,
        @ColumnInfo("title") val title : String,
        @ColumnInfo("snippet") val snippet : String)
{

        /**
         * Has three rounds of replacement:
         * - Replaces hyphens with spaces because of two cases:
         *      -- Split words with hyphens in between.
         *      -- Remove hyphens in the middle of a sentence.
         * - Replaces any double spaces caused by replacing hyphens with a single space.
         * - Remove any punctuation and other non-letter characters.
         */
        var snippetDisplay = this.snippet
                .replace("-".toRegex(), " ")
                .replace("  ".toRegex(), " ")
                .replace("[!\"#$%&'()*+,-./:;<=>?@\\[\\]^_`{|}~]".toRegex(), "")

        //var snippetDisplayList = snippetDisplay.split(" ").toList() create a list by splitting string on space
        //var snippetDisplayList : List<String> = listOf<String>(this.snippetDisplay.split(" ").toString())

        override fun toString(): String {
                return snippetDisplay
        }
}
