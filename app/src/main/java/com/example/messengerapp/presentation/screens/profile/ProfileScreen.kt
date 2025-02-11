package com.example.messengerapp.presentation.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.messengerapp.core.theme.AppTheme
import com.example.messengerapp.presentation.component.AccountInfoItem
import com.example.messengerapp.presentation.component.LogOutDialog
import com.example.messengerapp.presentation.navigation.NavBottomBar
import com.example.messengerapp.presentation.navigation.Screens
import com.example.messengerapp.presentation.viewmodel.AuthViewModel
import com.example.messengerapp.util.ResultState
import kotlinx.coroutines.launch


@Composable
fun ProfileScreen(
    authViewModel: AuthViewModel,
    navController: NavController = rememberNavController()
) {
    val user = authViewModel.usr.collectAsState().value
//    Log.d("userUiusr", "$usr")
    val painter = rememberAsyncImagePainter(model = user?.imageUrl)

    var showLogOutDialog by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        bottomBar = { NavBottomBar(navController = navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .background(color = AppTheme.colors.backgroundPrimary)
                .fillMaxSize()
                .padding(paddingValues),
            ) {

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, end = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.FillBounds
                )

                Spacer(modifier = Modifier.widthIn(20.dp))

                Text(
                    text = "${user?.firstName}",
                    color = AppTheme.colors.textPrimary,
                    style = AppTheme.typography.bodyRegular
                )
            }

            Column(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .background(color = AppTheme.colors.backgroundSecondary)
                    .fillMaxWidth()
            ) {

                Text(
                    text = "Account",
                    style = AppTheme.typography.body1,
                    color = AppTheme.colors.accentPrimary,
                    modifier = Modifier.padding(top = 6.dp, start = 12.dp)
                )

                AccountInfoItem(
                    modifier = Modifier.padding(top = 10.dp, start = 12.dp),
                    onClick = {},
                    info = user?.phoneNumber ?: "null",
                    description = "Press to change phone number"
                )

                HorizontalDivider(modifier = Modifier.padding(start = 12.dp, top = 4.dp, bottom = 4.dp))

                AccountInfoItem(
                    modifier = Modifier.padding(start = 12.dp),
                    onClick = {},
                    info = "Bio",
                    description = "Write a little about yourself"
                )

                HorizontalDivider(modifier = Modifier.padding(start = 12.dp, top = 4.dp, bottom = 4.dp))
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { showLogOutDialog = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppTheme.colors.accentSecondary,
                    contentColor = AppTheme.colors.textButton
                ),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(bottom = 14.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "Log out",
                    modifier = Modifier
                )
            }
        }
    }

    if (showLogOutDialog) {
        LogOutDialog(
            onDismiss = {
                showLogOutDialog = false
            },
            onConfirm = {
                coroutineScope.launch {
                    authViewModel.logOut().collect { logOutResult ->
                        when (logOutResult) {
                            is ResultState.Success -> {
                                navController.navigate(Screens.SignUpScreen.route)
                            }

                            else -> {
                            }
                        }
                    }
                }
            }
        )
    }
}




//@Preview
//@Composable
//fun ProfileScreenPreview() {
//    val authRepository = object : AuthRepository {
//        override fun verifyPhoneNumberWithOtp(
//            phoneNumber: String,
//            activity: Activity
//        ): Flow<ResultState<String>> {
//            TODO("Not yet implemented")
//        }
//
//        override fun signWithCredential(otp: String): Flow<ResultState<String>> {
//            TODO("Not yet implemented")
//        }
//
//        override fun logOut(): Flow<ResultState<String>> {
//            TODO("Not yet implemented")
//        }
//
//        override fun checkUserExists(phoneNumber: String): Flow<Boolean> {
//            TODO("Not yet implemented")
//        }
//
//        override fun getCurrentUser(phoneNumber: String): Flow<ResultState<User>> {
//            TODO("Not yet implemented")
//        }
//    }
//
//    val firestoreRepo = object : RegistrationRepository {
//        override fun insert(user: UserDto): Flow<ResultState<String>> {
//            TODO("Not yet implemented")
//        }
//
//        override fun uploadImage(imageUri: Uri?, userId: String): Flow<ResultState<String>> {
//            TODO("Not yet implemented")
//        }
//
//        override suspend fun getRegistrationAuthData(): AuthData {
//            TODO("Not yet implemented")
//        }
//    }
//    val authViewModel = AuthViewModel(
//        authRepository = authRepository,
//        registrationRepository = firestoreRepo,
//        loginUseCase = LoginUseCase()
//    )
//    val viewModel =
//    ProfileScreen(authViewModel = authViewModel)
//}