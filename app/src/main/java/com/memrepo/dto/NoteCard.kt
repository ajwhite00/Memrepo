package com.memrepo.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteCard(@PrimaryKey(autoGenerate = true) val cardID : Int,@ColumnInfo("title") val title : String,@ColumnInfo("snippet") val snippet : String){

        val snippetDisplay = this.snippet //has three rounds of replacement
                .replace("-".toRegex(), " ") //replace hyphens with space because of two cases: split words with hyphen in between and remove hyphen in the middle of a sentence
                .replace("  ".toRegex(), " ") //replace any double spaces caused by hyphen replacement with a single space
                .replace("[!\"#$%&'()*+,-./:;<=>?@\\[\\]^_`{|}~]".toRegex(), "") //remove punctuation and other non-letter chars

        //var snippetDisplayList = snippetDisplay.split(" ").toList() create a list by splitting string on space
        //var snippetDisplayList : List<String> = listOf<String>(this.snippetDisplay.split(" ").toString())

        override fun toString(): String {
                return this.snippetDisplay
        }
}
