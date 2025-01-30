package com.example.messengerapp.presentation.component

import android.util.Log
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.messengerapp.R
import com.example.messengerapp.core.theme.AppTheme


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchTextField(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val isImeVisible = WindowInsets.isImeVisible
    val focusManager = LocalFocusManager.current

    LaunchedEffect(isImeVisible) {
        if (!isImeVisible) {
            focusManager.clearFocus()
        }
    }

    OutlinedTextField(
        value = query,
        onValueChange = { changedQuery ->
            Log.d("querySearchTextFieldChanged", changedQuery)
            onQueryChange(changedQuery)
        },
        textStyle = TextStyle(
            color = AppTheme.colors.textPrimary
        ),
        modifier = modifier.fillMaxWidth(),
        label = {
            Text(
                text = "Search by country name",
                style = AppTheme.typography.caption1,
                color = AppTheme.colors.textTertiary
            )
        },
        leadingIcon = {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.search_gray),
                contentDescription = null,
                tint = AppTheme.colors.textSecondary
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text
        ),
        keyboardActions = KeyboardActions(onDone = {
            focusManager.clearFocus()
        }),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = AppTheme.colors.backgroundSecondary,
            focusedIndicatorColor = AppTheme.colors.onSuccess,
            focusedTextColor = AppTheme.colors.textSecondary,
            cursorColor = AppTheme.colors.onSuccess,
            unfocusedTextColor = AppTheme.colors.textSecondary,
            unfocusedContainerColor = AppTheme.colors.backgroundSecondary
        ),

    )
}


@Preview
@Composable
fun SearchTextFieldPreview() {
    SearchTextField(
        query = "",
        onQueryChange = {},

    )
}