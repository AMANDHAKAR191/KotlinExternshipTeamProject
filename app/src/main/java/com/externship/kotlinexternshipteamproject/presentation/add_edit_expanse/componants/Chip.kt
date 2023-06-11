package com.externship.kotlinexternshipteamproject.presentation.add_edit_expanse.componants

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Chip(modifier: Modifier = Modifier, label: String, onChipClick: () -> Unit) {
    Surface(
        modifier = modifier.clickable { onChipClick() },
        color = MaterialTheme.colorScheme.primary,
        shape = RoundedCornerShape(16.dp),
        tonalElevation = 4.dp,
        shadowElevation = 4.dp,
        content = {
            Text(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                text = label,
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onPrimary)
            )
        }
    )
}