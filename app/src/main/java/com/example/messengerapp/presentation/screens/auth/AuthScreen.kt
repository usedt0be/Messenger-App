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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.messengerapp.R
import com.example.messengerapp.core.theme.AppTheme
import com.example.messengerapp.data.entity.AuthData
import com.example.messengerapp.data.entity.UserEntity
import com.example.messengerapp.domain.AuthRepository
import com.example.messengerapp.domain.RegistrationRepository
import com.example.messengerapp.presentation.component.ConfirmNumberDialog
import com.example.messengerapp.presentation.component.CountryCodePicker
import com.example.messengerapp.presentation.component.CountryField
import com.example.messengerapp.presentation.component.PhoneNumberField
import com.example.messengerapp.presentation.component.SnackBar
import com.example.messengerapp.presentation.navigation.Screens
import com.example.messengerapp.presentation.viewmodel.AuthViewModel
import com.example.messengerapp.util.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    authViewModel: AuthViewModel,
    navController: NavController = rememberNavController(),
) {
    val activity = LocalContext.current as? Activity

    val defaultNumberLength by remember { mutableIntStateOf(12) }
    var number by remember { mutableStateOf("") }

    var showDialog by remember { mutableStateOf(false) }
    val snackBarHostState by remember { mutableStateOf(SnackbarHostState()) }
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showBottomSheet by remember { mutableStateOf(false) }

    val currentCountryData = authViewModel.currentCountry.collectAsState().value

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
                modifier = Modifier.fillMaxSize(0.30f)
            )
            Text(
                text = "Phone number",
                modifier = Modifier.padding(bottom = 8.dp),
                style = AppTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = AppTheme.colors.textPrimary
            )

            Text(
                text = "Select your country code and \nenter the phone number",
                style = AppTheme.typography.body2,
                color = AppTheme.colors.textSecondary,
                modifier = Modifier
                    .padding(top = 0.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            CountryField(
                countryData = currentCountryData,
                onCountryTextFieldClick = { showBottomSheet = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, top = 16.dp, end = 12.dp)
            )

            PhoneNumberField(
                phoneCode = currentCountryData?.countryPhoneCode,
                number = number,
                onNumberChange = { changedNumber ->
                    number = changedNumber
                },
                modifier = Modifier.padding(top = 16.dp)
            )


            FloatingActionButton(
                onClick = {
                    authViewModel.setUserNumber(number)
                    if (authViewModel.fullPhoneNumber.value?.length == defaultNumberLength) {
                        showDialog = true
                    } else {
                        scope.launch {
                            snackBarHostState.showSnackbar(
                                message = "Enter the correct number",
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                },
                shape = CircleShape,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 24.dp, end = 12.dp),
                containerColor = AppTheme.colors.accentPrimary,
                contentColor = AppTheme.colors.textButton
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.forward_ic), contentDescription = null
                )
            }

        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            }, sheetState = sheetState, containerColor = AppTheme.colors.backgroundSecondary
        ) {
            CountryCodePicker(countryList = authViewModel.countriesDataList.value,
                onQueryChange = authViewModel::findCountryCode,
                onCountryItemClick = { currentCountryData ->
                    authViewModel.setCountry(countryData = currentCountryData)
                    showBottomSheet = false
                })
        }
    }

    if (showDialog) {
        val fullNumber = authViewModel.fullPhoneNumber.collectAsState().value
        ConfirmNumberDialog(number = fullNumber!!, onDismiss = {
            showDialog = false
        }, onConfirm = {
            scope.launch(Dispatchers.IO) {
                authViewModel.signUpUserWithPhoneNumber(
                    fullNumber, activity!!
                ).collect {
                    when (it) {
                        is ResultState.Loading -> {
                            authViewModel.userNumber.value = fullNumber
                            showDialog = true
                        }

                        is ResultState.Success -> {
                            authViewModel.checkUserExists(fullNumber)
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
        })
    }
}

@Composable
@Preview
fun AuthScreenPreview() {
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
    AuthScreen(
//        activity = Activity(),
        authViewModel = authViewModel
    )
}