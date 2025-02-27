package com.example.messengerapp.presentation.screens.contacts

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.messengerapp.R
import com.example.messengerapp.core.theme.AppTheme


@Composable
fun ContactDropDownMenu(
    expanded: Boolean,
    modifier: Modifier = Modifier,
    offset: DpOffset,
    onDismissRequest: () -> Unit,
    onClickDelete: () -> Unit,
) {

    DropdownMenu(
        offset = offset,
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        containerColor = AppTheme.colors.backgroundSecondary
    ) {
        DropdownMenuItem(
            text = {
                Text(
                    text = "Delete", color = AppTheme.colors.textPrimary
                )
            },
            modifier = modifier,
            leadingIcon = {
                Icon(
                    ImageVector.vectorResource(id = R.drawable.delete),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            },
            onClick = {
                onClickDelete()
            },
        )
    }
}



@Preview
@Composable
fun ContactDropDownPreview() {
    ContactDropDownMenu(
        expanded = true,
        offset = DpOffset(x = 0.dp, y = 0.dp),
        onDismissRequest = {},
        onClickDelete = {}
    )
}