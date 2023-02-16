package com.memrepo.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "NoteCard")
data class NoteCard(@PrimaryKey var cardID : Int = 0, @ColumnInfo(name = "title_name") var title : String, @ColumnInfo(name = "snippet_name") var snippet : String){

        var snippetDisplay = this.snippet //has three rounds of replacement
                .replace("-".toRegex(), " ") //replace hyphens with space because of two cases: split words with hyphen in between and remove hyphen in the middle of a sentence
                .replace("  ".toRegex(), " ") //replace any double spaces caused by hyphen replacement with a single space
                .replace("[!\"#$%&'()*+,-./:;<=>?@\\[\\]^_`{|}~]".toRegex(), "") //remove punctuation and other non-letter chars

        var snippetDisplayList = snippetDisplay.split(" ").toList() //create a list by splitting string on space

}
