package com.example.messengerapp.presentation.screens.contacts

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.messengerapp.R
import com.example.messengerapp.core.theme.AppTheme
import com.example.messengerapp.domain.models.Contact
import com.example.messengerapp.presentation.component.contacts.ContactDetailsTopAppBar
import com.example.messengerapp.presentation.viewmodel.ContactDetailsViewModel
import kotlinx.coroutines.launch


@Composable
fun ContactDetailsScreen(
    contactId: Contact.Id,
    contactDetailsViewModel: ContactDetailsViewModel,
    onClickBackward:() -> Unit,
) {
    LaunchedEffect(Unit) {
        contactDetailsViewModel.getUser(contactId)
    }

    val contact = contactDetailsViewModel.contact.collectAsState().value

    contact?.let {
        val imageRes = rememberAsyncImagePainter(model = contact.photo)
        Log.d("contact_image", "${contact.photo}")

        Scaffold(
            topBar = {
                ContactDetailsTopAppBar(
                    modifier = Modifier
                        .windowInsetsPadding(insets = WindowInsets.statusBars)
                        .fillMaxWidth(),
                    onClickBackward = {
                        onClickBackward()
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(AppTheme.colors.backgroundPrimary),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = imageRes,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .size(80.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.FillBounds
                )

                Text(
                    text = "${contact.firstName} ${contact.secondName}",
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 16.dp),
                    textAlign = TextAlign.Center,
                    style = AppTheme.typography.body1,
                    color = AppTheme.colors.textPrimary
                )

                Text(
                    text = contact.phoneNumber,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    textAlign = TextAlign.Center,
                    style = AppTheme.typography.body1,
                    color = AppTheme.colors.textPrimary
                )
            }
        }
    }
}



@Preview
@Composable
fun ContactsScreenPreview() {
    ContactDetailsScreen(
        contactDetailsViewModel = viewModel(),
        onClickBackward = {},
        contactId = Contact.Id(value = "1")
    )
}