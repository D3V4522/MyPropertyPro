package com.example.propertypro.NavigationScreens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Destinations(
    val route: String,
    val title: String? = null,
    val icon: ImageVector? = null
) {
    object DashBoardScreen : Destinations(
        route = "Dash",
        title = "Home",
        icon = Icons.Outlined.Home
    )

    object SearchScreen : Destinations(
        route = "search",
        title = "Search",
        icon = Icons.Outlined.Search
    )
    object Favourite : Destinations(
        route = "favourite",
        title = "Favorite",
        icon = Icons.Outlined.FavoriteBorder
    )

    object AccountScreen : Destinations(
        route = "Account",
        title = "Profile",
        icon = Icons.Outlined.AccountCircle
    )
}
