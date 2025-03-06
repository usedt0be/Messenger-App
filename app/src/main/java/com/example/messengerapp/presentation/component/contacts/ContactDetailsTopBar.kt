package com.example.messengerapp.presentation.component.contacts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.messengerapp.R
import com.example.messengerapp.core.theme.AppTheme


@Composable
fun ContactDetailsTopAppBar(
    modifier: Modifier = Modifier,
    onClickBackward:() ->Unit,
){
    Row(
        modifier = modifier
            .background(color = AppTheme.colors.backgroundPrimary)
    ) {
        IconButton(
            onClick = {
                onClickBackward()
            },
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.backward_ic),
                contentDescription = null,
                tint = AppTheme.colors.onSuccess
            )
        }
    }

}