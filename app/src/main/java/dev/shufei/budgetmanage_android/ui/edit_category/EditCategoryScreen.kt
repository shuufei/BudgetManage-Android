package dev.shufei.budgetmanage_android.ui.edit_category

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import dev.shufei.budgetmanage_android.ui.shared.compose.CategoryFormScreenContent
import dev.shufei.budgetmanage_android.ui.shared.compose.CustomSystemUiController
import dev.shufei.budgetmanage_android.ui.shared.compose.Mode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun EditCategoryScreen(
    navController: NavController,
    snackbarHostState: SnackbarHostState = SnackbarHostState(),
    appScope: CoroutineScope = rememberCoroutineScope(),
    viewModel: EditCategoryScreenViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val categoryWithBudget by viewModel.categoryWithBudgetStream.collectAsStateWithLifecycle(
        initialValue = null
    )

    CustomSystemUiController()
    categoryWithBudget?.let {
        CategoryFormScreenContent(
            mode = Mode.EDIT,
            budgetId = it.budget.id,
            category = it.category,
            onClickBack = { navController.popBackStack() },
            done = { category ->
                scope.launch {
                    viewModel.update(category)
                    appScope.launch {
                        snackbarHostState.showSnackbar(
                            message = "カテゴリを更新しました",
                            withDismissAction = true
                        )
                    }
                    navController.popBackStack()
                }
            }
        )
    }
}