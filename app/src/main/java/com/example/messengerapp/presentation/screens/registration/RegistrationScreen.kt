package com.example.messengerapp.presentation.screens.registration

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.messengerapp.R


@Composable
fun RegistrationScreen() {
    val snackBarState by remember{ mutableStateOf(SnackbarHostState()) }


    var firstName by remember { mutableStateOf("") }



    var secondName by remember { mutableStateOf("") }

    Scaffold() {
        paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
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
                    .padding(start = 16.dp, end = 16.dp)
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
                    .padding(start = 16.dp, end = 16.dp)
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