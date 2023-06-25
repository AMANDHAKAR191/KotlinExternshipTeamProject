package com.externship.kotlinexternshipteamproject.presentation.home

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.externship.kotlinexternshipteamproject.presentation.home.componants.CustomProgressIndicator
import com.externship.kotlinexternshipteamproject.presentation.home.componants.ExpanseItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    navigateToAddEditExpanseScreen: () -> Unit,
    navigateToProfileScreen: () -> Unit
) {
    val state = viewModel.state.value

    val sumOfCurrentExpanses = viewModel.sumOfCurrentExpanses.value.expansesSumOfCurrentMonth

    viewModel.sumOfCurrentExpanses
    val progress = sumOfCurrentExpanses.let { (it) }
    val totalBudget = viewModel.budgetAmount.value.amount
    println("totalBudget: $totalBudget")
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MediumTopAppBar(
                title = { Text(text = "Aman Dhaker") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(),
                actions = {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(viewModel.photoUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(CircleShape)
                            .width(32.dp)
                            .height(32.dp)
                            .clickable {
                                navigateToProfileScreen()
                            }
                    )
//                    IconButton(onClick = {
//                        navigateToProfileScreen()
//                    }) {
//                        Icon(Icons.Default.Person, contentDescription = "Profile")
//                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navigateToAddEditExpanseScreen()
                },
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                shape = FloatingActionButtonDefaults.shape
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add note")
            }
        },
        bottomBar = {

        }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (totalBudget == "") {
                CustomProgressIndicator(totalBudgetAmount = 0f, progress = progress)
            } else {
                CustomProgressIndicator(
                    totalBudgetAmount = totalBudget.toFloat(),
                    progress = progress
                )
            }


            Spacer(modifier = Modifier.height(10.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 10.dp)
            ) {
                items(state.expanses) { expanse ->
                    ExpanseItem(
                        expanse = expanse,
                        modifier = Modifier,
                        onDeleteClick = {},
                        onItemClick = {})
                }
                item {
                    Spacer(modifier = Modifier.height(50.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = "This is the end of List")
                    }
                    Spacer(modifier = Modifier.height(50.dp))
                }
            }
        }
    }
}