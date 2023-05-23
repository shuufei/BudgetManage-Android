package dev.shufei.budgetmanage_android.ui.create_category

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import dev.shufei.budgetmanage_android.ui.shared.compose.CategoryFormScreenContent
import dev.shufei.budgetmanage_android.ui.shared.compose.CustomSystemUiController
import dev.shufei.budgetmanage_android.ui.shared.compose.Mode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun CreateCategoryScreen(
    navController: NavController,
    snackbarHostState: SnackbarHostState = SnackbarHostState(),
    appScope: CoroutineScope = rememberCoroutineScope(),
    viewModel: CreateCategoryScreenViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()

    CustomSystemUiController()
    CategoryFormScreenContent(
        mode = Mode.CREATE,
        budgetId = viewModel.budgetId,
        onClickBack = { navController.popBackStack() },
        done = {
            scope.launch {
                viewModel.addCategory(it)
                appScope.launch {
                    snackbarHostState.showSnackbar(
                        message = "新しいカテゴリを作成しました",
                        withDismissAction = true
                    )
                }
                navController.popBackStack()
            }
        }
    )
}