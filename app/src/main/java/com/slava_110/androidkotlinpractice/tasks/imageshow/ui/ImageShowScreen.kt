package com.slava_110.androidkotlinpractice.tasks.imageshow.ui

import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.net.toFile
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposeImageShow(
    modifier: Modifier = Modifier,
    viewModel: ImageShowViewModel = koinViewModel<ImageShowViewModel>()
) {
    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val (image, textField, butSet, butBack) = createRefs()

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
            modifier = Modifier.constrainAs(image) {
                centerTo(parent)
            }
        )

        var text by rememberSaveable { mutableStateOf("https://kotlinlang.org/docs/images/kotlin-logo.png") }

        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
            },
            label = { Text("Url") },
            singleLine = true,
            modifier = Modifier.constrainAs(textField) {
                top.linkTo(image.bottom)
                bottom.linkTo(parent.bottom)
                centerHorizontallyTo(parent)
            }
        )

        Button(
            onClick = {
                viewModel.downloadImage(text)
            },
            modifier = Modifier.constrainAs(butSet) {
                top.linkTo(textField.bottom)
                bottom.linkTo(parent.bottom)
                centerHorizontallyTo(parent)
            }
        ) {
            Text(text = "Set")
        }

        val ctx = LocalContext.current

        Button(
            onClick = {
                Toast.makeText(ctx, "No returning back in Compose!", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.constrainAs(butBack) {
                top.linkTo(butSet.bottom)
                bottom.linkTo(parent.bottom)
                centerHorizontallyTo(parent)
            }
        ) {
            Text(text = "Back")
        }
    }
}