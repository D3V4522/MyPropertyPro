package com.example.propertypro.utilScreens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AddPropertyScreen2(navController: NavHostController, propertyViewModel: PropertyViewModel) {


    val context = LocalContext.current
    var _city by remember {mutableStateOf("") }
    var _apartment by remember { mutableStateOf("") }
    var _locality by remember { mutableStateOf("") }
    var _floorDetails by remember {mutableStateOf("") }
    var _areaDetails by remember {mutableStateOf("") }
    var _coveredParking by remember { mutableIntStateOf(0) }
    var _openParking by remember {mutableIntStateOf(0) }

    val bedroomOptions = listOf("1", "2", "3","4","5+")
    var selectedBedroom by remember { mutableStateOf("1") }

    val onBedroomChange = { text: String ->
        selectedBedroom = text
    }

    val bathroomOptions = listOf("1", "2", "3", "4+")
    var selectedbathroom by remember { mutableStateOf("1") }
    val onBathroomChange = { text: String ->
        selectedbathroom = text
    }

    val balconiesOptions = listOf("0","1", "2","3","more than 3")
    var selectedBalconies by remember {mutableStateOf("0") }
    val onBalconiesChange = { text: String ->
        selectedBalconies = text
    }

    val otherRoomOptions = listOf("+ Pooja Room", "+ Study Room",
        "+ Servant Room","+ Others")
    var selectedOtherRoom by remember { mutableStateOf(mutableListOf("")) }
    //var selectedOtherRoom = remember { mutableListOf("") }
    val onOtherRoomChange = { text: String ->
        if (selectedOtherRoom.contains(text)){
            selectedOtherRoom.remove(text)
        }else{
            selectedOtherRoom.add(text)
        }

    }

    val furnishingOptions = listOf("Un Furnished", "Semi Furnished","Furnished")
    var selectedFurnishing by remember {mutableStateOf("") }
    val onFurnishingChange = { text: String ->
        selectedFurnishing = text
    }

    val availabilityOptions = listOf("Ready to Move", "Under Construction")
    var selectedAvailability by remember {mutableStateOf("") }
    val onAvailabilityChange = { text: String ->
        selectedAvailability = text
    }

    Scaffold(
        topBar = { TopAppBar(
            title = {
                Text(text ="Add Property Details",
                    modifier= Modifier.padding(horizontal = 5.dp),
                    color = Color.Black)
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack()}) {
                    Icon(imageVector =Icons.Filled.ArrowBack,
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
                text = "STEP 2 OF 3", color = Color.Black.copy(alpha = 0.8f),
                //fontWeight = ,
                modifier = Modifier.padding(top = 10.dp, start = 10.dp)
            )

    //location-------------------------------------------------
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
                            style = typography.bodySmall.merge(),
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
                            style = typography.bodySmall.merge(),
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
                            style = typography.bodySmall.merge(),
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
//                            style = typography.bodySmall.merge(),
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
//                                    if ( selectedOtherRoom.contains(text)) {
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
                            style = typography.bodySmall.merge(),
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
                        style = typography.bodySmall.merge(),
                        color = Color.Black.copy(alpha = 0.8f),
                        modifier = Modifier.padding(all = 0.5.dp)
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
                        style = typography.bodySmall.merge(),
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
                        style = typography.bodySmall.merge(),
                        color = Color.Black.copy(alpha = 0.8f),
                        modifier = Modifier.padding(all = 0.5.dp)
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
                        style = typography.bodySmall.merge(),
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
                            style = typography.bodySmall.merge(),
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
// next btn
            TextButton(
                onClick = {

                    if (!_city.isNullOrEmpty() && !_areaDetails.isNullOrEmpty() && !_floorDetails.isNullOrEmpty()){
                        propertyViewModel.propertyDetails["Apartment"] = _apartment
                        propertyViewModel.propertyDetails["Locality"] = _locality
                        propertyViewModel.propertyDetails["City"] = _city
                        propertyViewModel.propertyDetails["Room"] = selectedBedroom
                        propertyViewModel.propertyDetails["Bath Room"] = selectedbathroom
                        propertyViewModel.propertyDetails["Balconies"] = selectedBalconies
                        propertyViewModel.propertyDetails["Other Room"] = if (!selectedOtherRoom.isNullOrEmpty()){
                            selectedOtherRoom
                        }else{
                            "-"
                        }
                        propertyViewModel.propertyDetails["Furnishing Room"] = selectedFurnishing
                        propertyViewModel.propertyDetails["Availability"] = selectedAvailability
                        propertyViewModel.propertyDetails["Area Details"] = _areaDetails
                        propertyViewModel.propertyDetails["Floor Detail"] =_floorDetails
                        propertyViewModel.propertyDetails["Open Parking"] = _openParking.toString()
                        propertyViewModel.propertyDetails["Covered Parking"] = _coveredParking.toString()
                        navController.navigate("addProperty3")
                    }else{
                        Toast.makeText(context,"Provide all Essential Details",Toast.LENGTH_SHORT).show()
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