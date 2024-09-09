package com.example.messengerapp.presentation.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.messengerapp.presentation.component.OtpTextField
import com.example.messengerapp.presentation.navigation.Screens
import com.example.messengerapp.presentation.viewmodel.AuthViewModel
import com.example.messengerapp.util.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun OtpScreen(
    authViewModel: AuthViewModel,
    navController: NavController
) {

    var otp by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    val uid = authViewModel.uid.collectAsState().value

    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Enter the verification code sent to your phone")

        OtpTextField(
            otp = otp,
            onOtpChange = {
                otp = it
            },
            modifier = Modifier.padding(32.dp)
        )

        Button(
            onClick = {
                scope.launch(Dispatchers.IO) {
                    authViewModel.signInWithCredential(
                        otp
                    ).collect {
                        when(it) {
                            is ResultState.Success -> {
                                if(authViewModel.userExists.value == true) {
                                    withContext(Dispatchers.Main) {
                                        navController.navigate(Screens.ProfileScreen.route)
                                    }
                                } else {
                                    withContext(Dispatchers.Main) {
                                        navController.navigate(Screens.RegistrationScreen.route)
                                    }
                                }
                            }
                            is ResultState.Loading -> {
                                if(authViewModel.userExists.value != false) {
                                    authViewModel.getCurrentUser(authViewModel.userNumber.value!!)
                                }
                            }
                            is ResultState.Error -> {}
                        }

                    }
                }

            },
            modifier = Modifier.padding(
                top = 14.dp
            )
        ) {
                Text(text = "Confirm")
        }

    }
}







//@Composable
//@Preview
//fun OtpScreenPreview() {
//    OtpScreen()
//}