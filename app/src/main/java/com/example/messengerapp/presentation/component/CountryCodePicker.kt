package com.example.messengerapp.presentation.component

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.messengerapp.R
import com.example.messengerapp.data.entity.CountryData


@Composable
fun CountryCodePicker(
    query: String,
    onQueryChange: (String) -> Unit,
    countryList: List<CountryData>,
    onCountryItemClick: (String) -> Unit,
){

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->

        Column(modifier = Modifier.padding(paddingValues)) {
            Text(
                text = "Change country code"
            )

            SearchTextField(
                query = query,
                onQueryChange = { changedQuery ->
                    onQueryChange(changedQuery)
                }
            )
            LazyColumn {
                items(countryList) { countryData ->
                    CountryDataItem(
                        countryData = countryData,
                        onCountryItemClick = { countryItemData ->
                            onCountryItemClick(countryItemData.countryPhoneCode)
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
            modifier = Modifier.padding(start = 8.dp)
        )

        Text(
            text = countryData.countryName,
            modifier = Modifier.padding(start = 4.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = countryData.countryPhoneCode,
            modifier = Modifier.padding(end = 4.dp)
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
