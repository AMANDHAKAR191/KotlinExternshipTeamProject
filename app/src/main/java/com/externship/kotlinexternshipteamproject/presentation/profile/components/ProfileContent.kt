package com.externship.kotlinexternshipteamproject.presentation.profile.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.externship.kotlinexternshipteamproject.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileContent(
    padding: PaddingValues,
    photoUrl: String,
    displayName: String,
    budgetAmount: Float,
    isLoadingVisible: Boolean,
    onBudgetAmountChanged: (String) -> Unit,
    onTrailingIconClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier.height(48.dp)
        )
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(photoUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = Crop,
            modifier = Modifier
                .clip(CircleShape)
                .width(96.dp)
                .height(96.dp)
        )
        Text(
            text = displayName,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Divider()
        Spacer(modifier = Modifier.height(20.dp))
        CustomTextField(
            text = budgetAmount.toString(),
            label = "Budget(In Rupees)",
            onValueChange = {
                onBudgetAmountChanged(it)
            },
            enabled = true,
            leadingIcon = { Icons.Default.Home },
            trailingIcon = {
                println("isLoadingVisible: $isLoadingVisible")
                if (isLoadingVisible) {
                    CircularProgressIndicator()
                } else {
                    IconButton(onClick = {
                        onTrailingIconClicked()
                    }) {
                        //here i want to show a icon here which available in drawable
                        val icon: Painter = painterResource(id = R.drawable.baseline_done_24)
                        Icon(icon, contentDescription = "done")
                    }
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )
    }
}