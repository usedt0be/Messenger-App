package com.example.messengerapp.presentation.screens.contacts

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.messengerapp.R
import com.example.messengerapp.core.theme.AppTheme
import com.example.messengerapp.domain.models.Contact


@Composable
fun ContactsListItem(
    contact: Contact,
    modifier: Modifier = Modifier,
    onClickContact: (Contact.Id) -> Unit,
    onClickDelete: (Contact) -> Unit,
) {
    val contactPhotoPainter = rememberAsyncImagePainter(model = contact.photo)
    var longPressOffset by remember { mutableStateOf(Offset.Zero) }
    val destiny = LocalDensity.current
    var expanded by remember { mutableStateOf(false) }


    Row(modifier = modifier
        .padding(start = 8.dp)
        .fillMaxWidth()
        .heightIn(min = 60.dp, max = 60.dp)
        .background(color = AppTheme.colors.backgroundBottomMenu)
        .pointerInput(Unit) {
            detectTapGestures(
                onLongPress = { offset ->
                    longPressOffset = offset
                    expanded = true
                },
                onTap = {
                    onClickContact(contact.id)
                }
            )
        }
    ) {
        Image(
            painter = contactPhotoPainter,
            contentDescription = stringResource(R.string.contact_photo),
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .align(Alignment.CenterVertically),
            contentScale = ContentScale.FillBounds
        )

        Text(
            text = "${contact.firstName} ${contact.secondName}",
            style = AppTheme.typography.caption1,
            color = AppTheme.colors.textPrimary,
            modifier = Modifier.padding(start = 8.dp, top = 8.dp)
        )

        ContactDropDownMenu(
            onDismissRequest = { expanded = false },
            expanded = expanded,
            modifier = Modifier,
            offset = with(destiny) {
                Log.d("offsetDROP", "${longPressOffset.x.toDp()}, ${longPressOffset.y.toDp()}")
                DpOffset(
                    x = longPressOffset.x.toDp(), y = longPressOffset.y.toDp() - 20.dp
                )
            },
            onClickDelete = {
                onClickDelete(contact)
                expanded = false
            }
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
