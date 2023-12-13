package com.example.propertypro.utilScreens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.propertypro.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


val _productListM = mutableListOf<Map<String, Any>>()
var loadingM by mutableStateOf(true)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPropertyScreen(navController: NavHostController, propertyViewModel: PropertyViewModel) {

    val coroutineScopeF = rememberCoroutineScope()

    val ctx= LocalContext.current

    val databaseM = FirebaseDatabase.getInstance()
    val authM = FirebaseAuth.getInstance()
    val referenceM = databaseM.reference



    LaunchedEffect(true) {

        retrieveDataFromFirebaseM( authM,referenceM)

    }

    Scaffold(topBar= {
        TopAppBar(
            title = {
                TopAppBar(
                    title = {
                        Text(
                            text = "My Properties",
                            //modifier = Modifier.padding(horizontal = 5.dp),
                            color = Color.Black
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {  navController.navigate("Home") {
                            popUpTo("Home") {
                                inclusive = true
                            }
                        }}) {
                            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                        }
                    }
                )
            },
            modifier = Modifier.fillMaxWidth(),
            navigationIcon = { null },

            actions = {

                      TextButton(
                          colors= ButtonDefaults.buttonColors(
                              containerColor = Color.White,
                              contentColor = Color.White
                          ),
                          modifier = Modifier.padding(end = 8.dp),
                          onClick = {
                          navController.navigate("addProperty1")
                      }) {
                          Text(text = "+ Post", color = Color(0xFF5A98EB))
                      }
//                IconButton(onClick = {
//
//                },
//                    modifier = Modifier
//                        .height(50.dp)
//                        .width(50.dp)
//                        .padding(start = 5.dp, end = 5.dp)) {
//                    Column {
//                        Icon(imageVector = Icons.Outlined.Search, contentDescription = null)
//                        Text(text = "Add",
//                            modifier = Modifier.
//                            padding(top = 3.dp, start = 2.dp, end = 2.dp),
//                            fontSize = 8.sp
//                        )
//                    }
//                }
            },

            ) })
    { paddingValue ->

        Column(modifier = Modifier
            .padding(paddingValue)
            .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally) {


            if (!loadingM) {
                if (!_productListM.isNullOrEmpty()) {
                    LazyColumn {
                        items(_productListM) { product ->
                            Column(modifier = Modifier
                                .clickable {

                                    val s = product["Type"].toString()
                                    Log.d("clicked", "111111111111111111111111111111111111$s")
                                    print("11111111111vvvvvvvvvvvvvvvvvvv1111111111111111111111111111111111111")
                                    propertyViewModel.propertyDetails["Type"] =
                                        product["Type"].toString()
                                    navController.navigate("productDetailScreen")
                                }
                                .fillMaxWidth()) {
                                ProductItemM(product, propertyViewModel, navController,
                                    product,authM, referenceM)
                            }
                        }


                    }
                }else{
                    Column(modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        
                        Text(text = "Empty", color = Color.Black)
                    }
                }
            }}}}

