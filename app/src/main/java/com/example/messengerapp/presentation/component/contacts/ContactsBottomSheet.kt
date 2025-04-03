package com.example.messengerapp.presentation.component.contacts

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.messengerapp.core.theme.AppTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactsBottomSheet(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    onCreateContact:(String, String?, String) -> Unit
) {

    val sheetScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
    )


    if (isVisible) {
        ModalBottomSheet(
            onDismissRequest = {
                sheetScope.launch {
                    sheetState.hide()
                }.invokeOnCompletion {
                    onDismissRequest()
                }
            },
            sheetState = sheetState,
            containerColor = AppTheme.colors.secondary,
            modifier = modifier,
            contentWindowInsets = {WindowInsets.ime}
        ) {
            var firstName by remember { mutableStateOf("") }
            var secondName by remember { mutableStateOf("") }
            var phoneNumber by remember { mutableStateOf("+") }
            Log.d("phoneNumber", phoneNumber)


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 8.dp, end = 8.dp)
                    .imePadding()
            ) {
                Text(
                    text = "New contact",
                    style = AppTheme.typography.subtitle,
                    color = AppTheme.colors.textPrimary
                )

                OutlinedTextField(
                    value = firstName ,
                    onValueChange = {
                        firstName = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    label = {
                        Text(
                            text = "First name (required)",
                            style = AppTheme.typography.caption1
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    textStyle = AppTheme.typography.body2,
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = AppTheme.colors.textPrimary,
                        focusedLabelColor = AppTheme.colors.onSuccess,
                        focusedIndicatorColor = AppTheme.colors.onSuccess,
                        focusedContainerColor = Color.Transparent,
                        cursorColor = AppTheme.colors.onSuccess,
                        unfocusedLabelColor = AppTheme.colors.tertiary,
                        unfocusedIndicatorColor = AppTheme.colors.tertiary,
                        unfocusedContainerColor = Color.Transparent,

                    ),
                    shape = RoundedCornerShape(percent = 20)
                )


                OutlinedTextField(
                    value = secondName ,
                    onValueChange = {
                        secondName = it
                    },
                    label = {
                        Text(
                            text = "Second name (optional)",
                            style = AppTheme.typography.caption1
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    colors = TextFieldDefaults.colors(
                        focusedLabelColor = AppTheme.colors.onSuccess,
                        focusedIndicatorColor = AppTheme.colors.onSuccess,
                        focusedContainerColor = Color.Transparent,
                        cursorColor = AppTheme.colors.onSuccess,
                        unfocusedLabelColor = AppTheme.colors.tertiary,
                        unfocusedIndicatorColor = AppTheme.colors.tertiary,
                        unfocusedContainerColor = Color.Transparent,
                    ),
                    shape = RoundedCornerShape(percent = 20)
                )


                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { input ->
                        phoneNumber = input
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    colors = TextFieldDefaults.colors(
                        focusedLabelColor = AppTheme.colors.onSuccess,
                        focusedIndicatorColor = AppTheme.colors.onSuccess,
                        focusedContainerColor = Color.Transparent,
                        cursorColor = AppTheme.colors.onSuccess,
                        unfocusedLabelColor = AppTheme.colors.tertiary,
                        unfocusedIndicatorColor = AppTheme.colors.tertiary,
                        unfocusedContainerColor = Color.Transparent,
                    ),
                    shape = RoundedCornerShape(percent = 20)
                )


                Button(
                    onClick = {
                        onCreateContact(
                            firstName,
                            secondName,
                            phoneNumber
                        )
                    },
                    enabled = firstName != "",
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape( percent = 20),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppTheme.colors.accentPrimary,
                        contentColor = AppTheme.colors.textPrimary,
                        disabledContainerColor = AppTheme.colors.textTertiary,
                        disabledContentColor = AppTheme.colors.textPrimary
                    )
                ) {
                    Text(text = "Create contact")
                }
            }
        }
    }
}

