package com.externship.kotlinexternshipteamproject.presentation.home.componants

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.externship.kotlinexternshipteamproject.ui.theme.budgetEqualOrExceed
import com.externship.kotlinexternshipteamproject.ui.theme.budgetLessLess
import com.externship.kotlinexternshipteamproject.ui.theme.budgetNear

@Composable
fun CustomProgressIndicator(
    progress: Float
) {
    val normalizeValue = progress / 5000f
    val size by animateFloatAsState(
        targetValue = normalizeValue,
        tween(
            durationMillis = 1000,
            delayMillis = 200,
            easing = LinearOutSlowInEasing
        )
    )

    val progressBarColor = remember { mutableStateOf(budgetLessLess) }
    val percentage = (progress * 100) / 5000
    when {
        percentage >= 100 -> {
            progressBarColor.value = budgetEqualOrExceed
        }

        percentage >= 70 -> {
            progressBarColor.value = budgetNear
        }

        else -> {
            progressBarColor.value = budgetLessLess
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp)
            .height(30.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.primaryContainer)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth(size)
                .fillMaxHeight()
                .clip(RoundedCornerShape(20.dp))
                .background(progressBarColor.value)
                .animateContentSize()
        ) {
            Text(
                text = progress.toString(),
                modifier = Modifier
                    .fillMaxWidth(size)
                    .padding(vertical = 5.dp)
                    .align(Alignment.Center)
            )
        }

    }
}
