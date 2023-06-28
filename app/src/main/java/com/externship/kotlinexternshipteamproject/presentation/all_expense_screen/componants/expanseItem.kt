package com.externship.kotlinexternshipteamproject.presentation.all_expense_screen.componants

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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.externship.kotlinexternshipteamproject.domain.model.Expense
import com.externship.kotlinexternshipteamproject.presentation.add_edit_expanse.ListStringConverter
import com.externship.kotlinexternshipteamproject.presentation.add_edit_expanse.componants.Chip
import com.externship.kotlinexternshipteamproject.ui.theme.incomeColor
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ExpanseItem(
    expense: Expense,
    modifier: Modifier,
    onItemClick: () -> Unit,
    onItemTagClick: () -> Unit,
    onDeleteClick: @Composable () -> Unit
) {

    val tags: List<String> = ListStringConverter().toListString(expense.tags)
    var itemColor by remember { mutableStateOf<Color>(Color.Transparent) }
    var isDeleteItem by remember { mutableStateOf(false) }

    Surface(
        shape = RoundedCornerShape(10f),
        color = if (expense.type == "Expense") {
            Color.Transparent
        } else {
            incomeColor
        },
        tonalElevation = 10.dp,
        shadowElevation = 0.dp,
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
                    Text(text = expense.amount.toString(), modifier = Modifier.weight(5f))
                    Text(
                        text = com.externship.kotlinexternshipteamproject.presentation.filter_expense_by_tag.componants.formatDate(
                            expense.date
                        ), modifier = Modifier.weight(3f)
                    )
                    Image(
                        Icons.Default.Delete,
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                isDeleteItem = true
                            },
                        contentDescription = "Delete Icon"
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .padding(bottom = 5.dp)
                ) {
                    Text(text = expense.paymentMode, modifier = Modifier.wrapContentWidth())
                    FlowRow(
                        modifier = Modifier
                            .padding(8.dp)
                            .widthIn(max = 200.dp)
                    ) {
                        tags.forEach { chip ->
                            Chip(modifier = Modifier.padding(2.dp), label = chip, onChipClick = {
                                onItemTagClick()
                            })
                        }
                    }
                }
                Divider()
            }
        })


    if (isDeleteItem) {
        onDeleteClick()
        isDeleteItem = false
    }
}

fun formatDate(date: Date): String {
    val dateFormat = SimpleDateFormat("dd MMMM", Locale.ENGLISH)
    val formattedDate = date.let { dateFormat.format(it) }
    return formattedDate.toString()
}