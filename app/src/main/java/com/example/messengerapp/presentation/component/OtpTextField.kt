package com.example.messengerapp.presentation.component

import androidx.compose.foundation.background
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.messengerapp.core.theme.AppTheme


@Composable
fun OtpTextField(
    otp: String,
    onOtpChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    otpLength: Int = 6
) {


    BasicTextField(
        modifier = modifier
            .padding(top = 120.dp)
            .background(color = AppTheme.colors.backgroundPrimary),
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
                verticalAlignment = Alignment.CenterVertically,
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
                            .background(
                                color = AppTheme.colors.secondary,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .border(
                                width = if (isCurrent) {
                                    2.dp
                                } else {
                                    0.dp
                                },
                                color = if (isCurrent) {
                                    AppTheme.colors.accentPrimary
                                } else {
                                    Color.Transparent
                                },
                                shape = RoundedCornerShape(12.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = char,
                            style = AppTheme.typography.subtitle,
                            color = AppTheme.colors.textPrimary,
                            )
                    }

                    Spacer(
                        modifier = Modifier.width(
                            width =
                            if (index < otpLength - 1) {
                                8.dp
                            } else {
                                0.dp
                            }
                        )
                    )
                }
            }
        }
    )
}

@Preview
@Composable
fun OtpTextFieldPreview() {
    OtpTextField(
        otp = "435258",
        onOtpChange = {}
    )
}