package com.Mikita.realEstatetenantfinder.data.model.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.Mikita.realEstatetenantfinder.domain.Presentation.landlord.listings.ManageListingsScreen

import com.Mikita.realEstatetenantfinder.ui.Presentation.landlord.application.LandlordApplicationScreen
import com.Mikita.realEstatetenantfinder.ui.Presentation.landlord.chat.LandlordChatScreen
import com.Mikita.realEstatetenantfinder.ui.Presentation.home.LandlordHomeScreen
import com.Mikita.realEstatetenantfinder.ui.Presentation.landlord.Maintenance_Management.LandlordMaintenanceScreen
import com.Mikita.realEstatetenantfinder.ui.Presentation.landlord.payment.LandlordPaymentScreen
import com.Mikita.realEstatetenantfinder.ui.Presentation.profile.ProfileScreen

import com.Mikita.realEstatetenantfinder.ui.Presentation.splash.SplashScreen
import com.Mikita.realEstatetenantfinder.ui.Presentation.tenant.Maintenance.MaintenanceRequestScreen
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
        composable(TENANT_APPLICATION_SCREEN) {
            ApplicationScreen(
                navController,
                viewModel = TODO()
            )
        }


        composable(LANDLORD_SCREEN) {
            LandlordHomeScreen(navController, authViewModel)
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
           LandlordHomeScreen(navController, authViewModel)
        }
        composable(ROUTE_ROLE_SELECTION) {
            RoleSelectionScreen(navController, authViewModel)
        }
        composable(LANDLORD_PAYMENT_SCREEN) {
            LandlordPaymentScreen(navController)

        }
        composable(LANDLORD_CHAT_SCREEN) {
            LandlordChatScreen(navController)
        }
        composable(LANDLORD_APPLICATION_SCREEN) {
            LandlordApplicationScreen(navController)
        }
        composable(TENANT_PAYMENT_SCREEN) {
            TenantPaymentScreen(navController)
        }
        composable(TENANT_CHAT_SCREEN) {
            TenantChatScreen(navController)
        }


        composable(LANDLORD_LISTINGS_SCREEN) {
            ManageListingsScreen(navController,
                onAddListing = {
                    // Handle add listing action
                },
                onEditListing = { listing ->
                    // Handle edit listing action
                },
                onDeleteListing = { listing ->
                    // Handle delete listing action
                }
            )
        }
        composable(ROUTE_PROFILE) {
            ProfileScreen(navController, authViewModel)
        }
        composable(LANDLORD_MAINTENANCE_SCREEN) {
            LandlordMaintenanceScreen(navController)
        }
            composable(TENANT_MAINTENANCE_SCREEN) {
                MaintenanceRequestScreen(navController)
            }

        composable(TENANT_HOME_SCREEN) {
            TenantHomeScreen(navController, authViewModel)
        }
    }
}