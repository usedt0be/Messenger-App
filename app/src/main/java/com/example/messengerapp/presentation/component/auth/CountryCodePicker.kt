package com.example.messengerapp.presentation.component.auth

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.messengerapp.R
import com.example.messengerapp.core.theme.AppTheme
import com.example.messengerapp.data.entity.CountryData
import com.example.messengerapp.presentation.component.SearchTextField
import com.example.messengerapp.util.CountriesUtils


@Composable
fun CountryCodePicker(
    onQueryChange: (String) -> Unit,
    countryList: List<CountryData>,
    onCountryItemClick: (CountryData) -> Unit,
){

    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize()
            .background(color = AppTheme.colors.backgroundSecondary),
        containerColor = AppTheme.colors.backgroundSecondary,
        contentColor = AppTheme.colors.backgroundSecondary,
    ) { paddingValues ->

        Column(
            modifier = Modifier.padding(paddingValues)
                .fillMaxSize()
                .background(AppTheme.colors.backgroundSecondary)
        ) {
            Text(
                text = "Change country code",
                modifier = Modifier.padding(start = 8.dp, top = 8.dp),
                style = AppTheme.typography.subtitle,
                color = AppTheme.colors.textPrimary,
            )

            SearchTextField(
                query = searchQuery,
                onQueryChange = { changedQuery ->
                    onQueryChange(changedQuery)
                    searchQuery = changedQuery
                },
                modifier = Modifier.fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp, top = 8.dp)
                    .background(color = AppTheme.colors.backgroundSecondary)
            )

            LazyColumn(modifier = Modifier
                .padding(start = 8.dp, top = 16.dp, end = 8.dp),
                verticalArrangement = Arrangement.spacedBy(space = 8.dp)
            ) {
                items(countryList) { countryData ->
                    CountryDataItem(
                        countryData = countryData,
                        onCountryItemClick = { countryItemData ->
                            Log.d("queryCountryClick", "$countryItemData")
                            onCountryItemClick(countryItemData)
                        }
                    )
                }
            }
        }
    }
}



@Composable
fun CountryDataItem(
    countryData: CountryData,
    onCountryItemClick: (CountryData) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .clickable {
                onCountryItemClick(countryData)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = countryData.countryFlag),
            contentDescription = null,
            modifier = Modifier.alpha(0.92f),
            tint = Color.Unspecified
        )

        Text(
            text = countryData.countryName,
            modifier = Modifier.padding(start = 8.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = AppTheme.typography.body2,
            color = AppTheme.colors.textSecondary
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = countryData.countryPhoneCode,
            modifier = Modifier.padding(end = 4.dp),
            style = AppTheme.typography.body2,
            color = AppTheme.colors.textSecondary
        )
    }
}


@Preview
@Composable
fun CountryCodeItemPreview(){
    CountryDataItem(
        countryData = CountryData(
            countryName = "USA",
            countryPhoneCode = "+1",
            countryFlag = R.drawable.ic_us_united_states_of_america,
        ),
        onCountryItemClick = {}
    )
}



@Preview
@Composable
fun CountryCodePicker() {
    CountryCodePicker(
//        query = "",
        onQueryChange = {},
        onCountryItemClick = {},
        countryList = CountriesUtils.countriesList
    )
}
