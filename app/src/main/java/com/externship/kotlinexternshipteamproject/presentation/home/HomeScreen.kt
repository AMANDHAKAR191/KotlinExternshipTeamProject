package com.externship.kotlinexternshipteamproject.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.externship.kotlinexternshipteamproject.domain.model.Expense
import com.externship.kotlinexternshipteamproject.presentation.add_edit_expanse.AddEditExpenseEvent
import com.externship.kotlinexternshipteamproject.presentation.add_edit_expanse.AddEditExpenseViewModel
import com.externship.kotlinexternshipteamproject.presentation.all_expense_screen.componants.ExpanseItem
import com.externship.kotlinexternshipteamproject.presentation.home.componants.CustomProgressIndicator

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    navigateToAddEditExpanseScreen: () -> Unit,
    navigateToProfileScreen: () -> Unit,
    navigateToFilterByTagScreen: () -> Unit,
    navigateToAllExpenseScreen: () -> Unit
) {
    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }
    val sumOfCurrentExpanses = viewModel.sumOfCurrentExpenses.value.expensesSumOfCurrentMonth

    val openDialog = remember { mutableStateOf(false) }
    val itemToDelete = remember { mutableStateOf<Expense?>(null) }
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
                        itemToDelete.value?.let {
                            viewModel.onEvent(AddEditExpenseEvent.DeleteExpense(it))
                        }
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

    viewModel.sumOfCurrentExpenses
    val progress = sumOfCurrentExpanses.let { (it) }
    val totalBudget = viewModel.budgetAmount.value.amount

    LaunchedEffect(key1 = viewModel.eventFlow) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is AddEditExpenseViewModel.UiEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(event.message)
                }

                else -> {}
            }

        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
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
                CustomProgressIndicator(totalBudgetAmount = 0f, progress = progress, onEditClick = {
                    navigateToProfileScreen()
                })
            } else {
                CustomProgressIndicator(
                    totalBudgetAmount = totalBudget.toFloat(),
                    progress = progress,
                    onEditClick = { navigateToProfileScreen() }
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                Text(text = "Want to search by tag?")
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = "Search",
                    color = MaterialTheme.colorScheme.primary,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier
                        .clickable {
                            navigateToFilterByTagScreen()
                        })
            }
            Spacer(modifier = Modifier.height(10.dp))
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 10.dp),
                tonalElevation = 5.dp,
                shadowElevation = 0.dp,
                shape = RoundedCornerShape(10f)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 10.dp)
                        .height(420.dp)
                ) {
                    stickyHeader {
                        Card(
                            shape = RoundedCornerShape(10f),
                            elevation = CardDefaults.cardElevation(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = MaterialTheme.colorScheme.surfaceVariant)
                                .clickable {
                                    navigateToFilterByTagScreen()
                                }
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Transaction History",
                                    modifier = Modifier.padding(
                                        vertical = 10.dp,
                                        horizontal = 10.dp
                                    ),
                                    style = MaterialTheme.typography.headlineSmall
                                )
                                Text(
                                    text = "See all",
                                    modifier = Modifier
                                        .padding(vertical = 10.dp, horizontal = 10.dp)
                                        .clickable {
                                            navigateToAllExpenseScreen()
                                        },
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))

                    }
                    items(state.expense.take(3)) { expanse ->
                        ExpanseItem(
                            expense = expanse,
                            modifier = Modifier,
                            onDeleteClick = {
                                itemToDelete.value = expanse
                                openDialog.value = true
                            },
                            onItemTagClick = {
                                navigateToFilterByTagScreen()
                            },
                            onItemClick = {})
                    }
                }
            }
        }
    }


}