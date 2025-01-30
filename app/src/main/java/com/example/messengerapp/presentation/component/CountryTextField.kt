package com.example.messengerapp.presentation.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.messengerapp.R
import com.example.messengerapp.core.theme.AppTheme
import com.example.messengerapp.data.entity.CountryData


@Composable
fun CountryTextField(
    countryData: CountryData
) {

    OutlinedTextField(
        value = countryData.countryName,
        onValueChange = {},
        placeholder = {
            Text(text = "Country")
        },
        leadingIcon = {
            Icon(
                imageVector = ImageVector.vectorResource(id = countryData.countryFlag),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(width = 40.dp, height = 20.dp)
            )
        },
        trailingIcon = {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_chevron_right),
                contentDescription = null,
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = AppTheme.colors.backgroundSecondary,
            focusedIndicatorColor = AppTheme.colors.onSuccess,
            cursorColor = Color.Transparent,
            unfocusedTextColor = AppTheme.colors.textSecondary,
            unfocusedContainerColor = AppTheme.colors.backgroundSecondary
        ),

    )
}



@Preview
@Composable
fun CountryTextFieldPreview() {
    CountryTextField(countryData = CountryData(
        countryName = "United States",
        countryPhoneCode = "+1",
        countryFlag = R.drawable.ic_us_united_states_of_america
    ))
}
