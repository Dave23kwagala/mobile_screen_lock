package com.project.auto_aid

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import androidx.compose.foundation.R



@Composable
fun HomeScreen(navController: NavController) {
    val currentUser = Firebase.auth.currentUser
    val backgroundColor = Color(0xFFE3F2FD)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo2),
                contentDescription = "App Logo",
                modifier = Modifier.size(300.dp)
            )

            Spacer(modifier = Modifier.height(height = 34.dp))

            Text(
                text = "Welcome!",
                style = MaterialTheme.typography.headlineLarge,
                fontSize = 58.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            currentUser?.email?.let { email ->
                Text(
                    text = "Logged in as: $email",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 32.dp)
                )
            }

            Text(
                text = "Thank you for joining us. Please enjoy the app!",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            Button(
                onClick = {
                    Firebase.auth.signOut()
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Logout", fontSize = 28.sp)
            }
        }
    }
}

@Preview(showBackground = true, name = "Auto_aid")
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}