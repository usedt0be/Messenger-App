package com.example.messengerapp.presentation.component.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.messengerapp.R
import com.example.messengerapp.core.theme.AppTheme
import com.example.messengerapp.domain.models.Contact


@Composable
fun ChatTitle(
    contact: Contact,
    modifier: Modifier = Modifier,
    onClickBackToChats:() -> Unit
){

    Row(
        modifier = modifier.fillMaxWidth()
            .background(color = AppTheme.colors.backgroundSecondary)
            .heightIn(min = 64.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                onClickBackToChats()
            }
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.backward_ic),
                contentDescription = null,
                modifier = Modifier.padding(start = 8.dp)
                    .size(24.dp),
                tint = AppTheme.colors.onSuccess,
            )
        }

        Image(
            painter = rememberAsyncImagePainter(model = contact.photo),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 24.dp, end = 16.dp)
                .clip(CircleShape)
                .size(48.dp),
            contentScale = ContentScale.FillBounds
        )

        Text(
            text = "${contact.firstName} ${contact.secondName}",
            modifier = Modifier.padding(start = 8.dp),
            style = AppTheme.typography.body1,
            color = AppTheme.colors.textPrimary
        )
    }

}



@Preview
@Composable
fun ChatTitlePreview(){
    ChatTitle(contact = Contact(
        id = Contact.Id(value = "415"),
        firstName = "Legenda",
        secondName = "Alabai",
        photo =  "",
        phoneNumber = "+57262"
    ),
        onClickBackToChats = {}

    )
}