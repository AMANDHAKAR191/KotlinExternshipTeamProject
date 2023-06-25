package com.externship.kotlinexternshipteamproject.presentation.home.componants

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
    totalBudgetAmount: Float,
    progress: Float
) {
    val normalizeValue = progress / totalBudgetAmount
    val size by animateFloatAsState(
        targetValue = normalizeValue,
        tween(
            durationMillis = 1500,
            delayMillis = 200,
            easing = LinearOutSlowInEasing
        )
    )


    val progressBarColor = remember { mutableStateOf(budgetLessLess) }
    val percentage = (progress * 100) / totalBudgetAmount
    var textValue = remember {
        mutableStateOf("")
    }
    when {
        percentage >= 100 -> {
            val color by animateColorAsState(targetValue = budgetEqualOrExceed)
            progressBarColor.value = color
            textValue.value = "Ohh no! Budget is Exceeded"
        }

        percentage >= 70 -> {
            val color by animateColorAsState(targetValue = budgetNear)
            progressBarColor.value = color
            textValue.value =
                "Warning! if you continue spending at this rate. you will exceed the limit shortly"
        }

        else -> {
            val color by animateColorAsState(targetValue = budgetLessLess)
            progressBarColor.value = color
            textValue.value = "Well done! Budget is underControl"
        }
    }

    Card(
        shape = RoundedCornerShape(10f),
        elevation = CardDefaults.elevatedCardElevation(),
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .padding(vertical = 10.dp, horizontal = 50.dp),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 10.dp)
                    .height(80.dp)
            ) {
                Text(text = textValue.value, modifier = Modifier.weight(6f))
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(20.dp))
                        .background(MaterialTheme.colorScheme.inversePrimary)
                        .weight(4f)
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
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
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
    )


}
