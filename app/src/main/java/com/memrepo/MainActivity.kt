import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.memrepo.ui.theme.MemrepoTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MemrepoTheme {
                // A surface container using the 'background' color from the theme
                Surface(Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    MainScreen()
                }
            }
        }
    }

    @ExperimentalMaterialApi
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    fun MainScreen() {
        val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
        )
        val coroutineScope = rememberCoroutineScope()

        BottomSheetScaffold(
            scaffoldState = bottomSheetScaffoldState,
            sheetContent = {
                Box(Modifier.fillMaxWidth().fillMaxHeight()) {
                    Column { AddSnippet(bottomSheetScaffoldState) }
                }
            }, sheetPeekHeight = 0.dp
        ) {
            Scaffold(
                topBar = { TopAppBar(title = { Text("MemRepo") }) },
                // Bar across bottom of screen
                bottomBar = {
                    BottomAppBar {
                        Box(Modifier.fillMaxWidth()) {
                            Button(
                                onClick = {
                                    // When the button is clicked the bottom sheet will expand or collapse
                                    coroutineScope.launch {
                                        if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                            bottomSheetScaffoldState.bottomSheetState.expand()
                                        } else {
                                            bottomSheetScaffoldState.bottomSheetState.collapse()
                                        }
                                    }
                                },
                                modifier = Modifier.align(Alignment.Center).fillMaxWidth().fillMaxHeight()
                            ) { Text(text = "Add") }
                        }
                    }
                },
                // The content in between the top bar and bottom bar
                content = { MyContent() }
            )
        }
    }

    @Composable
    fun MyContent() {
        val paddingModifier = Modifier.padding(10.dp)
        Box(Modifier.fillMaxSize()) {
            Card(
                shape = RoundedCornerShape(20.dp),
                elevation = 10.dp,
                modifier = Modifier.padding(10.dp)
            ) {
                Column(paddingModifier) {
                    // Title and Snippet are placeholders for now, eventually these will be injected values from the database
                    Text(text = "Title", Modifier.fillMaxWidth())
                    Text(text = "Snippet", Modifier.fillMaxWidth())
                }
                // Button will have the options to Edit or delete the note card
                Button(modifier = Modifier.align(Alignment.TopEnd), onClick = {}) { Text("...") }
            }
        }
    }

    @OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
    @Composable
    fun AddSnippet(bottomSheetScaffoldState: BottomSheetScaffoldState) {
        var title by remember { mutableStateOf("") }
        var text by remember { mutableStateOf("") }
        val coroutineScope = rememberCoroutineScope()
        val keyBoardController = LocalSoftwareKeyboardController.current

// When Exit button is clicked collapse the bottom sheet, hide the key board, and set the text fields to empty
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Button(onClick = {
                    coroutineScope.launch { bottomSheetScaffoldState.bottomSheetState.collapse() }
                    keyBoardController?.hide()
                    title = ""
                    text = ""
                }) {
                    Text("Exit")
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
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

                // When the Save button is clicked collapse the bottom sheet, hide the keyboard, save the fields to database
                Button(
                    onClick = {
                        // Logic to save snippet
                        /* TODO */
                        coroutineScope.launch { bottomSheetScaffoldState.bottomSheetState.collapse() }
                        keyBoardController?.hide()
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Save")
                }
            }
        }
    }

    @ExperimentalMaterialApi
    @Preview(showBackground = false)
    @Composable
    fun DefaultPreview() {
        MemrepoTheme {
            MainScreen()
        }
    }
}