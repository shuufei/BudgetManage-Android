package dev.shufei.budgetmanage_android.ui.budget_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.bottomSheet
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import dev.shufei.budgetmanage_android.BudgetManageRoute
import dev.shufei.budgetmanage_android.BudgetManageScreens
import dev.shufei.budgetmanage_android.ui.shared.compose.CategoryListBottomSheet
import dev.shufei.budgetmanage_android.ui.shared.compose.CustomSystemUiController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialNavigationApi::class,
    ExperimentalFoundationApi::class
)
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
    val categories by viewModel.categoriesStream.collectAsStateWithLifecycle(initialValue = emptyList())

    CustomSystemUiController()

    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val bottomSheetNavController = rememberNavController(bottomSheetNavigator)
    ModalBottomSheetLayout(
        bottomSheetNavigator = bottomSheetNavigator,
        sheetElevation = 0.dp,
        sheetBackgroundColor = Color.Transparent
    ) {
        NavHost(navController = bottomSheetNavController, "main") {
            composable(route = "main") {
                Scaffold(
                    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                    topBar = {
                        BudgetListScreenTopAppBar(
                            scrollBehavior = scrollBehavior,
                            onClickCreateBudget = {
                                navController.navigate(BudgetManageRoute.CREATE_BUDGET)
                            },
                            onClickShowCategories = {
                                bottomSheetNavController.navigate("bottomSheet")
                            }
                        )
                    },
                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp),
                    content = { paddingValues ->
                        Surface(
                            modifier = Modifier.padding(paddingValues)
                        ) {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp))
                            ) {
                                itemsIndexed(budgets) { index, budget ->
                                    BudgetListItem(
                                        budget = budget,
                                        onClickItem = {
                                            navController.navigate("${BudgetManageScreens.BUDGET_SCREEN}/${budget.id}")
                                        },
                                        onClickEdit = {
                                            navController.navigate("${BudgetManageScreens.EDIT_BUDGET_SCREEN}/${budget.id}")
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
                                                .fillMaxWidth(),
                                            horizontalArrangement = Arrangement.Center
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
            bottomSheet(route = "bottomSheet") {
                CategoryListBottomSheet(categories)
            }
        }
    }
}
