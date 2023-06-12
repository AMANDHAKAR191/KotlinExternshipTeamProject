package com.externship.kotlinexternshipteamproject.presentation.add_edit_expanse.componants

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    text: String,
    label: String?,
    onValueChange: (it: String) -> Unit,
    enabled: Boolean,
    leadingIcon: () -> ImageVector,
    trailingIcon: () -> ImageVector,
    singleLine: Boolean,
    keyboardOptions: KeyboardOptions,
    onClick: (() -> Unit)? = null,
    onTrailingIconClick: (() -> Unit)? = null
) {
    TextField(
        value = text,
        label = {
            if (label != null) {
                Text(text = label)
            }
        },
        onValueChange = { onValueChange(it) },
        enabled = enabled,
        leadingIcon = { Icon(leadingIcon(), contentDescription = "") },
        trailingIcon = {
            Icon(trailingIcon(), contentDescription = "", modifier = Modifier.clickable {
                if (onTrailingIconClick != null) {
                    onTrailingIconClick()
                }
            })
        },
        singleLine = singleLine,
        keyboardOptions = keyboardOptions,
        modifier = Modifier
            .clickable {
                if (onClick != null) {
                    onClick()
                }
            }
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        shape = TextFieldDefaults.filledShape,
        colors = TextFieldDefaults.outlinedTextFieldColors(),
    )
}