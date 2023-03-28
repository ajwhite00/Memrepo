package com.memrepo.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.memrepo.dto.NoteCard
import com.memrepo.ui.theme.MemrepoTheme
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.exp
import androidx.compose.material3.DropdownMenuItem
class MainActivity : ComponentActivity() {

    private val viewModel : MainViewModel by viewModel()

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            setContent {

                val noteCards by viewModel.noteCards.observeAsState(initial = emptyList())

                MemrepoTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        MainScreen(noteCards)
                    }
                }
            }  
    }
    @ExperimentalMaterialApi
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    fun MainScreen(noteCards: List<NoteCard>) {

        // Bottom Sheet is used to create a 'Modal' but for android apps
        val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
        )
        val coroutineScope = rememberCoroutineScope()

        BottomSheetScaffold(
            scaffoldState = bottomSheetScaffoldState,
            sheetContent = {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    Column {
                        // Contents of the Bottom Sheet come from this component
                        AddSnippet(bottomSheetScaffoldState)


                    }
                }
            }, sheetPeekHeight = 0.dp
        ) {
            Scaffold(
                // Bar across top of screen
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                "MemRepo"
                            )
                        },
                    )
                },
                floatingActionButton = {
                    FloatingActionButton(onClick =
                    {
                        coroutineScope.launch {
                            if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                bottomSheetScaffoldState.bottomSheetState.expand()
                            } else {
                                bottomSheetScaffoldState.bottomSheetState.collapse()
                            }
                        }
                    }) {
                        Text("+")
                    }
                },
                floatingActionButtonPosition = FabPosition.Center,

                // The content in between the top bar and bottom bar
                content = {
                    LazyColumn(
                        Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .padding(vertical = 25.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "My List"
                                )
                            }
                        }
                        items(noteCards){ noteCard -> SnippetCard(noteCard) }
                    }

                }
            )
        }
    }
    @Composable
    fun SnippetCard(noteCard: NoteCard) {
        val mContext = LocalContext.current
        val paddingModifier = Modifier.padding(10.dp)
        var mExpanded by remember { mutableStateOf(false)}
        var openAlert = remember { mutableStateOf(false) }





            if (openAlert.value) {
            // if yes button clicked viewModel.deleteNoteCard(noteCard)
            AlertDialog(
                onDismissRequest = {openAlert.value = false},
                text={Text("Are you sure you want to delete this?")},
                confirmButton = {
                                Button(onClick = {
                                    openAlert.value = false
                                    viewModel.deleteNoteCard(noteCard) }) {
                                    Text("Confirm")
                                }; Button(onClick = {openAlert.value = false}){
                                    Text("No")
                }
                },



                )
        }

        Box(Modifier.fillMaxSize()) {
            Card(
                shape = RoundedCornerShape(20.dp),
                elevation = 10.dp,
                modifier = Modifier
                    .padding(10.dp)
                    .clickable {
                        val intent = Intent(mContext, PracticeActivity::class.java)
                        intent.putExtra("Title", noteCard.title)
                        intent.putExtra("Snippet", noteCard.snippet)
                        mContext.startActivity(Intent(mContext, PracticeActivity::class.java))
                    }
            ) {

                Column(paddingModifier) {
                    // Title and Snippet are placeholders for now, eventually these will be injected values from the database
                        Text(text = noteCard.title, Modifier.fillMaxWidth())
                        Text(text = noteCard.snippet, Modifier.fillMaxWidth())

                }
                // Button will have the options to Edit or delete the note card
               IconButton(
                   onClick = { mExpanded = true },
                   modifier = Modifier
                       .fillMaxWidth()
                       .wrapContentSize(Alignment.TopEnd)
                   ) {
                   Icon(
                       imageVector = Icons.Default.MoreVert,
                       contentDescription = "Open options"
                   )
               }
                       DropdownMenu(expanded = mExpanded, onDismissRequest = {mExpanded = false}, modifier = Modifier.align(Alignment.TopEnd))
                       {
                         DropdownMenuItem(
                              text = { Text("Edit")},
                             onClick = { }

                          )
                           DropdownMenuItem(
                               text = { Text("Delete")},
                               onClick = {
                                   mExpanded = false
                                   openAlert.value = true }
                               )

                       }
               }

               }
            }
    @OptIn(ExperimentalComposeUiApi::class)
    @ExperimentalMaterialApi
    @Composable
    fun AddSnippet(bottomSheetScaffoldState: BottomSheetScaffoldState) {
        var title by remember { mutableStateOf("") }
        var snippetText by remember { mutableStateOf("") }
        val coroutineScope = rememberCoroutineScope()
        val keyBoardController = LocalSoftwareKeyboardController.current
        Box (modifier = Modifier.fillMaxWidth()) {
            // When Exit button is clicked collapse the bottom sheet, hide the key board, and set the text fields to empty
            Button(
                onClick = {
                    coroutineScope.launch { bottomSheetScaffoldState.bottomSheetState.collapse() }
                    keyBoardController?.hide()
                    title = ""
                    snippetText = ""
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
                    value = snippetText,
                    onValueChange = { snippetText = it },
                    label = { Text("Snippet Text") },
                    modifier = Modifier
                        .height(300.dp)
                        .widthIn(max = 300.dp)
                )
            }
        }
        Box ( modifier = Modifier.fillMaxWidth() ){
            // When the Save button is clicked collapse the bottom sheet, hide the keyboard, save the fields to database
            Button (
                // Logic to save snippet
                /* TODO */
                onClick = {
                    if (title.isNotEmpty() && snippetText.isNotEmpty()){
                        viewModel.saveNoteCard(NoteCard(0, title, snippetText))
                    }
                    coroutineScope.launch { bottomSheetScaffoldState.bottomSheetState.collapse() }
                    keyBoardController?.hide()
                    title = ""
                    snippetText = ""
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

            }
        }
    }
}