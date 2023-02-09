package com.memrepo.dto

data class NoteCard(var cardID : Int, var title : String, var snippet : String){

        var hyphenAdjustedSnippet = this.snippet.replace("-".toRegex(), " ")
        var spacingAdjustedSnippet = hyphenAdjustedSnippet.replace("  ".toRegex(), " ")
        var punctuationFreeSnippet = spacingAdjustedSnippet.replace("[!\"#$%&'()*+,-./:;<=>?@\\[\\]^_`{|}~]".toRegex(), "")
        var snippetDisplay = punctuationFreeSnippet
        var snippetDisplayList = snippetDisplay.split(" ").toList()
}
