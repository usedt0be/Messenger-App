package com.example.messengerapp.presentation.screens.contacts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.messengerapp.R
import com.example.messengerapp.core.theme.AppTheme
import com.example.messengerapp.domain.models.Contact
import com.example.messengerapp.presentation.component.contacts.ContactsBottomSheet
import com.example.messengerapp.presentation.component.SnackBar
import com.example.messengerapp.presentation.component.contacts.ContactsListItem
import com.example.messengerapp.presentation.navigation.NavBottomBar
import com.example.messengerapp.presentation.viewmodel.ContactsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun ContactsListScreen(
    navController: NavController = rememberNavController(),
    onClickContact:(Contact.Id) -> Unit,
    contactsViewModel: ContactsViewModel,
) {
    val contacts = contactsViewModel.contacts.collectAsState().value
    var showBottomSheet by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val snackBarHostState by remember { mutableStateOf(SnackbarHostState()) }

    Scaffold(
        modifier = Modifier
            .imePadding(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showBottomSheet = true
                },
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.onTertiary
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.add_user_ic),
                    contentDescription = null
                )
            }
        },
        bottomBar = {
            NavBottomBar(navController = navController)
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState) { snackBarData ->
                SnackBar(
                    message = snackBarData.visuals.message,
                    onDismiss = { snackBarData.dismiss() }
                )
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = AppTheme.colors.backgroundPrimary)
                .padding(paddingValues)
        ) {

            Button(
                modifier = Modifier.padding(bottom = 20.dp),
                onClick = {
                contactsViewModel.getToken()
            }) {
                Text("Token")
            }

            if (contacts != null) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    itemsIndexed(contacts) { index: Int, contact ->
                        ContactsListItem(
                            contact = contact!!,
                            modifier = Modifier,
                            onClickContact = { contactId ->
                                onClickContact(contactId)
                            },
                            onClickDelete = contactsViewModel::deleteContact
                        )
                        if(index != contacts.lastIndex) {
                            HorizontalDivider(
                                thickness = 1.dp,
                                color = AppTheme.colors.textTertiary
                            )
                        }
                    }
                }
            } else {
                Text(
                    text = "Add your first contact",
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
        ContactsBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            isVisible = showBottomSheet,
            onCreateContact = { firstName, secondName, phoneNumber ->
                scope.launch(Dispatchers.IO) {
                    contactsViewModel.addContact(
                        firstName = firstName,
                        secondName = secondName,
                        phoneNumber = phoneNumber
                    )
                }
                showBottomSheet = false
            },
        )
    }
}



@Preview
@Composable
fun ContactsListPreview() {
    val navController = rememberNavController()
    ContactsListScreen(
        navController = navController,
        contactsViewModel = viewModel(),
        onClickContact = {},
    )
}