@Composable
fun ProductItemM(
    product: Map<String, Any>,
    propertyViewModel: PropertyViewModel,
    navControllerHome: NavHostController,
    product1: Map<String, Any>,
    authM: FirebaseAuth,
    referenceM: DatabaseReference,

    ) {
    val imglist = remember { mutableStateListOf<String>() }
    Card(
        //shape = MaterialTheme.shapes.medium,RoundedCornerShape(15.dp)
        shape = RectangleShape,
        // modifier = modifier.size(280.dp, 240.dp)
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                val borderSize = 1.dp.toPx()
                // val strokeWidth = indicatorWidth.value * density
                val y = size.height - borderSize / 2

                drawLine(
                    Color.LightGray,
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = borderSize
                )
            }
            .padding(top = 10.dp),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp,
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { })
        ) {

            if (product["Images"] != null){
                imglist.addAll(product["Images"] as List<String>)
                Box {

                    AsyncImage(
                        model = imglist[0],
                        placeholder = painterResource(id = R.drawable.holderimage),
                        error = painterResource(id = R.drawable.holderimage),
                        contentDescription = null,
                        modifier = Modifier
                            .clickable {
                                propertyViewModel.propertyDetails["Area Details"] =
                                    product["Area Details"].toString()
                                propertyViewModel.propertyDetails["Availability"] =
                                    product["Availability"].toString()
                                propertyViewModel.propertyDetails["City"] =
                                    product["City"].toString()
                                propertyViewModel.propertyDetails["Price"] =
                                    product["Price"].toString()
                                propertyViewModel.propertyDetails["Room"] =
                                    product["Room"].toString()
                                propertyViewModel.propertyDetails["Bath Room"] =
                                    product["Bath Room"].toString()
                                propertyViewModel.propertyDetails["Balconies"] =
                                    product["Balconies"].toString()
                                propertyViewModel.propertyDetails["Other Room"] =
                                    product["Other Room"].toString()
                                propertyViewModel.propertyDetails["Furnishing Room"] =
                                    product["Furnishing Room"].toString()
                                propertyViewModel.propertyDetails["Availability"] =
                                    product["Availability"].toString()
                                propertyViewModel.propertyDetails["Area Details"] =
                                    product["Area Details"].toString()
                                propertyViewModel.propertyDetails["Floor Detail"] =
                                    product["Floor Detail"].toString()
                                propertyViewModel.propertyDetails["Open Parking"] =
                                    product["Open Parking"].toString()
                                propertyViewModel.propertyDetails["Covered Parking"] =
                                    product["Covered Parking"].toString()
                                propertyViewModel.propertyDetails["Type"] =
                                    product["Type"].toString()
                                propertyViewModel.propertyDetails["Purpose"] =
                                    product["Purpose"].toString()
                                propertyViewModel.propertyDetails["Contact"] =
                                    product["Contact"].toString()
                                propertyViewModel.propertyDetails["Description"] =
                                    product["Description"].toString()

                                propertyViewModel.propertyDetails["Images"] = imglist

                                navControllerHome.navigate("productDetailScreen")
                            }
                            .height(250.dp)
                            .fillMaxWidth(),
                        contentScale = ContentScale.FillWidth
                    )

                    Row(modifier = Modifier.align(Alignment.TopEnd)) {

                        IconButton(modifier = Modifier
                            //   .align(Alignment.TopEnd)
                            .padding(16.dp)
                            .background(Color.White.copy(alpha = 0.6f), shape = CircleShape),
                            onClick = {

                                propertyViewModel.propertyDetails["Residential"] =
                                    product["Residential"].toString()
                                propertyViewModel.propertyDetails["Availability"] =
                                    product["Availability"].toString()
                                propertyViewModel.propertyDetails["City"] =
                                    product["City"].toString()
                                propertyViewModel.propertyDetails["Price"] =
                                    product["Price"].toString()
                                propertyViewModel.propertyDetails["Room"] =
                                    product["Room"].toString()
                                propertyViewModel.propertyDetails["Bath Room"] =
                                    product["Bath Room"].toString()
                                propertyViewModel.propertyDetails["Balconies"] =
                                    product["Balconies"].toString()
                                propertyViewModel.propertyDetails["Other Room"] =
                                    product["Other Room"].toString()
                                propertyViewModel.propertyDetails["Furnishing Room"] =
                                    product["Furnishing Room"].toString()
                                propertyViewModel.propertyDetails["Availability"] =
                                    product["Availability"].toString()
                                propertyViewModel.propertyDetails["Area Details"] =
                                    product["Area Details"].toString()
                                propertyViewModel.propertyDetails["Floor Detail"] =
                                    product["Floor Detail"].toString()
                                propertyViewModel.propertyDetails["Open Parking"] =
                                    product["Open Parking"].toString()
                                propertyViewModel.propertyDetails["Covered Parking"] =
                                    product["Covered Parking"].toString()
                                propertyViewModel.propertyDetails["Type"] =
                                    product["Type"].toString()
                                propertyViewModel.propertyDetails["Purpose"] =
                                    product["Purpose"].toString()
                                propertyViewModel.propertyDetails["Contact"] =
                                    product["Contact"].toString()
                                propertyViewModel.propertyDetails["Description"] =
                                    product["Description"].toString()

                                propertyViewModel.propertyDetails["Images"] = imglist

                                navControllerHome.navigate("editScreen")
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = null,
                                tint = Color.Blue
                            )
                        }

                        IconButton(modifier = Modifier
                            //   .align(Alignment.TopEnd)
                            .padding(16.dp)
                            .background(Color.White.copy(alpha = 0.6f), shape = CircleShape),
                            onClick = {
                                RemoveList(product, authM, referenceM)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null,
                                tint = Color.Red
                            )
                        }
                    }
                }
            }
            else{
                Box {

                    Image(
                        painter = painterResource(id = R.drawable.holderimage),
                        contentDescription = null,
                        modifier = Modifier
                            .clickable {
                                propertyViewModel.propertyDetails["Area Details"] =
                                    product["Area Details"].toString()
                                propertyViewModel.propertyDetails["Availability"] =
                                    product["Availability"].toString()
                                propertyViewModel.propertyDetails["City"] =
                                    product["City"].toString()
                                propertyViewModel.propertyDetails["Price"] =
                                    product["Price"].toString()
                                propertyViewModel.propertyDetails["Room"] =
                                    product["Room"].toString()
                                propertyViewModel.propertyDetails["Bath Room"] =
                                    product["Bath Room"].toString()
                                propertyViewModel.propertyDetails["Balconies"] =
                                    product["Balconies"].toString()
                                propertyViewModel.propertyDetails["Other Room"] =
                                    product["Other Room"].toString()
                                propertyViewModel.propertyDetails["Furnishing Room"] =
                                    product["Furnishing Room"].toString()
                                propertyViewModel.propertyDetails["Availability"] =
                                    product["Availability"].toString()
                                propertyViewModel.propertyDetails["Area Details"] =
                                    product["Area Details"].toString()
                                propertyViewModel.propertyDetails["Floor Detail"] =
                                    product["Floor Detail"].toString()
                                propertyViewModel.propertyDetails["Open Parking"] =
                                    product["Open Parking"].toString()
                                propertyViewModel.propertyDetails["Covered Parking"] =
                                    product["Covered Parking"].toString()
                                propertyViewModel.propertyDetails["Type"] =
                                    product["Type"].toString()
                                propertyViewModel.propertyDetails["Purpose"] =
                                    product["Purpose"].toString()
                                propertyViewModel.propertyDetails["Contact"] =
                                    product["Contact"].toString()
                                propertyViewModel.propertyDetails["Description"] =
                                    product["Description"].toString()

                                propertyViewModel.propertyDetails["Images"] = imglist

                                navControllerHome.navigate("productDetailScreen")
                            }
                            .height(250.dp)
                            .fillMaxWidth(),
                        contentScale = ContentScale.FillWidth
                    )

                    Row(modifier = Modifier.align(Alignment.TopEnd)) {

                        IconButton(modifier = Modifier
                            //   .align(Alignment.TopEnd)
                            .padding(16.dp)
                            .background(Color.White.copy(alpha = 0.6f), shape = CircleShape),
                            onClick = {

                                propertyViewModel.propertyDetails["Area Details"] =
                                    product["Area Details"].toString()
                                propertyViewModel.propertyDetails["Availability"] =
                                    product["Availability"].toString()
                                propertyViewModel.propertyDetails["City"] =
                                    product["City"].toString()
                                propertyViewModel.propertyDetails["Price"] =
                                    product["Price"].toString()
                                propertyViewModel.propertyDetails["Room"] =
                                    product["Room"].toString()
                                propertyViewModel.propertyDetails["Bath Room"] =
                                    product["Bath Room"].toString()
                                propertyViewModel.propertyDetails["Balconies"] =
                                    product["Balconies"].toString()
                                propertyViewModel.propertyDetails["Other Room"] =
                                    product["Other Room"].toString()
                                propertyViewModel.propertyDetails["Furnishing Room"] =
                                    product["Furnishing Room"].toString()
                                propertyViewModel.propertyDetails["Availability"] =
                                    product["Availability"].toString()
                                propertyViewModel.propertyDetails["Area Details"] =
                                    product["Area Details"].toString()
                                propertyViewModel.propertyDetails["Floor Detail"] =
                                    product["Floor Detail"].toString()
                                propertyViewModel.propertyDetails["Open Parking"] =
                                    product["Open Parking"].toString()
                                propertyViewModel.propertyDetails["Covered Parking"] =
                                    product["Covered Parking"].toString()
                                propertyViewModel.propertyDetails["Type"] =
                                    product["Type"].toString()
                                propertyViewModel.propertyDetails["Purpose"] =
                                    product["Purpose"].toString()
                                propertyViewModel.propertyDetails["Contact"] =
                                    product["Contact"].toString()
                                propertyViewModel.propertyDetails["Description"] =
                                    product["Description"].toString()

                                propertyViewModel.propertyDetails["Images"] = imglist

                               navControllerHome.navigate("editScreen")
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = null,
                                tint = Color.Blue
                            )
                        }

                        IconButton(modifier = Modifier
                         //   .align(Alignment.TopEnd)
                            .padding(16.dp)
                            .background(Color.White.copy(alpha = 0.6f), shape = CircleShape),
                            onClick = {
                                RemoveList(product, authM, referenceM)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null,
                                tint = Color.Red
                            )
                        }
                    }

                }
            }

            Column(modifier = Modifier
                .padding(16.dp)
                .clickable {
                    propertyViewModel.propertyDetails["Apartment"] =
                        product["Apartment"].toString()
                    propertyViewModel.propertyDetails["Availability"] =
                        product["Availability"].toString()
                    propertyViewModel.propertyDetails["City"] = product["City"].toString()
                    propertyViewModel.propertyDetails["Price"] = product["Price"].toString()
                    propertyViewModel.propertyDetails["Room"] = product["Room"].toString()
                    propertyViewModel.propertyDetails["Bath Room"] =
                        product["Bath Room"].toString()
                    propertyViewModel.propertyDetails["Balconies"] =
                        product["Balconies"].toString()
                    propertyViewModel.propertyDetails["Other Room"] =
                        product["Other Room"].toString()
                    propertyViewModel.propertyDetails["Furnishing Room"] =
                        product["Furnishing Room"].toString()
                    propertyViewModel.propertyDetails["Availability"] =
                        product["Availability"].toString()
                    propertyViewModel.propertyDetails["Area Details"] =
                        product["Area Details"].toString()
                    propertyViewModel.propertyDetails["Floor Detail"] =
                        product["Floor Detail"].toString()
                    propertyViewModel.propertyDetails["Open Parking"] =
                        product["Open Parking"].toString()
                    propertyViewModel.propertyDetails["Covered Parking"] =
                        product["Covered Parking"].toString()
                    propertyViewModel.propertyDetails["Type"] = product["Type"].toString()
                    propertyViewModel.propertyDetails["Purpose"] =
                        product["Purpose"].toString()
                    propertyViewModel.propertyDetails["Contact"] =
                        product["Contact"].toString()
                    propertyViewModel.propertyDetails["Description"] =
                        product["Description"].toString()
                    propertyViewModel.propertyDetails["Locality"]=
                        product["Locality"].toString()
                    propertyViewModel.propertyDetails["Images"] = imglist

                    propertyViewModel.propertyDetails["fromMyPro"] = "true"
                    navControllerHome.navigate("productDetailScreen")
                }) {
                Text(
                    text = "Property for " + product["Purpose"] + " in " + product["City"].toString(),
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )


                Spacer(modifier = Modifier.height(5.dp))

                Row {
                    Text(
                        text = "Asking Price",
                        maxLines = 1,
                        color = Color.Black.copy(alpha = 0.6f),
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = product["Price"].toString(),
                        maxLines = 2,
                        color = Color.Red.copy(alpha = 0.5f),
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }

                Text(
                    text = "Description",
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    color = Color.Black.copy(alpha = 0.9f),
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = product["Description"].toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    modifier = Modifier.padding(start = 6.dp),
                    color = Color.Black.copy(alpha = 0.6f),
                    overflow = TextOverflow.Ellipsis
                )

            }
        }
    }
}

