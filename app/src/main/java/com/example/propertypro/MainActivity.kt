package com.example.propertypro

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.propertypro.ui.theme.PropertyProTheme
import com.example.propertypro.utilScreens.AddPropertyScreen1
import com.example.propertypro.utilScreens.AddPropertyScreen2
import com.example.propertypro.utilScreens.AddPropertyScreen3
import com.example.propertypro.utilScreens.MyPropertyScreen
import com.example.propertypro.utilScreens.ProductDetailScreen
import com.example.propertypro.utilScreens.ProductEditScreen
import com.example.propertypro.utilScreens.PropertyViewModel
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        auth = Firebase.auth

        val user = auth.currentUser

        setContent {
            PropertyProTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (user != null){
                        MyApp("Home")
                    }else{
                        MyApp("splash")
                    }
                }
            }
        }
    }
}

@Composable
fun MyApp(launchScreen: String) {
    val navController = rememberNavController()
    val propertyViewModel: PropertyViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = launchScreen
    ) {
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("Home") { HomeScreen(navController,propertyViewModel) }
        composable("splash") { SplashScreen(navController) }
        composable("productDetailScreen") {
            ProductDetailScreen(navController,propertyViewModel) }

        composable("addProperty1") {
            AddPropertyScreen1(navController,propertyViewModel) }
        composable("addProperty2") {
            AddPropertyScreen2(navController,propertyViewModel) }
        composable("addProperty3") {
            AddPropertyScreen3(navController,propertyViewModel) }
        composable("myProperty") {
            MyPropertyScreen(navController,propertyViewModel) }
        composable("editScreen") {
            ProductEditScreen(navController,propertyViewModel) }

        //composable("searchScreen") { SearchScreen(navController,propertyViewModel) }
       // composable("productDetailScreen") { ProductDetailScreen(navController,propertyViewModel) }


//        composable("Favourites") { FavouritesScreen() }
//        composable("Profile") { ProfileScreen() }
//        composable("Search") { SearchScreen() }
    }
}

