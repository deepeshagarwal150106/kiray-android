package com.example.kiray.ui.structure

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun EditBox(placeHolder: String, displayName: @Composable ()->Unit,value : String, onValueChange: (String)->Unit){
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = displayName,
        placeholder = { Text(text = placeHolder) },
        maxLines = 2
    )
}