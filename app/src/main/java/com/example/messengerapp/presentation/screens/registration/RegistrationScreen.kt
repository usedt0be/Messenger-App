package com.example.messengerapp.presentation.screens.registration

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.messengerapp.R
import com.example.messengerapp.data.entity.UserEntity
import com.example.messengerapp.presentation.navigation.Screens
import com.example.messengerapp.presentation.viewmodel.AuthViewModel
import com.example.messengerapp.util.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun RegistrationScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
) {

    val snackBarHostState by remember{ mutableStateOf(SnackbarHostState()) }

    var uid by remember { mutableStateOf<String?>(null) }

    var firstName by remember { mutableStateOf("") }

    var secondName by remember { mutableStateOf("") }

    var imageUri by rememberSaveable { mutableStateOf<Uri?>(null) }

    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF7F779E),
            MaterialTheme.colorScheme.primary,
        ),
    )

    LaunchedEffect(key1 = Unit) {
        authViewModel.getCurrentUid()
        uid = authViewModel.uid.value
    }


    Log.d("user_image_uri", "$imageUri")

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
            model = imageUri?: "default_profile_image_$uid"
        )
    }

    val scope = rememberCoroutineScope()



    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState) {snackBarData ->
                Snackbar(snackbarData = snackBarData)
            }
        }
    ) {
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
                value = secondName ,
                onValueChange = {
                    secondName = it
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
                    scope.launch(Dispatchers.IO) {
                        authViewModel.uploadImage(imageUri!!,uid!!).collect{ uploadImageResult ->
                            when(uploadImageResult) {
                                is ResultState.Success -> {
                                    authViewModel.insertUser(user = UserEntity(
                                        userId = uid,
                                        phoneNumber = authViewModel.userNumber.value!!,
                                        imageUrl = uploadImageResult.data.toString(),
                                        firstName = firstName,
                                        secondName = secondName
                                    )).collect { insertUserResult ->
                                        when(insertUserResult) {
                                            is ResultState.Success -> {
                                                withContext(Dispatchers.Main) {
                                                    snackBarHostState.showSnackbar(
                                                        message = "registration successful",
                                                        duration = SnackbarDuration.Short
                                                    )
                                                    navController.navigate(Screens.ProfileScreen.route)
                                                }
                                            }
                                            is ResultState.Loading -> {
                                            }

                                            is ResultState.Error -> {
                                            }
                                        }
                                    }
                                }

                                is ResultState.Error -> {

                                }
                                is ResultState.Loading-> {

                                }
                            }
                        }


                    }
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


//@Preview
//@Composable
//fun RegistrationScreenPreview() {
//    RegistrationScreen()
//}