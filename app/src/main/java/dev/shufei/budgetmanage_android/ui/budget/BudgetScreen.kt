package dev.shufei.budgetmanage_android.ui.budget

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import dev.shufei.budgetmanage_android.ui.shared.compose.CustomSystemUiController
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetScreen(
    navController: NavController,
    snackbarHostState: SnackbarHostState = SnackbarHostState(),
    appScope: CoroutineScope = rememberCoroutineScope(),
    viewModel: BudgetScreenViewModel = hiltViewModel()
) {
    CustomSystemUiController()

    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topAppBarState)

    val budget by viewModel.budgetStream.collectAsStateWithLifecycle(initialValue = null)

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            BudgetScreenTopAppBar(
                title = budget?.title ?: "予算",
                scrollBehavior = scrollBehavior,
                onClickBack = {
                    navController.popBackStack()
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text(text = "出費") },
                icon = { Icon(Icons.Filled.Add, contentDescription = "") },
                onClick = { /*TODO*/ }
            )
        },
        content = { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                Text(text = budget?.id ?: "-")
                Text(text = budget?.title ?: "untitled")
                Text(text = budget?.startDate ?: "-")
                Text(text = budget?.endDate ?: "-")
                Text(text = budget?.budgetAmount.toString())
            }
        }
    )


}