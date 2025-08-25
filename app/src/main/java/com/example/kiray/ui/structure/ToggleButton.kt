package com.example.kiray.ui.structure

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ToggleButton(value: Boolean, onValueChange: (Boolean)->Unit,displayText: String){
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = displayText)
        Switch(
            checked = value,
            onCheckedChange = onValueChange,
            modifier = Modifier.padding(16.dp)
        )
    }
}