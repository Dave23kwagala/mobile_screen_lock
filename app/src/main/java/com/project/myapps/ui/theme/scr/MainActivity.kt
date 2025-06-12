package com.project.myapps.ui.theme.scr

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.myapps.R
import com.project.myapps.ui.theme.scr.Screen.LoginScreen

sealed class Screen {
    object LockScreen : Screen()
    object LoginScreen : Screen()
}

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var currentScreen: Screen by remember { mutableStateOf(Screen.LockScreen) }
            val context = LocalContext.current
            LoginScreen {


//            Toast
//                .makeText(context, "Login successful or back pressed", Toast.LENGTH_SHORT)
//                .show()
//        }
//            when (currentScreen) {
//                Screen.LockScreen -> BackgroundImageScreen {
//                    currentScreen = LoginScreen
//                }
//
//                LoginScreen -> LoginScreen {
//
//                    Toast
//                        .makeText(context, "Login successful or back pressed", Toast.LENGTH_SHORT)
//                        .show()
//                }
            }
        }
    }
}

@Composable
fun BackgroundImageScreen(function: () -> Unit) {

    val context = LocalContext.current
    var isFlashlightClicked by remember { mutableStateOf(false) }
    var isCameraClicked by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .padding(25.dp)
            .fillMaxSize()
            .clip(RoundedCornerShape(30.dp)),
    ) {
        Image(
            painter = painterResource(id = R.drawable.picture2),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()

                .fillMaxSize()
                .padding(23.dp),

            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            Spacer(modifier = Modifier.height(34.dp))

            Text(
                text = "Monday, 9 June",
                style = TextStyle(
                    color = Color(0xFFFFA500),
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp
                )
            )
            Text(
                text = "12:58",
                style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.W900,
                    fontSize = 100.sp
                )
            )
            Text(
                text = "Hello World!",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 18.sp

                )
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 30.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Icon(
                painter = painterResource(id = R.drawable.flashlight),
                contentDescription = "Torch",
                tint = if (isFlashlightClicked) Color.Blue else Color.White,
                modifier = Modifier
                    .size(70.dp)
                    .clickable {
                        isFlashlightClicked = !isFlashlightClicked
                        Toast
                            .makeText(context, "Torch Clicked", Toast.LENGTH_SHORT)
                            .show()
                    }

            )

            Icon(
                painter = painterResource(id = R.drawable.dslr_camera),
                contentDescription = "Camera",
                tint = if (isCameraClicked) Color.Blue else Color.White,
                modifier = Modifier
                    .size(70.dp)
                    .clickable {
                        isCameraClicked = !isCameraClicked
                        function
                        Toast
                            .makeText(context, "Camera Clicked", Toast.LENGTH_SHORT)
                            .show()
                    }
            )
        }
    }
}
