package com.example.propertypro

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.example.propertypro.NavigationScreens.auth
import com.example.propertypro.NavigationScreens.reference
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController){

    val ref=FirebaseDatabase.getInstance().reference
    val auth = FirebaseAuth.getInstance()
    var nameState by remember {mutableStateOf("") }
    var emailState  by remember { mutableStateOf("") }
    var passwordState by remember { mutableStateOf("") }
    var cxt = LocalContext.current
    var passwordVisible by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {

        Image(painter = painterResource(id = R.drawable.backround),
            contentDescription =null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize())

        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "PropertyPro", fontSize = 30.sp,fontFamily = FontFamily.SansSerif)
            Text(text = "Your Key to Seamless Property Discovery", fontSize = 14.sp, fontFamily = FontFamily.Cursive)
            Spacer(modifier = Modifier.height(60.dp))

            Text(text = "Register", color = Color.White,fontSize = 30.sp)
            Spacer(modifier = Modifier.height(30.dp))


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
                            Text("Please Wait")
                            Spacer(modifier = Modifier.height(10.dp))
                            CircularProgressIndicator()
                        }
                    }
                }

            }

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                value = nameState,
                keyboardOptions =  KeyboardOptions(keyboardType = KeyboardType.Text),
                label = { Text("User Name") },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    cursorColor = Color.Black,
                    disabledLabelColor = Color.LightGray,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                onValueChange = {
                    nameState = it
                },
                shape = RoundedCornerShape(20.dp),
                singleLine = true,
            )
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                value = emailState,
                keyboardOptions =  KeyboardOptions(keyboardType = KeyboardType.Email),
                label = { Text("Enter Mail-ID") },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    cursorColor = Color.Black,
                    disabledLabelColor = Color.LightGray,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                onValueChange = {
                    emailState = it
                },
                shape = RoundedCornerShape(20.dp),
                singleLine = true,
            )
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                value = passwordState,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions =  KeyboardOptions(keyboardType = KeyboardType.Password),
                label = { Text("Enter Password") },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    cursorColor = Color.Black,
                    disabledLabelColor = Color.LightGray,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),

                onValueChange = {
                    passwordState = it
                },
                shape = RoundedCornerShape(20.dp),
                singleLine = true,

                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff

                    // Localized description for accessibility services
                    val description = if (passwordVisible) "Hide password" else "Show password"

                    // Toggle button to hide or display password
                    IconButton(onClick = {passwordVisible = !passwordVisible}){
                        Icon(imageVector  = image, description)
                    }
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = {
                if (nameState.isNotEmpty() && nameState.length >=3) {
                    if (emailState.isNotEmpty()) {
                        if (isValidEmail(emailState)) {
                            if (passwordState.length >= 6) {
                                showDialog = true
                                auth.createUserWithEmailAndPassword(emailState, passwordState)
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            addUserInfo(ref, nameState,emailState)
                                            showDialog = false
                                            Toast.makeText(cxt, "Success", Toast.LENGTH_LONG).show()
                                            val navOptions = NavOptions.Builder()
                                                .setPopUpTo("register", true)
                                                .build()
                                            navController.navigate("Home", navOptions)
                                        } else {
                                            showDialog = false
                                            val exception = task.exception
                                            Toast.makeText(cxt, "$exception", Toast.LENGTH_LONG)
                                                .show()
                                        }
                                    }
                            } else {
                                Toast.makeText(
                                    cxt,
                                    "Password should be 6 characters or longer.",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        } else {
                            Toast.makeText(cxt, "Enter valid Email!", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Toast.makeText(cxt, "Enter Email!", Toast.LENGTH_LONG).show()
                    }
                }else{
                    Toast.makeText(cxt, "Name should be 3 characters or longer. ", Toast.LENGTH_LONG).show()
                }
            },
                modifier = Modifier
                    .padding(all = Dp(10F))
                    .width(200.dp),
                enabled = true,
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.textButtonColors(
                    containerColor = Color.Black.copy(alpha = 0.6f),
                    contentColor = Color.White))
            {
                Text(text = "Register", color = Color.White)
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically ) {
                Text(text = "Already have an account?")
                TextButton(
                    onClick = {
                              navController.navigate("login")
                    },
                    //  modifier = Modifier.padding(start = 0.dp)
                ) {
                    Text(
                        text = "Login",
                        color = Color.Black,
                        //fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        textDecoration = TextDecoration.Underline
                    )
                }
            }
        }
    }
}

fun isValidEmail(email: String): Boolean {
    val emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
    return email.matches(emailRegex)
}


fun addUserInfo(ref: DatabaseReference, nameState: String, emailState: String) {
    val uid= auth.currentUser!!.uid
   var userInfolist = mutableMapOf<String, String>()
    userInfolist["Name"]=nameState
    userInfolist["Email"]=emailState
    reference.child("User Info").child(uid!!)
        .child("Login Info").push().setValue(userInfolist)
}


//@Preview(showBackground = true)
//@Composable
//fun RegisterPreview() {
//    PropertyProTheme {
//        RegisterScreen()
//    }
//}