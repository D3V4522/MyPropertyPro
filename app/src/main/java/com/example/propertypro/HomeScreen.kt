package com.example.propertypro

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Scaffold
import androidx.navigation.NavHostController
//import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.propertypro.NavigationScreens.Destinations
import com.example.propertypro.NavigationScreens.NavigationGraph
import com.example.propertypro.utilScreens.PropertyViewModel


@Composable
fun HomeScreen(navControllerHome: NavHostController, propertyViewModel: PropertyViewModel) {

    val navController: NavHostController = rememberNavController()

    var buttonsVisible = remember { mutableStateOf(true) }

    Scaffold(
        bottomBar = {
            BottomBar(navController = navController, state = buttonsVisible,
                modifier = Modifier)
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
        ) {

            NavigationGraph(navController = navController,navControllerHome,propertyViewModel)
        }
    }
}


@Composable
fun BottomBar(
    navController: NavHostController, state: MutableState<Boolean>, modifier: Modifier = Modifier
) {
    val screens = listOf(
        Destinations.DashBoardScreen,
        Destinations.SearchScreen, Destinations.Favourite, Destinations.AccountScreen
    )

    NavigationBar(
        modifier = modifier,
        containerColor = Color.White,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        screens.forEach { screen ->

            NavigationBarItem(
                label = {
                   Text(text = screen.title!!)
                },
                icon = {
                    Icon(imageVector = screen.icon!!,
                        contentDescription = "",
                        tint = if (currentRoute == screen.route){
                            Color(0xFF5A98EB)
                        }else{
                            Color.Black
                        })
                },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    unselectedTextColor = Color.Black, selectedTextColor = Color(0xFF5A98EB)
                ),
            )
        }
    }

}
