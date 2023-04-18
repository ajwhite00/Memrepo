package com.memrepo

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.memrepo.dto.NoteCard
import java.util.*

@SuppressLint("MutableCollectionMutableState")
@Composable
fun SpeechRecognizerComponent(context: Context, activity: Activity, noteCard: NoteCard, speechRecognizer: SpeechRecognizer) {

    if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.RECORD_AUDIO), 1)
    }

    if(!SpeechRecognizer.isRecognitionAvailable(context)){
        Toast.makeText(context, "Speech recognition is not available", Toast.LENGTH_LONG).show()
    }

    val speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
    speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
    speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
    speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())

    var status by remember { mutableStateOf("") }
    var isListening by remember { mutableStateOf(false) }
    var revealed by remember { mutableStateOf(false) }

    val correctWordList : List<String> = mutableListOf()

    var remainingWords by remember { mutableStateOf(noteCard.createSnippetDisplayList()) }
    var correctWords by remember { mutableStateOf(correctWordList) }
    var incorrectWord by remember { mutableStateOf("") }
    val partialWords by remember { mutableStateOf(mutableListOf<String>()) }
    var blur by remember { mutableStateOf(Color.Gray) }

    /**
     * Callback functions can't change values of correctWords or remainingWords within their scope so this function is declared outside their scope
     */
    fun updateList() {
        Log.d("SpeechRecognizer.updateList()", "Removing '${remainingWords[0]}' from remainingWords")
        correctWords = correctWords + remainingWords.removeFirst()
        Log.d("SpeechRecognizer.updateList()", "Correct words $correctWords")
    }

    /**
     *  Return true or false if word is found in partialWords or all words in partialWords has been iterated through
     */
    fun isWordInPartialList(word: String, i: Int) : Boolean {
        if (partialWords.size - 1 >= i) {
            return word.lowercase() == partialWords[i]
        }
        return false
    }

    var recognizedWords : ArrayList<String>?

    speechRecognizer.setRecognitionListener(object : RecognitionListener {
        override fun onReadyForSpeech(p0: Bundle?) {
            isListening = true
            status = "Ready"
            if(incorrectWord.isNotEmpty()){
                incorrectWord = ""
            }
        }

        override fun onBeginningOfSpeech() {
            status = "Listening..."
        }

        override fun onRmsChanged(p0: Float) {}

        override fun onBufferReceived(p0: ByteArray?) {}

        override fun onEndOfSpeech() {
            Log.d("SpeechRecognizer.onEndOfSpeech()","Correct Words: $correctWords")
            Log.d("SpeechRecognizer.onEndOfSpeech()","Remaining Words: $remainingWords")
        }

        override fun onError(p0: Int) {
            partialWords.clear()
            status = ""
            isListening = false
            var errorMessage : String

            when (p0) {
                SpeechRecognizer.ERROR_AUDIO -> errorMessage = "Audio Recording Error"
                SpeechRecognizer.ERROR_CLIENT -> errorMessage = "Incorrect speech input"
                SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> errorMessage = "Insufficient Permissions"
                SpeechRecognizer.ERROR_LANGUAGE_NOT_SUPPORTED -> errorMessage = "Requested language is not available to be used with the current recognizer."
                SpeechRecognizer.ERROR_LANGUAGE_UNAVAILABLE -> errorMessage = "Requested language is supported, but not available currently"
                SpeechRecognizer.ERROR_NETWORK -> errorMessage = "Network Error"
                SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> errorMessage = "Network operation timed out"
                SpeechRecognizer.ERROR_NO_MATCH -> errorMessage = "No recognition result found"
                SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> errorMessage = "RecognitionService is busy"
                SpeechRecognizer.ERROR_SERVER -> errorMessage = "Server sends error status"
                SpeechRecognizer.ERROR_SERVER_DISCONNECTED -> errorMessage = "Server has been disconnected"
                SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> errorMessage = "No speech input"
                SpeechRecognizer.ERROR_TOO_MANY_REQUESTS -> errorMessage = "Too many request made"
                else -> {
                    errorMessage = "Error"
                }
            }
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            Log.w("SpeechRecognizer.onError()", "Error: $errorMessage")
        }

        override fun onResults(bundle: Bundle?) {
            recognizedWords = bundle!!.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)

            var i = 0
            val resultsAfterPartialClear : MutableList<String> = mutableListOf()

            for (word in recognizedWords!![0].split(" ")) {
                // Check if remainingWords is empty
                if (remainingWords.isEmpty() || word == incorrectWord){
                    break
                }

                // Add word to resultsAfterPartialClear if it is not already in partialWords
                if(!isWordInPartialList(word, i)){
                    resultsAfterPartialClear.add(word.lowercase())
                }

                i++
            }

            if (resultsAfterPartialClear.isNotEmpty()){
                for (word in resultsAfterPartialClear) {
                    if (word == (remainingWords[0])) {
                        if (incorrectWord.isNotEmpty()) {
                            incorrectWord = ""
                        }
                        // Add to correct word list and remove remaining list
                        updateList()

                    } else {
                        // add to incorrect word list and remove from remaining
                        Log.d("SpeechRecognizer.onResults", "Incorrect word: '$word'")
                        incorrectWord = word
                        speechRecognizer.destroy()
                        break
                    }
                }
            }

            Log.d("SpeechRecognizer.onResults()", "Results: ${recognizedWords!![0]}")
            Toast.makeText(context, recognizedWords!![0], Toast.LENGTH_LONG).show()
            partialWords.clear()
            status = ""
            isListening = false
            println("End of Results")
        }

        override fun onPartialResults(partialResults: Bundle?) {
            recognizedWords = partialResults!!.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            Log.d("SpeechRecognizer.onPartialResults()","Detected partial words: ${recognizedWords!![0]}")

            fun checkWord(){
                if (remainingWords.isNotEmpty() && partialWords.last() == (remainingWords[0])) {
                    // Add to correct word list and remove remaining list
                    updateList()
                } else {
                    // add to incorrect word list and remove from remaining
                    incorrectWord = partialWords.last()
                    Log.d("SpeechRecognizer.onPartialResults()", "Incorrect word: '${partialWords.last()}'")
                    partialWords.clear()
                    speechRecognizer.destroy()
                    status = ""
                    isListening = false
                }
            }

            /**
             * Update partialWords to add the last word detected as partial result
             */
            fun addResultToPartialWords(recognizedWords:  ArrayList<String>?) {
                if(recognizedWords!![0].split(" ").last().isNotEmpty()) {
                    partialWords.add(recognizedWords[0].split(" ").last().lowercase())
                    checkWord()
                }
            }

            // partialWords is initially empty we need to add the first partial result or else an exception will be thrown with partialWords.last()
            if(partialWords.isEmpty()){
                addResultToPartialWords(recognizedWords)
            }
            // Add the most recent partial result to partialWords
            else if (recognizedWords!![0].split(" ").last().lowercase() != partialWords.last()){
                addResultToPartialWords(recognizedWords)
            }

            Log.d("SpeechRecognizer.onPartialResults()", "Partial Words: $partialWords")

        }

        override fun onEvent(p0: Int, p1: Bundle?) {}

    })

    Box(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)){
                Text(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 1.dp),
                    text = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = Color.Green, fontSize = 20.sp)) {
                                Log.d("SpeechRecognizer", "Drawing correctWords: $correctWords")
                                if (correctWords.isNotEmpty()) {
                                    append("${correctWords.toString().replace("[,\\[\\]]".toRegex(), "")} ")
                                }
                            }
                            withStyle(style = SpanStyle(color = Color.Red, fontSize = 20.sp)) {
                                if (incorrectWord.isNotEmpty()) {
                                    append("$incorrectWord ")
                                }
                            }
                            remainingWords.forEach { word ->
                                withStyle(
                                    style = SpanStyle(
                                        color = Color.Gray,
                                        background = blur, fontSize = 20.sp
                                    )
                                ) {
                                    append(word)
                                }
                                append("  ")
                            }
                    }
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                shape = CircleShape,
                onClick = {
                    if (!isListening && remainingWords.isNotEmpty()) speechRecognizer.startListening(speechRecognizerIntent)
                },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_microphone_foreground),
                    contentDescription = "Microphone"
                )

            }
            Text(text = status, modifier = Modifier.align(Alignment.CenterHorizontally))
        }
        // Show the remaining text
        Button(modifier = Modifier.align(Alignment.BottomStart).padding(10.dp),shape = CircleShape, onClick = {
            if(!revealed){
                blur = Color.Transparent
                revealed = true
            } else {
                blur = Color.Gray
                revealed = false
            }
        }) {
            if(!revealed){
                Icon(
                    painter = painterResource(id = R.drawable.ic_reveal_foreground),
                    contentDescription = "Reveal"
                )
            }
            else {
                Icon(
                    painter = painterResource(id = R.drawable.ic_visibility_foreground),
                    contentDescription = "Reveal"
                )
            }
        }
        // Rest all of the progress
        Button(modifier = Modifier.align(Alignment.BottomEnd).padding(10.dp),shape = CircleShape,  onClick = {
            incorrectWord = ""
            correctWords = emptyList()
            remainingWords = noteCard.createSnippetDisplayList()
        }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_reset_foreground),
                contentDescription = "Reset"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val words = arrayListOf("This", "is", "a", "test")
    Box(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
        Column( modifier = Modifier.align(Alignment.Center)) {
            Row( modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Green)) {
                            append("This is a test")
                        }
                        withStyle(style = SpanStyle(color = Color.Red)) {
                            append(" This is a test")
                        }
                        words.forEach { word ->
                            append(" ")
                            withStyle(
                                style = SpanStyle(
                                    color = Color.Gray,
                                    background = Color.Gray
                                )
                            ) {
                                append(word)
                            }
                        }

                    }
                )
            }

            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {},
            ){
                Icon(painter = painterResource(id = R.drawable.ic_microphone_foreground), contentDescription = "Microphone")
            }
            Text(text = "Text", modifier = Modifier.align(Alignment.CenterHorizontally))
        }
        Button(modifier = Modifier.align(Alignment.BottomStart).padding(10.dp), onClick = {/*TODO*/ }) {
            Text(text = "Reveal")
        }
        Button(modifier = Modifier.align(Alignment.BottomEnd).padding(10.dp), onClick = { /*TODO*/ }) {
            Text(text = "Reset")
        }
    }

}