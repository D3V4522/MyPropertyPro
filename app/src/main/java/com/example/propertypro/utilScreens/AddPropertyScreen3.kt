package com.example.propertypro.utilScreens

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import okhttp3.internal.wait
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AddPropertyScreen3(navController: NavHostController, propertyViewModel: PropertyViewModel) {
    var _price by remember { mutableStateOf("") }
    var _description by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val uriList = remember { mutableStateListOf<Uri>() }
    val imguri = remember { mutableStateListOf<String>() }

    val database = FirebaseDatabase.getInstance()
    val auth = FirebaseAuth.getInstance()
    val reference = database.reference
    val uid = auth.currentUser!!.uid
    val storageReference = FirebaseStorage.getInstance().reference.child("images")
        .child(uid).child(UUID.randomUUID().toString())


    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        uriList.add(uri!!)
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
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Photo & Pricing Detail",
                        modifier = Modifier.padding(horizontal = 5.dp),
                        color = Color.Black
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack()}) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Step 3/3",
                color = Color.Black.copy(alpha = 0.8f),
                modifier = Modifier.padding(top = 10.dp, start = 10.dp)
            )

            Text(
                text = "Add Property Photos",
                color = Color.Black,
                modifier = Modifier.padding(top = 20.dp, start = 10.dp)
            )

            Spacer(modifier = Modifier.height(15.dp))

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

                        if (uriList != null) {

                            LazyRow {
                                items(uriList) { item ->
                                    val painter = rememberAsyncImagePainter(
                                        ImageRequest
                                            .Builder(LocalContext.current)
                                            .data(data = item)
                                            .build()
                                    )
                                    Image(
                                        painter = painter,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .width(100.dp) // Adjust the width as per your requirement
                                            .height(100.dp)
                                            .padding(4.dp) // Add padding between images
                                    )
                                }
                            }
                        }


                        Column(horizontalAlignment = Alignment.CenterHorizontally) {

                            Icon(
                                imageVector = Icons.Filled.Image,
                                contentDescription = null
                            )


                            Text(
                                text = "+ Add Photos",
                                color = Color(0xFF5A98EB),
                                modifier = Modifier.padding(top = 8.dp)
                            )
                            Text(
                                text = "Upload Upto 20 photos max size 10mb in formats png, jpg, jpeg",
                                color = Color.Black.copy(alpha = 0.4f),
                                modifier = Modifier.padding(top = 8.dp),
                                fontSize = 10.sp
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
                modifier = Modifier.padding(top = 20.dp, start = 10.dp, )
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

            TextButton(
                onClick = {
                    if (_description.isNullOrEmpty() && _price.isNullOrEmpty()) {
                        Toast.makeText(context, "Provide all details", Toast.LENGTH_SHORT).show()
                    } else {
                        propertyViewModel.propertyDetails["Price"] = _price
                        propertyViewModel.propertyDetails["Description"] = _description
                        propertyViewModel.propertyDetails["Images"] = imguri as List<String>

                        uploadProperty(propertyViewModel,navController,
                            context,reference,auth)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp, bottom = 20.dp, top = 40.dp),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF5A98EB),
                    contentColor = Color.White
                )
            ) {
                Text(text = "Next", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}


fun uploadProperty(
    propertyViewModel: PropertyViewModel,
    navController: NavHostController,
    context: Context,
    reference: DatabaseReference,
    auth: FirebaseAuth
) {

    val uid= auth.currentUser!!.uid
    reference.child("User Info").child(uid).
            child("Properties").push()
        .setValue(propertyViewModel.propertyDetails)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                 navController.navigate("myProperty")
                Toast.makeText(context,"Posted",Toast.LENGTH_LONG).show()
                print( "Data saved successfully")
            } else {
                // Handle the error
                val e = task.exception
                Toast.makeText(context,"$e",Toast.LENGTH_LONG).show()
                print("Error saving data to the database $e", )
            }
        }

        reference.child("All Products").push().setValue(propertyViewModel.propertyDetails)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
               // navController.navigate()
                Toast.makeText(context,"Posted",Toast.LENGTH_LONG).show()
                print( "Data saved successfully")
            } else {
                // Handle the error
                val e = task.exception
                Toast.makeText(context,"$e",Toast.LENGTH_LONG).show()
               print("Error saving data to the database $e", )
            }
        }
}







