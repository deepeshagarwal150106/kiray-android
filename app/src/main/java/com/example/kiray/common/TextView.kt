package com.example.kiray.common

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp

@Composable
fun TextViewClickSpan(
    primaryText: String,
    spanText: Array<String>,
    spanColor: Color = colorScheme.primary,
    modifier: Modifier = Modifier,
    textStyle: TextStyle? = null,
    spanIgnoreIndex: Array<Int>? = null,
    onSpanTextClick: (Int) -> Unit,
) {
//    val text =
//        getClickableSpanText(
//            primaryText = primaryText,
//            primaryTextColor = Color.Black,
//            primaryTextSize = 13,
//            spanText = spanText,
//            spanTextColor = spanColor,
//            spanIgnoreIndex = spanIgnoreIndex
//        )
//    val textLayoutResult = remember { mutableStateOf<TextLayoutResult?>(null) }
//
}

@Composable
fun TextViewSpan(
    primaryText: String,
    spanText: String,
    spanColor: Color = colorScheme.primary,
    modifier: Modifier = Modifier,
    maxLength: Int = Int.MAX_VALUE,
    style: TextStyle = typography.bodySmall,
    primaryColor: Color = Color.Black,
) {
    Text(
        style = style,
        text =
            getSpanText(
                primaryText = primaryText,
                primaryTextSize = 13,
                spanText = spanText,
                spanTextColor = spanColor,
                primaryTextColor = primaryColor
            ),
        modifier = modifier
    )
}


@Composable
fun getSpanText(
    primaryText: String,
    primaryTextSize: Int,
    primaryFontFamily: FontFamily? = null,
    primaryTextColor: Color? = null,
    spanText: String,
    spanTextColor: Color,
    spanTextSize: Int? = null,
    spanFontFamily: FontFamily? = null,
): AnnotatedString =
    buildAnnotatedString {
        withStyle(
            style =
                SpanStyle(
                    color = primaryTextColor ?: colorScheme.primary,
                    fontFamily = primaryFontFamily,
                    fontSize = primaryTextSize.sp
                )
        ) {
            append(primaryText)
            withStyle(
                style =
                    SpanStyle(
                        color = spanTextColor,
                        fontFamily = spanFontFamily,
                        fontSize = (spanTextSize ?: primaryTextSize).sp
                    )
            ) {
                append(spanText)
            }
        }
    }
