package com.example.messengerapp.presentation.screens.messenger

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
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
    navController: NavController
) {

    val user = authViewModel.currentUser.collectAsState().value
    Log.d("userUi", "$user")

    val painter = rememberAsyncImagePainter(model = user?.imageUrl)

    var showLogOutDialog by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        bottomBar = { NavBottomBar(navController = navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
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

                Text("${user?.firstName}")
            }

            Column(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth()
            ) {


                Text(
                    text = "Account",
                    modifier = Modifier.padding(top = 6.dp, start = 12.dp)
                )

                AccountInfoItem(
                    modifier = Modifier.padding(top = 10.dp, start = 12.dp),
                    onClick = {},
                    info = user?.phoneNumber ?: "null",
                    description = "Press to change phone number"
                )

                HorizontalDivider(modifier = Modifier.padding(top = 2.dp, start = 12.dp))

                AccountInfoItem(
                    modifier = Modifier.padding(top = 2.dp, start = 12.dp),
                    onClick = {},
                    info = "Bio",
                    description = "Write a little about yourself"
                )

                HorizontalDivider(modifier = Modifier.padding(top = 2.dp, start = 12.dp))


                Button(
                    onClick = { showLogOutDialog = true },
                    modifier = Modifier.fillMaxWidth(0.8f)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = "Log out",
                        modifier = Modifier,
                        color = Color.White
                    )
                }
            }
        }
    }

    if (showLogOutDialog) {
        LogOutDialog(
            onDismiss = {
                showLogOutDialog = false
                navController.popBackStack()
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
//    ProfileScreen(currentUser = UserEntity(
//        userId = "11124124",
//        phoneNumber = "89545788315",
//        firstName = "Alex",
//        secondName = "Value",
//        imageUrl = "https://f4.bcbits.com/img/a2718942742_10.jpg"
//    ))
//}