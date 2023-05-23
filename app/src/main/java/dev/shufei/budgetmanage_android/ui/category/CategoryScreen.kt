package dev.shufei.budgetmanage_android.ui.category

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import dev.shufei.budgetmanage_android.BudgetManageScreens
import dev.shufei.budgetmanage_android.ui.shared.compose.CustomSystemUiController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun CategoryScreen(
    navController: NavController,
    snackbarHostState: SnackbarHostState = SnackbarHostState(),
    appScope: CoroutineScope = rememberCoroutineScope(),
    viewModel: CategoryScreenViewModel = hiltViewModel()
) {
    var selectedTabState by remember {
        mutableStateOf(0)
    }
    val categoryWithBudget by viewModel.category.collectAsStateWithLifecycle(initialValue = null)
    val scope = rememberCoroutineScope()

    CustomSystemUiController()
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CategoryScreenTopAppBar(
                category = categoryWithBudget?.category,
                onClickBack = { navController.popBackStack() },
                onClickDelete = {
                    categoryWithBudget?.let {
                        scope.launch {
                            viewModel.delete(it.category)
                            appScope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "カテゴリを削除しました",
                                    withDismissAction = true
                                )
                            }
                            navController.popBackStack()
                        }

                    }
                },
                onClickEdit = {
                    categoryWithBudget?.let {
                        navController.navigate("${BudgetManageScreens.EDIT_CATEGORY_SCREEN}/${it.category.id}")
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp),
        contentColor = MaterialTheme.colorScheme.onSurface,
        content = { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues)
            ) {
                TabRow(
                    selectedTabIndex = selectedTabState,
                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(8.dp)
                ) {
                    Tab(
                        selected = selectedTabState == 0,
                        onClick = { selectedTabState = 0 },
                    ) {
                        Text(text = "出費", modifier = Modifier.padding(vertical = 12.dp))
                    }
                    Tab(selected = selectedTabState == 1, onClick = { selectedTabState = 1 }) {
                        Text(text = "詳細", modifier = Modifier.padding(vertical = 12.dp))
                    }
                }
                Text(text = "カテゴリ詳細")
                categoryWithBudget?.let {
                    Text(text = it.budget.title)
                    Text(text = it.category.name)
                }
            }
        }
    )
}
