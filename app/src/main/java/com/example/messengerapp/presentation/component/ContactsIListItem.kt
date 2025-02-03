package com.example.messengerapp.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.messengerapp.R
import com.example.messengerapp.core.theme.AppTheme
import com.example.messengerapp.data.entity.ContactDto


@Composable
fun ContactsListItem(
    contact:ContactDto,
    modifier: Modifier = Modifier
) {

    val contactPhotoPainter = rememberAsyncImagePainter(model = contact.phoneNumber)
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        Image(
            painter = contactPhotoPainter,
            contentDescription = stringResource(R.string.contact_photo),
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape),
            contentScale = ContentScale.FillBounds
        )

        Text(
            text = "${contact.firstName} ${contact.secondName}",
            style = AppTheme.typography.body1,
            modifier = Modifier
        )
    }
}

@Preview
@Composable
fun ContactsListItemPreview() {
    ContactsListItem(
        contact = ContactDto(
            id = "",
            firstName = "Example",
            secondName = "Andreevich",
            phoneNumber = "+79089438573",
            photoUrl = "https://i.ytimg.com/vi/S-HFqJgNtKY/maxresdefault.jpg",
        )
    )
}