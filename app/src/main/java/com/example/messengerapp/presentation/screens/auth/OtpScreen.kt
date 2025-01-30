package com.example.messengerapp.presentation.screens.auth

import android.app.Activity
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.messengerapp.core.theme.AppTheme
import com.example.messengerapp.data.entity.AuthData
import com.example.messengerapp.data.entity.UserEntity
import com.example.messengerapp.domain.AuthRepository
import com.example.messengerapp.domain.RegistrationRepository
import com.example.messengerapp.presentation.component.OtpTextField
import com.example.messengerapp.presentation.navigation.Screens
import com.example.messengerapp.presentation.viewmodel.AuthViewModel
import com.example.messengerapp.util.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun OtpScreen(
    authViewModel: AuthViewModel,
    navController: NavController = rememberNavController()
) {

    var otp by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .background(color = AppTheme.colors.backgroundPrimary)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Enter the verification code sent to your phone",
            style = AppTheme.typography.bodyMedium,
            color = AppTheme.colors.textPrimary
        )

        OtpTextField(
            otp = otp,
            onOtpChange = { otpChanged ->
                otp = otpChanged
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
                            is ResultState.Loading -> {
                                if(authViewModel.userExists.value == true) {
                                    authViewModel.getCurrentUser(authViewModel.userNumber.value!!)
                                }
                            }
                            is ResultState.Success -> {
                                if(authViewModel.userExists.value == true) {
                                    withContext(Dispatchers.Main) {
                                        navController.navigate(Screens.BottomBarScreens.ProfileScreen.route)
                                    }
                                } else {
                                    withContext(Dispatchers.Main) {
                                        navController.navigate(Screens.RegistrationScreen.route)
                                    }
                                }
                            }
                            is ResultState.Error -> {}
                        }
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(
                    contentColor = AppTheme.colors.textButton,
                    containerColor = AppTheme.colors.accentPrimary
            ),
            modifier = Modifier.padding(top = 14.dp)
        ) {
            Text(
                text = "Confirm",
                style = AppTheme.typography.button,
            )
        }
    }
}







@Composable
@Preview
fun OtpScreenPreview() {
    val authRepository = object : AuthRepository {
        override fun verifyPhoneNumberWithOtp(
            phoneNumber: String,
            activity: Activity
        ): Flow<ResultState<String>> {
            TODO("Not yet implemented")
        }

        override fun signWithCredential(otp: String): Flow<ResultState<String>> {
            TODO("Not yet implemented")
        }

        override fun logOut(): Flow<ResultState<String>> {
            TODO("Not yet implemented")
        }

        override fun checkUserExists(phoneNumber: String): Flow<Boolean> {
            TODO("Not yet implemented")
        }

        override fun getCurrentUser(phoneNumber: String): Flow<ResultState<UserEntity>> {
            TODO("Not yet implemented")
        }

        override suspend fun getAuthData(): AuthData {
            TODO("Not yet implemented")
        }
    }
    val firestoreRepo = object : RegistrationRepository {
        override fun insert(user: UserEntity): Flow<ResultState<String>> {
            TODO("Not yet implemented")
        }

        override fun uploadImage(imageUri: Uri?, userId: String): Flow<ResultState<String>> {
            TODO("Not yet implemented")
        }

    }
    val authViewModel = AuthViewModel(
        authRepository = authRepository,
        registrationRepository = firestoreRepo
    )
    OtpScreen(authViewModel = authViewModel)
}