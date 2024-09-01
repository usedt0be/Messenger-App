package com.example.messengerapp.presentation.auth

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.messengerapp.R
import com.example.messengerapp.presentation.component.SnackBar
import com.example.messengerapp.presentation.navigation.Screens
import com.example.messengerapp.presentation.viewmodel.AuthViewModel
import com.example.messengerapp.util.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun AuthScreen(
    activity: Activity,
    authViewModel: AuthViewModel,
    navController: NavController,
    ) {

    val defaultNumberLength by remember { mutableIntStateOf(11) }

    var number by remember { mutableStateOf("") }

    var showDialog by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    var isLoading by remember {mutableStateOf(false)}

    val snackBarHostState by remember { mutableStateOf(SnackbarHostState()) }

    if(showDialog) {
        ConfirmNumberDialog(
            number = number,
            onDismiss = {
                showDialog = false
            },
            onConfirm = {
                if(number.length < defaultNumberLength) {
                    scope.launch {
                        snackBarHostState.showSnackbar(
                            message = "Please enter a valid number",
                            duration = SnackbarDuration.Short
                        )
                    }
                } else {
                    scope.launch(Dispatchers.IO) {
                        authViewModel.signUpUserWithPhoneNumber(
                            number,
                            activity
                        ).collect {
                            when (it) {
                                is ResultState.Success -> {
                                    withContext(Dispatchers.Main) {
                                        Toast.makeText(activity, "${it.data}", Toast.LENGTH_SHORT)
                                            .show()
                                        showDialog = false
                                        isLoading = false
                                        navController.navigate(Screens.OtpScreen.route)
                                    }
                                }

                                is ResultState.Loading -> {
                                    showDialog = true
                                    isLoading = true
                                }

                                is ResultState.Error -> {
                                    showDialog = false
                                    isLoading = false
                                }
                            }
                        }
                    }
                }
            }
        )
    }



    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState) { snackBarData ->
                SnackBar(message = snackBarData.visuals.message)
            }
        }
    ) {
        paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = paddingValues)
            .then(
                if (showDialog) {
                    Modifier.blur(radius = 10.dp)
                } else {
                    Modifier
                }
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(text = "Enter your phone number")

            OutlinedTextField(
                value = number ,
                onValueChange = {
                    number = it
                },
                label = {
                    Text(
                        "Phone number"
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
            )


            FloatingActionButton(
                onClick = {
                    showDialog = true
                },
                shape = CircleShape,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 24.dp)
                    .graphicsLayer {
                        if (showDialog) {
                            // Keep the button visible and not affected by the blur
                            alpha = 1f
                        }
                    }

            ) {
                Icon(
                    painter = painterResource(id = R.drawable.forward_ic),
                    contentDescription = null
                )
            }

        }

    }


}

//@Composable
//@Preview
//fun SignUpScreenPreview() {
//    SignUpScreen()
//}