package com.example.propertypro.utilScreens

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.propertypro.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.owlbuddy.www.countrycodechooser.CountryCodeChooser
import com.owlbuddy.www.countrycodechooser.utils.enums.CountryCodeType
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ProductEditScreen(navController: NavHostController, propertyViewModel: PropertyViewModel) {


    val oldProduct=propertyViewModel.propertyDetails
    val databaseEd = FirebaseDatabase.getInstance()
    val authEd = FirebaseAuth.getInstance()
    val referenceEd = databaseEd.reference
    val uid = authEd.currentUser!!.uid
    val storageReference = FirebaseStorage.getInstance().reference.child("images")
        .child(uid).child(UUID.randomUUID().toString())
    val img = propertyViewModel.propertyDetails["Images"] as MutableList<String>
    var imguri = remember { mutableSetOf<String>( ) }
    imguri.addAll(img)
    val uriList = remember { mutableStateListOf<Uri>() }
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val purposeOptions = listOf("Sell", "Rent/Lease", "Paying Guest",)
    var selectedPurpose by remember { mutableStateOf(oldProduct["Purpose"].toString()) }
    val onPurposeChange = { text: String ->
        selectedPurpose = text
    }

    var contactState  by remember { mutableStateOf(propertyViewModel.propertyDetails["Contact"].toString()) }

    val typeOptions = listOf("Apartment",
        "Independent House/Villa",
        "Independent/Builder Floor",
        "Plot/Land","1 Rk/Studio Apartment","Farm House","others")
    var selectedType by remember { mutableStateOf(propertyViewModel.propertyDetails["Type"].toString()) }
    val onTypeChange = { text: String ->
        selectedType = text
    }

    val residentialOptions = listOf("Residential","Commercial")
    var selectedResidential by remember { mutableStateOf(propertyViewModel.propertyDetails["Residential"].toString()) }
    val onResidentialChange = { text: String ->
        selectedResidential = text
    }

    var _city by remember {mutableStateOf(propertyViewModel.propertyDetails["City"].toString()) }
    var _apartment by remember { mutableStateOf(propertyViewModel.propertyDetails["Apartment"].toString()) }
    var _locality by remember { mutableStateOf(propertyViewModel.propertyDetails["Locality"].toString()) }
    var _floorDetails by remember {mutableStateOf(propertyViewModel.propertyDetails["Floor Detail"].toString().trim()) }
    var _areaDetails by remember {mutableStateOf(  propertyViewModel.propertyDetails["Area Details"].toString().trim()) }
    var co: String? = propertyViewModel.propertyDetails["Covered Parking"] as? String
    var op: String? = propertyViewModel.propertyDetails["Open Parking"] as? String
    var _coveredParking by remember { mutableStateOf(co?.toIntOrNull() ?: 0) }
    var _openParking by remember { mutableStateOf(op?.toIntOrNull() ?: 0) }

    val bedroomOptions = listOf("1", "2", "3","4","5+")
    var selectedBedroom by remember { mutableStateOf(propertyViewModel.propertyDetails["Room"].toString()) }

    val onBedroomChange = { text: String ->
        selectedBedroom = text
    }

    val bathroomOptions = listOf("1", "2", "3", "4+")
    var selectedbathroom by remember { mutableStateOf(propertyViewModel.propertyDetails["Bath Room"].toString()) }
    val onBathroomChange = { text: String ->
        selectedbathroom = text
    }

    val balconiesOptions = listOf("0","1", "2","3","more than 3")
    var selectedBalconies by remember {mutableStateOf(propertyViewModel.propertyDetails["Balconies"].toString()) }
    val onBalconiesChange = { text: String ->
        selectedBalconies = text
    }

    val otherRoomOptions = listOf("+ Pooja Room", "+ Study Room",
        "+ Servant Room","+ Others")
    var selectedOtherRoom by remember { mutableStateOf(mutableListOf(propertyViewModel.propertyDetails["Other Room"].toString())) }
    //var selectedOtherRoom = remember { mutableListOf("") }
    val onOtherRoomChange = { text: String ->
        if (selectedOtherRoom.contains(text)){
            selectedOtherRoom.remove(text)
        }else{
            selectedOtherRoom.add(text)
        }
    }

    val furnishingOptions = listOf("Un Furnished", "Semi Furnished","Furnished")
    var selectedFurnishing by remember {mutableStateOf(propertyViewModel.propertyDetails["Furnishing Room"].toString()) }
    val onFurnishingChange = { text: String ->
        selectedFurnishing = text
    }


    val availabilityOptions = listOf("Ready to Move", "Under Construction")
    var selectedAvailability by remember {mutableStateOf(propertyViewModel.propertyDetails["Availability"].toString()) }
    val onAvailabilityChange = { text: String ->
        selectedAvailability = text
    }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
       // uriList.add(uri!!)
        imguri.add(uri.toString())
        showDialog = true
        val uploadTask = storageReference.putFile(uri!!)
        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            storageReference.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUrl = task.result.toString()
                imguri.add(downloadUrl)
                showDialog=false
            } else {
                showDialog=false
                uriList.remove(uri)
                Toast.makeText(context,"something went wrong",Toast.LENGTH_SHORT)
            }
        }
    }

    var _price by remember { mutableStateOf( propertyViewModel.propertyDetails["Price"].toString()) }
    var _description by remember { mutableStateOf(propertyViewModel.propertyDetails["Description"].toString()) }

    Scaffold(
        topBar = { TopAppBar(
            title = {
                Text(text ="Edit Property Deatails",
                    modifier= Modifier.padding(horizontal = 5.dp),
                    color = Color.Black)
            },
            navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(imageVector = Icons.Filled.ArrowBack,
                        contentDescription =null)
                }
            })
        },

        ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {


            Text(
                text = "You are looking to?", color = Color.Black,
                modifier = Modifier.padding(top = 20.dp, start = 10.dp)
            )

            Row(modifier = Modifier.padding(top = 10.dp, start = 10.dp)) {
                purposeOptions.forEach { text ->
                    Row(
                        modifier = Modifier
                            .padding(
                                all = 8.dp,
                            ),
                    ) {

                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodySmall.merge(),
                            color = if (text == selectedPurpose) {
                                Color.Black
                            } else {
                                Color.Black.copy(alpha = 0.8f)
                            },
                            modifier = Modifier
                                .border(
                                    BorderStroke(
                                        1.dp,
                                        if (text == selectedPurpose) {
                                            Color(0xFF5A98EB).copy(alpha = 0.5f)
                                        } else {
                                            Color.LightGray
                                        }
                                    ),
                                    shape = CircleShape
                                )
                                .clip(
                                    shape = CircleShape,
                                )
                                .clickable {
                                    onPurposeChange(text)
                                }
                                .background(
                                    if (text == selectedPurpose) {
                                        Color(0xFF5A98EB).copy(alpha = 0.2f)
                                    } else {
                                        Color.White
                                    }
                                )
                                .padding(
                                    vertical = 12.dp,
                                    horizontal = 16.dp,
                                ),
                        )
                    }
                }
            }

            Text(
                text = "What Kind Of Property?", color = Color.Black,
                modifier = Modifier.padding(top = 20.dp, start = 10.dp)
            )

            Row(modifier = Modifier.padding(top = 10.dp, start = 10.dp)) {
                residentialOptions.forEach { text ->
                    Row(
                        modifier = Modifier
                            .padding(
                                all = 8.dp,
                            ),
                    ) {

                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodySmall.merge(),
                            color =  if (text == selectedResidential) {
                                Color.Black
                            } else {
                                Color.Black.copy(alpha = 0.8f)
                            },
                            modifier = Modifier
                                .border(
                                    BorderStroke(
                                        1.dp,
                                        if (text == selectedResidential) {
                                            Color(0xFF5A98EB).copy(alpha = 0.5f)
                                        } else {
                                            Color.LightGray
                                        }
                                    ),
                                    shape = CircleShape
                                )
                                .clip(
                                    shape = CircleShape,
                                )
                                .clickable {
                                    onResidentialChange(text)
                                }
                                .background(
                                    if (text == selectedResidential) {
                                        Color(0xFF5A98EB).copy(alpha = 0.2f)
                                    } else {
                                        Color.White
                                    }
                                )
                                .padding(
                                    vertical = 12.dp,
                                    horizontal = 16.dp,
                                ),
                        )
                    }
                }
            }

            Text(
                text = "Select Property Type", color = Color.Black,
                modifier = Modifier.padding(top = 20.dp, start = 10.dp)
            )

            FlowRow(modifier = Modifier.padding(top = 10.dp, start = 10.dp)) {
                typeOptions.forEach { text ->
                    Row(
                        modifier = Modifier
                            .padding(
                                all = 8.dp,
                            ),
                    ) {

                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodySmall.merge(),
                            color =  if (text == selectedType) {
                                Color.Black
                            } else {
                                Color.Black.copy(alpha = 0.8f)
                            },
                            modifier = Modifier
                                .border(
                                    BorderStroke(
                                        1.dp,
                                        if (text == selectedType) {
                                            Color(0xFF5A98EB).copy(alpha = 0.5f)
                                        } else {
                                            Color.LightGray
                                        }
                                    ),
                                    shape = CircleShape
                                )
                                .clip(
                                    shape = CircleShape,
                                )
                                .clickable {
                                    onTypeChange(text)
                                }
                                .background(
                                    if (text == selectedType) {
                                        Color(0xFF5A98EB).copy(alpha = 0.2f)
                                    } else {
                                        Color.White
                                    }
                                )
                                .padding(
                                    vertical = 12.dp,
                                    horizontal = 16.dp,
                                ),
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Your Contact Details", color = Color.Black,
                modifier = Modifier.padding(top = 10.dp, start = 10.dp)
            )
            Spacer(modifier = Modifier.height(15.dp))

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp)) {
                CountryCodeChooser(
                    modifier = Modifier
                        .height(55.dp)
                        .width(60.dp)
                        .border(
                            width = 1.dp,
                            shape = RectangleShape,
                            color = Color.Gray
                        ),
                    defaultCountryCode = "44",
                    countryCodeType = CountryCodeType.TEXT,
                    onCountyCodeSelected = { code, codeWithPrefix ->
                        Log.d("SelectedCountry", "$code, $codeWithPrefix")
                    }
                )
                OutlinedTextField(value = contactState, onValueChange = {
                    contactState=it
                },
                    modifier = Modifier
                        .height(55.dp)
                        .fillMaxWidth(),
                    shape = RectangleShape,
                    singleLine = true,
                    placeholder = { Text(text = "Enter Your contact",
                        color = Color.Black.copy(alpha = 0.8f))
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF5A98EB),
                        unfocusedBorderColor = Color.LightGray
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                )
            }

            Text(
                text = "Property Location", color = Color.Black,
                modifier = Modifier.padding(top = 20.dp, start = 10.dp,)
            )

            OutlinedTextField(value = _apartment, onValueChange = {
                _apartment=it
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp, top = 15.dp),
                shape = RectangleShape,
                placeholder = { Text(text = "House No, Street Name",
                    color = Color.Black.copy(alpha = 0.8f))},
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
//                    focusedContainerColor = Color.White,
//                    unfocusedContainerColor = Color.White,
//                    disabledContainerColor = Color.White,
                    focusedBorderColor = Color(0xFF5A98EB),
                    unfocusedBorderColor = Color.LightGray
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            OutlinedTextField(value = _locality, onValueChange = {
                _locality=it
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp, top = 15.dp),
                shape = RectangleShape,
                placeholder = { Text(text = "locality",
                    color = Color.Black.copy(alpha = 0.8f))},
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
//                    focusedContainerColor = Color.White,
//                    unfocusedContainerColor = Color.White,
//                    disabledContainerColor = Color.White,
                    focusedBorderColor = Color(0xFF5A98EB),
                    unfocusedBorderColor = Color.LightGray
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            OutlinedTextField(value = _city, onValueChange = {
                _city=it
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp, top = 15.dp),
                shape = RectangleShape,
                placeholder = { Text(text = "City",
                    color = Color.Black.copy(alpha = 0.8f))},
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor=Color.Black,
                    unfocusedPlaceholderColor=Color.Black,
//                    focusedContainerColor = Color.White,
//                    unfocusedContainerColor = Color.White,
//                    focusedTextColor = Color.Black,
                    disabledContainerColor = Color.White,
                    focusedBorderColor = Color(0xFF5A98EB),
                    unfocusedBorderColor = Color.LightGray
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

//                Row(modifier = Modifier.padding(start = 15.dp, top = 15.dp)) {
//                    Icon(imageVector = Icons.Filled.MyLocation,
//                        contentDescription = null,
//                        tint= Color(0xFF5A98EB),
//                        modifier = Modifier.padding(end = 10.dp,))
//
//                    Text(text = "Use my current location", color = Color(0xFF5A98EB))
//                }

            Text(
                text = "Room Details", color = Color.Black,
                modifier = Modifier.padding(top = 20.dp, start = 10.dp)
            )
//bedroom
            Text(
                text = "No. of bedrooms", color = Color.Black,
                modifier = Modifier.padding(top = 10.dp, start = 10.dp)
            )

            FlowRow(modifier = Modifier.padding(top = 10.dp, start = 10.dp)) {
                bedroomOptions.forEach { text ->
                    Row(
                        modifier = Modifier
                            .padding(
                                all = 8.dp,
                            ),
                    ) {

                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodySmall.merge(),
                            color = if (text == selectedBedroom) {
                                Color.Black
                            } else {
                                Color.Black.copy(alpha = 0.8f)
                            },
                            modifier = Modifier
                                .border(
                                    BorderStroke(
                                        1.dp,
                                        if (text == selectedBedroom) {
                                            Color(0xFF5A98EB).copy(alpha = 0.5f)
                                        } else {
                                            Color.LightGray
                                        }
                                    ),
                                    shape = CircleShape
                                )
                                .clip(
                                    shape = CircleShape,
                                )
                                .clickable {
                                    onBedroomChange(text)
                                }
                                .background(
                                    if (text == selectedBedroom) {
                                        Color(0xFF5A98EB).copy(alpha = 0.2f)
                                    } else {
                                        Color.White
                                    }
                                )
                                .padding(
                                    vertical = 12.dp,
                                    horizontal = 16.dp,
                                ),
                        )
                    }
                }
            }
// bathroom
            Text(
                text = "No. of bathrooms", color = Color.Black,
                modifier = Modifier.padding(top = 20.dp, start = 10.dp)
            )

            FlowRow(modifier = Modifier.padding(top = 10.dp, start = 10.dp)) {
                bathroomOptions.forEach { text ->
                    Row(
                        modifier = Modifier
                            .padding(
                                all = 8.dp,
                            ),
                    ) {

                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodySmall.merge(),
                            color =  if (text == selectedbathroom) {
                                Color.Black
                            } else {
                                Color.Black.copy(alpha = 0.8f)
                            },
                            modifier = Modifier
                                .border(
                                    BorderStroke(
                                        1.dp,
                                        if (text == selectedbathroom) {
                                            Color(0xFF5A98EB).copy(alpha = 0.5f)
                                        } else {
                                            Color.LightGray
                                        }
                                    ),
                                    shape = CircleShape
                                )
                                .clip(
                                    shape = CircleShape,
                                )
                                .clickable {
                                    onBathroomChange(text)
                                }
                                .background(
                                    if (text == selectedbathroom) {
                                        Color(0xFF5A98EB).copy(alpha = 0.2f)
                                    } else {
                                        Color.White
                                    }
                                )
                                .padding(
                                    vertical = 12.dp,
                                    horizontal = 16.dp,
                                ),
                        )
                    }
                }
            }
// balconies
            Text(
                text = "No. of balconies", color = Color.Black,
                modifier = Modifier.padding(top = 20.dp, start = 10.dp)
            )

            FlowRow(modifier = Modifier.padding(top = 10.dp, start = 10.dp)) {
                balconiesOptions.forEach { text ->
                    Row(
                        modifier = Modifier
                            .padding(
                                all = 8.dp,
                            ),
                    ) {

                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodySmall.merge(),
                            color =  if (text == selectedBalconies) {
                                Color.Black
                            } else {
                                Color.Black.copy(alpha = 0.8f)
                            },
                            modifier = Modifier
                                .border(
                                    BorderStroke(
                                        1.dp,
                                        if (text == selectedBalconies) {
                                            Color(0xFF5A98EB).copy(alpha = 0.5f)
                                        } else {
                                            Color.LightGray
                                        }
                                    ),
                                    shape = CircleShape
                                )
                                .clip(
                                    shape = CircleShape,
                                )
                                .clickable {
                                    onBalconiesChange(text)
                                }
                                .background(
                                    if (text == selectedBalconies) {
                                        Color(0xFF5A98EB).copy(alpha = 0.2f)
                                    } else {
                                        Color.White
                                    }
                                )
                                .padding(
                                    vertical = 12.dp,
                                    horizontal = 16.dp,
                                ),
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))

            // Area Details
            Text(
                text = "Area Details", color = Color.Black,
                modifier = Modifier.padding(top = 10.dp, start = 10.dp)
            )
            Spacer(modifier = Modifier.height(15.dp))

            OutlinedTextField(value = _areaDetails,
                onValueChange = {
                    _areaDetails=it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                shape = RectangleShape,
                placeholder = { Text(text = "Carpet Area in sq.ft",
                    color = Color.Black.copy(alpha = 0.8f))},
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    focusedBorderColor = Color(0xFF5A98EB),
                    unfocusedBorderColor = Color.LightGray
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
//other rooms
//            Row(modifier = Modifier.padding(top = 10.dp, start = 10.dp)) {
//                Text(
//                    text = "Any Other Rooms?", color = Color.Black,
//
//                    )
//                Text(
//                    text = "(Optional)", color = Color.LightGray,
//                    modifier = Modifier.padding(start = 10.dp)
//                )
//            }
//
//            FlowRow(modifier = Modifier.padding(top = 10.dp, start = 10.dp)) {
//                otherRoomOptions.forEach { text ->
//                    Row(
//                        modifier = Modifier
//                            .padding(
//                                all = 8.dp,
//                            ),
//                    ) {
//
//                        Text(
//                            text = text,
//                            style = MaterialTheme.typography.bodySmall.merge(),
//                            color =  if (selectedOtherRoom.contains(text)) {
//                                Color.Black
//                            } else {
//                                Color.Black.copy(alpha = 0.8f)
//                            },
//                            modifier = Modifier
//                                .border(
//                                    BorderStroke(
//                                        1.dp,
//                                        if (selectedOtherRoom.contains(text)) {
//                                            Color(0xFF5A98EB).copy(alpha = 0.5f)
//                                        } else {
//                                            Color.LightGray
//                                        }
//                                    ),
//                                    shape = CircleShape
//                                )
//                                .clip(
//                                    shape = CircleShape,
//                                )
//                                .clickable {
//                                    onOtherRoomChange(text)
//                                }
//                                .background(
//                                    if (selectedOtherRoom.contains(text)) {
//                                        Color(0xFF5A98EB).copy(alpha = 0.2f)
//                                    } else {
//                                        Color.White
//                                    }
//                                )
//                                .padding(
//                                    vertical = 12.dp,
//                                    horizontal = 16.dp,
//                                ),
//                        )
//                    }
//                }
//            }
//furnishing
            Row(modifier = Modifier.padding(top = 10.dp, start = 10.dp)) {
                Text(
                    text = "Furnishing Details", color = Color.Black,

                    )
                Text(
                    text = "(Optional)", color = Color.LightGray,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }

            FlowRow(modifier = Modifier.padding(top = 10.dp, start = 10.dp)) {
                furnishingOptions.forEach { text ->
                    Row(
                        modifier = Modifier
                            .padding(
                                all = 8.dp,
                            ),
                    ) {
                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodySmall.merge(),
                            color =  if (text == selectedFurnishing) {
                                Color.Black
                            } else {
                                Color.Black.copy(alpha = 0.8f)
                            },
                            modifier = Modifier
                                .border(
                                    BorderStroke(
                                        1.dp,
                                        if (text == selectedFurnishing) {
                                            Color(0xFF5A98EB).copy(alpha = 0.5f)
                                        } else {
                                            Color.LightGray
                                        }
                                    ),
                                    shape = CircleShape
                                )
                                .clip(
                                    shape = CircleShape,
                                )
                                .clickable {
                                    onFurnishingChange(text)
                                }
                                .background(
                                    if (text == selectedFurnishing) {
                                        Color(0xFF5A98EB).copy(alpha = 0.2f)
                                    } else {
                                        Color.White
                                    }
                                )
                                .padding(
                                    vertical = 12.dp,
                                    horizontal = 16.dp,
                                ),
                        )
                    }
                }
            }

//parking
            Row(modifier = Modifier.padding(top = 10.dp, start = 10.dp)) {
                Text(
                    text = "Reserved Parking", color = Color.Black,

                    )
                Text(
                    text = "(Optional)", color = Color.LightGray,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = "No. Covered Parking",  color = Color.Black.copy(alpha = 0.8f),
                    modifier = Modifier.padding(top = 10.dp, start = 10.dp)
                )
                Row(verticalAlignment= Alignment.CenterVertically) {
                    Text(
                        text = "-",
                        style = MaterialTheme.typography.bodySmall.merge(),
                        color = Color.Black.copy(alpha = 0.8f),
                        modifier = Modifier
                            .padding(all = 0.5.dp)
                            .border(
                                BorderStroke(1.dp, Color.LightGray),
                                shape = CircleShape
                            )
                            .clip(shape = CircleShape,)
                            .clickable {
                                if (_coveredParking != 0) {
                                    --_coveredParking
                                }
                            }
                            .background(Color.White)
                            .padding(
                                vertical = 12.dp,
                                horizontal = 16.dp,
                            ),
                    )
                    Text(text = _coveredParking.toString(), modifier = Modifier.padding(horizontal = 10.dp))
                    Text(
                        text = "+",
                        style = MaterialTheme.typography.bodySmall.merge(),
                        color = Color.Black.copy(alpha = 0.8f),
                        modifier = Modifier
                            .border(
                                BorderStroke(1.dp, Color.LightGray),
                                shape = CircleShape
                            )
                            .clip(shape = CircleShape,)
                            .clickable {

                                ++_coveredParking

                            }
                            .background(Color.White)
                            .padding(
                                vertical = 12.dp,
                                horizontal = 16.dp,
                            ),
                    )
                }
            }

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, end = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = "No. Open Parking",color = Color.Black.copy(alpha = 0.8f),
                    modifier = Modifier.padding(top = 10.dp, start = 10.dp)
                )
                Row(verticalAlignment= Alignment.CenterVertically) {
                    Text(
                        text = "-",
                        style = MaterialTheme.typography.bodySmall.merge(),
                        color = Color.Black.copy(alpha = 0.8f),
                        modifier = Modifier
                            .padding(all = 0.5.dp)
                            .border(
                                BorderStroke(1.dp, Color.LightGray),
                                shape = CircleShape
                            )
                            .clip(shape = CircleShape,)
                            .clickable {
                                if (_openParking != 0) {
                                    --_openParking
                                }
                            }
                            .background(Color.White)
                            .padding(
                                vertical = 12.dp,
                                horizontal = 16.dp,
                            ),
                    )
                    Text(text = _openParking.toString(),
                        modifier = Modifier.padding(horizontal = 10.dp))
                    Text(
                        text = "+",
                        style = MaterialTheme.typography.bodySmall.merge(),
                        color = Color.Black.copy(alpha = 0.8f),
                        modifier = Modifier
                            .border(
                                BorderStroke(1.dp, Color.LightGray),
                                shape = CircleShape
                            )
                            .clip(shape = CircleShape,)
                            .clickable {
                                ++_openParking
                            }
                            .background(Color.White)
                            .padding(
                                vertical = 12.dp,
                                horizontal = 16.dp,
                            ),
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            //floor details
            Text(
                text = "Floor Details", color = Color.Black,
                modifier = Modifier.padding(top = 10.dp, start = 10.dp)
            )
            Spacer(modifier = Modifier.height(15.dp))

            OutlinedTextField(value = _floorDetails, onValueChange = {
                _floorDetails=it
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                shape = RectangleShape,

                placeholder = { Text(text = "Total Floors",
                    color = Color.Black.copy(alpha = 0.8f))},
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    focusedBorderColor = Color(0xFF5A98EB),
                    unfocusedBorderColor = Color.LightGray
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

// availability

            Text(
                text = "Availability", color = Color.Black,
                modifier = Modifier.padding(top = 20.dp, start = 10.dp)
            )

            FlowRow(modifier = Modifier.padding(top = 10.dp, start = 10.dp)) {
                availabilityOptions.forEach { text ->
                    Row(
                        modifier = Modifier
                            .padding(
                                all = 8.dp,
                            ),
                    ) {

                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodySmall.merge(),
                            color =  if (text == selectedAvailability) {
                                Color.Black
                            } else {
                                Color.Black.copy(alpha = 0.8f)
                            },
                            modifier = Modifier
                                .border(
                                    BorderStroke(
                                        1.dp,
                                        if (text == selectedAvailability) {
                                            Color(0xFF5A98EB).copy(alpha = 0.5f)
                                        } else {
                                            Color.LightGray
                                        }
                                    ),
                                    shape = CircleShape
                                )
                                .clip(
                                    shape = CircleShape,
                                )
                                .clickable {
                                    onAvailabilityChange(text)
                                }
                                .background(
                                    if (text == selectedAvailability) {
                                        Color(0xFF5A98EB).copy(alpha = 0.2f)
                                    } else {
                                        Color.White
                                    }
                                )
                                .padding(
                                    vertical = 12.dp,
                                    horizontal = 16.dp,
                                ),
                        )
                    }
                }
            }



            Text(
                text = "Pricing Details",
                color = Color.Black,
                modifier = Modifier.padding(top = 20.dp, start = 10.dp,)
            )

            OutlinedTextField(
                value = _price,
                onValueChange = {
                    _price=it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp, top = 15.dp),
                shape = RectangleShape,
                singleLine = true,
                placeholder = {
                    Text(
                        text = "Amount in GBP (£)",
                        color = Color.Black.copy(alpha = 0.8f)
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF5A98EB),
                    unfocusedBorderColor = Color.LightGray
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Text(
                text = "Property Description",
                color = Color.Black,
                modifier = Modifier.padding(top = 20.dp, start = 10.dp,)
            )

            OutlinedTextField(
                value = _description,
                onValueChange = {
                    _description=it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp, top = 15.dp),
                shape = RectangleShape,
                placeholder = {
                    Text(
                        text = "Describe about your property",
                        color = Color.Black.copy(alpha = 0.8f)
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF5A98EB),
                    unfocusedBorderColor = Color.LightGray
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )


//            Column {
//
//                if (imguri != null) {
//
//                    LazyRow {
//                        items(imguri) { item ->
//                            val painter = rememberAsyncImagePainter(
//                                ImageRequest
//                                    .Builder(LocalContext.current)
//                                    .data(data = item)
//                                    .build()
//                            )
//                            Image(
//                                painter = painter,
//                                contentDescription = null,
//                                modifier = Modifier
//                                    .width(100.dp) // Adjust the width as per your requirement
//                                    .height(100.dp)
//                                    .padding(4.dp) // Add padding between images
//                            )
//
//                            IconButton(onClick = { imguri.remove(item) }) {
//                                Icon(imageVector = Icons.Filled.Delete, contentDescription = null)
//                            }
//                        }
//                    }
//                }
//
//                Column(horizontalAlignment = Alignment.CenterHorizontally) {
//
//                    Icon(
//                        imageVector = Icons.Filled.Image,
//                        contentDescription = null
//                    )
//
//                    Text(
//                        text = "+ Add Photos",
//                        color = Color(0xFF5A98EB),
//                        modifier = Modifier.padding(top = 8.dp)
//                    )
//                    Text(
//                        text = "Upload Upto 20 photos max size 10mb in formats png, jpg, jpeg",
//                        color = Color.Black.copy(alpha = 0.4f),
//                        modifier = Modifier.padding(top = 8.dp),
//                        fontSize = 10.sp
//                    )
//                }
//            }

//            if (showDialogEd) {
//                Dialog(
//                    onDismissRequest = { showDialogEd = false },
//                    DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
//                ) {
//                    Box(
//                        contentAlignment= Alignment.Center,
//                        modifier = Modifier
//                            .size(200.dp)
//                            .background(Color.White, shape = RoundedCornerShape(8.dp))
//                    ) {
//                        Column(modifier = Modifier.align(alignment = Alignment.Center),
//                            horizontalAlignment = Alignment.CenterHorizontally,
//                            verticalArrangement = Arrangement.Center) {
//                            Text("Uploading")
//                            Spacer(modifier = Modifier.height(15.dp))
//                            CircularProgressIndicator()
//                        }
//                    }
//                }
//            }

            Box(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .background(
                        Color.LightGray.copy(alpha = 0.4f),
                        shape = RectangleShape
                    )
                    .padding(start = 10.dp, end = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                TextButton(
                    shape= RectangleShape,
                    onClick = {

                        launcher.launch(
                            PickVisualMediaRequest(
                                mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )

                    }) {


                    Column {

                        if (imguri != null) {

                            LazyRow {
                                items(imguri.toList()) { item ->

                                    Column {
                                        AsyncImage(
                                            model = item,
//                                            placeholder = painterResource(id = R.drawable.holderimage),
//                                            error = painterResource(id = R.drawable.holderimage),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .width(100.dp) // Adjust the width as per your requirement
                                                .height(100.dp)
                                                .padding(4.dp),
                                            contentScale= ContentScale.FillWidth
                                        )

//
//                                       IconButton(onClick = {
//                                          imguri.remove(item)
//                                       }) {
//                                           Icon(
//                                               imageVector = Icons.Filled.Delete,
//                                               contentDescription = null
//                                           )
//                                        }
                                    }
                                }
                            }
                        }


                        Column(horizontalAlignment = Alignment.CenterHorizontally) {

                            Text(
                                text = "+ Add Photos",
                                color = Color(0xFF5A98EB),
                                modifier = Modifier.padding(top = 8.dp)
                            )

                        }
                    }
                }
            }


            if (showDialog) {
                Dialog(
                    onDismissRequest = { showDialog = false },
                    DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
                ) {
                    Box(
                        contentAlignment= Alignment.Center,
                        modifier = Modifier
                            .size(200.dp)
                            .background(Color.White, shape = RoundedCornerShape(8.dp))
                    ) {
                        Column(modifier = Modifier.align(alignment = Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center) {
                            Text("Uploading")
                            Spacer(modifier = Modifier.height(15.dp))
                            CircularProgressIndicator()
                        }
                    }
                }
            }


            TextButton(
                onClick = {
                    if (contactState.isNullOrEmpty()){
                        Toast.makeText(context,"Enter contact details", Toast.LENGTH_SHORT).show()
                    }else{
                        propertyViewModel.propertyDetails["Apartment"] = _apartment
                        propertyViewModel.propertyDetails["Locality"] = _locality

                        propertyViewModel.propertyDetails["Room"] = selectedBedroom
                        propertyViewModel.propertyDetails["Bath Room"] = selectedbathroom
                        propertyViewModel.propertyDetails["Balconies"] = selectedBalconies
                        propertyViewModel.propertyDetails["Other Room"] = if (!selectedOtherRoom.isNullOrEmpty()){
                            selectedOtherRoom[0]
                        }else{
                            "0"
                        }
                        propertyViewModel.propertyDetails["Purpose"] = selectedPurpose
                        propertyViewModel.  propertyDetails["Residential"] = selectedResidential
                        propertyViewModel. propertyDetails["Type"] =selectedType

                        propertyViewModel.propertyDetails["Furnishing Room"] = selectedFurnishing
                        propertyViewModel.propertyDetails["Availability"] = selectedAvailability
                        propertyViewModel.propertyDetails["Area Details"] = _areaDetails
                        propertyViewModel.propertyDetails["Floor Detail"] =_floorDetails
                        propertyViewModel.propertyDetails["Open Parking"] = _openParking.toString()
                        propertyViewModel.propertyDetails["Covered Parking"] = _coveredParking.toString()
                        propertyViewModel.propertyDetails["Price"] = _price

                        propertyViewModel.propertyDetails["Images"] = imguri.toList()

                        UpdateList(oldProduct,authEd,
                            referenceEd,navController,context,_city,_description,contactState)
//                        navController.navigate("addProperty2")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp, bottom = 50.dp, top = 25.dp),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF5A98EB),
                    contentColor = Color.White)
            ) {
                Text(text = "Next", fontWeight = FontWeight.Bold, fontSize = 18.sp
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
        }
    }

}



fun UpdateList(
    product: MutableMap<String, Any>,
    authEd: FirebaseAuth,
    referenceEd: DatabaseReference,
    navController: NavHostController,
    context: Context,
    _city: String,
    _description: String,
    contactState: String
) {

    val uid = authEd.currentUser!!.uid
    Toast.makeText(context,"pls Wait",Toast.LENGTH_SHORT).show()
    referenceEd.child("User Info").child(uid).child("Properties")
        .addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (productSnapshot in snapshot.children) {

                    val _product = productSnapshot.value as Map<String, Any>
                    if ((product["Description"].toString() == _product["Description"].toString()) &&
                        (product["City"].toString() == _product["City"].toString()) &&
                        (product["Contact"].toString() == _product["Contact"].toString())
                    ){

                        product["City"] = _city
                        product["Description"] = _description
                        product["Contact"]=contactState

                        referenceEd.child("User Info").child(uid)
                            .child("Properties")
                            .child(productSnapshot.key!!).updateChildren(product).addOnCompleteListener {
                                if (it.isSuccessful) {
                                  navController.navigate("Home")
                                }else{
                                    Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show()
                                }
                            }
                    }else{

                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,"Something went wrong",Toast.LENGTH_SHORT).show()
            }
        })
}

