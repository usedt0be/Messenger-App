package com.example.messengerapp.presentation.screens.contacts

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.messengerapp.core.theme.AppTheme
import com.example.messengerapp.domain.models.Contact


@Composable
fun ContactDetailsScreen(contact: Contact) {
    val imageRes = rememberAsyncImagePainter(model = contact.photo)
    Scaffold(
    ) { paddingValues ->
        Column (modifier = Modifier.padding(paddingValues)){
            Image(
                painter = imageRes,
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Text(
                text = "${contact.firstName} ${contact.secondName}",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = AppTheme.typography.caption1
                )
        }
    }
}


@Preview
@Composable
fun ContactsScreenPreview() {
    ContactDetailsScreen(
        contact = Contact(
            id = Contact.Id(value = "512"),
            firstName = "Alexey",
            secondName = "Savage",
            phoneNumber = "+7800773535"
        )
    )
}