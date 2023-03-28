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
data class NoteCard(@PrimaryKey(autoGenerate = true) val cardID : Int,@ColumnInfo("title") val title : String,@ColumnInfo("snippet") val snippet : String){

        fun createSnippetDisplayList(): MutableList<String> {
                return this.snippet
                        .replace("-".toRegex(), " ")
                        .replace("  ".toRegex(), " ")
                        .replace("[!\"#$%&'()*+,-./:;<=>?@\\[\\]^_`{|}~]".toRegex(), "")
                        .lowercase()
                        .split(" ")
                        .toMutableList()
        }
}