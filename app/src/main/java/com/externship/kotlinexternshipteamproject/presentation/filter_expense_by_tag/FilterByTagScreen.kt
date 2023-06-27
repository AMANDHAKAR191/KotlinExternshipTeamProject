package com.externship.kotlinexternshipteamproject.presentation.filter_expense_by_tag

import androidx.compose.animation.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.externship.kotlinexternshipteamproject.presentation.add_edit_expanse.AddEditExpenseEvent
import com.externship.kotlinexternshipteamproject.presentation.add_edit_expanse.AddEditExpenseViewModel
import com.externship.kotlinexternshipteamproject.presentation.filter_expense_by_tag.componants.ExpanseTagFilteredItem
import com.externship.kotlinexternshipteamproject.presentation.filter_expense_by_tag.componants.FilteredChipField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterByTagScreen(
    viewModel: FilterByTagViewModel = hiltViewModel(),
    navigateToHomeScreen: () -> Unit
) {
    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }

    val openDialog = remember { mutableStateOf(false) }
    println("openDialog: ${openDialog.value}")

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
            TopAppBar(
                title = { Text(text = "Aman Dhaker") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(),
                navigationIcon = {
                    IconButton(onClick = { navigateToHomeScreen() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "back")
                    }
                }
            )
        },
        bottomBar = {

        }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                println("tags: ${state.expenseTags}")
                FilteredChipField(tags = state.expenseTags, onChipClicked = { chipText ->
                    viewModel.onEvent(AddEditExpenseEvent.GetExpensesFilteredByTag(chipText))
                })
            }
            Spacer(modifier = Modifier.height(10.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 10.dp)
            ) {
                items(state.expense) { expanse ->
                    ExpanseTagFilteredItem(
                        expense = expanse,
                        modifier = Modifier,
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