fun RemoveList(product: Map<String, Any>, authM: FirebaseAuth, referenceM: DatabaseReference) {
    val uid = authM.currentUser!!.uid

    referenceM.child("User Info").child(uid).child("Properties")
        .addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (productSnapshot in snapshot.children) {

                    val _product = productSnapshot.value as Map<String, Any>

                   if (product["Description"] == _product["Description"] &&
                       product["City"] == _product["City"] &&
                       product["Contact"] == _product["Contact"] &&
                       product["Type"] == _product["Type"]){
                       referenceM.child("User Info").child(uid)
                           .child("Properties")
                           .child(productSnapshot.key!!).removeValue().addOnCompleteListener {
                               if (it.isSuccessful) {
                                   loadingM = true
                                   retrieveDataFromFirebaseM(authM, referenceM)
                               }
                           }
                   }

                }

            }


            override fun onCancelled(error: DatabaseError) {

            }
        })

}


fun retrieveDataFromFirebaseM(authM: FirebaseAuth, referenceM: DatabaseReference,) {

    val uid = authM.currentUser!!.uid

    referenceM.child("User Info").child(uid).child("Properties")
        .addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                _productListM.clear()
                for (productSnapshot in snapshot.children) {
             productSnapshot.key
                    val product = productSnapshot.value as Map<String, Any>
                    _productListM.add(product)
                }
                loadingM = false
            }


            override fun onCancelled(error: DatabaseError) {
                loadingM = false
            }
        })
}
