package com.example.kiray.ui.structure

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kiray.R
import com.example.kiray.navigation.Screens

@Composable
fun TopBar(navController: NavHostController, onCLick: (Boolean) -> Unit, owner: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(96.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.Absolute.Left,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "app logo " + stringResource(R.string.app_name),
                modifier = Modifier
                    .clip(RoundedCornerShape(32.dp))
                    .clickable {
                        navController.navigate(Screens.HomeScreen.route)
                    }
            )
//            Text(
//                text = stringResource(R.string.app_name),
//                fontWeight = FontWeight.Bold,
//                fontSize = 36.sp,
//                fontFamily = FontFamily.Cursive,
//                modifier = Modifier.padding(start = 16.dp)
//            )
        }
        ToggleButton(
            value = owner,
            onValueChange = onCLick,
            displayText =
                if (owner) "Owner"
                else "Tenant"
        )
    }
}