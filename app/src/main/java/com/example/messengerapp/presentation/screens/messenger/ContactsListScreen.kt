package com.example.messengerapp.presentation.screens.messenger

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.messengerapp.R
import com.example.messengerapp.core.theme.AppTheme
import com.example.messengerapp.presentation.component.ContactsBottomSheet
import com.example.messengerapp.presentation.navigation.NavBottomBar
import com.example.messengerapp.presentation.viewmodel.ContactsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun ContactsListScreen(
    navController: NavController = rememberNavController(),
    contactsViewModel: ContactsViewModel
) {

    var showBottomSheet by remember { mutableStateOf(false) }

//    var firstName by remember { mutableStateOf("") }
//    var secondName by remember { mutableStateOf("") }
//    var phoneNumber by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

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
        }) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = AppTheme.colors.backgroundPrimary)
                .padding(paddingValues)
        ) {
            Text(text = "Contacts Screen")
        }

        ContactsBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            isVisible = showBottomSheet,
            onCreateContact = { _, _, phoneNumber ->
                scope.launch(Dispatchers.IO) {
                    contactsViewModel.getContact(phoneNumber)
                }
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
        contactsViewModel = viewModel()
        )
}
