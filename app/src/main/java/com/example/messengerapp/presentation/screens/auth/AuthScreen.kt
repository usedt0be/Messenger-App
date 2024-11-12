package com.example.messengerapp.presentation.screens.auth

import android.app.Activity
import android.net.Uri
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.messengerapp.R
import com.example.messengerapp.core.theme.AppTheme
import com.example.messengerapp.data.entity.AuthData
import com.example.messengerapp.data.entity.UserEntity
import com.example.messengerapp.domain.AuthRepository
import com.example.messengerapp.domain.FirestoreRepository
import com.example.messengerapp.domain.StorageRepository
import com.example.messengerapp.presentation.component.ConfirmNumberDialog
import com.example.messengerapp.presentation.component.SnackBar
import com.example.messengerapp.presentation.navigation.Screens
import com.example.messengerapp.presentation.viewmodel.AuthViewModel
import com.example.messengerapp.util.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun AuthScreen(
//    activity: Activity,
    authViewModel: AuthViewModel,
    navController: NavController = rememberNavController(),
) {

    val activity = LocalContext.current as? Activity

    val defaultNumberLength by remember { mutableIntStateOf(11) }

    var number by remember { mutableStateOf("+7") }

    var showDialog by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    val snackBarHostState by remember { mutableStateOf(SnackbarHostState()) }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState) { snackBarData ->
                SnackBar(
                    message = snackBarData.visuals.message,
                    onDismiss = { snackBarData.dismiss() }
                )
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues)
                .background(AppTheme.colors.backgroundSecondary)
                .then(
                    if (showDialog) {
                        Modifier.blur(radius = 10.dp)
                    } else {
                        Modifier
                    }
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.messenger_logo),
                contentDescription = null,
                tint = AppTheme.colors.textPrimary,
                modifier = Modifier
                    .fillMaxSize(0.35f)
            )


            Text(
                text = "Enter your phone number",
                style = AppTheme.typography.body2,
                color = AppTheme.colors.textSecondary
            )

            OutlinedTextField(
                value = number,
                onValueChange = { input ->
                    number = input
                },
                textStyle = TextStyle(
                    color = AppTheme.colors.textPrimary
                ),
                label = {
                    Text(
                        text = "Phone number",
                        style = AppTheme.typography.caption1,
                        color = AppTheme.colors.textTertiary
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
//                colors = TextFieldDefaults.colors(
//                    focusedIndicatorColor = AppTheme.colors.onSuccess
//                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, top = 8.dp, end = 12.dp),
                )


            FloatingActionButton(
                onClick = {
                    showDialog = true
                },
                shape = CircleShape,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 24.dp, end = 12.dp),
                containerColor = AppTheme.colors.accentPrimary,
                contentColor = AppTheme.colors.textButton
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.forward_ic),
                    contentDescription = null
                )
            }
        }
    }

    if (showDialog) {
        ConfirmNumberDialog(
            number = number,
            onDismiss = {
                showDialog = false
            },
            onConfirm = {
                if (number.length < defaultNumberLength) {
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
                            activity!!
                        ).collect {
                            when (it) {
                                is ResultState.Loading -> {
                                    authViewModel.userNumber.value = number
                                    showDialog = true
                                }

                                is ResultState.Success -> {
                                    authViewModel.checkUserExists(number)
                                    withContext(Dispatchers.Main) {
                                        showDialog = false
                                        navController.navigate(Screens.OtpScreen.route)
                                    }
                                }

                                is ResultState.Error -> {
                                    showDialog = false
                                }
                            }
                        }
                    }
                }
            }
        )
    }
}

@Composable
@Preview
fun AuthScreenPreview() {
    val authRepository = object : AuthRepository {
        override fun registerUserWithPhoneNumber(
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

        override suspend fun getAuthData(): AuthData {
            TODO("Not yet implemented")
        }
    }
    val storageRepository = object : StorageRepository {
        override fun uploadImage(imageUri: Uri?, userId: String): Flow<ResultState<String>> {
            TODO("Not yet implemented")
        }
    }
    val firestoreRepo = object : FirestoreRepository {
        override fun insert(user: UserEntity): Flow<ResultState<String>> {
            TODO("Not yet implemented")
        }

        override fun getCurrentUser(phoneNumber: String): Flow<ResultState<UserEntity>> {
            TODO("Not yet implemented")
        }

        override fun checkUserExists(phoneNumber: String): Flow<Boolean> {
            TODO("Not yet implemented")
        }

    }
    val authViewModel = AuthViewModel(
        authRepository = authRepository,
        storageRepository = storageRepository,
        firestoreRepository = firestoreRepo
    )
    AuthScreen(
//        activity = Activity(),
        authViewModel = authViewModel
    )
}