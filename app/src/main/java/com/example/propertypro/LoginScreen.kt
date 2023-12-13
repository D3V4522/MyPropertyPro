package com.example.propertypro
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

import android.widget.Toast
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavOptions
import com.google.firebase.auth.FirebaseAuth


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen( navController: NavController){
    var resetShowDialog by remember { mutableStateOf(false) }
    var resetEmail by remember { mutableStateOf("") }
    val auth = FirebaseAuth.getInstance()
    var cxt = LocalContext.current
    var passwordVisible by remember { mutableStateOf(false) }
    var emailState  by remember { mutableStateOf("") }
    var passwordState by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    
    Box(modifier = Modifier.fillMaxWidth()) {
        
        Image(painter = painterResource(id =com.example.propertypro.R.drawable.backround),
            contentDescription =null,
        contentScale = ContentScale.FillBounds,
        modifier = Modifier.matchParentSize())

        Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "PropertyPro", fontSize = 30.sp, fontFamily = FontFamily.SansSerif)
            Text(text = "Your Key to Seamless Property Discovery", fontSize = 14.sp, fontFamily = FontFamily.Cursive)

            Spacer(modifier = Modifier.height(60.dp))


            Text(text = "Login", color = Color.White,fontSize = 30.sp)

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
                            .background(White, shape = RoundedCornerShape(8.dp))
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

  // Mail-ID

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                value = emailState,
                enabled = true,
                keyboardOptions =  KeyboardOptions(keyboardType = KeyboardType.Email),
                label = { Text("Enter Mail-ID") },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor =Color.White ,
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

 // Password
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                value = passwordState,
                 enabled = true,
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

   //Forget password
            TextButton(onClick = {
                resetShowDialog=true
            },
            modifier = Modifier
                .align(alignment = Alignment.End)
                .padding(end = 20.dp)) {
                Text(text = "Forget Password?",
                    color = Color.Black)
            }



            if (resetShowDialog) {
                AlertDialog(
                    onDismissRequest = {
                        resetShowDialog = false
                    },
                    title = {
                        Text("Forgot Password",
                            modifier = Modifier.padding(bottom = 5.dp))
                    },
                    text = {
                        // Input field for email in the dialog
                        TextField(
                            value = resetEmail,
                            onValueChange = { resetEmail = it },
                            label = { Text("Enter Email") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                if (resetEmail.isNotEmpty()) {
                                    // Implement the logic for sending a password reset email here
                                    sendPasswordResetEmail(resetEmail,cxt)
                                    resetShowDialog = false
                                }
                            }
                        ) {
                            Text("Reset Password")
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = {
                                resetShowDialog = false
                            }
                        ) {
                            Text("Cancel")
                        }
                    }
                )
            }






  // Login
            Button(onClick = {



                if (emailState.isNotEmpty()) {
                    if (isValidEmail(emailState)) {
                        if (passwordState.length >= 6) {
                            showDialog=true
                            auth.signInWithEmailAndPassword(emailState, passwordState)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        showDialog=false
                                        Toast.makeText(cxt, "Welcome", Toast.LENGTH_LONG).show()
                                        val navOptions = NavOptions.Builder()
                                            .setPopUpTo("login", true)
                                            .build()
                                        navController.navigate("Home", navOptions )
                                    } else {
                                        showDialog=false
                                        val exception = task.exception
                                        Toast.makeText(cxt, ""+exception, Toast.LENGTH_LONG).show()
                                    }

                                }
                        } else {
                            Toast.makeText(cxt, "Password should be 6 characters or longer.", Toast.LENGTH_LONG).show()
                        }
                    }else{
                        Toast.makeText(cxt, "Enter valid Email!", Toast.LENGTH_LONG).show()
                    }
                }else{
                    Toast.makeText(cxt, "Enter Email!", Toast.LENGTH_LONG).show()
                }
            },
                modifier = Modifier
                    .padding(all = Dp(10F))
                    .width(200.dp),
                enabled = true,
               // border = BorderStroke(width = 1.dp, brush = SolidColor(Color.Blue)),
                shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.textButtonColors(
                containerColor = Color.Black.copy(alpha = 0.6f),
                contentColor = Color.White))
            {
                Text(text = "Login", color = Color.White)
            }


   //Register
            Row(
                horizontalArrangement = Arrangement.Center,
              verticalAlignment = Alignment.CenterVertically ) {
                Text(text = "Don't have an account?")
                TextButton(
                    onClick = {
                              navController.navigate("register")
                    },
                  //  modifier = Modifier.padding(start = 0.dp)
                ) {
                    Text(
                        text = "Register",
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


//@Composable
//fun LoadingDialog(loading: Boolean, onDismissRequest: ()->Unit) {
//
//    println("loading = " + loading)
//
//    if (loading) {
//        Dialog(
//            onDismissRequest = onDismissRequest,
//            DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
//        ) {
//
//            Box(
//                contentAlignment = Alignment.Center,
//                modifier = Modifier
//                    .size(100.dp)
//                    .background(
//                        MaterialTheme.colorScheme.background,
//                        shape = RoundedCornerShape(8.dp)
//                    )
//            ) {
//                CircularProgressIndicator()
//            }
//        }
//    }
//}

//@Preview(showBackground = true)
//@Composable
//fun LoginPreview() {
//    PropertyProTheme {
//        LoginScreen()
//    }
//}

private fun sendPasswordResetEmail(email: String, cxt: Context) {
    val auth = FirebaseAuth.getInstance()

    auth.sendPasswordResetEmail(email)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(cxt, "We Sent a password reset Link to your mail!", Toast.LENGTH_LONG).show()
            } else {
                val exception = task.exception
                Toast.makeText(cxt, "Failed to send password reset email. ${exception?.message}", Toast.LENGTH_LONG).show()
            }
        }
}


