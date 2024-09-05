package com.example.messengerapp.presentation.screens.registration

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.messengerapp.R


@Composable
fun RegistrationScreen() {

    val snackBarState by remember{ mutableStateOf(SnackbarHostState()) }

    var firstName by remember { mutableStateOf("") }

    var secondName by remember { mutableStateOf("") }

    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF7F779E),
            MaterialTheme.colorScheme.primary,
        ),
    )

    var imageUri by rememberSaveable {
        mutableStateOf<Uri?>(null)
    }

    Log.d("uri", "$imageUri")

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if(uri != null) {
            imageUri = uri
        }
    }





    val painter = if(imageUri == null) {
        painterResource(id = R.drawable.add_photo_ic)
    } else {
        rememberAsyncImagePainter(
            model = imageUri
        )
    }


    Scaffold() {
        paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(start = 32.dp, end = 32.dp, top = 58.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            IconButton(
                onClick = {
                    galleryLauncher.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                },
                modifier = Modifier
                    .background(
                        brush = gradient,
                        shape = CircleShape
                    )
                    .size(80.dp)
                    .indication(interactionSource = MutableInteractionSource(), indication = null)
            ) {
                Image(
                    painter = painter,
                    contentDescription = null,
                    contentScale = if (imageUri != null) {
                        ContentScale.FillBounds
                    } else {
                        ContentScale.None
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }



            Text(
                text = "Profile info",
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
            )

            OutlinedTextField(
                value = firstName ,
                onValueChange = {
                    firstName = it
                },
                label = {
                    Text(
                        "First name (required)"
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(22.dp))

            OutlinedTextField(
                value = firstName ,
                onValueChange = {
                    firstName = it
                },
                label = {
                    Text(
                        "Second name (optional)"
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )


            FloatingActionButton(
                onClick = {

                },
                shape = CircleShape,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 24.dp)

            ) {
                Icon(
                    painter = painterResource(id = R.drawable.forward_ic),
                    contentDescription = null
                )
            }

        }
    }

}


@Preview
@Composable
fun RegistrationScreenPreview() {
    RegistrationScreen()
}