package com.memrepo.ui


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.speech.SpeechRecognizer
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.memrepo.SpeechRecognizerComponent
import com.memrepo.dto.NoteCard
import com.memrepo.ui.theme.MemrepoTheme

class PracticeActivity : ComponentActivity() {

    private val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)

    override fun onDestroy() {
        super.onDestroy()
        speechRecognizer.destroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MemrepoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    PracticeScreen()
                }
            }
        }
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    fun PracticeScreen() {
        val mContext = LocalContext.current
        Scaffold(
            // Bar across top of screen
            topBar = {
                TopAppBar(

                    title = { Text(text = "Practice") },
                    navigationIcon = {
                        IconButton(onClick = {
                            mContext.startActivity(Intent(mContext, MainActivity::class.java))
                        }) {
                            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back")
                        }
                    }
                )
            },
            content = {
                MyContent()

            }
        )
    }

    @Composable
    fun MyContent() {
        val context = LocalContext.current
        val intent = (context as PracticeActivity).intent
        val title = intent.getStringExtra("Title")
        val snippet = intent.getStringExtra("Snippet")

        Box(Modifier.fillMaxSize()) {
            Column(Modifier.padding(10.dp).fillMaxWidth()){
                Text(
                    text = title!!,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontSize = 25.sp
                )
            }

        }

        // You can't create a noteCard without title and snippet so these values won't be null
        SpeechToText(title!!, snippet!!)
    }

    @Composable
    fun SpeechToText(title: String, snippet: String) {

        val context = LocalContext.current
        val noteCard = NoteCard(cardID = 0, title = title, snippet = snippet)

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            SpeechRecognizerComponent(
                context = context,
                activity =this@PracticeActivity,
                noteCard = noteCard,
                speechRecognizer = speechRecognizer
            )
        }

    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        MemrepoTheme {
            PracticeScreen()
        }
    }
}