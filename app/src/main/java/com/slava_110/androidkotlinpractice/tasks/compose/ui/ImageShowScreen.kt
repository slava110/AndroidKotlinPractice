package com.slava_110.androidkotlinpractice.tasks.compose.ui

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toFile
import com.slava_110.androidkotlinpractice.tasks.imageshow.ui.ImageShowState
import com.slava_110.androidkotlinpractice.tasks.imageshow.ui.ImageShowViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ComposeImageShow(
    modifier: Modifier = Modifier,
    viewModel: ImageShowViewModel = koinViewModel<ImageShowViewModel>()
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(5.dp)
    ) {
        ImageShowElements(
            viewModel = viewModel,
            modifier = Modifier
                .align(Alignment.TopCenter)
        )

        Divider(
            color = Color.Red,
            thickness = 3.dp,
            modifier = Modifier
                .align(Alignment.Center)
        )

        LazyColumnJustBecauseNeededForSomeReason(
            modifier = Modifier
                .align(Alignment.BottomCenter)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ImageShowElements(
    viewModel: ImageShowViewModel,
    modifier: Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(
            space = 5.dp,
            alignment = Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val imageState = viewModel.imageState.collectAsState(initial = ImageShowState.Default).value

        val imagePainter = when(imageState) {
            is ImageShowState.Complete -> {
                val imageFile = Uri.parse(imageState.outputImageUri).toFile()
                val imageBitmap = BitmapFactory.decodeFile(imageFile.absolutePath)
                remember { BitmapPainter(imageBitmap.asImageBitmap()) }
            }
            ImageShowState.Default -> rememberVectorPainter(image = Icons.Filled.Person)
            is ImageShowState.Errored -> rememberVectorPainter(image = Icons.Filled.Close)
            is ImageShowState.Loading -> rememberVectorPainter(image = Icons.Filled.ArrowDropDown)
        }

        Image(
            painter = imagePainter,
            contentDescription = "Image",
            modifier = Modifier
                .defaultMinSize(
                    minWidth = 100.dp,
                    minHeight = 100.dp
                )
        )

        var text by rememberSaveable { mutableStateOf("https://kotlinlang.org/docs/images/kotlin-logo.png") }

        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
            },
            label = {
                Text(
                    text = "Url",
                    fontSize = 20.sp
                )
            },
            singleLine = true,
        )

        Button(
            onClick = {
                viewModel.downloadImage(text)
            }
        ) {
            Text(
                text = "Set",
                fontSize = 20.sp
            )
        }
    }
}

@Composable
private fun LazyColumnJustBecauseNeededForSomeReason(
    modifier: Modifier
) {
    LazyColumn(
        modifier = modifier
            .border(1.dp, Color.Blue)
    ) {
        items(listOf(
            "first",
            "second",
            "third",
            "fourth",
            "fifth"
        )) { line ->
            Card {
                Text(line)
            }
        }
    }
}