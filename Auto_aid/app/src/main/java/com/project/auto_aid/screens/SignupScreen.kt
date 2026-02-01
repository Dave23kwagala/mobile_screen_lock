package com.project.auto_aid.screens

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.project.auto_aid.R
import com.project.auto_aid.authentcation.presentation.components.GoogleAuthHelper
import com.project.auto_aid.authentcation.presentation.components.SocialMediaOptions
import com.project.auto_aid.navigation.Routes
import androidx.compose.foundation.layout.imePadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(navController: NavController) {

    val context = LocalContext.current
    val isPreview = LocalInspectionMode.current

    val auth = if (!isPreview) FirebaseAuth.getInstance() else null
    val db = if (!isPreview) FirebaseFirestore.getInstance() else null

    var firstName by remember { mutableStateOf("") }
    var otherName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    var selectedRole by remember { mutableStateOf("USER") }

    // Google Sign-In launcher
    val googleLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            GoogleAuthHelper.handleResult(
                data = result.data,
                onSuccess = {
                    Toast.makeText(context, "Google login successful", Toast.LENGTH_SHORT).show()
                    navController.navigate(Routes.HomeScreen.route) {
                        popUpTo(Routes.SignupScreen.route) { inclusive = true }
                    }
                },
                onError = {
                    Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                }
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()) // ✅ RESPONSIVE
                .imePadding()                          // ✅ KEYBOARD SAFE
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Top image
            Image(
                painter = painterResource(id = R.drawable.logo14),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 120.dp),
                contentScale = ContentScale.Crop,
                alpha = 0.13f
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Sign Up As",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            // ROLE SELECTION
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                RoleOption("User", selectedRole == "USER") {
                    selectedRole = "USER"
                }
                RoleOption("Provider", selectedRole == "SERVICE_PROVIDER") {
                    selectedRole = "SERVICE_PROVIDER"
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // FORM CARD
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {

                    OutlinedTextField(
                        value = firstName,
                        onValueChange = { firstName = it },
                        label = { Text("First Name") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = otherName,
                        onValueChange = { otherName = it },
                        label = { Text("Other Name") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text("Phone Number") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        visualTransformation =
                            if (showPassword) VisualTransformation.None
                            else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { showPassword = !showPassword }) {
                                Image(
                                    painter = painterResource(
                                        id = if (showPassword) R.drawable.no_see else R.drawable.see
                                    ),
                                    contentDescription = null
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // SIGN UP BUTTON
            Button(
                onClick = {
                    if (
                        firstName.isBlank() ||
                        otherName.isBlank() ||
                        phone.isBlank() ||
                        email.isBlank() ||
                        password.isBlank()
                    ) {
                        Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    isLoading = true

                    auth?.createUserWithEmailAndPassword(email, password)
                        ?.addOnSuccessListener { result ->
                            val userId = result.user?.uid ?: return@addOnSuccessListener

                            val userData = hashMapOf(
                                "firstName" to firstName,
                                "otherName" to otherName,
                                "phone" to phone,
                                "email" to email,
                                "uid" to userId,
                                "role" to selectedRole
                            )

                            db?.collection("users")?.document(userId)
                                ?.set(userData)
                                ?.addOnSuccessListener {
                                    isLoading = false
                                    Toast.makeText(
                                        context,
                                        "Signed up as $selectedRole",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    navController.navigate(Routes.HomeScreen.route) {
                                        popUpTo(Routes.SignupScreen.route) { inclusive = true }
                                    }
                                }
                                ?.addOnFailureListener {
                                    isLoading = false
                                    Toast.makeText(context, "Error saving data", Toast.LENGTH_LONG).show()
                                }
                        }
                        ?.addOnFailureListener {
                            isLoading = false
                            Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                        }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = !isLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0A9AD9),
                    contentColor = Color.White
                )
            ) {
                Text(if (isLoading) "Signing up..." else "Sign Up")
            }

            Spacer(modifier = Modifier.height(20.dp))

            // LOGIN LINK
            Text(
                text = buildAnnotatedString {
                    append("Already have an account? ")
                    withStyle(
                        SpanStyle(
                            color = Color(0xFF0A9AD9),
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    ) { append("Login") }
                },
                modifier = Modifier.clickable {
                    navController.navigate(Routes.LoginScreen.route)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // SOCIAL LOGIN
            SocialMediaOptions(
                onGoogleClick = {
                    googleLauncher.launch(
                        GoogleAuthHelper.getSignInIntent(context)
                    )
                },
                onFacebookClick = {
                    Toast.makeText(context, "Facebook login coming soon", Toast.LENGTH_SHORT).show()
                },
                onTikTokClick = {
                    Toast.makeText(context, "TikTok login coming soon", Toast.LENGTH_SHORT).show()
                },
                onInstagramClick = {
                    Toast.makeText(context, "Instagram login coming soon", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}

// ROLE OPTION
@Composable
fun RoleOption(
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(120.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            color = if (selected) Color(0xFF0A9AD9) else Color.Black
        )

        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .height(3.dp)
                .fillMaxWidth(0.6f)
                .background(
                    if (selected) Color(0xFF0A9AD9) else Color.Transparent,
                    shape = RoundedCornerShape(2.dp)
                )
        )
    }
}

@Preview(showBackground = true, device = "spec:width=360dp,height=640dp")
@Composable
fun SignupScreenPreview() {
    SignupScreen(navController = rememberNavController())
}
