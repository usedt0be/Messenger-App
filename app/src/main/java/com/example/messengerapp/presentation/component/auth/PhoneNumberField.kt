package com.example.messengerapp.presentation.component.auth

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.messengerapp.core.theme.AppTheme


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PhoneNumberField(
    phoneCode: String?,
    number: String,
    onNumberChange:(String) -> Unit,
    modifier:Modifier = Modifier,
) {
    val isImeVisible = WindowInsets.isImeVisible
    val focusManager = LocalFocusManager.current

    LaunchedEffect(isImeVisible) {
        if(!isImeVisible) {
            focusManager.clearFocus()
        }
    }

    OutlinedTextField(
        value = number,
        onValueChange = { changedNumber ->
            onNumberChange(changedNumber)
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 12.dp, top = 8.dp, end = 12.dp),
        textStyle = AppTheme.typography.body1,
        leadingIcon = {
            Row(modifier = Modifier
                .padding(paddingValues = TextFieldDefaults.contentPaddingWithLabel())
            ){
                Text(
                    text = phoneCode ?: "+",
                    modifier = Modifier.fillMaxWidth(0.16f),
                    style = AppTheme.typography.body1,
                )

                VerticalDivider(modifier = Modifier.heightIn(max = 20.dp))
            }
        },
        singleLine = true,
        label = {
            Text(
                text = "Phone number",
                style = AppTheme.typography.caption1,
                color = AppTheme.colors.textTertiary
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            }
        ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = AppTheme.colors.backgroundSecondary,
            focusedIndicatorColor = AppTheme.colors.onSuccess,
            focusedTextColor = AppTheme.colors.textPrimary,
            focusedLeadingIconColor = AppTheme.colors.textPrimary,
            cursorColor = AppTheme.colors.onSuccess,
            unfocusedTextColor = AppTheme.colors.textPrimary,
            unfocusedContainerColor = AppTheme.colors.backgroundSecondary,
            unfocusedLeadingIconColor = AppTheme.colors.textPrimary
        )
    )
}

@Preview
@Composable
fun PhoneNumberFieldPreview(){
    PhoneNumberField(
        phoneCode = "+7",
//        onPhoneCodeChange = {},
        onNumberChange = {},
        number = "839109",
    )
}
