package com.example.kiray.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.kiray.model.House
import com.example.kiray.model.User
import com.example.kiray.ui.home.HomeScreen
import com.example.kiray.ui.login.LoginScreen
import com.example.kiray.ui.myHouse.owner.EditHouseScreen
import com.example.kiray.ui.myHouse.MyHouseScreen
import com.example.kiray.ui.myHouse.tenent.HouseDetailScreen
import com.example.kiray.ui.otp.OtpVerificationScreen
import com.example.kiray.ui.profile.EditProfileScreen
import com.example.kiray.ui.profile.ProfileScreen
import com.example.kiray.ui.register.RegisterScreen

@Composable
fun Navigation(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Screens.Router.route
    ) {
        composable(Screens.HomeScreen.route) {
            HomeScreen()
        }
        composable(Screens.MyHouseScreen.route) {
            MyHouseScreen(
                onEdit = { house ->
                navController.currentBackStackEntry?.savedStateHandle?.set("house", house)
                navController.navigate(Screens.EditHouseScreen.route)
            }, onClick = { house ->
                navController.currentBackStackEntry?.savedStateHandle?.set("house", house)
                navController.navigate(Screens.HouseDetailScreen.route)
            },
                onAddHouseClick = {
                    navController.navigate(Screens.EditHouseScreen.route)
                })
        }
        composable(Screens.EditHouseScreen.route) {
            val house = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<House>("house")
            EditHouseScreen(house, onSave = {
                navController.popBackStack()
            }, onCancel = {
                navController.popBackStack()
            })
        }
        composable(Screens.HouseDetailScreen.route) {
            val house = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<House>("house")
            HouseDetailScreen(house, onBack = {
                navController.popBackStack()
            })
        }
        composable(Screens.ProfileScreen.route) {
            ProfileScreen(
                onLogOut = {
                    navController.navigate(Screens.LoginScreen.route) {
                        popUpTo(Screens.ProfileScreen.route) { inclusive = true }
                    }
                },
                onEditProfile = {user->
                    navController.currentBackStackEntry?.savedStateHandle?.set("user", user)
                    navController.navigate(Screens.EditProfileScreen.route) {
                        popUpTo(Screens.EditProfileScreen.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screens.EditProfileScreen.route) {
            val user = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<User>("user")
            EditProfileScreen(user,onBackPress = {
                navController.popBackStack()
            })
        }
        composable(Screens.LoginScreen.route) {
            LoginScreen(
                onLogin = {
                    navController.navigate(Screens.HomeScreen.route) {
                        popUpTo(Screens.LoginScreen.route) { inclusive = true }
                    }
                },
                toRegisterScreen = { navController.navigate(Screens.RegisterScreen.route) }
            )
        }
        composable(Screens.RegisterScreen.route) {
            RegisterScreen(
                toOtpScreen = { user ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("user", user)
                    navController.navigate(Screens.OtpVerificationScreen.route) {
                        popUpTo(Screens.RegisterScreen.route)
                    }
                },
                toLoginScreen = { navController.navigate(Screens.LoginScreen.route) }
            )
        }
        composable(Screens.OtpVerificationScreen.route) {
            val user = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<User>("user")
            if (user != null) {
                OtpVerificationScreen(
                    onOtpVerified = {
                        navController.navigate(Screens.LoginScreen.route) {
                            popUpTo(Screens.OtpVerificationScreen.route) { inclusive = true }
                        }
                    },
                    user = user
                )
            } else {
                Text("Something went wrong")
            }
        }
        composable(Screens.Router.route) {}
    }
}