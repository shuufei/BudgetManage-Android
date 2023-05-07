package dev.shufei.budgetmanage_android.ui.budget_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import dev.shufei.budgetmanage_android.BudgetManageRoute
import dev.shufei.budgetmanage_android.ui.shared.CustomSystemUiController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetListScreen(
    navController: NavController,
    snackbarHostState: SnackbarHostState = SnackbarHostState(),
    appScope: CoroutineScope = rememberCoroutineScope(),
    viewModel: BudgetListScreenViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topAppBarState)

    val budgets by viewModel.budgetsStream.collectAsStateWithLifecycle(initialValue = emptyList())

    CustomSystemUiController()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            BudgetListScreenTopAppBar(
                scrollBehavior = scrollBehavior,
                onClickCreateBudget = {
                    navController.navigate(BudgetManageRoute.CREATE_BUDGET)
                }
            )
        },
        content = { paddingValues ->
            Surface(
                modifier = Modifier.padding(paddingValues)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    itemsIndexed(budgets) { index, budget ->
                        BudgetListItem(
                            budget = budget,
                            onClickItem = {
                                navController.navigate(BudgetManageRoute.BUDGET)
                            },
                            onClickDelete = {
                                scope.launch {
                                    viewModel.delete(budget)
                                    snackbarHostState.showSnackbar(
                                        message = "予算を削除しました",
                                        withDismissAction = true
                                    )
                                }
                            }
                        )
                    }
                    if (budgets.isEmpty()) {
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                            ) {
                                Text(
                                    text = "予算が登録されていません",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}
