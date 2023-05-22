package dev.shufei.budgetmanage_android.ui.create_budget

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import dev.shufei.budgetmanage_android.ui.shared.compose.BudgetFormScreenContent
import dev.shufei.budgetmanage_android.ui.shared.compose.CustomSystemUiController
import dev.shufei.budgetmanage_android.ui.shared.compose.Mode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun CreateBudgetScreen(
    navController: NavController,
    snackbarHostState: SnackbarHostState = SnackbarHostState(),
    appScope: CoroutineScope = rememberCoroutineScope(),
    viewModel: CreateBudgetScreenViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()

    CustomSystemUiController()
    BudgetFormScreenContent(
        mode = Mode.CREATE,
        onClickBack = { navController.popBackStack() },
        done = { budget ->
            scope.launch {
                viewModel.addBudget(budget)
                appScope.launch {
                    snackbarHostState.showSnackbar(
                        message = "新しい予算を作成しました",
                        withDismissAction = true
                    )
                }
                navController.popBackStack()
            }
        }
    )
}