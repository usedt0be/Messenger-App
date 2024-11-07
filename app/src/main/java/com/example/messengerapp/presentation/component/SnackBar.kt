package com.example.messengerapp.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.messengerapp.R
import com.example.messengerapp.core.theme.AppTheme


@Composable
fun SnackBar(
    message: String,
    onDismiss:() -> Unit,
    modifier: Modifier = Modifier

) {
    Snackbar(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 40.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        containerColor = AppTheme.colors.onError,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {

            Text(
                text = message,
                style = AppTheme.typography.caption1,
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
            )

            Spacer(modifier = modifier.weight(1f))

            IconButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.close),
                    contentDescription = null
                )
            }
        }
    }
}



@Preview
@Composable
fun UpdateErrorMessagePreview() {
    SnackBar(
        message = "Please enter a valid number",
        onDismiss = {}
    )
}