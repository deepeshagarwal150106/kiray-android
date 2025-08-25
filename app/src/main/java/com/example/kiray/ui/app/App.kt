package com.example.kiray.ui.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.kiray.common.LocalRole
import com.example.kiray.navigation.Navigation
import com.example.kiray.navigation.Screens
import com.example.kiray.ui.structure.BottomBar
import com.example.kiray.ui.structure.TopBar

@Composable
fun App(
    viewModel: AppViewModel = hiltViewModel()
){
    val navController: NavHostController = rememberNavController()
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route
    val owner = remember { mutableStateOf(false) }

    val token by viewModel.token



    LaunchedEffect(token) {
        if (token != "null") {
            navController.navigate(Screens.HomeScreen.route) {
                popUpTo(Screens.Router.route) { inclusive = true }
            }
        } else {
            navController.navigate(Screens.LoginScreen.route) {
                popUpTo(Screens.Router.route) { inclusive = true }
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {

            if (currentRoute != Screens.LoginScreen.route
                && currentRoute != Screens.RegisterScreen.route
                && currentRoute != Screens.OtpVerificationScreen.route
                && currentRoute != Screens.EditProfileScreen.route
                && currentRoute != Screens.EditHouseScreen.route
                && currentRoute != Screens.HouseDetailScreen.route
                && currentRoute != Screens.Router.route) {
                BottomBar(navController = navController)
            }
        },
        topBar = {
            if (currentRoute != Screens.LoginScreen.route
                && currentRoute != Screens.RegisterScreen.route
                && currentRoute != Screens.OtpVerificationScreen.route
                && currentRoute != Screens.EditProfileScreen.route
                && currentRoute != Screens.EditHouseScreen.route
                && currentRoute != Screens.HouseDetailScreen.route
                && currentRoute != Screens.Router.route
            ) {
                TopBar(
                    navController = navController,
                    onCLick = {
                        owner.value = it
                    },
                    owner = owner.value
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            CompositionLocalProvider(
                LocalRole provides owner.value
            ) {
                Navigation(navController = navController)
            }
        }
    }
}

