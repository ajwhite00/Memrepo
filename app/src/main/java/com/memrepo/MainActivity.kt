package com.memrepo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) { BottomSheet() }
            }
        }
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
fun BottomSheet() {
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
                    .background(Color.Gray)
            ) {
                Column() {
                    AddSnippet(bottomSheetScaffoldState)
                }
            }
        }, sheetPeekHeight = 0.dp
    ) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                coroutineScope.launch {
                    if (bottomSheetScaffoldState.bottomSheetState.isCollapsed){
                        bottomSheetScaffoldState.bottomSheetState.expand()
                    } else {
                        bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                }
            }) {
                Text(text = "Click Me")
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun AddSnippet(bottomSheetScaffoldState: BottomSheetScaffoldState) {
    var title by remember { mutableStateOf("") }
    var text by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    Box (modifier = Modifier.fillMaxWidth()) {
        Button(
            onClick = {
                coroutineScope.launch { bottomSheetScaffoldState.bottomSheetState.collapse() }
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
                modifier = Modifier.padding(bottom = 10.dp).widthIn(max = 300.dp),
                singleLine = true
            )
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Snippet Text") },
                modifier = Modifier.height(300.dp).widthIn(max = 300.dp)

            )
        }
    }
    Box ( modifier = Modifier.fillMaxWidth() ){
        Button (
            // Logic to save snippet
            /* TODO */
            onClick = { coroutineScope.launch { bottomSheetScaffoldState.bottomSheetState.collapse() } },
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text("Save")
        }
    }

}