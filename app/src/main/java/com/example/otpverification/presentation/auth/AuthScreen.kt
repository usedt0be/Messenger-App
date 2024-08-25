package com.example.otpverification.presentation.auth

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.otpverification.presentation.navigation.Screens
import com.example.otpverification.presentation.viewmodel.AuthViewModel
import com.example.otpverification.R
import com.example.otpverification.util.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun AuthScreen(
    activity: Activity,
    authViewModel: AuthViewModel,
    navController: NavController
    ) {

    var number by remember { mutableStateOf("") }


    var showDialog by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    var isLoading by remember {mutableStateOf(false)}
    
    if(showDialog) {
        ConfirmNumberDialog(
            number = number,
            onDismiss = {
                showDialog = false
            },
            onConfirm = {
                scope.launch(Dispatchers.IO) {
                    authViewModel.signUpUserWithPhoneNumber(
                        number,
                        activity
                    ).collect{
                        when(it) {
                            is ResultState.Success -> {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(activity, "${it.data}", Toast.LENGTH_SHORT).show()
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
        )
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
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

            ) {
                Icon(
                    painter = painterResource(id = R.drawable.forward_ic),
                    contentDescription = null
                )
        }

    }
}

//@Composable
//@Preview
//fun SignUpScreenPreview() {
//    SignUpScreen()
//}