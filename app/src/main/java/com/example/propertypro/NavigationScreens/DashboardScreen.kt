package com.example.propertypro.NavigationScreens

import android.os.Handler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.propertypro.R
import com.example.propertypro.utilScreens.PropertyViewModel
import com.example.propertypro.utilScreens._productListM
import com.example.propertypro.utilScreens.loadingM
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.delay

var saleCount by mutableStateOf(0)
var rentCount by mutableStateOf(0)
var myPropertyCount by mutableStateOf(0)
@Composable
fun DashboardScreen(
    navController: NavHostController,
    propertyViewModel: PropertyViewModel,
    navControllerHome: NavHostController
) {
    Box(modifier = Modifier.fillMaxSize()) {

        LaunchedEffect(true){
            retrieveDataFromFirebase()
            retrieveDataFromFirebaseSM(auth, reference)
        }
        BackgroundImageChanger()

        Column(modifier = Modifier.matchParentSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            TextButton(
                onClick = {
                    propertyViewModel.propertyDetails["FormDash"] = "ForSale"
                    navController.navigate(Destinations.SearchScreen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                   // navController.navigate(Destinations.SearchScreen.route)
            },
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 50.dp),
                shape = RectangleShape,
                colors =ButtonDefaults.buttonColors(containerColor = Color(0xFF5A98EB),
                    contentColor = Color.Green)
                ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "For Sale",
                        fontSize = 20.sp,
                        modifier = Modifier,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "($saleCount)",
                        fontSize = 15.sp,
                        modifier = Modifier,
                        color = Color.Black.copy(alpha = 0.6f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))


            TextButton(onClick = {
                propertyViewModel.propertyDetails["FormDash"] = "ForRent"
                navController.navigate(Destinations.SearchScreen.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
                    //navController.navigate(Destinations.SearchScreen.route)
            },
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 50.dp),
                shape = RectangleShape,
                colors =ButtonDefaults.buttonColors(containerColor = Color(0xFF5A98EB),
                    contentColor = Color.Green)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "For Rent",
                        fontSize = 20.sp,
                        modifier = Modifier,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "($rentCount)",
                        fontSize = 15.sp,
                        modifier = Modifier,
                        color = Color.Black.copy(alpha = 0.6f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))


            TextButton(onClick = {  navControllerHome.navigate("myProperty") },
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 50.dp),
                shape = RectangleShape,
                colors =ButtonDefaults.buttonColors(containerColor = Color(0xFF5A98EB),
                    contentColor = Color.Green)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "My Properties",
                        fontSize = 20.sp,
                        modifier = Modifier,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "($myPropertyCount)",
                        fontSize = 15.sp,
                        modifier = Modifier,
                        color = Color.Black.copy(alpha = 0.6f)
                    )
                }
            }
        }
    }
}

@Composable
fun BackgroundImageChanger() {
    // List of drawable resources for the background
    val backgroundImages = listOf(
        R.drawable.image1,
        R.drawable.image2,
        R.drawable.image3,
        R.drawable.image4,
    )
    var currentIndex by remember { mutableStateOf(0) }
    val handler = rememberUpdatedState(Handler())

    LaunchedEffect(Unit) {
        // Start the image changing loop
        while (true) {
            delay(5000) // Change image every 5 seconds
            handler.value.post {
                currentIndex = (currentIndex + 1) % backgroundImages.size
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {
        Image(
            painter = painterResource(id = backgroundImages[currentIndex]),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )


    }
}

fun retrieveDataFromFirebase() {
    reference.child("All Products").addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            var list1 = mutableListOf<Map<String, Any>>()
            var list2 = mutableListOf<Map<String, Any>>()
            for (productSnapshot in snapshot.children) {
                val product = productSnapshot.value as Map<String, Any>
                if (product["Purpose"] == "Sell"){
                    list1.add(product)
                }else if (product["Purpose"] == "Rent/Lease"){
                    list2.add(product)
                }
            }
            saleCount=list1.size
            rentCount=list2.size
        }
        override fun onCancelled(error: DatabaseError) {
        }
    })
}

fun retrieveDataFromFirebaseSM(authM: FirebaseAuth, referenceM: DatabaseReference,) {

    val uid = authM.currentUser!!.uid

    referenceM.child("User Info").child(uid).child("Properties")
        .addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                _productListM.clear()
                for (productSnapshot in snapshot.children) {
                    val product = productSnapshot.value as Map<String, Any>
                    _productListM.add(product)
                }
               myPropertyCount = _productListM.size
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
}


