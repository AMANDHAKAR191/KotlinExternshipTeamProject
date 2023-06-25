package com.externship.kotlinexternshipteamproject.presentation.home.componants

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.externship.kotlinexternshipteamproject.ui.theme.budgetEqualOrExceed
import com.externship.kotlinexternshipteamproject.ui.theme.budgetLessLess
import com.externship.kotlinexternshipteamproject.ui.theme.budgetNear

@Composable
fun CustomProgressIndicator(
    totalBudgetAmount: Float,
    progress: Float
) {
    val normalizeValue = progress / totalBudgetAmount
    val size by animateFloatAsState(
        targetValue = normalizeValue,
        tween(
            durationMillis = 5500,
            delayMillis = 200,
            easing = LinearOutSlowInEasing
        )
    )


    val progressBarColor = remember { mutableStateOf(budgetLessLess) }
    val percentage = (progress * 100) / totalBudgetAmount
    when {
        percentage >= 100 -> {
            val color by animateColorAsState(targetValue = budgetEqualOrExceed)
            progressBarColor.value = color
        }

        percentage >= 70 -> {
            val color by animateColorAsState(targetValue = budgetNear)
            progressBarColor.value = color
        }

        else -> {
            val color by animateColorAsState(targetValue = budgetLessLess)
            progressBarColor.value = color
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
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(size)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(30.dp))
                    .background(progressBarColor.value)
                    .animateContentSize()
            )
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = progress.toString(),
                    modifier = Modifier
                        .padding(vertical = 5.dp, horizontal = 10.dp)
                )
                Text(
                    text = totalBudgetAmount.toString(),
                    modifier = Modifier
                        .padding(vertical = 5.dp, horizontal = 10.dp)
                )
            }

        }
    }
}
