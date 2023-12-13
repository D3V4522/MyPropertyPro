package com.example.propertypro.NavigationScreens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.propertypro.R
import com.example.propertypro.utilScreens.PropertyViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

val database = FirebaseDatabase.getInstance()
val auth = FirebaseAuth.getInstance()
val reference = database.reference
val _productList = mutableListOf<Map<String, Any>>()
val _productListSf = mutableListOf<Map<String, Any>>()
var loading by mutableStateOf(true)
var selectedOptionTextS by mutableStateOf("")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavHostController,
    propertyViewModel: PropertyViewModel,
    navControllerHome: NavHostController
) {

    val coroutineScopeF = rememberCoroutineScope()

    val ctx= LocalContext.current
    var fromDash by remember {
        mutableStateOf("")
    }


    LaunchedEffect(true) {

        if (propertyViewModel.propertyDetails["FormDash"] == "ForSale"){
            fromDash="ForSale"
        }else if (propertyViewModel.propertyDetails["FormDash"] == "ForRent"){
            fromDash="ForRent"
        }
        retrieveDataFromFirebase(selectedOptionTextS,fromDash)
        retrieveDataFromFirebaseSF()
    }

    Scaffold(topBar= {
        TopAppBar(
            title = {
                docs(_productList)
            },
            modifier = Modifier.fillMaxWidth(),
            navigationIcon = { null },

            actions = {
                IconButton(onClick = {
                                     loading=true
                    retrieveDataFromFirebase(selectedOptionTextS, fromDash)
                },
                    modifier = Modifier
                        .height(50.dp)
                        .width(50.dp)
                        .padding(start = 5.dp, end = 5.dp)) {
                    Column {
                        Icon(imageVector = Icons.Outlined.Search, contentDescription = null)
                        Text(text = "Search",
                            modifier = Modifier.
                            padding(top = 3.dp, start = 2.dp, end = 2.dp),
                            fontSize = 8.sp
                        )
                    }
                }
            },

            ) })
    { paddingValue ->

        if (!loading){
            LazyColumn(modifier = Modifier.padding(paddingValues = paddingValue)) {
                items(_productList) { product ->
                    Column(modifier = Modifier
                        .clickable {
                            val s = product["Type"].toString()
                            Log.d("clicked", "111111111111111111111111111111111111$s")
                            print("11111111111vvvvvvvvvvvvvvvvvvv1111111111111111111111111111111111111")
                            propertyViewModel.propertyDetails["Type"] = product["Type"].toString()
                            navControllerHome.navigate("productDetailScreen")
                        }
                        .fillMaxWidth()) {
                        ProductItem(product, propertyViewModel,
                            navControllerHome,ctx,coroutineScopeF)
                    }
                }
            }
        }else{
            Column(verticalArrangement = Arrangement.Center,
              horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Please wait", color = Color.Black)
                CircularProgressIndicator()
            }
        }
    }
}

    @Composable
    fun ProductItem(
        product: Map<String, Any>,
        propertyViewModel: PropertyViewModel,
        navControllerHome: NavHostController,
        ctx: Context,
        coroutineScopeF: CoroutineScope
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

                            IconButton(
                                modifier = Modifier
                                    .align(Alignment.TopEnd) // Align to the top end of the Box
                                    .padding(16.dp)
                                    .background(Color.White.copy(alpha = 0.6f), shape = CircleShape)  ,
                                onClick = {


                                    if (!isFav(_productListSf,product)){
                                        val uid= auth.currentUser!!.uid


                                        reference.child("User Info").child(uid!!)
                                            .child("Favorite").push().setValue(product)
                                            .addOnCompleteListener { task ->
                                                if (task.isSuccessful) {
                                                    loading=true
                                                    retrieveDataFromFirebaseSF()
                                                    Toast.makeText(ctx,"Added",Toast.LENGTH_SHORT).show()
                                                } else {
                                                    // Handle the error
                                                    val e = task.exception
                                                    Toast.makeText(ctx,"$e",Toast.LENGTH_SHORT).show()
                                                } }
                                    }else{
                                        coroutineScopeF.launch {
                                            removeItemFromWishlist(reference,product)
                                            retrieveDataFromFirebaseSF()
                                        }
                                    }

                                }) {
                                Icon(
                                    imageVector = if (!isFav(_productListSf,product)){
                                        Icons.Outlined.FavoriteBorder
                                    }else{
                                        Icons.Outlined.Favorite
                                    },
                                    contentDescription = null,
                                    tint = Color(0xFF5A98EB)
                                )
                            }
                        }
                    }else{
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

                            IconButton(
                                modifier = Modifier
                                    .align(Alignment.TopEnd) // Align to the top end of the Box
                                    .padding(16.dp)
                                    .background(Color.White.copy(alpha = 0.6f), shape = CircleShape)  ,
                                onClick = {


                                    if (!isFav(_productListSf,product)){
                                        val uid= auth.currentUser!!.uid


                                        reference.child("User Info").child(uid!!)
                                            .child("Favorite").push().setValue(product)
                                            .addOnCompleteListener { task ->
                                                if (task.isSuccessful) {
                                                    loading=true
                                                    retrieveDataFromFirebaseSF()
                                                    Toast.makeText(ctx,"Added",Toast.LENGTH_SHORT).show()
                                                } else {
                                                    // Handle the error
                                                    val e = task.exception
                                                    Toast.makeText(ctx,"$e",Toast.LENGTH_SHORT).show()
                                                } }
                                    }else{
                                        coroutineScopeF.launch {
                                            removeItemFromWishlist(reference,product)
                                            loading=true
                                            retrieveDataFromFirebaseSF()
                                        }
                                    }

                                }) {
                                Icon(
                                    imageVector = if (!isFav(_productListSf,product)){
                                        Icons.Outlined.FavoriteBorder
                                    }else{
                                        Icons.Outlined.Favorite
                                    },
                                    contentDescription = null,
                                    tint = Color(0xFF5A98EB)
                                )
                            }
                        }
                    }



                    Column(modifier = Modifier
                        .padding(16.dp)
                        .clickable {
                            propertyViewModel.propertyDetails["Area Details"] =
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

                            propertyViewModel.propertyDetails["Images"] = imglist

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
                            color = Color.Black.copy(alpha = 0.5f),
                            overflow = TextOverflow.Ellipsis
                        )


                    }
                }
            }
    }

