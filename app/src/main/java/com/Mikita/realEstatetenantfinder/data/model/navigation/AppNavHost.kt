package com.Mikita.realEstatetenantfinder.data.model.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.Mikita.realEstatetenantfinder.ui.Presentation.home.HomeScreen
import com.Mikita.realEstatetenantfinder.ui.Presentation.landlord.application.LandlordApplicationScreen
import com.Mikita.realEstatetenantfinder.ui.Presentation.landlord.home.LandlordChatScreen
import com.Mikita.realEstatetenantfinder.ui.Presentation.splash.SplashScreen
import com.Mikita.realEstatetenantfinder.ui.Presentation.tenant.application.ApplicationScreen
import com.Mikita.realEstatetenantfinder.ui.Presentation.tenant.chat.TenantChatScreen
import com.Mikita.realEstatetenantfinder.ui.Presentation.tenant.home.TenantHomeScreen
import com.Mikita.realEstatetenantfinder.ui.Presentation.tenant.payments.TenantPaymentScreen
import com.example.eventflow.screens.auth.LoginScreen
import com.example.eventflow.screens.auth.RoleSelectionScreen
import com.example.eventflow.screens.auth.SignUpScreen
import com.example.eventflow.viewmodel.AuthViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = ROUTE_SPLASH,
    authViewModel: AuthViewModel
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(TENANT_SCREEN) {
            ApplicationScreen()
        }

        composable(LANDLORD_SCREEN) {
            LandlordApplicationScreen(navController)
        }
        composable(ROUTE_SPLASH) {
            SplashScreen(navController)
        }
        composable(ROUTE_LOGIN) {
            LoginScreen(navController, authViewModel)
        }
        composable(ROUTE_REGISTER) {
            SignUpScreen(navController, authViewModel)
        }
        composable(ROUTE_HOME) {
            HomeScreen(navController)
        }
        composable(ROUTE_ROLE_SELECTION) {
            RoleSelectionScreen(navController, authViewModel)
        }
        composable(LANDLORD_PROPERTY_SCREEN) {
            LandlordChatScreen(navController)
        }
        composable(TENANT_APPLICATION_SCREEN) {
            TenantPaymentScreen(navController)
        }
        composable(LANDLORD_CHAT_SCREEN) {
            LandlordChatScreen(navController)
        }
        composable(LANDLORD_APPLICATION_SCREEN) {
            LandlordApplicationScreen(navController)
        }
        composable(LANDLORD_PAYMENT_SCREEN) {
            TenantPaymentScreen(navController)
        }
        composable(TENANT_CHAT_SCREEN) {
            TenantChatScreen(navController)
        }
        composable(TENANT_PAYMENT_SCREEN) {
            TenantPaymentScreen(navController)
        }
        composable(ROUTE_TENANT) {
            TenantHomeScreen(navController, authViewModel)
        }
    }
}