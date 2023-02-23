package com.memrepo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.room.Room
import com.memrepo.database.NoteCardDatabase
import com.memrepo.ui.theme.MemrepoTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * Sets up instance of room database.
         * To use functions for front end, please reference functions from DAO.
         *
         * CURRENT DAO FUNCTIONS
         *
         * getAllNoteCards()
         * editNoteCard()
         * insertAll()
         * delete()
         *
         * Example: var cards = db.ICardDAO.getAllNoteCards()
         * This will successfully store all notecards into the variable cards.
         */
        val db = Room.databaseBuilder(
            applicationContext,
            NoteCardDatabase::class.java,
            "NoteCard-database"
        ).build()

        setContent {
            MemrepoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen()
                }

            }
        }
    }
}

@ExperimentalMaterialApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed))
    val coroutineScope = rememberCoroutineScope()
    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Column() {
                    AddSnippet(bottomSheetScaffoldState)
                }
            }
        }, sheetPeekHeight = 0.dp
    ) {

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "MemRepo"
                        )
                    },
                )
            },

            bottomBar = {
                BottomAppBar {
                    Box(Modifier.fillMaxWidth()) {
                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                        bottomSheetScaffoldState.bottomSheetState.expand()
                                    } else {
                                        bottomSheetScaffoldState.bottomSheetState.collapse()
                                    }
                                }
                            },
                            modifier = Modifier
                                .align(Alignment.Center)
                                .fillMaxWidth()
                                .fillMaxHeight()
                        ) {
                            (Text(text = "Add"))
                        }
                    }

                }
            },
            content = {
                MyContent()
            }
        )
    }
}
@Composable
fun MyContent() {
//    var title by rememberSaveable { mutableStateOf("") }
//    var snippet by rememberSaveable { mutableStateOf("") }
    val paddingModifier = Modifier.padding(10.dp)

    Box(Modifier.fillMaxSize()) {
        Card(
            shape = RoundedCornerShape(20.dp),
            elevation = 10.dp,
            modifier = Modifier.padding(10.dp)

        ) {
            Column(paddingModifier) {
                Text(text = "Title", Modifier.fillMaxWidth())
                Text(text = "Snippet", Modifier.fillMaxWidth())
            }
            Button(modifier = Modifier.align(Alignment.TopEnd), onClick = {}){

                Text("...")

            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalMaterialApi
@Composable
fun AddSnippet(bottomSheetScaffoldState: BottomSheetScaffoldState) {
    var title by remember { mutableStateOf("") }
    var text by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val keyBoardController = LocalSoftwareKeyboardController.current
    Box (modifier = Modifier.fillMaxWidth()) {
        Button(
            onClick = {
                coroutineScope.launch { bottomSheetScaffoldState.bottomSheetState.collapse() }
                keyBoardController?.hide()
                title = ""
                text = ""
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 10.dp)
        ) {
            Text("Exit")
        }
    }
    Box (modifier = Modifier.fillMaxWidth()) {
        Column (modifier = Modifier.align(Alignment.Center)){
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .widthIn(max = 300.dp),
                singleLine = true
            )
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Snippet Text") },
                modifier = Modifier
                    .height(300.dp)
                    .widthIn(max = 300.dp)

            )
        }
    }
    Box ( modifier = Modifier.fillMaxWidth() ){
        Button (
            // Logic to save snippet
            /* TODO */
            onClick = {
                coroutineScope.launch { bottomSheetScaffoldState.bottomSheetState.collapse() }
                keyBoardController?.hide()
                      },
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text("Save")
        }
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = false)
@Composable
fun DefaultPreview() {
    MemrepoTheme {
        Column {
            MainScreen()
        }
    }
}