fun isFav(wishlistInfo: List<Map<String, Any>>?, product: Map<String, Any>?): Boolean {
    if (wishlistInfo == null || product == null) {
        return false
    }

    val img1 = wishlistInfo.firstOrNull()?.get("Images") as? List<String>
    val img2 = product["Images"] as? List<String>

    if (img1 != null && img2 != null && img1.isNotEmpty() && img2.isNotEmpty()) {
        return img1[0] == img2[0]
    }

    return false
}


//fun isFav(wishlistInfo: List<Map<String, Any>>, product: Map<String, Any>): Boolean {
//    for (item in wishlistInfo) {
//        val img1=item["Images"] as List<String>
//        val img2=product["Images"] as List<String>
//        if (img1[0] == img2[0]) {
//            return true
//        }
//    }
//    return false
//}

fun retrieveDataFromFirebase(selectedOptionTextS: String, fromDash: String) {



    reference.child("All Products").addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            _productList.clear()
            for (productSnapshot in snapshot.children) {
                val product = productSnapshot.value as Map<String, Any>

                if (fromDash == "ForSale"){
                    if (product["Purpose"] == "Sell"){
                        if (selectedOptionTextS.isNullOrEmpty()){
                            _productList.add(product)
                        }else{
                            if (product["City"]==selectedOptionTextS){
                                _productList.add(product)
                            }
                        }
                    }

                }else if (fromDash == "ForRent"){

                    if (product["Purpose"] == "Rent/Lease"){
                        if (selectedOptionTextS.isNullOrEmpty()){
                            _productList.add(product)
                        }else{
                            if (product["City"]==selectedOptionTextS){
                                _productList.add(product)
                            }
                        }
                    }
                }else{
                    if (selectedOptionTextS.isNullOrEmpty()){
                        _productList.add(product)
                    }else{
                        if (product["City"]==selectedOptionTextS){
                            _productList.add(product)
                        }
                    }
                }


            }

            loading= false
        }
        override fun onCancelled(error: DatabaseError) {
          loading = false
        }
    })
}

