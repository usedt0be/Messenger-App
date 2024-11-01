package com.example.messengerapp.presentation.screens.messenger

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.messengerapp.presentation.component.ProfileItem
import com.example.messengerapp.presentation.viewmodel.AuthViewModel


@Composable
fun ProfileScreen(
    authViewModel: AuthViewModel
) {

    val currentUser = authViewModel.currentUser.collectAsState().value

    val painter = rememberAsyncImagePainter(
        model = currentUser?.imageUrl,
    )

    Scaffold(
    )
    { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),

        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(start = 4.dp,end = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.FillBounds
                )

                Spacer(modifier = Modifier.widthIn(20.dp))

                Text("${currentUser?.firstName}")
            }



            Column(
                modifier = Modifier.padding(top = 20.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Account",
                    modifier = Modifier.padding(top = 6.dp, start = 12.dp)
                )


                ProfileItem(
                    modifier = Modifier.padding(top = 10.dp, start = 12.dp),
                    onClick = {},
                    text = currentUser?.phoneNumber ?: "dd",
                    description = "Press to change phone number"
                )

                HorizontalDivider(modifier = Modifier.padding(top = 2.dp,start = 12.dp))

                ProfileItem(
                    modifier = Modifier.padding(top = 2.dp, start = 12.dp),
                    onClick = {},
                    text = "Bio",
                    description = "Write a little about yourself"
                )

                HorizontalDivider(modifier = Modifier.padding(top = 2.dp ,start = 12.dp))
            }

        }

    }


}




//@Preview
//@Composable
//fun ProfileScreenPreview() {
//    ProfileScreen(currentUser = UserEntity(
//        userId = "11124124",
//        phoneNumber = "89545788315",
//        firstName = "Alex",
//        secondName = "Value",
//        imageUrl = "https://f4.bcbits.com/img/a2718942742_10.jpg"
//    ))
//}