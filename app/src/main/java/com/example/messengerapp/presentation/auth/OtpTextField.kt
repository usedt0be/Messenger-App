package com.example.messengerapp.presentation.auth

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun OtpTextField(
    otp: String,
    onOtpChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    otpLength: Int = 6
) {


    BasicTextField(
        modifier = modifier.padding(top = 120.dp),
        value = otp ,
        onValueChange = {
            if (it.length <= 6) {
                onOtpChange(it)
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword
        ),
        decorationBox = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(otpLength) { index ->
                    val char = when {
                        index >= otp.length -> ""
                        else -> otp[index].toString()
                    }
                    val isCurrent = otp.length == index
                    Box(
                        modifier = Modifier
                            .width(40.dp)
                            .height(54.dp)
                            .border(
                                width = if (isCurrent) {
                                    2.dp
                                } else {
                                    1.dp
                                },
                                color = if (isCurrent) {
                                    Color.LightGray
                                } else {
                                    Color.DarkGray
                                },
                                shape = RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                        text = char,
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    )
}

//@Preview
//@Composable
//fun OtpTextFieldPreview() {
//    OtpTextField(
//
//    )
//}