package dev.shufei.budgetmanage_android.ui.edit_budget

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import dev.shufei.budgetmanage_android.ui.shared.compose.BudgetFormScreenContent
import dev.shufei.budgetmanage_android.ui.shared.compose.CustomSystemUiController
import dev.shufei.budgetmanage_android.ui.shared.compose.Mode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun EditBudgetScreen(
    navController: NavController,
    snackbarHostState: SnackbarHostState = SnackbarHostState(),
    appScope: CoroutineScope = rememberCoroutineScope(),
    viewModel: EditBudgetScreenViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val budget by viewModel.budgetStream.collectAsStateWithLifecycle(initialValue = null)

    CustomSystemUiController()
    if (budget != null) {
        BudgetFormScreenContent(
            budget = budget,
            mode = Mode.EDIT,
            onClickBack = { navController.popBackStack() },
            done = { budget ->
                scope.launch {
                    viewModel.update(budget)
                    appScope.launch {
                        snackbarHostState.showSnackbar(
                            message = "予算を更新しました",
                            withDismissAction = true
                        )
                    }
                    navController.popBackStack()
                }
            }
        )
    }
}
