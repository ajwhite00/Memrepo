package com.memrepo.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteCard(@PrimaryKey(autoGenerate = true) val cardID : Int,@ColumnInfo("title") val title : String,@ColumnInfo("snippet") val snippet : String){

        var snippetDisplay = this.snippet
                .replace("-".toRegex(), " ")
                .replace("  ".toRegex(), " ")
                .replace("[!\"#$%&'()*+,-./:;<=>?@\\[\\]^_`{|}~]".toRegex(), "")

        override fun toString(): String {
                return snippetDisplay
        }
}
