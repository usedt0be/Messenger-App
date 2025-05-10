package com.example.messengerapp.util

import android.util.Log
import com.example.messengerapp.R
import com.example.messengerapp.data.CountryData
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Date
import java.util.Locale


object AppUtils {
    fun formatTimeStamp(
        timestamp: Long
    ): String {
        val date = Date(timestamp * 1000)
        val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
        return formatter.format(date)
    }

    fun formatMessageTime(timestamp: Long): String {
        val now = LocalDate.now()
        val messageDate = Instant.ofEpochMilli(timestamp * 1000)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()

        val daysBetween = ChronoUnit.DAYS.between(messageDate, now)

        return when {
            messageDate == now -> {
                messageDate.format(DateTimeFormatter.ofPattern("HH:mm"))
            }

            daysBetween in 1..6 -> {
                // В пределах последней недели
                messageDate.format(DateTimeFormatter.ofPattern("EEE", Locale("ru")))
            }

            daysBetween in 7..30 -> {
                // До месяца назад
                messageDate.format(DateTimeFormatter.ofPattern("dd.MM"))
            }

            daysBetween in 31 .. 366 -> {
                // Более месяца назад
                messageDate.format(DateTimeFormatter.ofPattern("LLLL", Locale("en")))
            }

            else -> {
                messageDate.format(DateTimeFormatter.ofPattern("yyyy", Locale("en")))
            }
        }
    }


    val countriesList: List<CountryData> = listOf(
        CountryData(countryName = "Bangladesh", countryPhoneCode = "+880", countryFlag = R.drawable.bd_bangladesh),
        CountryData(countryName = "Belgium", countryPhoneCode = "+32", countryFlag = R.drawable.be_belgium),
        CountryData(countryName = "Burkina Faso", countryPhoneCode = "+226", countryFlag = R.drawable.bf_burkina_faso),
        CountryData(countryName = "Bulgaria", countryPhoneCode = "+359", countryFlag = R.drawable.bd_bangladesh),
        CountryData(countryName = "Bahrain", countryPhoneCode = "+973", countryFlag = R.drawable.bh_bahrain),
        CountryData(countryName = "Burundi", countryPhoneCode = "+257", countryFlag = R.drawable.bi_burundi),
        CountryData(countryName = "Benin", countryPhoneCode = "+229", countryFlag = R.drawable.bj_benin),
        CountryData(countryName = "Bangladesh", countryPhoneCode = "+880", countryFlag = R.drawable.bo_bolivia),
        CountryData(countryName = "Brazil", countryPhoneCode = "+55", countryFlag = R.drawable.br_brazil),
        CountryData(countryName = "Bahamas", countryPhoneCode = "+1", countryFlag = R.drawable.bs_bahamas),
        CountryData(countryName = "United States", countryPhoneCode = "+1", countryFlag = R.drawable.ic_us_united_states_of_america),
        CountryData(countryName = "Russia", countryPhoneCode = "+7", countryFlag = R.drawable.ic_us_united_states_of_america),
    )
}

