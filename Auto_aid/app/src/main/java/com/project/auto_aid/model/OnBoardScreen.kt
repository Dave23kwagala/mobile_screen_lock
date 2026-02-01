package com.project.auto_aid.model

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.project.auto_aid.R
import com.project.auto_aid.navigation.Routes
import kotlinx.coroutines.launch

@Composable
fun OnBoardScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    val pages = listOf(
        OnBoardModel(
            title = "Instant Vehicle Assistance",
            description = "Get roadside help, towing, and emergency support anytime, anywhere with AutoAid.",
            imageRes = R.drawable.logo10,
            buttonText = "Skip"
        ),
        OnBoardModel(
            title = "Track & Manage Repairs",
            description = "Keep track of your vehicle services, repairs, and maintenance history all in one place.",
            imageRes = R.drawable.logo11,
            buttonText = "Next"
        ),
        OnBoardModel(
            title = "Smart & User-Friendly!",
            description = "Easily request assistance, track your requests, and stay updated with a simple, intuitive interface.",
            imageRes = R.drawable.fuel,
            buttonText = "Get Started"
        )
    )

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { pages.size }
    )

    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())   // ✅ RESPONSIVE FIX
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->

            val model = pages[page]

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {

                Image(
                    painter = painterResource(id = model.imageRes),
                    contentDescription = model.title,
                    contentScale = ContentScale.Fit,      // ✅ DO NOT CROP
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 260.dp)           // ✅ NO FIXED HEIGHT
                        .clip(RoundedCornerShape(24.dp))
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = model.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = Color(0xFF0A9AD9),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = model.description,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Dots Indicator
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            repeat(pages.size) { index ->
                val color =
                    if (pagerState.currentPage == index) Color(0xFF0A9AD9) else Color.Gray

                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(10.dp)
                        .clip(RoundedCornerShape(50))
                        .background(color)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        AuthenticationButton(
            title = pages[pagerState.currentPage].buttonText,
            onClick = {
                scope.launch {
                    when (pagerState.currentPage) {
                        0, 1 -> pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        pages.lastIndex -> navController.navigate(Routes.LoginScreen.route)
                    }
                }
            }
        )
    }
}

@Composable
fun AuthenticationButton(
    title: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF0A9AD9),
            contentColor = Color.White
        )
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}



@Preview(
    showBackground = true,
    device = "spec:width=411dp,height=891dp"
)
@Composable
fun OnBoardScreenPreview() {
    val navController = rememberNavController()
    OnBoardScreen(navController = navController)
}