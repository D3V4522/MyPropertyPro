package com.example.propertypro.NavigationScreens


import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import coil.compose.AsyncImage
import com.example.propertypro.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID


@Composable
fun ProfileScreen(navController: NavHostController, navControllerHome: NavHostController) {
    val database = FirebaseDatabase.getInstance()
    val auth = FirebaseAuth.getInstance()
    val reference = database.reference
   val context = LocalContext.current
    var _name by remember { mutableStateOf("John") }
    var _email by remember { mutableStateOf("Example: John@gmail.com") }
    var _img by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LaunchedEffect(true) {
            // retrieveUserInfo(auth)
            retrieveUserInfo(auth) { userInfoList ->
                if (userInfoList != null) {
                    for (userInfo in userInfoList) {
                        _name = userInfo["Name"] as String
                        _email = userInfo["Email"] as String
                       // _img = (userInfo["Image"] as? String).toString()
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfileImage(modifier = Modifier
                .size(120.dp)
                .clip(MaterialTheme.shapes.small))
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = _name,
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = _email,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(15.dp))
            LogoutButton(onLogoutClick = {
                auth.signOut()
                val navOptions = NavOptions.Builder()
                    .setPopUpTo(Destinations.AccountScreen.route, true)
                    .build()
                navControllerHome.navigate("login", navOptions)

            })
        }

//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            ProfileImage(modifier = Modifier
//                .size(200.dp)
//                .clip(MaterialTheme.shapes.medium),context,_img)
//            Spacer(modifier = Modifier.height(16.dp))
//            Text(
//                text = _name,
//                style = MaterialTheme.typography.titleMedium
//                    .copy(fontWeight = FontWeight.Bold),
//                color = MaterialTheme.colorScheme.onSurface
//            )
//            Spacer(modifier = Modifier.height(4.dp))
//            Text(
//                text = _email,
//                style = MaterialTheme.typography.bodySmall,
//                color = MaterialTheme.colorScheme.onSurface
//            )

//
//
//        }
    }
}

@Composable
fun LogoutButton(onLogoutClick: () -> Unit) {
    Button(
        onClick = onLogoutClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Default.Logout, contentDescription = null, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Logout")
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
//@Composable
//fun ProfileImage(modifier: Modifier = Modifier, context: Context, _img: String) {
//
//    var _editing by remember { mutableStateOf(false) }
//    var imguri by remember { mutableStateOf(_img) }
//
//    val database = FirebaseDatabase.getInstance()
//    val auth = FirebaseAuth.getInstance()
//    val reference = database.reference
//    val uid = auth.currentUser!!.uid
//    var showDialog by remember { mutableStateOf(false) }
//    val storageReference = FirebaseStorage.getInstance().reference.child("images")
//        .child(uid).child(UUID.randomUUID().toString())
//
//
//    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
////        uriList.add(uri!!)
////        showDialog = true
//        val uploadTask = storageReference.putFile(uri!!)
//        uploadTask.continueWithTask { task ->
//            if (!task.isSuccessful) {
//                task.exception?.let {
//                    throw it
//                }
//            }
//            storageReference.downloadUrl
//        }.addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                val downloadUrl = task.result.toString()
//                imguri=downloadUrl
//
//                val uid=auth.currentUser!!.uid
//
//                val map= mutableMapOf<String,String>()
//                map.put("Image",imguri)
//                reference.child("User Info").child(uid!!)
//                    .child("Login Info").push().setValue(map)
//                showDialog=false
//            } else {
//                showDialog=false
//               // uriList.remove(uri)
//                Toast.makeText(context,"something went wrong", Toast.LENGTH_SHORT)
//            }
//        }
//    }
//
//    Surface(
//        modifier = modifier,
//        shape = MaterialTheme.shapes.small,
//        onClick = {
//            // Handle image click
//
//            launcher.launch(
//                PickVisualMediaRequest(
//                    mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
//                )
//            )
//        },
//        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(8.dp)
//        ) {
//
//            // Placeholder image
//
//           if(imguri != null){
//
//               AsyncImage(
//                   model = imguri,
//                   placeholder = painterResource(id = R.drawable.holderimage),
//                   error = painterResource(id = R.drawable.holderimage),
//                   contentDescription = null,
//                   modifier = Modifier.clip(shape = CircleShape)
//                       .fillMaxSize(),
//                   contentScale= ContentScale.Fit
//               )
//
//           }else{
//               Icon(
//                   imageVector = Icons.Default.Person,
//                   contentDescription = null,
//                   modifier = Modifier
//                       .fillMaxSize()
//                       .padding(8.dp),
//                   tint = MaterialTheme.colorScheme.onSurface
//               )
//           }
//
//
//            // Edit icon
//            if (_editing) {
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .background(MaterialTheme.colorScheme.background.copy(alpha = 0.8f))
//                        .clickable {
//                            // Handle edit click
//                        }
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.Settings,
//                        contentDescription = null,
//                        modifier = Modifier
//                            .size(24.dp)
//                            .padding(8.dp),
//                        tint = MaterialTheme.colorScheme.onPrimary
//                    )
//                }
//            }
//
//            if (showDialog) {
//                Dialog(
//                    onDismissRequest = { showDialog = false },
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
//
//        }
//    }
//}


@Composable
fun ProfileImage(modifier: Modifier = Modifier) {
    val context = LocalContext.current
//    val uriHandler = LocalUriHandler.current
//    val density = LocalDensity.current.density
//    val keyboardController = LocalSoftwareKeyboardController.current
//    val densityOwner = LocalDensityOwner.current
//    val uriHandlerOwner = LocalUriHandlerOwner.current
    var _imageUri by remember { mutableStateOf("") }
    var _editing by remember { mutableStateOf(false) }
    var _tempImageUri by remember { mutableStateOf("") }
    var _errorState by remember { mutableStateOf(ErrorState.None) }

    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        onClick = {
            // Handle image click
        },
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            // Add your image loading logic here

            // Placeholder image
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )

            // Edit icon
            if (_editing) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background.copy(alpha = 0.8f))
                        .clickable {
                            // Handle edit click
                        }
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .padding(8.dp),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            // Error icon
            if (_errorState != ErrorState.None) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.error.copy(alpha = 0.8f))
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .padding(8.dp),
                        tint = MaterialTheme.colorScheme.onError
                    )
                }
            }
        }
    }
}

sealed class ErrorState {
    object None : ErrorState()
    object ImageLoadError : ErrorState()
    object ImageUploadError : ErrorState()
}


fun retrieveUserInfo(auth: FirebaseAuth,
                     callback: (List<Map<String, String>>?) -> Unit) {

    if (auth.currentUser != null){
        val uid = auth.currentUser!!.uid
        val reference = FirebaseDatabase.getInstance().getReference("User Info").child(uid).child("Login Info")

        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val userInfoList = mutableListOf<Map<String, String>>()
                    for (childSnapshot in dataSnapshot.children) {
                        val typeIndicator = object : GenericTypeIndicator<Map<String, String>>() {}
                        val userInfo = childSnapshot.getValue(typeIndicator)
                        if (userInfo != null) {
                            userInfoList.add(userInfo)
                        }
                    }

                    callback(userInfoList)
                } else {
                    callback(null)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                callback(null)
            }
        })
    }

}