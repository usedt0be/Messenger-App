package com.example.messengerapp.presentation.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.messengerapp.R
import com.example.messengerapp.core.theme.AppTheme
import com.example.messengerapp.domain.models.Contact
import com.example.messengerapp.presentation.screens.contacts.ContactDropDownMenu


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContactsListItem(
    contact: Contact,
    modifier: Modifier = Modifier,
    onClickContact: (Contact.Id) -> Unit,
    onClickDelete: (Contact) -> Unit,
) {
    val contactPhotoPainter = rememberAsyncImagePainter(model = contact.photo)

    var longPressOffset by remember { mutableStateOf(Offset.Zero) }
    var expanded by remember { mutableStateOf(false) }


    Row(
        modifier = modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { offset ->
                        longPressOffset = offset
                        expanded = true
                    }
                )
            }
    ) {
        ContactDropDownMenu(
            expanded = expanded,
            onClickDelete = {onClickDelete(contact)},
            onDismissRequest = { expanded = false }
        )
        Image(
            painter = contactPhotoPainter,
            contentDescription = stringResource(R.string.contact_photo),
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape),
            contentScale = ContentScale.FillBounds
        )

        Text(
            text = "${contact.firstName} ${contact.secondName}, ${contact.phoneNumber} ",
            style = AppTheme.typography.caption1,
            color = AppTheme.colors.textPrimary,
            modifier = Modifier.combinedClickable(
                onClick = { onClickContact(contact.id) },
                onLongClick = { }
            )
        )
    }
}

@Preview
@Composable
fun ContactsListItemPreview() {
    ContactsListItem(
        contact = Contact(
            id = Contact.Id(value = "512"),
            firstName = "Example",
            secondName = "Andreevich",
            phoneNumber = "+79089438573",
            photo = "https://i.ytimg.com/vi/S-HFqJgNtKY/maxresdefault.jpg",
        ),
        onClickContact = {},
        onClickDelete = {}
    )
}