fun retrieveDataFromFirebaseSF() {


    val uid= authF.currentUser!!.uid
    _productListSf.clear()
    reference.child("User Info").child(uid!!)
        .child("Favorite").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (productSnapshot in snapshot.children) {
                    val product = productSnapshot.value as Map<String, Any>
                    _productListSf.add(product)
                }

                loading= false
                // Now, you have the list of products (productList) from Firebase
                // You can update your UI using this data in Jetpack Compose
            }
            override fun onCancelled(error: DatabaseError) {
                loading = false
            }
        })
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun docs(_productList: MutableList<Map<String, Any>>) {

    val uniqueCities = mutableSetOf<String>()
    val cities = _productList.mapNotNull { it["City"] as? String }
        .filter { uniqueCities.add(it) }

    val options = cities
        //listOf("Option 1", "comment", "Afghanistan", "Albania", "Algeria", "Andorra", "Egypt")
    var expanded by remember { mutableStateOf(false) }
    //var selectedOptionText by remember { mutableStateOf("") }
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(50.dp)) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier
                .border(1.dp, Color.Gray)
                .height(50.dp)
                .fillMaxWidth()
        ) {
            TextField(
                // The `menuAnchor` modifier must be passed to the text field for correctness.
                modifier = Modifier
                    .border(1.dp, Color.Gray)
                    .menuAnchor()
                    .fillMaxWidth()
                    .height(50.dp),
                   // .heightIn(50.dp),
                // .padding(start = 15.dp, end = 15.dp),
                value = selectedOptionTextS,
                onValueChange = { selectedOptionTextS = it },
                shape = CircleShape,
                singleLine = true,
                placeholder = {
                    Text(
                        text = "Search based on your desired city",
                        fontSize = 12.sp,
                        color = Color.Black.copy(alpha = 0.8f)
                    )
                },
                textStyle = TextStyle(
                    fontSize = 16.sp
                ),

                colors = ExposedDropdownMenuDefaults.textFieldColors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
            )
            // filter options based on text field value
            val filteringOptions =
                options.filter { it.contains(selectedOptionTextS, ignoreCase = true) }
            if (filteringOptions.isNotEmpty()) {
                DropdownMenu(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxWidth()
                        .height(400.dp)
                        .padding(start = 15.dp, end = 15.dp, top = 5.dp),
                    properties = PopupProperties(focusable = false),
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    filteringOptions.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption) },
                            onClick = {
                                selectedOptionTextS = selectionOption
                                expanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        )
                    }
                }
            }
        }
    }
}

suspend fun removeItemFromWishlist(referenceF: DatabaseReference, product: Map<String, Any>) {

    val uid = FirebaseAuth.getInstance().currentUser?.uid
    uid?.let { currentUser ->
        val event = withContext(Dispatchers.IO) {
            referenceF
                .child("User Info")
                .child(currentUser)
                .child("Favorite")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (childSnapshot in snapshot.children) {
                            val value = childSnapshot.value as Map<*, *>

                            val img1=value["Images"] as List<String>
                            val img2 = product["Images"] as List<String>
                            // Your logic here based on the value
                            if (img1[0] == img2[0]) {
                                childSnapshot.ref.removeValue()
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Handle errors here
                    }
                })
        }

        // Your logic based on the data snapshot
    }
}
