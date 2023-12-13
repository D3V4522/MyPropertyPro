package com.example.propertypro.utilScreens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.owlbuddy.www.countrycodechooser.CountryCodeChooser
import com.owlbuddy.www.countrycodechooser.utils.enums.CountryCodeType

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AddPropertyScreen1(navController: NavHostController, propertyViewModel: PropertyViewModel) {

    val context = LocalContext.current
    val purposeOptions = listOf("Sell", "Rent/Lease", "Paying Guest",)
    var selectedPurpose by remember { mutableStateOf("Rent/Lease") }
    val onPurposeChange = { text: String ->
        selectedPurpose = text
    }

    var contactState  by remember { mutableStateOf("") }

    val typeOptions = listOf("Apartment",
        "Independent House/Villa",
        "Independent/Builder Floor",
        "Plot/Land","1 Rk/Studio Apartment","Farm House","others")
    var selectedType by remember { mutableStateOf("Independent House/Villa") }
    val onTypeChange = { text: String ->
        selectedType = text
    }

    val residentialOptions = listOf("Residential","Commercial")
    var selectedResidential by remember {mutableStateOf("Residential") }
    val onResidentialChange = { text: String ->
        selectedResidential = text
    }

    Scaffold(
        topBar = { TopAppBar(
            title = {
                Text(text ="Add Basic Details",
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
                text = "Step 1/3", color = Color.Black.copy(alpha = 0.8f),
                //fontWeight = ,
                modifier = Modifier.padding(top = 10.dp, start = 10.dp)
            )

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
                            style = typography.bodySmall.merge(),
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
                            style = typography.bodySmall.merge(),
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
                            style = typography.bodySmall.merge(),
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
                        color = Color.Black.copy(alpha = 0.8f))},
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF5A98EB),
                        unfocusedBorderColor = Color.LightGray
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                )
            }
            TextButton(
                onClick = {
                          if (contactState.isNullOrEmpty()){
                              Toast.makeText(context,"Enter contact details",Toast.LENGTH_SHORT).show()
                          }else{
                          propertyViewModel.propertyDetails["Purpose"] = selectedPurpose
                              propertyViewModel.  propertyDetails["Residential"] = selectedResidential
                              propertyViewModel. propertyDetails["Type"] =selectedType
                              propertyViewModel. propertyDetails["Contact"]=contactState
                              navController.navigate("addProperty2")
                          }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp, bottom = 25.dp, top = 25.dp),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF5A98EB),
                    contentColor = Color.White)
            ) {
                Text(text = "Next", fontWeight = FontWeight.Bold, fontSize = 18.sp
                )
            }

        }
    }
}