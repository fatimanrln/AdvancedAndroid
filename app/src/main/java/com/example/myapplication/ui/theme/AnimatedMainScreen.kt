package com.example.myapplication.ui.theme

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.myapplication.R

@Composable
fun AnimatedSplashScreen(onSplashFinished: () -> Unit) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animation))
    val lottieAnimationState = animateLottieCompositionAsState(composition = composition, iterations = 1)
    LaunchedEffect(lottieAnimationState.isAtEnd && lottieAnimationState.isPlaying) {
        if (lottieAnimationState.isAtEnd && lottieAnimationState.isPlaying) {
            onSplashFinished()
        }
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        LottieAnimation(composition = composition, progress = { lottieAnimationState.progress })
    }
}

@Composable
fun AnimatedAppIcon() {
    val scale by animateFloatAsState(targetValue = 1f, animationSpec = tween(durationMillis = 1000))
    Image(
        painter = painterResource(R.drawable.app_icon),
        contentDescription = "App Icon",
        modifier = Modifier.size(100.dp).scale(scale)
    )
}

@Composable
fun AnimatedMainScreen() {
    var showSplash by remember { mutableStateOf(true) }
    Crossfade(targetState = showSplash) { isSplash ->
        if (isSplash) {
            AnimatedSplashScreen(onSplashFinished = { showSplash = false })
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AnimatedAppIcon()
                Spacer(modifier = Modifier.height(16.dp))
                CatalogScreen()
            }
        }
    }
}
