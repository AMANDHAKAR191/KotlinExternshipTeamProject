package com.externship.kotlinexternshipteamproject.presentation.home.componants

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import com.externship.kotlinexternshipteamproject.domain.model.Expanse
import com.externship.kotlinexternshipteamproject.presentation.add_edit_expanse.ListStringConverter
import com.externship.kotlinexternshipteamproject.presentation.add_edit_expanse.componants.Chip

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ExpanseItem(
    expanse: Expanse,
    modifier: Modifier,
    cornerRadius: Dp = 10.dp,
    cutCornerSize: Dp = 30.dp,
    onItemClick: () -> Unit,
    onDeleteClick: () -> Unit
) {

    val tags: List<String> = ListStringConverter().toListString(expanse.tags)
    var itemColor by remember {
        mutableStateOf<Color>(Color.Green)
    }
    if (expanse.type == "Expanse") {
        itemColor = Color.Red
    } else {
        itemColor = Color.Green
    }

    Box(modifier = modifier) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val clipPath = Path().apply {
                lineTo(size.width - cutCornerSize.toPx(), 0f)
                lineTo(size.width, cutCornerSize.toPx())
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }

            clipPath(clipPath) {
                drawRoundRect(
                    color = Color.Transparent,
                    size = size,
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
                drawRoundRect(
                    color = Color(
                        ColorUtils.blendARGB(0x000000, 0x000000, 0.2f)
                    ),
                    topLeft = Offset(size.width - cutCornerSize.toPx(), -100f),
                    size = Size(cutCornerSize.toPx() + 100f, cutCornerSize.toPx() + 100f),
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(end = 32.dp)
                .clickable { onItemClick() }
                .background(itemColor, shape = RectangleShape)
        ) {
            Image(Icons.Default.Home, modifier = Modifier.weight(1f), contentDescription = "Icon")
            Column(modifier = Modifier.weight(6f)) {
                Text(text = expanse.amount.toString())
                FlowRow(
                    modifier = Modifier
                        .padding(8.dp)
                        .widthIn(max = 200.dp)
                ) {
                    tags.forEach { chip ->
                        Chip(modifier = Modifier.padding(2.dp), label = chip, onChipClick = {

                        })
                    }
                }
//                Text(text = expanse.tags)
            }
            Column(modifier = Modifier.weight(3f)) {
                Text(text = expanse.date.toString())
                Text(text = expanse.paymentMode)
            }
        }
    }
}