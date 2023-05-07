package dev.shufei.budgetmanage_android.ui.budget_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import dev.shufei.budgetmanage_android.ui.shared.CustomSystemUiController

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
        topBar = {
            BudgetListScreenTopAppBar(
                scrollBehavior = scrollBehavior,
                onClickCreateBudget = {}
            )
        },
        content = { paddingValues ->
            Surface(
                modifier = Modifier.padding(paddingValues)
            ) {
                LazyColumn(
                    modifier = Modifier
//                        .padding(top = 16.dp, bottom = 40.dp, start = 16.dp, end = 16.dp)
                        .fillMaxWidth()
                ) {
                    itemsIndexed(budgets) { index, budget ->
                        BudgetListItem(
                            budget = budget,
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
                        if (budgets.lastIndex != index) {
                            Divider()
                        }
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
