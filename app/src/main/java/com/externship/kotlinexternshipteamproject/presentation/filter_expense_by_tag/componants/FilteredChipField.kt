package com.externship.kotlinexternshipteamproject.presentation.filter_expense_by_tag.componants

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.externship.kotlinexternshipteamproject.presentation.add_edit_expanse.componants.Chip

@OptIn(
    ExperimentalLayoutApi::class
)
@Composable
fun FilteredChipField(
    tags: List<String>,
    onChipClicked: (String) -> Unit
) {
    val (chips, setChips) = remember { mutableStateOf(listOf<String>()) }
    //write the code to show incoming tags
    setChips(tags)
    FlowRow(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        chips.forEach { chip ->
            Chip(modifier = Modifier.padding(2.dp),
                label = chip,
                onChipClick = {
                    onChipClicked(chip)
                }
            )
        }
    }
}
