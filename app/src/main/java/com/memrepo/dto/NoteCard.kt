package com.memrepo.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteCard(
    @PrimaryKey(autoGenerate = true) val cardID: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "snippet") val snippet: String
) {
    val snippetDisplay: String
        get() = snippet.replace("-", " ")
                       .replace(Regex("[^a-zA-Z ]"), "")
                       .replace(Regex(" +"), " ")

    //var snippetDisplayList = snippetDisplay.split(" ").toList() create a list by splitting string on space
    //var snippetDisplayList : List<String> = listOf<String>(this.snippetDisplay.split(" ").toString())

    override fun toString() = snippetDisplay
}
