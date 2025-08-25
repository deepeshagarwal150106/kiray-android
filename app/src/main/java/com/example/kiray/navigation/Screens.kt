package com.example.kiray.navigation

sealed class Screens (val route: String) {
    data object LoginScreen : Screens("LoginScreen")
    data object EditProfileScreen : Screens("EditProfileScreen")
    data object HomeScreen : Screens("HomeScreen")
    data object MyHouseScreen : Screens("MyHouseScreen")
    data object EditHouseScreen : Screens("EditHouseScreen")
    data object HouseDetailScreen : Screens("HouseDetailScreen")
    data object ProfileScreen : Screens("ProfileScreen")
    data object RegisterScreen : Screens("RegisterScreen")
    data object OtpVerificationScreen : Screens("OtpVerificationScreen")
    data object Router: Screens("router")
}