package com.externship.kotlinexternshipteamproject.presentation.home.componants

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.externship.kotlinexternshipteamproject.domain.model.Expanse
import com.externship.kotlinexternshipteamproject.presentation.add_edit_expanse.ListStringConverter
import com.externship.kotlinexternshipteamproject.presentation.add_edit_expanse.componants.Chip
import com.externship.kotlinexternshipteamproject.ui.theme.incomeColor
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ExpanseItem(
    expanse: Expanse,
    modifier: Modifier,
    onItemClick: () -> Unit,
    onDeleteClick: () -> Unit
) {

    val tags: List<String> = ListStringConverter().toListString(expanse.tags)
    var itemColor by remember {
        mutableStateOf<Color>(Color.Transparent)
    }
    if (expanse.type == "Expanse") {
//        itemColor = expanseColor
    } else {
        itemColor = incomeColor
    }
    Card(
        shape = RoundedCornerShape(10f),
        elevation = CardDefaults.elevatedCardElevation(),
        colors = CardDefaults.cardColors(
            containerColor = itemColor
        ),
        modifier = modifier
            .clickable { onItemClick() }
            .padding(vertical = 10.dp),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 5.dp)
                    .padding(top = 5.dp)
            ) {
                Row(modifier = Modifier) {
                    Image(
                        Icons.Default.Home,
                        modifier = Modifier.weight(1f),
                        contentDescription = "Icon"
                    )
                    Text(text = expanse.amount.toString(), modifier = Modifier.weight(5f))
                    Text(text = formatDate(expanse.date), modifier = Modifier.weight(3f))
//                Text(text = expanse.tags)
                }
                Row(
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .padding(bottom = 5.dp)
                ) {
                    Text(text = expanse.paymentMode, modifier = Modifier.wrapContentWidth())
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
                }
            }
        }
    )
}

fun formatDate(date: Date): String {
    val dateFormat = SimpleDateFormat("dd MMMM", Locale.ENGLISH)
    val formattedDate = date.let { dateFormat.format(it) }
    return formattedDate.toString()
}