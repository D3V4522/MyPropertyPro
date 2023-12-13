package com.example.propertypro.NavigationScreens

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.propertypro.utilScreens.ProductDetailScreen
import com.example.propertypro.utilScreens.PropertyViewModel

@Composable
fun NavigationGraph(
    navController: NavHostController,
    navControllerHome: NavHostController,
    propertyViewModelHome: PropertyViewModel,
) {
    val propertyViewModel: PropertyViewModel = viewModel()
    NavHost(navController, startDestination = Destinations.DashBoardScreen.route) {

        composable(Destinations.DashBoardScreen.route) {
            DashboardScreen(navController,propertyViewModel,navControllerHome)
        }
        composable(Destinations.SearchScreen.route) {
            SearchScreen(navController, propertyViewModelHome,navControllerHome)
        }
        composable(Destinations.Favourite.route) {
            FavouritesScreen(navController,propertyViewModelHome,navControllerHome)
        }
        composable(Destinations.AccountScreen.route) {
            ProfileScreen(navController,navControllerHome)
        }
//        composable("productDetailScreen") {
//            ProductDetailScreen(navController,propertyViewModel) }


    }
}