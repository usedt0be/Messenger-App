package com.example.messengerapp.presentation.component

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import com.example.messengerapp.R


@Composable
fun SearchTextField(
    query: String,
    onQueryChange: (String) -> Unit,
) {

    val focusManager = LocalFocusManager.current

    TextField(
        value = query,
        onValueChange = { changedQuery ->
            onQueryChange(changedQuery)
        },
        leadingIcon = {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.search_gray),
                contentDescription = null
            )
        },
        placeholder = {
            Text(
                text = "Search by country name"
            )
        }
    )
}