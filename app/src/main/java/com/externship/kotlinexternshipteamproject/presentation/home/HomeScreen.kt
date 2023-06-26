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
import androidx.compose.material3.AlertDialog
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.externship.kotlinexternshipteamproject.domain.model.Expanse
import com.externship.kotlinexternshipteamproject.presentation.add_edit_expanse.AddEditExpanseEvent
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

    val openDialog = remember { mutableStateOf(false) }
    val itemToDelete = remember { mutableStateOf<Expanse?>(null) }
    println("openDialog: ${openDialog.value}")
    if (openDialog.value) {  //ask confirmation from user to delete the expanse
        AlertDialog(
            title = { Text(text = "Alert") },
            text = { Text(text = "This expanse will deleted permanently. Do you still want to delete?") },
            onDismissRequest = {
                openDialog.value = false
            },
            confirmButton = {
                Text(text = "Yes,delete",
                    modifier = Modifier.clickable {
                        itemToDelete.value?.let { viewModel.onEvent(AddEditExpanseEvent.DeleteExpanse) }
                        openDialog.value = false
                    })
            },
            dismissButton = {
                Text(text = "No",
                    modifier = Modifier.clickable {
                        println("dismissButton")
                        openDialog.value = false
                    })
            }
        )
    }

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
                        onDeleteClick = {
                            itemToDelete.value = expanse
                            println("check 2")
                            openDialog.value = true
                        },
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