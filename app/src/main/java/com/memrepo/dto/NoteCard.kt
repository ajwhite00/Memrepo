package com.memrepo.dto

import androidx.room.PrimaryKey

data class NoteCard(var cardID : Int, var title : String, var snippet : String,){

        var snippetDisplay = this.snippet //has three rounds of replacement
                .replace("-".toRegex(), " ") //replace hyphens with space because of two cases: split words with hyphen in between and remove hyphen in the middle of a sentence
                .replace("  ".toRegex(), " ") //replace any double spaces caused by hyphen replacement with a single space
                .replace("[!\"#$%&'()*+,-./:;<=>?@\\[\\]^_`{|}~]".toRegex(), "") //remove punctuation and other non-letter chars

        var snippetDisplayList = snippetDisplay.split(" ").toList() //create a list by splitting string on space
}
