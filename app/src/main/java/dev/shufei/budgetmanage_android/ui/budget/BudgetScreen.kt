package dev.shufei.budgetmanage_android.ui.budget

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import dev.shufei.budgetmanage_android.BudgetManageDestinationsArgs
import dev.shufei.budgetmanage_android.BudgetManageScreens
import dev.shufei.budgetmanage_android.ui.shared.compose.CategoryCard
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
    val budgetWithCategory by viewModel.budgetWithCategoriesStream.collectAsStateWithLifecycle(initialValue = null)

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
        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp),
        content = { paddingValues ->
            Column(modifier = Modifier.padding(
                paddingValues
            )) {
                Column(modifier = Modifier.padding(
                    vertical = 8.dp,
                    horizontal = 16.dp
                )) {
                    budget?.let { BudgetDetailCard(budget = it) }
                    Row(
                        modifier = Modifier.padding(top = 16.dp, start = 8.dp, end = 8.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "カテゴリ",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        TextButton(
                            onClick = { /*TODO*/ }
                        ) {
                            Text(text = "編集")
                        }
                    }
                    Column(
                        modifier = Modifier.padding(top = 4.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        for (category in budgetWithCategory?.categories ?: emptyList()) {
                            CategoryCard(
                                category = category,
                                balanceAmount = 10000,
                                onClick = {
                                    budget?.let {
                                        navController.navigate(
                                            "${BudgetManageScreens.CATEGORY_SCREEN}/${category.id}"
                                        )
                                    }
                                }
                            )
                        }
                    }
                }

                Button(onClick = {
                    budget?.let {
                        navController.navigate(
                            "${BudgetManageScreens.CREATE_CATEGORY_SCREEN}?${BudgetManageDestinationsArgs.BUDGET_ID}=${it.id}"
                        )
                    }
                }) {
                    Text(text = "カテゴリを追加")
                }
            }
        }
    )


}